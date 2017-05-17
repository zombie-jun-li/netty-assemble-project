package framework.web.route.http;

/**
 * Created by jun.
 */
public enum HttpScheme {
    HTTP("http"), HTTPS("https");

    private String scheme;

    HttpScheme(String scheme) {
        this.scheme = scheme;
    }

    public String scheme() {
        return scheme;
    }
}
