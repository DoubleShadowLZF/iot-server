package org.d.iot.iotserver.lock.event;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.context.ApplicationEvent;

/**
 * ClassName: DeviceEvent <br>
 * Description: 设备事件 <br>
 * date: 2019/9/5 23:16<br>
 *
 * @author Double <br>
 * @since JDK 1.8
 */
@Data
@Accessors(chain = true)
public class DeviceEvent extends ApplicationEvent {

  protected int id;

  public DeviceEvent(Object src) {
    super(src);
  }
}
