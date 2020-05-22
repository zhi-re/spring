package fun.chenqi.config;

import org.springframework.context.annotation.*;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Spring的配置类
 */
@Configuration
@ComponentScan("fun.chenqi")
@Import({JdbcConfig.class, TransactionManagerConfig.class})
@PropertySource("classpath:jdbc.properties")
@EnableTransactionManagement//开启事务支持
public class SpringConfiguration {
}
