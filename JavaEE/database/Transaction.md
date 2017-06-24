# [Transaction](https://zh.wikipedia.org/wiki/%E6%95%B0%E6%8D%AE%E5%BA%93%E4%BA%8B%E5%8A%A1)
> 数据库事务（简称：事务）是[数据库管理系统](https://zh.wikipedia.org/wiki/%E6%95%B0%E6%8D%AE%E5%BA%93%E7%AE%A1%E7%90%86%E7%B3%BB%E7%BB%9F)执行过程中的一个逻辑单位，由一个有限的数据库操作序列构成。

## ACID性质
> 并非任意的对数据库的操作序列都是数据库事务。数据库事务拥有以下四个特性，习惯上被称之为[ACID特性](https://zh.wikipedia.org/wiki/ACID)。
1. 原子性（Atomicity）：事务作为一个整体被执行，包含在其中的对数据库的操作要么全部被执行，要么都不执行。
2. 一致性（Consistency）：事务应确保数据库的状态从一个一致状态转变为另一个一致状态。一致状态的含义是数据库中的数据应满足完整性约束。
3. 隔离性（Isolation）：多个事务并发执行时，一个事务的执行不应影响其他事务的执行。
4. 持久性（Durability）：已被提交的事务对数据库的修改应该永久保存在数据库中。

## mysql的事务
- 默认的事务
    - 一条sql语句就是一个事务,默认就开启事务并自动提交.
- 手动事务
    - 显式开启一条事务 : start transaction
    - 事务提交 : commit代表从开启事务到事务提交,中间的所有sql都认为有效真正的更新数据库.
    - 事务的回滚 : rollback代表事务的回滚,从开启事务到事务回滚,中间所有的sql操作都认为无效,数据库没有被更新.
---
## JDBC的事务
- 开启事务
    - connection.setAutoCommit(false);
- 提交事务
    - connection.commit();
- 回滚事务
    - connection.rollback();

### DBUtils事务操作
- QueryRunner
    - 有参构造
        - 有参构造将数据源(连接池)作为参数传入QueryRunner,QueryRunner会从连接池中获得一个数据库连接资源操作数据库,所以直接使用无Connection参数的方法即可操作数据库.
    - 无参构造
        -无参的构造函数没有将数据源(连接池)作为参数传入QueryRunner,那么我们在使用QueryRunner对象操作数据库时要使用有Connection参数的方法.
---
## 并发访问问题
> 由隔离性引起
- 如果不考虑隔离性,事务存在三种并发问题.
1. 脏读
    - B事务读取到了A事务尚未提交的数据.
2. 不可重复读
    - 一个事务中,两次读取的数据内容不一致.
3. 幻读/虚读
    - 一个事务中,两次读取的数据的数量不一致.
---

## 事务的隔离级别
1. read uncommitted
    - 读取尚未提交的数据.
2. read committed
    - 读取已经提交的数据.可以解决脏读.
3. repeatable read
    - 重读读取,可以解决脏读和不可重复读.
4. serializable
    - 串行化,可以解决脏读,不可重复度和虚读.
### mysql数据库默认的隔离级别
- 查询当前隔离级别
    >
        SELECT @@tx_isolation;
- 设置隔离级别
    >
        SET SESSION TRANSACTION ISOLATION LEVEL READ UNCOMMITTED ;
        SET SESSION TRANSACTION ISOLATION LEVEL READ COMMITTED ;
        SET SESSION  TRANSACTION ISOLATION LEVEL REPEATABLE READ ;
        SET SESSION TRANSACTION ISOLATION LEVEL SERIALIZABLE ;