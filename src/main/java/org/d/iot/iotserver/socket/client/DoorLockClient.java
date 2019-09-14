package org.d.iot.iotserver.socket.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.d.iot.iotserver.config.IotServerProperties;
import org.d.iot.iotserver.socket.message.BaseLockMsg;
import org.d.iot.iotserver.socket.message.LedOnMsgBase;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * ClassName: DoorLockClient <br>
 * Description: 模拟门锁客户端<br>
 * date: 2019/9/14 10:34<br>
 *
 * @author Double <br>
 * @since JDK 1.8
 */
@Slf4j
// @Component
public class DoorLockClient {

  private byte[] msg = {0x55, (byte) 0xaa, 0x00, 0x07, 0x01, 0x00, 0x00, 0x01, 0x02, 0x01, 0x00};

  private Channel clientChannel;

  public DoorLockClient(@Autowired IotServerProperties properties) {
    Channel channel = null;
    EventLoopGroup group;
    Class<? extends Channel> channelClass = NioSocketChannel.class;
    group = new NioEventLoopGroup();
    Bootstrap b = new Bootstrap();
    b.group(group).channel(channelClass);
    b.option(ChannelOption.SO_KEEPALIVE, true);
    b.option(ChannelOption.TCP_NODELAY, true);
    b.option(ChannelOption.SO_REUSEADDR, true);
    b.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000);
    try {
      b.handler(new SslClientChannelInitializer());
      this.clientChannel = b.connect("127.0.0.1", properties.getPort()).sync().channel();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * 向服务端发送信息
   *
   * @param msg 锁数据帧
   */
  public void sendMessage(BaseLockMsg msg) {
    this.clientChannel.writeAndFlush(msg);
  }

  public static void main(String[] args) {
    IotServerProperties properties = new IotServerProperties();
    properties.setPort(7000);
    DoorLockClient doorLockClient = new DoorLockClient(properties);
    doorLockClient.sendMessage(new LedOnMsgBase());
  }
}
