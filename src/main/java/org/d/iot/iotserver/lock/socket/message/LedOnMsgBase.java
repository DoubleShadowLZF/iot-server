package org.d.iot.iotserver.lock.socket.message;

/**
 * ClassName: LedOnMsgBase <br>
 * Description: 指示灯开始动作 <br>
 * date: 2019/9/5 23:16<br>
 *
 * @author Double <br>
 * @since JDK 1.8
 */
public class LedOnMsgBase extends BaseLockMsg {

  public LedOnMsgBase() {
    setCmd((byte) 0x04);
  }
}
