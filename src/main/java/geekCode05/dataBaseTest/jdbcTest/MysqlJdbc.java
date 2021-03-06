package geekCode05.dataBaseTest.jdbcTest;

import java.sql.*;
import java.util.*;

/**
 * 1）使用 JDBC 原生接口，实现数据库的增删改查操作。
 * 2）使用事务，PrepareStatement 方式，批处理方式，改进上述操作。
 */
public class MysqlJdbc {
    private static Connection connection = null;
    private PreparedStatement preparedStatement = null;

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println();
        } catch (ClassNotFoundException e) {
            System.out.println("Can't find mysql jdbc driver");
            e.printStackTrace();
        }

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/geekBase", "root", "root");
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

    public boolean insert(String table, String column, List<Object> values) {
        try {
            String insertTemplate = buildInsertSqlTemplate(table, column, values.size());
            preparedStatement = connection.prepareStatement(insertTemplate);
            for (int i=1; i<values.size() + 1; i++) {
                preparedStatement.setObject(i, values.get(i-1));
            }
            System.out.println(preparedStatement.toString());

            preparedStatement.execute();
            System.out.println("Insert successfully");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private String buildInsertSqlTemplate(String table, String column, int valueAmount) {
        return "insert into " + table + " " + column + " values (?" +
                String.join("", Collections.nCopies(Math.max(0, valueAmount - 1), ",?")) +
                ")";
    }

    public List<Map<String, Object>> query(String table, Map<String, Object> values, String condition) {
        try {
            String sqlTemplate = buildQuerySqlTemplate(table, values, condition);

            preparedStatement = connection.prepareStatement(sqlTemplate);

            ResultSet resultSet = preparedStatement.executeQuery();

            List<Map<String, Object>> list = new ArrayList<>();
            while (resultSet.next()) {
                for (String key: values.keySet()) {
                    values.put(key, resultSet.getObject(key));
                }
                list.add(new HashMap<>(values));
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static int updateOrdelete(String executeSql) throws SQLException {
        return connection.createStatement().executeUpdate(executeSql);
    }

    public String buildQuerySqlTemplate(String table, Map<String, Object> values, String condition) {
        String sqlTemplate = "select " + values.keySet().toString().substring(1,
                values.keySet().toString().length()-1) + " from " + table;
        if (condition != null) {
            sqlTemplate += " where " + condition;
        }
        return sqlTemplate;
    }

    private void close() throws SQLException {
        preparedStatement.close();
        connection.close();
        System.out.println("Connection close");
    }

    public static void main(String[] args) {
        Map<String, Object> valuesMap = new HashMap<>();
        valuesMap.put("id", 0);
        valuesMap.put("name", "geekXm");
        valuesMap.put("password", "110");
        valuesMap.put("phoneNumber", "123456");
        valuesMap.put("money", "1000");
        System.out.println(valuesMap.keySet().toString().substring(1, valuesMap.keySet().toString().length()-1));

        try {
            MysqlJdbc mysqlJdbc = new MysqlJdbc();
            String table = "users";
            String columns = "(name, password, phoneNumber, money)";
            List<Object> values = Arrays.asList("geekXm", "110", "123456", "1000");

            // 使用PrepareStatement来增加、查询数据
            mysqlJdbc.insert(table, columns, values);
            List<Map<String, Object>> results = mysqlJdbc.query(table, valuesMap, null);
            for (Map r: results) {
                System.out.println(r.toString());
            }
            // 使用原生接口来修改、删除数据
            //更新
            String updateSql = "update users set money='10000' WHERE name = 'geekXm'";
            System.out.println("更新执行结果:"+updateOrdelete(updateSql));
            //删除
            String deleteSql = "delete FROM users WHERE name = 'geekXm'";
            System.out.println("删除执行结果:"+updateOrdelete(deleteSql));
            mysqlJdbc.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
