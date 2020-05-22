package fun.chenqi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * 事务管理的配置类
 */
public class TransactionManagerConfig {

    @Bean("transactionManager")
    public DataSourceTransactionManager createDataSourceTransactionManager(DataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
    }
}
