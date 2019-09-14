package org.d.iot.iotserver.socket.server;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import org.d.iot.iotserver.config.IotServerProperties;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.d.iot.iotserver.config.init.SslChannelInitializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.net.ssl.KeyManagerFactory;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * ClassName: IotService <br/>
 * Description: IOT 服务 <br/>
 * date: 2019/9/5 23:16<br/>
 *
 * @author Double <br/>
 * @since JDK 1.8
 */
@Component
@Slf4j
public class IotService {


    //服务端实现部分
    
    private IotServerProperties properties;

    public IotService(@Autowired IotServerProperties properties) {
        this.properties = properties;

        // (1)、 初始化用于Acceptor的主"线程池"以及用于I/O工作的从"线程池"；//实际不是线程池而是可复用线程
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        //负责读取数据的线程，主要用于读取数据以及业务逻辑处理
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        ThreadFactory factory = new ThreadFactoryBuilder().setNameFormat("server-pool-%d").build();

        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(1024), factory, new ThreadPoolExecutor.AbortPolicy());
        executor.execute(() -> {
            try {
                //(2)、 初始化ServerBootstrap实例， 此实例是netty服务端应用开发的入口
                ServerBootstrap server = new ServerBootstrap();
                //(3)、 通过ServerBootstrap的group方法，设置（1）中初始化的主从"线程池"；
                server.group(bossGroup, workerGroup)
                        //(4)、 指定通道channel的类型，由于是服务端，故而是NioServerSocketChannel；
                        .channel(NioServerSocketChannel.class)
                        .childHandler(
                                //(6)、 设置子通道也就是SocketChannel的处理器， 其内部是实际业务开发的"主战场"
                                new SslChannelInitializer(initSSLContext())
                        )
                        // (7)、 配置ServerSocketChannel的选项
                        .option(ChannelOption.SO_BACKLOG, 128)
                        //(8)、 配置子通道也就是SocketChannel的选项
                        .childOption(ChannelOption.SO_KEEPALIVE, true)
                        //接收和发送的缓冲区 32字节
                        .childOption(ChannelOption.SO_RCVBUF, 16)
                        // 绑定端口，开始接收进来的连接
                        .childOption(ChannelOption.TCP_NODELAY, true);
                // ((9)、 绑定并侦听某个端口
                ChannelFuture cf = server.bind(this.properties.getPort()).sync();

                log.info("nb server started, listen on port: {}", this.properties.getPort());

                // 等待服务器 socket 关闭 。
                // 在这个例子中，这不会发生，但你可以优雅地关闭你的服务器。
                cf.channel().closeFuture().sync();
            } catch (Exception ex) {
                log.error("Error starting server ", ex);
            }
        });
    }

    /**
     * 初始化SSL上下文
     * @return SSL上下文
     * @throws NoSuchAlgorithmException
     * @throws IOException
     * @throws KeyStoreException
     * @throws CertificateException
     */
    private SslContext initSSLContext() throws NoSuchAlgorithmException, IOException, KeyStoreException, CertificateException {
        KeyStore jks = KeyStore.getInstance("JKS");
        jks.load(new FileInputStream("classpath:ssl/sChat.jks"),"sNetty".toCharArray());
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("iot-server-ssl");
        return SslContextBuilder.forServer(keyManagerFactory).build();
    }

}
