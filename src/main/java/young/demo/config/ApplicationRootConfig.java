package young.demo.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;


/**
 * Spring的配置文件
 */
@Configuration
@ComponentScan(basePackages = {"young.demo.bean","young.demo.dao"})
@ImportResource(locations = "classPath:application.properties")
public class ApplicationRootConfig {


}
