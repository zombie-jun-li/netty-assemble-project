package framework.web.route.http.request;

import framework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Optional;

/**
 * Created by jun.
 */
public interface Request {

    String host();

    String scheme();

    Optional<String> head(String name);

    RequestMethod method();

    String clientIp();

    String path();

    Optional<List<String>> param(String name);

}
