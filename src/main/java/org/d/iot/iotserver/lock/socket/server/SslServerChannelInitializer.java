package org.d.iot.iotserver.lock.socket.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.ssl.util.SelfSignedCertificate;
import org.d.iot.iotserver.lock.config.SslConfig;
import org.springframework.util.ResourceUtils;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLException;
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

  private SslConfig sslConfig;

  public SslServerChannelInitializer(SslConfig sslConfig) {
    this.sslConfig = sslConfig;
  }

  @Override
  protected void initChannel(SocketChannel channel) throws Exception {
    SslContext ctx = initSSLContext1();
    SSLEngine engine = ctx.newEngine(channel.alloc());
    // 注意：此处服务端应该设置为false
    engine.setUseClientMode(false);
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
   * 使用自己生成的证书，初始化SSL上下文
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
    File file = ResourceUtils.getFile(sslConfig.getServerFilePath());
    jks.load(new FileInputStream(file), "sNetty".toCharArray());
    KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("SunX509");
    keyManagerFactory.init(jks, "sNetty".toCharArray());
    return SslContextBuilder.forServer(keyManagerFactory).build();
  }

  /**
   * 使用netty内部的证书，初始化SSL上下文
   *
   * @return
   * @throws CertificateException
   * @throws SSLException
   */
  private SslContext initSSLContext1() throws CertificateException, SSLException {
    SelfSignedCertificate ssc = new SelfSignedCertificate();
    return SslContext.newServerContext(ssc.certificate(), ssc.privateKey());
  }
}
