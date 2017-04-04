package framework.web.route;

/**
 * Created by jun.
 */
@FunctionalInterface
public interface Interceptor {
    boolean intercept();
}
