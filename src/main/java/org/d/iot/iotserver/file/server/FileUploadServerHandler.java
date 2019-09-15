package org.d.iot.iotserver.file.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Map;

public class FileUploadServerHandler extends ChannelInboundHandlerAdapter {
  private int byteRead;
  private volatile long start = 0;
  public RandomAccessFile randomAccessFile;
  public RandomAccessFile randomAccessFile1;
  private String file_dir = "e:";
  private File file;
  private FileQo ef;
  private Map map;
  private long startTime = 0;
  private long endTime = 0;
  private long startsize = 0;

  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) {
    try {
      if (msg instanceof FileQo) {
        map = new HashMap();
        ef = (FileQo) msg;
        byte[] bytes = ef.getBytes();
        byteRead = ef.getEndPos();
        String status = ef.getStatus();
        String fileName = ef.getFileName();
        String path = file_dir + File.separator + fileName;
        String temppath =
            file_dir
                + File.separator
                + fileName.substring(0, fileName.lastIndexOf("."))
                + "_temp"
                + fileName.substring(fileName.lastIndexOf("."));
        file = new File(path);
        File tempfile = new File(temppath);
        // 只有第一次才会进入这个方法
        if (start == 0) {
          // 临时文件存在，继续上传
          if (tempfile.exists()) {
            System.out.println("继续上传!!!!!!");
            // 获取文件已经传入的部分的大小，把指针移到这，继续上传
            start = (long) file.length();
            map.put("start", start);
            map.put("status", "temp");
            ctx.writeAndFlush(map);
            // 文件存在，且临时文件不存在，服务端已存在文件不允许重复上传
          } else if (file.exists() && !tempfile.exists()) {
            // 标志位
            status = "exist";
            map.put("status", status);
            ctx.writeAndFlush(map);
            // 文件不存在，开始上传
          } else {
            System.out.println("开始上传文件");
            writeFile(file, fileName, bytes, ctx, byteRead, status);
          }
          // 客户端已读完，关闭客户端
        } else if (start == ef.getFileSize()) {
          tempfile.delete();
          ctx.close();
        } else {
          writeFile(file, fileName, bytes, ctx, byteRead, status);
        }
      }
    } catch (Exception e) {

    }
  }

  public void writeFile(
      File file,
      String fileName,
      byte[] bytes,
      ChannelHandlerContext ctx,
      int byteRead,
      String status)
      throws IOException {
    randomAccessFile = new RandomAccessFile(file, "rws");
    map = new HashMap();
    String path1 =
        file_dir
            + File.separator
            + fileName.substring(0, fileName.lastIndexOf("."))
            + "_temp"
            + fileName.substring(fileName.lastIndexOf("."));
    File f = new File(path1);
    randomAccessFile1 = new RandomAccessFile(f, "rws");
    randomAccessFile.seek(start);
    // 开始写文件
    randomAccessFile.write(bytes);
    randomAccessFile1.seek(start);
    randomAccessFile1.write(bytes);
    start = start + byteRead;
    randomAccessFile1.close();
    randomAccessFile.close();
    if (byteRead > 0) {
      map.put("start", start);
      map.put("endTime", endTime);
      ctx.writeAndFlush(map);
    }
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
    cause.printStackTrace();
    ctx.close();
  }
}
