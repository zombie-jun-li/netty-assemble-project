package framework;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;

import javax.net.ssl.SSLException;
import java.security.cert.CertificateException;

/**
 * Created by jun.
 */
public class ServerChannelInitializer extends ChannelInitializer {
    private final ChannelHandler[] channelHandlers;

    public ServerChannelInitializer(ChannelHandler... channelHandlers) {
        this.channelHandlers = channelHandlers;
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast(new HTTPRequestDecoder(sslContext()));
        if (null != channelHandlers) {
            pipeline.addLast(channelHandlers);
        }
    }

    private SslContext sslContext() throws SSLException, CertificateException {
        SelfSignedCertificate ssc = new SelfSignedCertificate();
        return SslContextBuilder.forServer(ssc.certificate(), ssc.privateKey()).build();
    }
}
