package org.d.iot.iotserver.socket.client;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.SslHandler;
import org.d.iot.iotserver.socket.server.IotChannelHandler;
import org.d.iot.iotserver.socket.server.IotMsgDecoder;
import org.d.iot.iotserver.socket.server.IotMsgEncoder;
import org.springframework.util.ResourceUtils;

import javax.net.ssl.SSLEngine;
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

  @Override
  protected void initChannel(SocketChannel channel) throws Exception {
    SslContext ctx = initSSLContext();
    SSLEngine engine = ctx.newEngine(channel.alloc());
    // 注意：此处客户端应该设置为true
    engine.setUseClientMode(true);
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
      throws NoSuchAlgorithmException, IOException, KeyStoreException, CertificateException {
    KeyStore keyStore = KeyStore.getInstance("JKS");
    File file = ResourceUtils.getFile("classpath:ssl/client/cChat.jks");
    keyStore.load(new FileInputStream(file), "cNetty".toCharArray());
    TrustManagerFactory tf = TrustManagerFactory.getInstance("SunX509");
    tf.init(keyStore);
    return SslContextBuilder.forClient().trustManager(tf).build();
  }
}
