package example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * <h> CommunicationsException.3 </h>
 * <p>
 * If you set the tidb timeout parameter wait_timeout to be very small, such as 1.
 * The database connection will be disconnected soon over time:
 * <p> ["read packet timeout, close this connection"] [conn=249] [idle=1.000691855s] [waitTimeout=1] [error="read tcp 172.16.101.3:4000->172.16.200.149:60749: i/o timeout"] </p>
 * <p>
 * So after sleep 5s, execute SQL will throw CommunicationsException.3:
 * <p>
 * Exception in thread "main" com.mysql.cj.jdbc.exceptions.CommunicationsException:
 * The last packet successfully received from the server was 5,049 milliseconds ago.
 * The last packet sent successfully to the server was 5,058 milliseconds ago.
 * is longer than the server configured value of 'wait_timeout'.
 * You should consider either expiring and/or testing connection validity before use in your application,
 * increasing the server configured values for client timeouts,
 * or using the Connector/J connection property 'autoReconnect=true' to avoid this problem.
 */
public class CommunicationsException_3 {

    public static void main(String[] args)
            throws SQLException, InterruptedException, ClassNotFoundException {

        // Set database variables WAIT_TIME = 1 first.
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn =
                DriverManager.getConnection(
                        "jdbc:mysql://172.16.101.3:4000/test?useServerPrepStmts=true&cachePrepStmts=true",
                        "root",
                        "");
        conn.setAutoCommit(false);

        Thread.sleep(15000);

        Statement sm = conn.createStatement();
        sm.executeQuery("select 1");
        sm.close();
        conn.close();
    }
}
