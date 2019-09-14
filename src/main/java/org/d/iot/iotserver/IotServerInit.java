package org.d.iot.iotserver;

import org.d.iot.iotserver.lock.config.IotServerProperties;
import org.d.iot.iotserver.lock.config.SslConfig;
import org.d.iot.iotserver.lock.socket.client.DoorLockClient;
import org.d.iot.iotserver.lock.socket.message.LedOnMsgBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * ClassName: IotServerInit <br>
 * Description: <br>
 * date: 2019/9/5 22:17<br>
 *
 * @author Double <br>
 * @since JDK 1.8
 */
@Component
public class IotServerInit implements ApplicationRunner {

  @Autowired private ApplicationContext context;

  @Autowired private IotServerProperties properties;

  @Autowired private SslConfig sslConfig;

  /**
   * 初始化线程池
   *
   * @param args
   * @throws Exception
   */
  @Override
  public void run(ApplicationArguments args) throws Exception {
    // 门锁管理器
    //    IotDoorLockManager.init(context, properties);

    TimeUnit.SECONDS.sleep(3);
    // 由于服务端和客户端是在同一个工程里面，在初始化时，进行校验
    DoorLockClient doorLockClient = new DoorLockClient(properties, sslConfig);
    doorLockClient.init();
    doorLockClient.sendMessage(new LedOnMsgBase());
  }
}
