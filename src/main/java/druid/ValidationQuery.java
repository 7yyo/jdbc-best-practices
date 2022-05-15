package druid;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.druid.util.JdbcUtils;
import lombok.SneakyThrows;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.Properties;

public class ValidationQuery {

    @SneakyThrows
    public static void main(String[] args) {
        // druid uses the ping method by default
        System.setProperty("druid.mysql.usePingMethod", "false");
        Properties p = new Properties();
        p.load(JdbcUtils.class.getClassLoader().getResourceAsStream("druid.properties"));
        DataSource dataSource = DruidDataSourceFactory.createDataSource(p);
        Connection connection = dataSource.getConnection();
    }

}
