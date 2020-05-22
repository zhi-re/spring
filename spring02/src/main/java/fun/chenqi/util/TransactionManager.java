package fun.chenqi.util;

import java.sql.SQLException;

// 事务管理器
public class TransactionManager {
    private ConnectionUtil connectionUtil;

    public void setConnectionUtil(ConnectionUtil connectionUtil) {
        this.connectionUtil = connectionUtil;
    }

    // 开启事务
    public void begin() {
        // 从当前线程获取连接实现开启事务
        try {
            connectionUtil.getThreadConnection().setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 提交事务
    public void commit() {
        try {
            connectionUtil.getThreadConnection().commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 回滚事务
    public void rollback() {
        try {
            connectionUtil.getThreadConnection().rollback();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 释放连接
    public void close() {
        try {
            // 关闭连接 换回池中
            connectionUtil.getThreadConnection().close();
            // 解绑线程
            connectionUtil.remove();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
