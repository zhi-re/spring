package fun.chenqi.dao.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;

/* @PropertySource
  用于加载.properties 文件中的配置。例如我们配置数据源时，可以把连接数据库的信息写到
  properties 配置文件中，就可以使用此注解指定 properties 配置文件的位置。
  属性：
  value[]：用于指定 properties 文件位置。如果是在类路径下，需要写上 classpath:
* */

//@Configuration//写不写都行
@PropertySource("classpath:jdbc.properties")
public class JdbcConfig {

    @Value("${jdbc.driver}")
    private String driver;
    @Value("${jdbc.url}")
    private String url;
    @Value("${jdbc.username}")
    private String username;
    @Value("${jdbc.password}")
    private String password;

    /* @Bean作用：
        该注解只能写在方法上，表明使用此方法创建一个对象，并且放入 spring 容器。
        属性：
        name：给当前@Bean 注解方法创建的对象指定一个名称(即 bean 的 id）
   */


    //创建一个数据源 并存入spring容器中
    @Bean(name = "dataSource")
    public DataSource createDataSource() {
        try {
            ComboPooledDataSource ds = new ComboPooledDataSource();
            ds.setUser(username);
            ds.setPassword(password);
            ds.setDriverClass(driver);
            ds.setJdbcUrl(url);
            return ds;
        } catch (PropertyVetoException e) {
            throw new RuntimeException();
        }
    }

    @Bean("runner")
    @Scope("prototype")
    public QueryRunner createQueryRunner(DataSource dataSource) {
        return new QueryRunner(dataSource);
    }

}
