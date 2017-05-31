# Mysql
## 基础
### 创建库
>
    CREATE DATABASE IF NOT EXISTS Web08;
### 查询已经存在的数据库
>
    SHOW DATABASES;
### 切换到指定数据库
>
    USE web08;/*切换到web08的数据库*/
### 创建表
>
    CREATE TABLE IF NOT EXISTS user (
        id       INTEGER PRIMARY KEY AUTO_INCREMENT,
        username VARCHAR(20),
        password VARCHAR(255)
    );
### 列出数据库中已存在的表
>
    SHOW TABLES;