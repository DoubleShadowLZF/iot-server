package org.d.iot.iotserver.lock.socket.client;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import org.d.iot.iotserver.lock.config.SslConfig;
import org.d.iot.iotserver.lock.socket.server.IotMsgDecoder;
import org.d.iot.iotserver.lock.socket.server.IotMsgEncoder;
import org.d.iot.iotserver.lock.socket.server.IotReadChannelHandler;
import org.springframework.util.ResourceUtils;

import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLException;
import javax.net.ssl.TrustManagerFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

/**
 * ClassName: SslClientChannelInitializer <br>
 * Description: <br>
 * date: 2019/9/14 10:08<br>
 *
 * @author Double <br>
 * @since JDK 1.8
 */
public class SslClientChannelInitializer extends ChannelInitializer<SocketChannel> {

  private SslConfig sslConfig;

  public SslClientChannelInitializer(SslConfig sslConfig) {
    this.sslConfig = sslConfig;
  }

  /**
   * 使用netty内部的证书
   *
   * @param channel
   * @throws Exception
   */
  @Override
  protected void initChannel(SocketChannel channel) throws Exception {
    SslContext ctx = initSSLContext1();
    SSLEngine engine = ctx.newEngine(channel.alloc());
    // 注意：此处客户端应该设置为true
    engine.setUseClientMode(true);
    channel
        .pipeline()
        .addLast("decoder", new IotMsgDecoder())
        .addLast("encoder", new IotMsgEncoder())
        .addLast(new IotReadChannelHandler());
    if (sslConfig.isUseSsl()) {
      channel.pipeline().addFirst("ssl", new SslHandler(engine));
    }
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
      throws NoSuchAlgorithmException, IOException, KeyStoreException, CertificateException {
    KeyStore keyStore = KeyStore.getInstance("JKS");
    File file = ResourceUtils.getFile(sslConfig.getClientFilePath());
    keyStore.load(new FileInputStream(file), "cNetty".toCharArray());
    TrustManagerFactory tf = TrustManagerFactory.getInstance("SunX509");
    tf.init(keyStore);
    return SslContextBuilder.forClient().trustManager(tf).build();
  }

  private SslContext initSSLContext1() throws SSLException {
    return SslContext.newClientContext(InsecureTrustManagerFactory.INSTANCE);
  }
}
