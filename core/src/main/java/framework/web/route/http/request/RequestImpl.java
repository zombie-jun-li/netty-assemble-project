package framework.web.route.http.request;

import framework.AttributeKeyConstant;
import framework.web.bind.annotation.RequestMethod;
import framework.web.route.http.HttpScheme;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.QueryStringDecoder;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.Optional;

/**
 * Created by jun.
 */
public final class RequestImpl implements Request {

    private final FullHttpRequest fullHttpRequest;

    private final QueryStringDecoder queryStringDecoder;

    private final Channel channel;

    public RequestImpl(FullHttpRequest fullHttpRequest, Channel channel) {
        this.fullHttpRequest = fullHttpRequest;
        this.queryStringDecoder = new QueryStringDecoder(fullHttpRequest.uri());
        this.channel = channel;
    }


    @Override
    public String host() {
        return ((InetSocketAddress)channel.localAddress()).getHostName();
    }

    @Override
    public String scheme() {
        return channel.hasAttr(AttributeKeyConstant.SCHEME)
                ? channel.attr(AttributeKeyConstant.SCHEME).get() : HttpScheme.HTTP.scheme();
    }

    @Override
    public Optional<String> head(String name) {
        return null;
    }

    @Override
    public RequestMethod method() {
        return RequestMethod.valueOf(fullHttpRequest.method().name());
    }

    @Override
    public String clientIp() {
        return ((InetSocketAddress) channel.remoteAddress()).getAddress().getHostAddress();
    }

    @Override
    public String path() {
        return this.queryStringDecoder.path();
    }

    @Override
    public Optional<List<String>> param(String name) {
        return Optional.ofNullable(this.queryStringDecoder.parameters().get(name));
    }
}
