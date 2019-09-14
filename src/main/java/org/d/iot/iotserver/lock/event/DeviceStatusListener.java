package org.d.iot.iotserver.lock.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * ClassName: DeviceStatusListener <br>
 * Description: 设备状态变化监听器 <br>
 * date: 2019/9/5 23:16<br>
 *
 * @author Double <br>
 * @since JDK 1.8
 */
@Slf4j
@Component
public class DeviceStatusListener {

  @EventListener
  public void online(DeviceOnlineEvent evt) {
    log.debug("device online, id: {}", evt.getId());
  }

  @EventListener
  public void offline(DeviceOfflineEvent evt) {
    log.debug("device offline, id: {}", evt.getId());
  }
}
