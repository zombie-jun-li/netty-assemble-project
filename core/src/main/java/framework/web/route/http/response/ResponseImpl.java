package framework.web.route.http.response;

import framework.utils.Pair;
import framework.web.route.http.HttpStatus;
import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.FullHttpResponse;

/**
 * Created by jun.
 */
public final class ResponseImpl implements Response {

    public ResponseImpl(FullHttpResponse fullHttpResponse) {
        this.fullHttpResponse = fullHttpResponse;
    }

    @Override
    public void status(HttpStatus httpStatus) {

    }

    @Override
    public void head(Pair<String, String> pair) {

    }

    @Override
    public void content(ByteBuf content) {

    }
}
