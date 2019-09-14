package org.d.iot.iotserver.socket.message;

/**
 * ClassName: DoorBaseLockMsg <br/>
 * Description: 门锁关闭动作 <br/>
 * date: 2019/9/5 23:16<br/>
 *
 * @author Double <br/>
 * @since JDK 1.8
 */
public class DoorBaseLockMsg extends BaseLockMsg {

    public DoorBaseLockMsg() {
        setCmd((byte) 0x03);
        setDirection((byte)0x01);
        setVersion((byte)0x01);
    }
}
