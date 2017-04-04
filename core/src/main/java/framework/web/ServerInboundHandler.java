package framework.web;

import framework.web.route.Dispatcher;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.util.AsciiString;

import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * Created by jun.
 */
@ChannelHandler.Sharable
public class ServerInboundHandler extends SimpleChannelInboundHandler {

    private static final AsciiString CONTENT_LENGTH = new AsciiString("Content-Length");
    private static final AsciiString CONNECTION = new AsciiString("Connection");
    private static final AsciiString KEEP_ALIVE = new AsciiString("keep-alive");

    private Dispatcher dispatcher;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof FullHttpRequest) {
            FullHttpRequest fullHttpRequest = (FullHttpRequest) msg;
            FullHttpResponse fullHttpResponse = new DefaultFullHttpResponse(HTTP_1_1, OK);
            dispatcher.dispatch(fullHttpRequest, fullHttpResponse);
            fullHttpResponse.headers().setInt(CONTENT_LENGTH, fullHttpResponse.content().readableBytes());
            if (!HttpUtil.isKeepAlive(fullHttpRequest)) {
                ctx.write(fullHttpResponse).addListener(ChannelFutureListener.CLOSE);
            } else {
                fullHttpResponse.headers().set(CONNECTION, KEEP_ALIVE);
                ctx.write(fullHttpResponse);
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ctx.close();
    }
}
