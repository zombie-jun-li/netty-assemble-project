package framework.web.route.http.response;

import framework.utils.Pair;
import framework.web.route.http.HttpStatus;
import io.netty.buffer.ByteBuf;

/**
 * Created by jun.
 */
public interface Response {
    void status(HttpStatus httpStatus);

    void head(Pair<String, String> pair);

    void content(ByteBuf content);
}
