package org.d.iot.iotserver.tcp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;
import org.d.iot.iotserver.tcp.message.BaseLockMsg;
import org.d.iot.iotserver.tcp.message.LockMsgBuilder;
import org.d.iot.iotserver.utils.decode.Bytes;

import java.util.List;

/**
 * ClassName: IotMsgDecoder <br/>
 * Description: 信息解码器 <br/>
 * date: 2019/9/5 23:16<br/>
 *
 * @author Double <br/>
 * @since JDK 1.8
 */
@Slf4j
public class IotMsgDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out){
        byte[] pck = new byte[in.readableBytes()];
        in.readBytes(pck);
        // 头部校验帧，2 字节
        int sof = Bytes.bytes2Int(true,0,2,pck);

        // 取长度，2字节
        int len = Bytes.bytes2Int(true, 2, 2,pck);

        // 取协议版本，1 字节
        byte version = pck[4];

        // 取设备id， 3 字节
        int deviceId = Bytes.bytes2Int(true,5,3,pck);

        // 取流向， 1 字节
        byte direction = pck[8];

        // 取指令， 1 字节
        byte cmd = pck[9];

        // 取状态
        byte status = pck[10];

        BaseLockMsg msg = LockMsgBuilder.build(cmd);

        msg.setCmd(cmd);
        msg.setStatus(status);
        msg.setDeviceId(deviceId);
        msg.setDirection(direction);
        msg.setVersion(version);
        log.debug("连接进来并处理后的数据:{}",msg);
        out.add(msg);
    }

}
