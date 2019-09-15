package org.d.iot.iotserver.file.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import org.d.iot.iotserver.file.server.FileQo;

import java.io.File;
import java.io.FileInputStream;
import java.nio.channels.FileChannel;

/** */
public class UploadClient {
  public void connect(int port, String host, final FileQo fileUploadFile) throws Exception {
    EventLoopGroup group = new NioEventLoopGroup();
    try {
      Bootstrap b = new Bootstrap();
      b.group(group)
          .channel(NioSocketChannel.class)
          .option(ChannelOption.TCP_NODELAY, true)
          .handler(
              new ChannelInitializer<Channel>() {
                @Override
                protected void initChannel(Channel ch) throws Exception {
                  ch.pipeline().addLast(new ObjectEncoder());
                  ch.pipeline()
                      .addLast(
                          new ObjectDecoder(ClassResolvers.weakCachingConcurrentResolver(null)));
                  ch.pipeline().addLast(new FileUploadClientHandler(fileUploadFile));
                }
              });
      ChannelFuture f = b.connect(host, port).sync();
      f.channel().closeFuture().sync();
    } finally {
      group.shutdownGracefully();
    }
  }

  public static void main(String[] args) {
    int port = 8080;
    if (args != null && args.length > 0) {
      try {
        port = Integer.valueOf(args[0]);
      } catch (NumberFormatException e) {
        e.printStackTrace();
      }
    }
    try {
      FileQo uploadFile = new FileQo();
      File file = new File("D:\\360极速浏览器下载\\GhostXP_SP3_Deepin_2018.iso");
      if (!file.exists()) {
        System.out.println("文件不存在，请确定路径是否正确");
        return;
      }
      String fileName = file.getName();
      FileInputStream fis = new FileInputStream(file);
      FileChannel fc = fis.getChannel();
      uploadFile.setFile(file);
      uploadFile.setFileName(fileName);
      uploadFile.setStarPos(0);
      uploadFile.setFileSize(fc.size());
      new UploadClient().connect(port, "127.0.0.1", uploadFile);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
