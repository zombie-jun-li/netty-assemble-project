package framework.web.route;

import framework.utils.Maps;
import framework.web.bind.RequestMapping;
import framework.web.bind.RequestHandler;
import framework.web.route.http.request.Request;
import framework.web.route.http.response.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by jun.
 */
public class Dispatcher {
    private final List<Interceptor> interceptors = new ArrayList<>();

    private final Map<RequestMapping, RequestHandler> handlerMapping = Maps.hashMap(256);

    public void dispatch(Request request, Response response) {
        boolean result = interceptors.stream()
                .anyMatch(interceptor -> !interceptor.intercept(null, null));
        if (result) {
            return;
        }
        doDispatch(request, response);
    }

    private void doDispatch(Request request, Response response) {
        String path = request.path();
        String method = request.method().name();
        RequestMapping requestMapping = RequestMapping.build(path, method);

        RequestHandler requestHandler = handlerMapping.get(requestMapping);
        if (null == requestHandler) {
            // todo requestHandler not found, default requestHandler
            return;
        }
        // todo set response content and status code
//        fullHttpResponse.setStatus(HttpResponseStatus.OK);
        requestHandler.handle();
    }
}
