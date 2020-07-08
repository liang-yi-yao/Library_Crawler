package Mysql;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;

public class MyJDBC {	
	/*
	 * private static final String URL=
	 * "jdbc:mysql://localhost:3306/test1?useUnicode=true&characterEncoding=UTF-8&userSSL=false&serverTimezone=GMT%2B8";
	 * private static final String USER="root"; private static final String
	 * PASSWORD="123"; public static void main(String[] args) throws
	 * ClassNotFoundException, SQLException { //1.加载驱动程序
	 * Class.forName("com.mysql.cj.jdbc.Driver"); //2.获得数据连接 Connection conn =
	 * DriverManager.getConnection(URL, USER, PASSWORD); //3.使用数据库的连接创建声明 Statement
	 * stmt = conn.createStatement(); //4.使用声明执行SQL语句
	 */

        
	/*
	 * String sql = "insert into user(username,password) values(?,?)";
	 * PreparedStatement ps = conn.prepareStatement(sql); //接口 PreparedStatement
	 * 表示预编译的 SQL 语句的对象 ps.setString(1,"张三"); ps.setString(2,"1234");
	 * ps.executeUpdate();//方法：在此 PreparedStatement 对象中执行 SQL 语句 成功后返回操作的行数
	 * ResultSet rs = stmt.executeQuery("select * from user"); //5.读取数据库的信息
	 * while(rs.next()){ String id = rs.getString("id"); String name =
	 * rs.getString("username"); String pwd = rs.getString("password");
	 * System.out.println(id+" 用户名："+name+"密码："+pwd); } }
	 */
	
    public static DataSource getDataSource(String connectURI){
        BasicDataSource ds = new BasicDataSource();
         //MySQL的jdbc驱动
        ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
        ds.setUsername("root");              //所要连接的数据库名
        ds.setPassword("123");                //MySQL的登陆密码
        ds.setUrl(connectURI);
        return ds;
    }
	
	
}



