package framework.context.support.annotation;

import javax.inject.Singleton;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by jun.
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Singleton
public @interface Bean {
}
