package geekCode07;

import java.sql.*;
public class InsertDataToMysql {
    private static Connection connection = null;
    private static PreparedStatement preparedStatement = null;

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println();
        } catch (ClassNotFoundException e) {
            System.out.println("Can't find mysql jdbc driver");
            e.printStackTrace();
        }

        try {
            // MySQL的JDBC连接的url中要加rewriteBatchedStatements参数，并保证5.1.13以上版本的驱动，才能实现高性能的批量插入
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/geekBase?&rewriteBatchedStatements=true", "root", "root");
            if (connection != null) {
                System.out.println("Connection successful!");
            } else {
                System.out.println("Connection failed!");
            }
        } catch (SQLException e) {
            System.out.println("Connection failed!");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        insertData();
        long end = System.currentTimeMillis();
        System.out.println("耗时：" + (end - start)/1000 + "秒");
    }

    private static void insertData() {
        try {
            String insertTemplate = "insert into " +
                    "tb_order_xm(order_id,goods_id,goods_sum,goods_price,goods_total,create_date,update_date)" +
                    "values(?,?,?,?,?,?,?)";
            preparedStatement = connection.prepareStatement(insertTemplate);
            for (int i = 0; i < 1000000; i++) {
                preparedStatement.setInt(1,(int)(Math.random()*1000000));
                preparedStatement.setInt(2,(int)(Math.random()*1000000));
                preparedStatement.setInt(3,(int)(Math.random()*1000000));
                preparedStatement.setInt(4,(int)(Math.random()*1000000));
                preparedStatement.setInt(5,(int)(Math.random()*1000000));
                preparedStatement.setDate(6,new Date(System.currentTimeMillis()));
                preparedStatement.setDate(7,new Date(System.currentTimeMillis()));
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}