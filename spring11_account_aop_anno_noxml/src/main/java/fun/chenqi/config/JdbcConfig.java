package fun.chenqi.config;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;


import javax.sql.DataSource;


public class JdbcConfig {
    @Value("${jdbc.driver}")
    private String driver;
    @Value("${jdbc.url}")
    private String url;
    @Value("${jdbc.username}")
    private String username;
    @Value("${jdbc.password}")
    private String password;

    @Bean
    public JdbcTemplate getJdbcTemp(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public DataSource createDataSource() {
        try {
            BasicDataSource basicDataSource = new BasicDataSource();
            basicDataSource.setDriverClassName(driver);
            basicDataSource.setUrl(url);
            basicDataSource.setUsername(username);
            basicDataSource.setPassword(password);
            return basicDataSource;
        } catch (Exception e) {
            throw new RuntimeException();
        }

    }

}
