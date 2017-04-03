package framework.context;

import framework.context.exception.BeanCreationException;
import framework.context.support.BeanDefinition;
import framework.context.support.Factory;
import framework.utils.Assert;
import framework.utils.Maps;

import javax.inject.Inject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Map;

/**
 * Created by jun.
 */
public class DefaultBeanFactory implements Factory {

    private final Map<BeanDefinition, Object> beanDefinitionObjectMap = Maps.concurrentHashMap(256);

    @Override
    public void registerBean(String beanName, Class<?> beanClass) {
        BeanDefinition beanDefinition = BeanDefinition.of(beanName, beanClass);
        registerBean(beanDefinition, construct(beanClass));
    }

    @Override
    public void registerBean(BeanDefinition beanDefinition, Object bean) {
        if (null != beanDefinitionObjectMap.putIfAbsent(beanDefinition, bean)) {
            String message = String.format("bean=%s already exists.", beanDefinition);
            throw new BeanCreationException(message);
        }
    }

    @Override
    public <T> T getBean(String beanName, Class<T> beanClass) {
        BeanDefinition beanDefinition = BeanDefinition.of(beanName, beanClass);
        return getBean(beanDefinition);
    }

    @Override
    public <T> T getBean(BeanDefinition beanDefinition) {
        return (T) beanDefinitionObjectMap.get(beanDefinition);
    }

    @Override
    public void refresh() {
        // inject bean dependency
        beanDefinitionObjectMap.forEach((key, value) -> inject(value));
    }

    private Object construct(Class<?> beanClass) {
        Assert.notNull(beanClass, "beanClass must not be null.");
        if (beanClass.isAnonymousClass()
                || beanClass.isEnum()
                || beanClass.isInterface()
                || Modifier.isAbstract(beanClass.getModifiers())) {
            throw new BeanCreationException("bean object must be concrete and independent.");
        }

        try {
            Constructor<?> constructor = beanClass.getDeclaredConstructor();
            if ((!Modifier.isPublic(constructor.getModifiers()) || !Modifier.isPublic(constructor.getDeclaringClass().getModifiers()))
                    && !constructor.isAccessible()) {
                constructor.setAccessible(true);
            }
            return beanClass.newInstance();
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            throw new BeanCreationException(e.getMessage(), e);
        }
    }

    private void inject(Object object) {
        Arrays.stream(object.getClass().getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Inject.class))
                .forEach(field -> {
                    try {
                        Class<?> beanClass = field.getType();
                        String beanName = field.getName();
                        Object bean = getBean(beanName, field.getType());
                        Assert.notNull(bean, String.format("bean=%s not registered.", BeanDefinition.of(beanName, beanClass)));
                        if (!field.isAccessible()) {
                            field.setAccessible(true);
                        }
                        field.set(object, bean);
                    } catch (IllegalAccessException e) {
                        throw new BeanCreationException(e.getMessage(), e);
                    }
                });
    }
}
