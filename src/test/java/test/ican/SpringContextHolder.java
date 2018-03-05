package test.ican;

/*import org.apache.commons.lang.Validate;*/
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

/**
 * applicationContext静态化
 * 使用了ApplicationContextAware接口的类，如果受spring容器管理的
 * 话，那么就会自动的调用ApplicationContextAware中的setApplicationContext方法
 *
 */
@Service
@Lazy(false)
public class SpringContextHolder implements ApplicationContextAware, DisposableBean {

    private static ApplicationContext applicationContext;

    //获得applicationContext
    public static ApplicationContext getApplicationContext() {
        //assertContextInjected();
        return applicationContext;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        applicationContext = applicationContext;
    }

    public static void clearHolder() {
        applicationContext = null;
    }

    //获取Bean
    public static <T> T getBean(Class<T> requiredType) {
        //assertContextInjected();
        return (T) getApplicationContext().getBean(requiredType);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) {
        assertContextInjected();
        return (T) getApplicationContext().getBean(name);
    }

    //判断application是否为空
    public static void assertContextInjected() {
        //  Validate.isTrue(applicationContext == null, "application未注入 ，请在springContext.xml中注入SpringHolder!");
    }

    //清空applicationContext 设置其为null
    @Override
    public void destroy() throws Exception {
        com.ican.SpringContextHolder.clearHolder();
    }

}