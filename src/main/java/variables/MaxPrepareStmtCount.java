package variables;

import lombok.SneakyThrows;

import java.sql.*;

public class MaxPrepareStmtCount {

    @SneakyThrows
    public static void main(String[] args) {
        // In TiDB, max_prepared_stmt_count = -1, mean no limit.
        // mysql> show variables like '%prepare%';
        // +-------------------------+-------+
        // | Variable_name           | Value |
        // +-------------------------+-------+
        // | max_prepared_stmt_count | -1    |
        // +-------------------------+-------+
        // 1 row in set (0.02 sec)

        // Set global max_prepared_stmt_count = 1
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn =
                DriverManager.getConnection(
                        "jdbc:mysql://172.16.101.3:4000/test?useConfigs=maxPerformance&useServerPrepStmts=true&cachePrepStmts=true&rewriteBatchedStatements=true&allowMultiQueries=true",
                        "root",
                        "");
        conn.setAutoCommit(false);
        // Run prepared stmt 1, normal
        PreparedStatement ps1 = conn.prepareStatement("select * from t where id = ?");
        ps1.setInt(1, 1);
        ResultSet rs = ps1.executeQuery();
        while (rs.next()) {
            System.out.println(rs.getString("id"));
        }
        ps1.close();

        // Run prepared stmt 2, will print error in tidb.log. It will retry statement to run SQL.
        // [2022/02/21 14:40:22.846 +08:00] [INFO] [conn.go:995] ["command dispatched failed"] [conn=23] [connInfo="id:23, addr:172.16.103.8:59481 status:1, collation:utf8mb4_general_ci, user:root"]
        // [command=Prepare] [status="inTxn:1, autocommit:0"] [sql="select * from t where id > ?"] [txn_mode=PESSIMISTIC]
        // [err="[variable:1461]Can't create more than maxPreparedStmtCount statements (current value: 1)PreparedStatement ps2 = conn.prepareStatement("select * from t where id > ?");
        PreparedStatement ps2 = conn.prepareStatement("select * from t where id > ?");
        ps2.setInt(1, 0);
        rs = ps2.executeQuery();
        while (rs.next()) {
            System.out.println(rs.getString("id"));
        }
    }

}
