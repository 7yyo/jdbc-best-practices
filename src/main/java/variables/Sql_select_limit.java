package variables;

import java.sql.*;

/**
 * <h> sql_select_limit </h>
 * <p>
 * If sql_select_limit is set, the number of queries will be limited.
 */
public class Sql_select_limit {

    /*

    MySQL root@(none):test> desc t;
    +-------+-------------+------+-----+---------+-------+
    | Field | Type        | Null | Key | Default | Extra |
    +-------+-------------+------+-----+---------+-------+
    | a     | int         | YES  |     | <null>  |       |
    | b     | varchar(11) | YES  |     | <null>  |       |
    +-------+-------------+------+-----+---------+-------+

    MySQL root@(none):test> select * from t;
    +---+---+
    | a | b |
    +---+---+
    | 3 | c |
    | 2 | b |
    | 1 | a |
    +---+---+
    3 rows in set

     */
    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn =
                DriverManager.getConnection("jdbc:mysql://172.16.5.133:4000/test", "root", "");

        Statement stmt = conn.createStatement();
        // SetMaxRows, JDBC will req SQL 'set sql_select_limit = 1'
        stmt.setMaxRows(1);
        /*
         * SELECT * FROM t WHERE a > 0
         *
         * id                      	task     	estRows	operator info                          	actRows	execution info                                                                                                                                                                                                                           	memory   	disk
         * Limit_8                 	root     	1      	offset:0, count:1                      	1      	time:745.6µs, loops:2                                                                                                                                                                                                                   	N/A      	N/A
         * └─TableReader_13        	root     	1      	data:Limit_12                          	1      	time:742.7µs, loops:1, cop_task: {num: 1, max: 662.1µs, proc_keys: 3, rpc_num: 1, rpc_time: 636.8µs, copr_cache_hit_ratio: 0.00}                                                                                                      	246 Bytes	N/A
         *   └─Limit_12            	cop[tikv]	1      	offset:0, count:1                      	1      	tikv_task:{time:0s, loops:1}, scan_detail: {total_process_keys: 3, total_process_keys_size: 123, total_keys: 4, rocksdb: {delete_skipped_count: 0, key_skipped_count: 3, block: {cache_hit_count: 0, read_count: 0, read_byte: 0 Bytes}}}	N/A      	N/A
         *     └─Selection_11      	cop[tikv]	1      	gt(test.t.a, 0)                        	3      	tikv_task:{time:0s, loops:1}                                                                                                                                                                                                             	N/A      	N/A
         *       └─TableFullScan_10	cop[tikv]	3      	table:t, keep order:false, stats:pseudo	3      	tikv_task:{time:0s, loops:1}                                                                                                                                                                                                             	N/A      	N/A
         */
        ResultSet rs = stmt.executeQuery("select * from t where c1 > 0");

        // ResultSet is only 3
        while (rs.next()) {
            System.out.println(rs.getString("c1"));
        }

        rs.close();
        stmt.close();
        conn.close();
    }
}
