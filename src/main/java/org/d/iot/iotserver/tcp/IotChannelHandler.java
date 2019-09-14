package org.d.iot.iotserver.tcp;

import org.d.iot.iotserver.service.IotDoorLockManager;
import org.d.iot.iotserver.tcp.message.BaseLockMsg;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * ClassName: IotChannelHandler <br/>
 * Description: 心跳处理器 <br/>
 * date: 2019/9/5 23:16<br/>
 *
 * @author Double <br/>
 * @since JDK 1.8
 */
@Slf4j
public class IotChannelHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg){
        if (msg instanceof BaseLockMsg) {
            IotDoorLockManager.dataRead(ctx, (BaseLockMsg) msg);
        } else {
            log.debug("unkown data read from device: {}", msg);
        }
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) {

        log.debug("new connection registered");
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx){
        log.debug("new connection unregistered");
        IotDoorLockManager.deviceUnRegistered(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.error("exception caught,", cause);
    }

}
