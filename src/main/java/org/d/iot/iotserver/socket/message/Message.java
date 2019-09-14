package org.d.iot.iotserver.socket.message;

/**
 * ClassName: Message <br/>
 * Description: 信息 <br/>
 * date: 2019/9/5 23:16<br/>
 *
 * @author Double <br/>
 * @since JDK 1.8
 */
public interface Message {

    void setDatas(byte[] datas);

    byte[] getDatas();

}
