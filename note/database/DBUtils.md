# DBUtils
## 概述
- QueryRunner
    - 提供对sql语句操作的api
- ResultSetHandler接口
    - 用于定义select操作后,怎样封装结果集.
- DbUtils类
    - 它是一个工具类,定义了关闭资源与事务处理的方法
## QueryRunner核心类
- QueryRunner(DataSource ds)
    - 提供数据源(连接池),DBUtils底层自动维护连接connection
- update(String sql,Object... params)
    - 执行更新数据
- query(String sql,ResultSetHandler<T> rsh,Object... params)
    - 执行查询
### ResultSetHandler结果集处理类
- ArrayHandler
    - 将结果集中的第一条记录封装到一个Object[]数组中,数组中的每一个元素就是这条记录中的每一个字段的值.
- ArrayListHandler
    - 将结果集中的每一条记录都封装到一个Object[]数组中,将这些数组再封装到List集合中.
- BeanHandler
    - 就昂结果集中第一条记录封装到一个指定的javaBean中
- BeanListHandler
    - 将结果集中每一条记录封装到指定的javaBean中,再将这些javaBean封装到List集合中.
- ColumnListHandler
    - 将结果集中指定的列的字段值,封装到一个List集合中.
- KeyedHandler
    - 将结果集中每一条记录封装到Map<String,Object>,再将这个map集合作为另一个Map的value,另一个map集合的key是指定的字段的值.
- MapHandler
    - 将结果集中第一条记录封装到Map<String,Object>集合中,key就是字段名,value是字段值
- MapListHandler
    - 将结果集中每一条记录封装到Map<String,Object>集合中,key就是字段名,value是字段值,再将这些map封装到List集合中.
- ScalarHandler
    - 用于单数据,例如select count(*) from 表 操作.