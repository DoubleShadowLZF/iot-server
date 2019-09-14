package org.d.iot.iotserver.lock.controller;

import lombok.extern.slf4j.Slf4j;
import org.d.iot.iotserver.lock.service.IotDoorLockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * ClassName: 门锁 web 路由 <br>
 * Description: 信息构建器 <br>
 * date: 2019/9/5 23:16<br>
 *
 * @author Double <br>
 * @since JDK 1.8
 */
@RestController
@RequestMapping("/lock")
@Slf4j
public class IotDoorLockController {

  @Autowired private IotDoorLockService service;

  /**
   * 查询门锁状态
   *
   * @param id 门锁id
   * @return 设备状态 0 成功 -1 失败
   */
  @GetMapping("/{id}")
  public Object qryDoorLockStatus(@PathVariable(name = "id") Integer id) {
    if (id == null || id < 0) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    int result = service.findDoorLockStatusMessage(id);
    log.debug("deviceId:{}", result);
    return ResponseEntity.ok(result);
  }

  /**
   * 用户向 {id} 门锁发起上锁动作
   *
   * @param id 门锁 id
   * @return 设备状态 0 成功 -1 失败
   */
  @PutMapping("/{id}/lock")
  public Object lock(@PathVariable(name = "id") Integer id) {
    if (id == null || id < 0) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    int result = service.lock(id);
    log.debug("deviceId:{}", result);
    return ResponseEntity.ok(result);
  }

  /**
   * 用户向 {id} 门锁发起开锁动作
   *
   * @param id 门锁 id
   * @return 设备状态 0 成功 -1 失败
   */
  @PutMapping("/{id}/unlock")
  public Object unLock(@PathVariable(name = "id") Integer id) {
    if (id == null || id < 0) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    int result = service.unLock(id);
    log.debug("deviceId:{}", result);
    return ResponseEntity.ok(result);
  }

  /**
   * 用户向 {id} 门锁发起点亮指示灯的操作
   *
   * @param id 门锁id
   * @return 设备状态 0 成功 -1 失败
   */
  @PutMapping("/{id}/ledon")
  public Object ledOn(@PathVariable(name = "id") Integer id) {
    if (id == null || id < 0) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    int result = service.turnOnLed(id);
    return ResponseEntity.ok(result);
  }

  /**
   * 用户向 {id} 门锁发送熄灭指定的操作
   *
   * @param id 门锁id
   * @return 设备状态 0 成功 -1 失败
   */
  @PutMapping("/{id}/ledoff")
  public Object ledOff(@PathVariable(name = "id") Integer id) {
    if (id == null || id < 0) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    int result = service.turnOffLed(id);

    return ResponseEntity.ok(result);
  }
}
