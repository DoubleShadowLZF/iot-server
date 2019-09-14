package org.d.iot.iotserver.lock.socket.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * ClassName: IotClientChannelHandler <br>
 * Description: <br>
 * date: 2019/9/14 18:08<br>
 *
 * @author Double <br>
 * @since JDK 1.8
 */
public class IotClientChannelHandler extends SimpleChannelInboundHandler {
  @Override
  protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o)
      throws Exception {}

  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {
    super.channelActive(ctx);
  }

  @Override
  public void channelInactive(ChannelHandlerContext ctx) throws Exception {}

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    super.exceptionCaught(ctx, cause);
  }
}
