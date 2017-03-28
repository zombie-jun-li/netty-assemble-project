package framework.context.support;



/**
 * Created by jun.
 */
public interface Factory {
    <T> T getBean(String beanName, Class<T> beanClass);

    <T> T getBean(BeanDefinition beanDefinition);

    void registerBean(String beanName, Class<?> beanClass);

    void registerBean(BeanDefinition beanDefinition, Object bean);

    void refresh();
}
