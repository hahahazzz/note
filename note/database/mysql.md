# mysql
## 基础
### 创建库
>
    /*指定编码*/
    CREATE DATABASE IF NOT EXISTS Web08 CHARACTER SET utf8;
    /*默认编码*/
    CREATE DATABASE IF NOT EXISTS Web08;
### 查询已经存在的数据库
>
    SHOW DATABASES;
### 切换到指定数据库
>
    USE web08;/*切换到web08的数据库*/
### 查看当前正在操作的数据库
>
    SELECT database();
### 创建表
>
    CREATE TABLE IF NOT EXISTS user (
        id INTEGER PRIMARY KEY AUTO_INCREMENT,
        username VARCHAR(20),
        password VARCHAR(100)
    );
### 列出数据库中已存在的表
>
    SHOW TABLES;
### 显示创建数据库的语句
>
    SHOW CREATE DATABASE web08;
### 显示创建表的语句
>
    SHOW CREATE TABLE user;
### 查看表结构
>
    DESC user;
### 删除数据库
>
    DROP DATABASE Web08;
### 删除表
>
    DROP TABLE user;/*删除表数据并删除表结构*/
    DELETE FROM user;/*删除表数据*/
    TRUNCATE TABLE user;/*摧毁表结构,再重新建一张相同的表,auto_increment将重置为0*/
- 删除表中所有记录,使用delete from 表名还是truncate table 表名?
    - 删除方式:
        - delete一条一条删除,不会清空auto_increment记录数.
        - truncate直接将表删除(摧毁),重新建一张相同的表,auto_increment将被重置从0开始.
    - 事务方面:
        - delete删除的数据,如果是在一个事务中,可以找回.
        - truncate删除的数据无法找回.
### 修改表
>
    ALTER TABLE user MODIFY password VARCHAR(255);/*修改password长度*/
    ALTER TABLE user ADD gender VARCHAR(10);/*添加gender列*/
    ALTER TABLE USER DROP text;/*删除一列*/
    ALTER TABLE user CHARACTER SET utf8;/*修改表编码*/
### 修改表名
>
    RENAME TABLE 表名 TO 新表名;
### 外键
>
    ALTER TABLE product ADD FOREIGN KEY (category_id) REFERENCES category (cid);
---
## JDBC开发步骤
### 注册驱动
- 分析步骤1
    - JDBC规范定义驱动接口:java.sql.Driver,mysql驱动包提供了实现类com.mysql.jdbc.Driver
- 分析步骤2
    - DriverManager工具类,提供注册驱动的方法:registerDriver(),方法参数是java.sql.Driver,所以可以通过DriverManager.registerDriver(new com.mysql.jdbc.Driver());
    - 不足:
        - 硬编码,后期不易于程序扩展和维护.
        - 驱动被注册两次.
- 分析步骤3
    - 开发通常使用Class.forName()加载一个使用字符串描述的驱动类
    >
        Class.forName("com.mysql.jdbc.Driver");
    - 如果使用Class.forName()将类加载到内存,该类的静态代码将自动执行.com.mysql.jdbc.Driver源码中,会主动进行Driver注册.
    >
        public class Driver extends NonRegisteringDriver implements java.sql.Driver {
            public Driver() throws SQLException {
            }

            static {
                try {
                    DriverManager.registerDriver(new Driver());
                } catch (SQLException var1) {
                    throw new RuntimeException("Can't register driver!");
                }
            }
        }
### 获得连接
>
    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/数据库", "username", "password");
### 获得语句执行者
>
    Statement statement = connection.createStatement();
### 执行sql语句
>
    String sql = "SELECT * FROM user";
    ResultSet resultSet = statement.executeQuery(sql);
### 处理结果
>
     while (resultSet.next()) {
        int id = resultSet.getInt(1);
        String username = resultSet.getString(2);
        String password = resultSet.getString(3);
        String gender = resultSet.getString(4);
        System.out.println(String.format("%d,%s,%s,%s", id, username, password, gender));
    }    
### 释放资源
>
    resultSet.close();
    statement.close();
    connection.close();