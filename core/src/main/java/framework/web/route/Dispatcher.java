package framework.web.route;

import framework.utils.Maps;
import framework.web.bind.RequestMapping;
import framework.web.bind.RequestHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpResponseStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by jun.
 */
public class Dispatcher {
    private final List<Interceptor> interceptors = new ArrayList<>();

    private final Map<RequestMapping, RequestHandler> handlerMapping = Maps.hashMap(256);

    public void dispatch(FullHttpRequest fullHttpRequest, FullHttpResponse fullHttpResponse) {
        boolean result = interceptors.stream()
                .anyMatch(interceptor -> !interceptor.intercept());
        if (result) {
            return;
        }
        doDispatch(fullHttpRequest, fullHttpResponse);
    }

    private void doDispatch(FullHttpRequest fullHttpRequest, FullHttpResponse fullHttpResponse) {
        HttpMethod httpMethod = fullHttpRequest.method();
        String path = fullHttpRequest.uri();
        RequestMapping requestMapping = RequestMapping.build(path, httpMethod.name());

        RequestHandler requestHandler = handlerMapping.get(requestMapping);
        if (null == requestHandler) {
            // todo requestHandler not found, default requestHandler
            return;
        }
        // todo set response content and status code
        fullHttpResponse.setStatus(HttpResponseStatus.OK);
        requestHandler.handle();
    }
}
