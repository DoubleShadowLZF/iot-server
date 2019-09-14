package org.d.iot.iotserver.service;


/**
 * ClassName: IotDoorLockService <br/>
 * Description: 门锁服务 <br/>
 * date: 2019/9/5 23:16<br/>
 *
 * @author Double <br/>
 * @since JDK 1.8
 */
public interface IotDoorLockService {

    int findDoorLockStatusMessage(int devId);

    int lock(int devId);

    int unLock(int devId);

    int turnOnLed(int devId);

    int turnOffLed(int devId);
    
}
