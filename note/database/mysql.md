# Mysql
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
### 修改表
>
    ALTER TABLE user MODIFY password VARCHAR(255);/*修改password长度*/
    ALTER TABLE user ADD gender VARCHAR(10);/*添加gender列*/
    ALTER TABLE USER DROP text;/*删除一列*/
    ALTER TABLE user CHARACTER SET utf8;/*修改表编码*/
### 修改表名
>
    RENAME TABLE 表名 TO 新表名;