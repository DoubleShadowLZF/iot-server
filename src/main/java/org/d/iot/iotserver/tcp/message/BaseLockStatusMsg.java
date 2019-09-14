/**
 * @Date: 2019/3/22 15:25
 * 作者：caisj
 * 版权：广州市森锐科技股份有限公司
 */
package org.d.iot.iotserver.tcp.message;

/**
 * ClassName: BaseLockStatusMsg <br/>
 * Description: 门锁状态动作 <br/>
 * date: 2019/9/5 23:16<br/>
 *
 * @author Double <br/>
 * @since JDK 1.8
 */
public class BaseLockStatusMsg extends BaseLockMsg {

    public BaseLockStatusMsg() {
        setCmd((byte) 0x07);
        setDirection((byte)0x01);
        setVersion((byte)0x01);
    }

}
