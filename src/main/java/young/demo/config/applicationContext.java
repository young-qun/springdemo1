package young.demo.config;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

public class applicationContext implements WebApplicationInitializer {

    public void onStartup(ServletContext servletContext) throws ServletException {
        //Spring的配置 容器启动的监听器 加载Spring的容器
        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
        rootContext.register(ApplicationRootConfig.class);

        servletContext.addListener(new ContextLoaderListener(rootContext));

        //Spring MVC的容器  --加载SpringMVC的容器，父子容器的关系
        /**
         * 将SpringMVC的web.xml的配置转换成java配置
         * <servlet>
         *     <servlet-name></servlet-name>
         *     <servlet-class></servlet-class>
         *     <init-param></init-param>
         * </servlet>
         */
        AnnotationConfigWebApplicationContext dispatchContext = new AnnotationConfigWebApplicationContext();
        dispatchContext.register(ApplicationDispatchConfig.class);

        ServletRegistration.Dynamic dispatch = servletContext.addServlet("dispatch", new DispatcherServlet(dispatchContext));
        dispatch.setLoadOnStartup(1);
        dispatch.addMapping("/");

        //配置文件上传的视图解析器的内容
        MultipartConfigElement multipartConfigElement = new MultipartConfigElement("tmp/data/uploads",1024000,4194304,0);
        dispatch.setMultipartConfig(multipartConfigElement);
    }
}
