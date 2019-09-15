package org.d.iot.iotserver.file.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.d.iot.iotserver.file.server.FileQo;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Map;

public class FileUploadClientHandler extends ChannelInboundHandlerAdapter {
  private int byteRead;
  private volatile long start = 0;
  float size;
  int length = 1024 * 1024 * 24;
  private volatile int lastLength = 0;
  public RandomAccessFile randomAccessFile;
  private FileQo fileUploadFile;
  private byte[] bytes;
  private long startTime = 0;
  private long endTime = 0;

  public FileUploadClientHandler(FileQo ef) {
    if (ef.getFile().exists()) {
      if (!ef.getFile().isFile()) {
        System.out.println("Not a file :" + ef.getFile());
        return;
      }
    }
    this.fileUploadFile = ef;
  }

  @Override
  public void channelActive(ChannelHandlerContext ctx) {
    try {
      // 只读形式打开文件
      randomAccessFile = new RandomAccessFile(fileUploadFile.getFile(), "r");
      // seek(0)把指针移到初始位置，seek(m)把指针移到m字节处
      randomAccessFile.seek(fileUploadFile.getStarPos());
      if (randomAccessFile.length() < (1024 * 1024 * 120)
          && randomAccessFile.length() > (1024 * 10)) {
        lastLength = (int) randomAccessFile.length() / 10;
        bytes = new byte[lastLength];
      } else if (randomAccessFile.length() >= (1024 * 1024 * 120)) {
        bytes = new byte[length];
      } else {
        bytes = new byte[(int) randomAccessFile.length()];
      }
      // 文件大小
      size = (float) (randomAccessFile.length()) / 1024 / 1024;
      // 把文件读到内存中
      if ((byteRead = randomAccessFile.read(bytes)) != -1) {
        fileUploadFile.setEndPos(byteRead);
        fileUploadFile.setBytes(bytes);
        ctx.writeAndFlush(fileUploadFile);
        randomAccessFile.close();
      } else {
        System.out.println("文件已经读完");
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException i) {
      i.printStackTrace();
    }
  }

  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    startTime = System.nanoTime();
    Map map = (Map) msg;
    if (map.get("start") != null) {
      start = Long.parseLong(map.get("start").toString());
      String status = "";
      if (map.get("status") != null) {
        status = map.get("status").toString();
      }
      if ("temp".equals(status)) {
        System.out.println("续传开始！！！！");
      }
      if (start != -1) {
        randomAccessFile = new RandomAccessFile(fileUploadFile.getFile(), "r");
        // 把指针移到start处
        randomAccessFile.seek(start);
        int a = (int) (randomAccessFile.length() - start);
        int b = 0;
        if (randomAccessFile.length() < (120 * 1024 * 1024)
            && randomAccessFile.length() > (10 * 1024)) {
          b = (int) (randomAccessFile.length() / 10);
        } else if (randomAccessFile.length() >= (1024 * 1024 * 120)) {
          b = length;
        } else {
          b = (int) randomAccessFile.length();
        }
        if (a != 0) {
          if (a < b && a > 0) {
            lastLength = a;
          }
          if (a < 0) {
            lastLength = b;
          }
          if (a > b && a > 0) {
            lastLength = b;
          }
          bytes = new byte[lastLength];
          if ((byteRead = randomAccessFile.read(bytes)) != -1
              && (randomAccessFile.length() - start) > 0) {
            fileUploadFile.setEndPos(byteRead);
            fileUploadFile.setBytes(bytes);
            try {
              ctx.writeAndFlush(fileUploadFile);
              endTime = System.nanoTime();
              double time = (double) ((double) (endTime - startTime) / 1000 / 1000 / 1000);
              double filesize = (double) ((double) (bytes.length)) / 1024 / 1024;
              //					startTime=endTime;
              //					System.out.println("时间为："+time);
              //					System.out.println("大小为："+filesize);
              double speed = filesize / time;
              if (time != 0) {
                //						time=(double)bytes.length/(double)randomAccessFile.length()*100;
                System.out.println("速度为：" + speed + "M/s");
              }
              randomAccessFile.close();
            } catch (Exception e) {
              e.printStackTrace();
            }
          } else {
            randomAccessFile.close();
          }
        } else {
          randomAccessFile.close();
          fileUploadFile.setStatus("q");
          ctx.writeAndFlush(fileUploadFile);
        }
      }
    } else {
      if ("exist".equals(map.get("status"))) {
        System.out.println("文件已存在，禁止重复上传");
      }
    }
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
    cause.printStackTrace();
    ctx.close();
  }
}
