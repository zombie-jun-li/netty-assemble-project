package context;

import framework.context.DefaultBeanFactory;
import framework.utils.Assert;
import org.junit.Test;

import javax.inject.Inject;
import java.util.function.Consumer;

/**
 * Created by jun.
 */
public class DefaultBeanFactoryTest {
    @Test
    public void testFactory() {
        DefaultBeanFactory defaultBeanFactory = new DefaultBeanFactory();
        Consumer<Class<?>> registerBean = (clazz) -> defaultBeanFactory.registerBean(clazz.getSimpleName().toLowerCase(), clazz);
        registerBean.accept(Controller.class);
        registerBean.accept(Service.class);
        defaultBeanFactory.refresh();

        Service service = defaultBeanFactory.getBean(Service.class.getSimpleName().toLowerCase(), Service.class);
        Controller controller = defaultBeanFactory.getBean(Controller.class.getSimpleName().toLowerCase(), Controller.class);
        Assert.equals(true, controller.save());
        Assert.equals(service, controller.service);
    }


    public static class Controller {

        @Inject
        private Service service;

        public boolean save() {
            return service.save();
        }
    }

    public static class Service {
        public boolean save() {
            return true;
        }
    }
}
