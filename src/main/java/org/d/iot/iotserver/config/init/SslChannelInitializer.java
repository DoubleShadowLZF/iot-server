package org.d.iot.iotserver.config.init;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslHandler;
import org.d.iot.iotserver.socket.IotChannelHandler;
import org.d.iot.iotserver.socket.IotMsgDecoder;
import org.d.iot.iotserver.socket.IotMsgEncoder;

import javax.net.ssl.SSLEngine;

/**
 * ClassName: SslChannelInitializer <br/>
 * Description: <br/>
 * date: 2019/9/14 10:08<br/>
 *
 * @author Double <br/>
 * @since JDK 1.8
 */
public class SslChannelInitializer extends ChannelInitializer<SocketChannel> {

    private final SslContext ctx;

    public SslChannelInitializer(SslContext ctx){
        this.ctx = ctx;
    }

    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        SSLEngine engine = ctx.newEngine(channel.alloc());
        engine.setUseClientMode(false);
        channel.pipeline()
            .addFirst("ssl",new SslHandler(engine))
                .addLast("decoder", new IotMsgDecoder())
                .addLast("encoder", new IotMsgEncoder())
                .addLast(new IotChannelHandler());
    }
}
