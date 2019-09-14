package org.d.iot.iotserver.service;

import org.d.iot.iotserver.config.IotServerProperties;
import org.d.iot.iotserver.event.DeviceOfflineEvent;
import org.d.iot.iotserver.event.DeviceOnlineEvent;
import io.netty.channel.ChannelHandlerContext;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.d.iot.iotserver.utils.decode.Hex;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.d.iot.iotserver.socket.message.*;

import java.util.Map;
import java.util.concurrent.*;

/**
 * ClassName: IotDoorLockManager <br/>
 * Description: 门锁管理器 <br/>
 * date: 2019/9/5 23:16<br/>
 * fixme 未做线程安全操作，同时另个请求同一把锁，可能出现问题
 *
 * @author Double <br/>
 * @since JDK 1.8
 */
@Slf4j
public class IotDoorLockManager {
    /**
     * 调用ThreadLocal前, 先调用对应的remove()方法
     * 线程使用完毕之后,并未销毁而是等待下一次调用,这样下一次的调用有可能get到上一次set进去的内容
     * 所以调用前先remove()是正确姿势
     */

    private static Map<Integer, ChannelHandlerContext> contextMap = new ConcurrentHashMap<>();
    private static Map<ChannelHandlerContext, DeviceChannelInfo> deviceMap = new ConcurrentHashMap<>();

    private static ApplicationContext appCtx = null;
    private static IotServerProperties serverProperties = null;

    private static ThreadPoolExecutor executor;

    /**
     * 设置线程池缓存队列的排队策略为FIFO，并且指定缓存队列大小为5
     */
    private static BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(5);

    private static Map<Integer, BaseLockMsg> resultMap = new ConcurrentHashMap<>();
    private static Map<Integer, Object> locks = new ConcurrentHashMap<>();

    public static void init(ApplicationContext ctx, IotServerProperties properties) {
        appCtx = ctx;
        serverProperties = properties;

        executor = new ThreadPoolExecutor(5, 10, 200, TimeUnit.SECONDS, workQueue);

        log.info("manager initialized.");
    }

    public static void deviceRegistered(Integer id, ChannelHandlerContext cxt) {
        contextMap.put(id, cxt);
        deviceMap.put(cxt, new DeviceChannelInfo().setDeviceId(id).setLastActiveTime(System.currentTimeMillis()));
        // 发送设备上线通知
        log.debug("device online event");
        publish(new DeviceOnlineEvent(cxt).setId(id));
    }

    public static void deviceUnRegistered(ChannelHandlerContext cxt) {
        DeviceChannelInfo info = deviceMap.remove(cxt);
        if (info == null) {
            return;
        }
        contextMap.remove(info.getDeviceId());
        // 发送设备下线通知
        log.debug("device offline event");
        publish(new DeviceOfflineEvent(cxt).setId(info.getDeviceId()));
    }

    public static void dataRead(ChannelHandlerContext ctx, BaseLockMsg msg) {

        //输出收到的报文
        byte[] datas = msg.getDatas();
        log.info("packet: {}", Hex.dumpBytes(datas, 0, datas.length, 16, false, false));

//        IotDoorLockManager.send(new DoorBaseLockMsg().setDeviceId(1));

        // 更新最后活跃时间
        DeviceChannelInfo info = deviceMap.get(ctx);
        if (info != null) {
            info.setLastActiveTime(System.currentTimeMillis());
        } else {
            // todo 这里是否需要设置新的信息？
        }

        if (msg instanceof BaseLockLoginMsg) {
            // 登录信息，注册设备
            BaseLockLoginMsg loginMsg = (BaseLockLoginMsg) msg;

            IotDoorLockManager.deviceRegistered(loginMsg.getDeviceId(), ctx);
        } else if (msg instanceof HeartBeatMsgBase) {
            // 心跳信息
        } else if (msg instanceof BaseLockTestMsg) {
            // 测试信息
            log.info("test message read from device {}", msg.getDeviceId());
        } else {
            //执行设备下发指令
            IotDoorLockManager.send(new DoorBaseLockMsg().setDeviceId(msg.getDeviceId()));
            // 设置响应
            deviceResponse(msg);
        }
    }

    private static void deviceResponse(BaseLockMsg response) {
        if (response == null) {
            log.warn("response is null, ignore.");
            return;
        }
        log.debug("response received: {}", response.getDeviceId());
        // 如果lock对象已经不存在，表示已经超时，不处理
        log.debug("locks:{}", locks);
        Object lock = locks.get(response.getDeviceId());
        if (lock != null) {
            synchronized (lock) {
                resultMap.put(response.getDeviceId(), response);
                lock.notify();
            }
        } else {
            log.warn("request was timeout, ignore result: {}", response.getDeviceId());
        }
    }

    /**
     * 发布事件
     */
    private static void publish(ApplicationEvent evt) {
        if (appCtx == null){
            return;
        }
        appCtx.publishEvent(evt);
    }

    /**
     * todo:是否在此添加同步锁以保证线程安全?
     *
     * @param request
     * @return
     */
    //使用Callable+FutureTask获取执行结果
    public static BaseLockMsg send(BaseLockMsg request) {
        ChannelHandlerContext ctx = null;

        try {
            ctx = contextMap.get(request.getDeviceId());
            // sendToWorker(ctx, id, wltData);
            FutureTask<BaseLockMsg> task = new FutureTask(new SyncRequestTask(ctx, request));
            executor.submit(task);
//            get()方法会阻塞当前线程直到任务完成
            //这里需要往返回结果放置信息，用于与异步线程的通信
            resultMap.put(request.getDeviceId(), request);
            return task.get();
        } catch (Exception ex) {
            log.error("send device request exception", ex);
            try {
                ctx.close().sync();
                ctx = null;
            } catch (Exception e) {

            }
            return null;
        } finally {

        }
        // return getWorkerResult(id);
    }

    /**
     * 清理超时没有任何数据来回的设备连接
     * <p>
     * fixme: 这里可能会引起问题。比如：当清理时还有数据交互，会出问题。不过一般不会出现
     */
    public static void cleanTimeoutDevice() {
        if (serverProperties == null) {
            log.debug("manager not initialized, ignore timeout checking.");
            return;
        }
        log.debug("check timeout devices");

        deviceMap.forEach((ctx, info) -> {
            // 检查是否超时
            if (System.currentTimeMillis() - info.getLastActiveTime() > serverProperties.getHeartBeatTimeout()) {
                // 超时了，关闭通道
                log.info("device {} timeout.", info.getDeviceId());
                ctx.close();
            }
        });

        log.debug("live:{}", deviceMap);
    }

    @Data
    @Accessors(chain = true)
    static class DeviceChannelInfo {
        private int deviceId;
        private long lastActiveTime;
    }

    static class SyncRequestTask implements Callable<BaseLockMsg> {

        private BaseLockMsg msg;
        private ChannelHandlerContext ctx;

        public SyncRequestTask(ChannelHandlerContext ctx, BaseLockMsg msg) {
            this.ctx = ctx;
            this.msg = msg;
        }

        @Override
        public BaseLockMsg call() throws Exception {
            // 等待回复
            Object lock = new Object();
            locks.put(msg.getDeviceId(), lock);
            synchronized (lock) {
                //输出收到的报文
                byte[] datas = msg.getDatas();
                log.debug("send packet: {}", Hex.dumpBytes(datas, 0, datas.length, 16, false, false));

                // 发送消息
                this.ctx.write(msg);
                this.ctx.flush();
                log.debug("request {} has sent to device, id: {}", msg.getClass().getName(), msg.getDeviceId());

                // 最多等待1000毫秒，第一次启动的时候会比较慢
                lock.wait(5000);
                locks.remove(msg.getDeviceId());
                BaseLockMsg result = resultMap.remove(msg.getDeviceId());
                if (result == null) {
                    log.debug("response timeout: {}", msg.getDeviceId());
                }
                return result;
            }
        }
    }
}
