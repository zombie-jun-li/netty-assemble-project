package framework.web.route.http.request;

import framework.web.bind.annotation.RequestMethod;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.QueryStringDecoder;

import java.util.List;
import java.util.Optional;

/**
 * Created by jun.
 */
public final class RequestImpl implements Request {

    private final FullHttpRequest fullHttpRequest;

    private final QueryStringDecoder queryStringDecoder;

    public RequestImpl(FullHttpRequest fullHttpRequest) {
        this.fullHttpRequest = fullHttpRequest;
        this.queryStringDecoder = new QueryStringDecoder(fullHttpRequest.uri());
    }

    @Override
    public String host() {
        return fullHttpRequest.headers().get("host");
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
        return null;
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
