package org.d.iot.iotserver.socket.message;

/**
 * ClassName: HeartBeatMsgBase <br/>
 * Description: 心跳信息 <br/>
 * date: 2019/9/5 23:16<br/>
 *
 * @author Double <br/>
 * @since JDK 1.8
 */
public class HeartBeatMsgBase extends BaseLockMsg {

    public HeartBeatMsgBase() {
        setCmd((byte) 0x06);
    }

}
