package org.d.iot.iotserver.tcp.message;

/**
 * ClassName: BaseLockTestMsg <br/>
 * Description: 门锁测试动作 <br/>
 * date: 2019/9/5 23:16<br/>
 *
 * @author Double <br/>
 * @since JDK 1.8
 */
public class BaseLockTestMsg extends BaseLockMsg {

    public BaseLockTestMsg() {
        setCmd((byte) 0xff);
    }

}
