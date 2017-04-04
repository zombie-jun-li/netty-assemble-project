package framework.web.bind;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by jun.
 */
public class RequestHandler {
    private final Object target;

    private final Method method;

    public RequestHandler(Object target, Method method) {
        this.target = target;
        this.method = method;
    }

    public void handle(Object... args) {
        try {
            method.invoke(target, args);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new IllegalStateException(e);
        }
    }
}
