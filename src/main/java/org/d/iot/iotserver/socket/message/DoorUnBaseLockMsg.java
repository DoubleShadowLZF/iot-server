package org.d.iot.iotserver.socket.message;

/**
 * ClassName: LockUnlockMsg <br/>
 * Description: 门锁开锁动作 <br/>
 * date: 2019/9/5 23:16<br/>
 *
 * @author Double <br/>
 * @since JDK 1.8
 */
public class DoorUnBaseLockMsg extends BaseLockMsg {

    public DoorUnBaseLockMsg() {
        setCmd((byte) 0x02);
        setDirection((byte)0x01);
        setVersion((byte)0x01);
    }
}
