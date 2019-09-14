package org.d.iot.iotserver.socket.server;

import org.d.iot.iotserver.service.IotDoorLockManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * ClassName: LockMsgBuDevHeartChecker <br/>
 * Description: 心跳检测器 <br/>
 * date: 2019/9/5 23:16<br/>
 *
 * @author Double <br/>
 * @since JDK 1.8
 */
@Component
public class DevHeartBeatChecker {

    @Scheduled(fixedDelay = 5000)
    public void checkDevStatus() {
        IotDoorLockManager.cleanTimeoutDevice();
    }
}
