# [Transaction](https://zh.wikipedia.org/wiki/%E6%95%B0%E6%8D%AE%E5%BA%93%E4%BA%8B%E5%8A%A1)
> 数据库事务（简称：事务）是[数据库管理系统](https://zh.wikipedia.org/wiki/%E6%95%B0%E6%8D%AE%E5%BA%93%E7%AE%A1%E7%90%86%E7%B3%BB%E7%BB%9F)执行过程中的一个逻辑单位，由一个有限的数据库操作序列构成。

## ACID性质
> 并非任意的对数据库的操作序列都是数据库事务。数据库事务拥有以下四个特性，习惯上被称之为[ACID特性](https://zh.wikipedia.org/wiki/ACID)。
1. 原子性（Atomicity）：事务作为一个整体被执行，包含在其中的对数据库的操作要么全部被执行，要么都不执行。
2. 一致性（Consistency）：事务应确保数据库的状态从一个一致状态转变为另一个一致状态。一致状态的含义是数据库中的数据应满足完整性约束。
3. 隔离性（Isolation）：多个事务并发执行时，一个事务的执行不应影响其他事务的执行。
4. 持久性（Durability）：已被提交的事务对数据库的修改应该永久保存在数据库中。