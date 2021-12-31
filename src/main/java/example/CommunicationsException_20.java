package example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * <h> CommunicationsException.3 </h>
 * Because JDBC don't know why connection lost, so throw it.
 * <p>
 * Exception in thread "main" com.mysql.cj.jdbc.exceptions.CommunicationsException: Communications link failure
 * The last packet successfully received from the server was 15,082 milliseconds ago. The last packet sent successfully to the server was 15,093 milliseconds ago.
 * </p>
 * <p> We haven't figured out a good reason, so copy it. </p>
 */
public class CommunicationsException_20 {


    public static void main(String[] args) throws SQLException, InterruptedException, ClassNotFoundException {

        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection("jdbc:mysql://172.16.101.3:4000/test?useServerPrepStmts=true&cachePrepStmts=true", "root", "");
        conn.setAutoCommit(false);

        // Kill this connection id in database
        Thread.sleep(15000);

        Statement sm = conn.createStatement();
        sm.executeQuery("select 1");
        sm.close();
        conn.close();

    }

}
