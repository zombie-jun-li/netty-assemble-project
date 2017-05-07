package framework.web.route;

import framework.web.route.http.request.Request;
import framework.web.route.http.response.Response;

/**
 * Created by jun.
 */
@FunctionalInterface
public interface Interceptor {
    boolean intercept(Request request, Response response);
}
