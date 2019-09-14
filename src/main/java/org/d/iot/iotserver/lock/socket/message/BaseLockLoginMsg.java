package org.d.iot.iotserver.lock.socket.message;

/**
 * ClassName: BaseLockLoginMsg <br>
 * Description: 门锁上线动作<br>
 * date: 2019/9/5 23:16<br>
 *
 * @author Double <br>
 * @since JDK 1.8
 */
public class BaseLockLoginMsg extends BaseLockMsg {

  public BaseLockLoginMsg() {
    setCmd((byte) 0x01);
  }
}
