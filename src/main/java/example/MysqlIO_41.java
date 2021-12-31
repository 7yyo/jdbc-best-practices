package example;

import java.sql.*;


/**
 * <h> MysqlIO.41 </h>
 * <p>
 * No statements may be issued when any streaming result sets are open and in use on a given connection.
 * Ensure that you have called .close() on any active streaming result sets before attempting more queries.
 */
public class MysqlIO_41 {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection("jdbc:mysql://172.16.101.3:4000/test?useServerPrepStmts=true&cachePrepStmts=true", "root", "");
        conn.setAutoCommit(false);

        PreparedStatement ps = conn.prepareStatement("select id from t where id = ?");
        ps.setInt(1, 1);
        ps.setFetchSize(Integer.MIN_VALUE);
        ResultSet rs = ps.executeQuery();

        // Comment out this code, you can reproduce MysqlIO.41
        // If you want to forcibly close the resultset without reporting MysqlIO.41, please set <h> clobberStreamingResults=true </h> at jdbc url.
        while (rs.next()) {
            System.out.println(rs.getString("id"));
        }

        ps = conn.prepareStatement("select * from t where id = ?");
        ps.setInt(1, 1);
        rs = ps.executeQuery();
        while (rs.next()) {
            System.out.println(rs.getString("id"));
        }

        rs.close();
        ps.close();
        conn.close();
    }

}
