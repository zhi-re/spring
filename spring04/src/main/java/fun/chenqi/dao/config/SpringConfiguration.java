package fun.chenqi.dao.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


//@Configuration // spring 的配置类，相当于 bean.xml 文件
@ComponentScan("fun.chenqi") // 相当于<context:component-scan base-package="fun.chenqi"/>
@Import({JdbcConfig.class}) // 用于导入其他配置类，在引入其他配置类时，可以不用再写@Configuration 注解。当然，写上也没问题。
public class SpringConfiguration {

}