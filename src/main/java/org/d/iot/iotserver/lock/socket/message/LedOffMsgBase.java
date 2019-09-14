package org.d.iot.iotserver.lock.socket.message;

/**
 * ClassName: LedOffMsgBase <br>
 * Description: 指示灯关闭动作 <br>
 * date: 2019/9/5 23:16<br>
 *
 * @author Double <br>
 * @since JDK 1.8
 */
public class LedOffMsgBase extends BaseLockMsg {

  public LedOffMsgBase() {
    setCmd((byte) 0x05);
  }
}
