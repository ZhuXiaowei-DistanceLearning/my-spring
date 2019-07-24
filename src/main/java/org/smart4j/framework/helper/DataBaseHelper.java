package org.smart4j.framework.helper;

import org.smart4j.framework.constant.ConfigConstant;
import org.smart4j.framework.utils.DBUtil;
import org.smart4j.framework.utils.Logger;
import org.smart4j.framework.utils.PropsUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * 事务控制类
 */
public final class DataBaseHelper {
    private static final ThreadLocal<Connection> CONNECTION_HOLDER = new ThreadLocal<>();
    private static Connection connection;
    private static final Logger logger = new Logger(DataBaseHelper.class);

    static {
        Properties properties = PropsUtils.loadProperties(ConfigConstant.CONFIG_FILE);
        String username = PropsUtils.getString(properties, ConfigConstant.JDBC_USERNAME);
        String password = PropsUtils.getString(properties, ConfigConstant.JDBC_PASSWORD);
        String url = PropsUtils.getString(properties, ConfigConstant.JDBC_URL);
        connection = DBUtil.getConnection(url, username, password);
    }

    public static void beginTransaction() {
        if (connection != null) {
            try {
                connection.setAutoCommit(false);
                logger.info("事务开启");
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException();
            } finally {
                CONNECTION_HOLDER.set(connection);
            }
        }
    }

    public static void commitTransaction() {
        if (connection != null) {
            try {
                connection.commit();
                logger.info("事务提交");
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException();
            } finally {
                CONNECTION_HOLDER.remove();
            }
        }
    }

    public static void rollbackTransaction() {
        if (connection != null) {
            try {
                connection.rollback();
                logger.info("事务回滚");
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException();
            } finally {
                CONNECTION_HOLDER.remove();
            }
        }
    }
}
