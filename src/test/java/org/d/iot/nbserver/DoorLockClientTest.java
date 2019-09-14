package org.d.iot.nbserver;

import org.d.iot.iotserver.utils.ExceptionUtil;
import org.junit.Test;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class DoorLockClientTest {

  // 登录指令
  byte[] bytes = {0x55, (byte) 0xaa, 0x00, 0x07, 0x01, 0x00, 0x00, 0x01, 0x02, 0x01, 0x00};

  @Test
  public void loginTest() throws IOException {
    Socket socket = new Socket("127.0.0.1", 7000);
    // 当网卡收到关闭连接请求后，等待delay_time
    // 如果在delay_time过程中数据发送完毕，正常四次挥手关闭连接
    // 如果在delay_time过程中数据没有发送完毕，发送RST包关闭连接
    socket.setSoLinger(true, 5000);

    for (int i = 0; i < 50; i++) {
      new Worker(socket, bytes).start();
    }
  }

  private class Worker extends Thread {
    private Socket socket;
    private byte[] msg;

    public Worker(Socket socket, byte[] bytes) {
      this.socket = socket;
      this.msg = bytes;
    }

    @Override
    public void run() {
      try {
        OutputStream out = socket.getOutputStream();
        out.write(bytes);
        out.flush();
        Thread.sleep(20 * 1000);
        socket.close();
      } catch (Exception e) {
        ExceptionUtil.stackTrace(e);
      }
    }
  }
}
