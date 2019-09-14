package org.d.iot.iotserver.socket.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.SslHandler;
import org.springframework.util.ResourceUtils;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLEngine;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

/**
 * ClassName: SslClientChannelInitializer <br>
 * Description: <br>
 * date: 2019/9/14 10:08<br>
 *
 * @author Double <br>
 * @since JDK 1.8
 */
public class SslServerChannelInitializer extends ChannelInitializer<SocketChannel> {

  @Override
  protected void initChannel(SocketChannel channel) throws Exception {
    SslContext ctx = initSSLContext();
    SSLEngine engine = ctx.newEngine(channel.alloc());
    // 注意：此处服务端应该设置为false
    engine.setUseClientMode(false);
    channel
        .pipeline()
        .addFirst("ssl", new SslHandler(engine))
        .addLast("decoder", new IotMsgDecoder())
        .addLast("encoder", new IotMsgEncoder())
        .addLast(new IotChannelHandler());
  }

  /**
   * 初始化SSL上下文
   *
   * @return SSL上下文
   * @throws NoSuchAlgorithmException
   * @throws IOException
   * @throws KeyStoreException
   * @throws CertificateException
   */
  private SslContext initSSLContext()
      throws NoSuchAlgorithmException, IOException, KeyStoreException, CertificateException,
          UnrecoverableKeyException {
    KeyStore jks = KeyStore.getInstance("JKS");
    File file = ResourceUtils.getFile("classpath:ssl/server/sChat.jks");
    jks.load(new FileInputStream(file), "sNetty".toCharArray());
    KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("SunX509");
    keyManagerFactory.init(jks, "sNetty".toCharArray());
    return SslContextBuilder.forServer(keyManagerFactory).build();
  }
}
