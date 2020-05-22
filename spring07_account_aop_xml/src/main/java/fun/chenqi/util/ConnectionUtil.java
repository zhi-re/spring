package fun.chenqi.util;

import javax.sql.DataSource;
import java.sql.Connection;

public class ConnectionUtil {
    /**
     * 一个用于管理连接的工具类 用于实现连接和线程的绑定
     */

    //创建ThreadLocal对象
    private ThreadLocal<Connection> tl = new ThreadLocal<Connection>();

    // 创建一个连接池
     private DataSource dataSource;

     public void setDataSource(DataSource dataSource) {
         this.dataSource = dataSource;
    }

    /**
     * 获取当前线程上绑定的连接
     */
    public Connection getThreadConnection() {
        try {
            Connection conn = tl.get();
            if (conn == null) {
                // 从数据源中获取一个连接
               conn = dataSource.getConnection();
                // 和线程局部变量的绑定
                tl.set(conn);
            }
            // 返回线程上的连接
            return tl.get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 把连接和当前线程解绑
     */
    public void remove() {
        tl.remove();
    }

}



