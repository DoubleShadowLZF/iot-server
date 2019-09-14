package org.d.iot.iotserver.service.impl;

import org.d.iot.iotserver.contant.TCP;
import org.d.iot.iotserver.service.IotDoorLockManager;
import org.d.iot.iotserver.service.IotDoorLockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.d.iot.iotserver.tcp.message.*;

/**
 * ClassName: IotDoorLockServiceImpl <br/>
 * Description: 门锁服务实现类 <br/>
 * date: 2019/9/5 23:16<br/>
 *
 * @author Double <br/>
 * @since JDK 1.8
 */
@Slf4j
@Service
public class IotDoorLockServiceImpl implements IotDoorLockService {

    @Override
    public int findDoorLockStatusMessage(int devId) {
        BaseLockMsg result = IotDoorLockManager.send(new BaseLockStatusMsg().setDeviceId(devId));
        if (result == null) {
            return TCP.OVERTIME;
        }
        return result.getStatus();
    }

    @Override
    public int lock(int devId) {
        BaseLockMsg result = IotDoorLockManager.send(new DoorBaseLockMsg().setDeviceId(devId));
        if (result == null) {
            return TCP.OVERTIME;
        }

        return result.getStatus();
    }

    @Override
    public int unLock(int devId) {
        BaseLockMsg result = IotDoorLockManager.send(new DoorUnBaseLockMsg().setDeviceId(devId));

        if (result == null) {
            return TCP.OVERTIME;
        }

        return result.getStatus();
    }

    @Override
    public int turnOffLed(int devId) {
        BaseLockMsg result = IotDoorLockManager.send(new LedOffMsgBase().setDeviceId(devId));
        if (result == null) {
            return TCP.OVERTIME;
        }

        return result.getStatus();
    }

    @Override
    public int turnOnLed(int devId) {
        BaseLockMsg result = IotDoorLockManager.send(new LedOnMsgBase().setDeviceId(devId));
        if (result == null) {
            return TCP.OVERTIME;
        }

        return result.getStatus();
    }


}
