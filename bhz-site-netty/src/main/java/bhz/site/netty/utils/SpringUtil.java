package bhz.site.netty.utils;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by as on 2018/1/9.
 */
public class SpringUtil {
    public static ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
    public static Object getBean(String serviceName){
        return context.getBean(serviceName);
    }
}
