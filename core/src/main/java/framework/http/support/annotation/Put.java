package framework.http.support.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by jun.
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Method
public @interface Put {
}
