package young.demo.config;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

public class applicationContext implements WebApplicationInitializer {

    public void onStartup(ServletContext servletContext) throws ServletException {
        //Spring的配置 容器启动的监听器
        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
        rootContext.register(ApplicationRootConfig.class);

        servletContext.addListener(new ContextLoaderListener(rootContext));

        //Spring MVC的容器
        AnnotationConfigWebApplicationContext dispatchContext = new AnnotationConfigWebApplicationContext();
        dispatchContext.register(ApplicationDispatchConfig.class);

        ServletRegistration.Dynamic dispatch = servletContext.addServlet("dispatch", new DispatcherServlet(dispatchContext));
        dispatch.setLoadOnStartup(1);
        dispatch.addMapping("/");
    }
}
