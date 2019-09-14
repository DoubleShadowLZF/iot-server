package org.d.iot.iotserver.socket.message;

/**
 * ClassName: LockMsgBuilder <br/>
 * Description: 信息构建器 <br/>
 * date: 2019/9/5 23:16<br/>
 *
 * @author Double <br/>
 * @since JDK 1.8
 */
public class LockMsgBuilder {

    public static BaseLockMsg build(byte cmd) {
        switch (cmd) {
        case 0x01:
            return new BaseLockLoginMsg();
        case 0x02:
            return new DoorUnBaseLockMsg();
        case 0x03:
            return new DoorBaseLockMsg();
        case 0x04:
            return new LedOnMsgBase();
        case 0x05:
            return new LedOffMsgBase();
        case 0x06:
            return new HeartBeatMsgBase();
        case 0x07:
            return new BaseLockStatusMsg();
        default:
            return new BaseLockMsg() {
            };
        }
    }
}
