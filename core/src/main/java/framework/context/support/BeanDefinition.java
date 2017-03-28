package framework.context.support;

import java.util.Objects;

/**
 * Created by jun.
 */
@SuppressWarnings("PMD.AccessorClassGeneration")
public class BeanDefinition {
    private final String beanName;

    private final Class<?> beanClass;

    private BeanDefinition(BeanDefinitionBuilder beanDefinitionBuilder) {
        this.beanName = beanDefinitionBuilder.beanName;
        this.beanClass = beanDefinitionBuilder.beanClass;
    }

    public static class BeanDefinitionBuilder {
        private String beanName;

        private Class<?> beanClass;

        public BeanDefinitionBuilder setBeanName(String beanName) {
            this.beanName = beanName;
            return this;
        }

        public BeanDefinitionBuilder setBeanClass(Class<?> beanClass) {
            this.beanClass = beanClass;
            return this;
        }

        public BeanDefinition build() {
            return new BeanDefinition(this);
        }
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
