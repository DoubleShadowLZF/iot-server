package org.d.iot.iotserver.lock.socket.message;

import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.d.iot.iotserver.utils.ByteUtil;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

/**
 * ClassName: BaseLockMsg <br>
 * Description: 门锁基础信息类 <br>
 * date: 2019/9/5 23:16<br>
 *
 * @author Double <br>
 * @since JDK 1.8
 */
@Data
@Slf4j
@Accessors(chain = true)
public abstract class BaseLockMsg implements Message {

  /** 1.协议版本 0x01 固定 2.数据流向 0x01 服务器发送给设备 ; 0x02 设备发送给服务器 */
  private byte cmd;
  /** 标识设备动作 */
  private byte status;
  /** 协议版本 */
  private byte version;

  private int deviceId;
  /** 数据流向 */
  private byte direction;

  @Override
  public byte[] getDatas() {
    try {
      ByteArrayOutputStream bout = new ByteArrayOutputStream();
      DataOutputStream out = new DataOutputStream(bout);

      // 写入头部
      out.write(new byte[] {(byte) 0x55, (byte) 0xaa});

      // 写入长度，现在的长度都是固定的 7
      out.write(ByteUtil.int2Bytes(7, true), 2, 2);

      // 写入协议版本     目前协议版本固定为0x01
      out.writeByte(getVersion());

      // 写入设备ID，3字节
      out.write(ByteUtil.int2Bytes(getDeviceId(), true), 1, 3);

      // 写入数据流向    服务器流向客户端固定为0x01   客户端向服务器流固定为0x02
      out.writeByte(getDirection());

      // 写入指令
      out.writeByte(getCmd());

      // 写入状态
      out.writeByte(getStatus());

      return bout.toByteArray();
    } catch (Exception ex) {
      return null;
    }
  }

  @Override
  public void setDatas(byte[] datas) {
    // 0，1 字节是头部，忽略

    // 取长度
    byte[] lenBytes = {datas[2], datas[3]};

    @SuppressWarnings("noused")
    int len = ByteUtil.bytes2Int(false, 0, 2, lenBytes);

    // 取协议版本
    this.version = datas[4];

    // 取设备id
    byte[] deviceIdBytes = {datas[5], datas[6], datas[7]};
    this.deviceId = ByteUtil.bytes2Int(false, 0, 3, deviceIdBytes);

    // 取流向
    this.direction = datas[8];

    // 取指令
    this.cmd = datas[9];

    // 取状态
    this.status = datas[10];
  }
}
