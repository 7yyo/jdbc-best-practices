package connection_pool;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.*;

/**
 * <h> connectionTimeout cannot be less than 250ms </h>
 */
public class GetConnectionTimeout {

    public static void main(String[] args) throws SQLException {

        HikariConfig config = new HikariConfig();

        config.setJdbcUrl(
                "jdbc:mysql://172.16.101.3:4000/test?useServerPrepStmts=true&cachePrepStmts=true");
        config.setUsername("root");
        config.setPassword("");

        config.setAutoCommit(false);
        // Can't less than 250ms
        config.setConnectionTimeout(250);
        config.setMaximumPoolSize(1);
        config.setPoolName("Hikari-01");

        HikariDataSource dataSource = new HikariDataSource(config);

        for (int i = 0; i < 2; i++) {
            Task t = new Task(dataSource.getConnection());
            t.start();
        }
    }
}

class Task extends Thread {

    private final Connection conn;

    public Task(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(60000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
