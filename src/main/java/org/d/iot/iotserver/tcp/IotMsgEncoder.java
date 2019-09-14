package org.d.iot.iotserver.tcp;

import org.d.iot.iotserver.tcp.message.BaseLockMsg;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * ClassName: IotMsgEncoder <br/>
 * Description: 信息编码器 <br/>
 * date: 2019/9/5 23:16<br/>
 *
 * @author Double <br/>
 * @since JDK 1.8
 */
public class IotMsgEncoder extends MessageToByteEncoder<BaseLockMsg> {

    @Override
    protected void encode(ChannelHandlerContext ctx, BaseLockMsg msg, ByteBuf out) {
        out.writeBytes(msg.getDatas());
    }

}
