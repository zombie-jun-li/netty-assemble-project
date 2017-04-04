package framework.web.bind;

import java.util.Objects;

/**
 * Created by jun.
 */
public final class RequestMapping {
    private final String path;

    private final String method;

    private RequestMapping(String path, String method) {
        this.path = path;
        this.method = method;
    }

    public static RequestMapping build(String path, String method) {
        return new RequestMapping(path, method);
    }


    @Override
    public String toString() {
        return String.format("RequestMapping={path=%s, method=%s}", path, method);
    }

    @Override
    public int hashCode() {
        return Objects.hash(path, method);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof RequestMapping)) {
            return false;
        }
        RequestMapping otherRequestMapping = (RequestMapping) other;
        return Objects.equals(path, otherRequestMapping.path)
                && Objects.equals(method, otherRequestMapping.method);
    }
}
