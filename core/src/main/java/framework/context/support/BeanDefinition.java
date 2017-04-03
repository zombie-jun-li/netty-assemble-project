package framework.context.support;

import java.util.Objects;

/**
 * Created by jun.
 */
public final class BeanDefinition {
    private final String beanName;

    private final Class<?> beanClass;

    private BeanDefinition(String beanName, Class<?> beanClass) {
        this.beanName = beanName;
        this.beanClass = beanClass;
    }

    public static BeanDefinition of(String beanName, Class<?> beanClass) {
        return new BeanDefinition(beanName, beanClass);
    }

    @Override
    public boolean equals(Object obj) {
        if (null == obj || getClass() != obj.getClass()) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        BeanDefinition beanDefinition = (BeanDefinition) obj;
        return Objects.equals(beanName, beanDefinition.beanName)
                && Objects.equals(beanClass, beanDefinition.beanClass);
    }

    @Override
    public String toString() {
        return String.format("BeanDefinition={beanName=%s, beanClass=%s}", beanName, beanClass);
    }

    @Override
    public int hashCode() {
        return Objects.hash(beanName, beanClass);
    }
}
