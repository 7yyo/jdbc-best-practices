package variables;

import java.sql.*;

public class Isolation_level {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn =
                DriverManager.getConnection("jdbc:mysql://172.16.101.3:4000/test", "root", "");

        // The isolation level 'READ-UNCOMMITTED' is not supported. Set tidb_skip_isolation_level_check=1 to skip this error
        // The isolation level 'SERIALIZABLE'     is not supported. Set tidb_skip_isolation_level_check=1 to skip this error
        conn.setAutoCommit(false);
        // SET SESSION TRANSACTION ISOLATION LEVEL READ COMMITTED
        conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("select * from t");

        while (rs.next()) {
            System.out.println(rs.getString("id"));
        }

        rs = stmt.executeQuery("select * from t");

        while (rs.next()) {
            System.out.println(rs.getString("id"));
        }

        conn.commit();
        rs.close();
        stmt.close();
        conn.close();
    }
}
