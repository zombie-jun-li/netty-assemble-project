package framework;

import framework.web.route.http.HttpScheme;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslHandler;

import java.util.List;

/**
 * Created by jun.
 */
public class HTTPRequestDecoder extends ByteToMessageDecoder {
    private final SslContext sslCtx;

    public HTTPRequestDecoder(SslContext sslCtx) {
        this.sslCtx = sslCtx;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        // will use the first five bytes to detect a protocol.
        if (in.readableBytes() < 5) {
            return;
        }

        if (isSsl(in)) {
            enableSsl(ctx);
            ctx.channel().attr(AttributeKeyConstant.SCHEME).set(HttpScheme.HTTPS.scheme());
        }
        enableHttp(ctx);
    }

    private void enableSsl(ChannelHandlerContext ctx) {
        ChannelPipeline pipeline = ctx.pipeline();
        pipeline.addLast(sslCtx.newHandler(ctx.alloc()));
    }

    private void enableHttp(ChannelHandlerContext ctx) {
        ChannelPipeline pipeline = ctx.pipeline();
        pipeline.addLast(new HttpResponseEncoder());
        pipeline.addLast(new ServerOutboundHandler());

        pipeline.addLast(new HttpRequestDecoder());
        pipeline.addLast(new HttpObjectAggregator(Integer.MAX_VALUE));
        pipeline.addLast(new ServerInboundHandler());
        pipeline.remove(this); // todo why?
    }

    private boolean isSsl(ByteBuf buf) {
        return SslHandler.isEncrypted(buf);
    }
}
