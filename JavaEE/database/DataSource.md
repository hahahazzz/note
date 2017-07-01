# 数据库连接池
> 用池来管理Connection,这样就可以复用Connection.有了池,我们就不用自己去创建Connection,而是通过池来获取Connection对象,当使用完Connection后,调用Connection的close()方法也不会真的关闭Connection,而是把Connection归还给池,池就可以再利用这个Connection对象.
---
## C3P0连接池
- c3p0-config.xml,位置为src目录下,IDEA启用maven支持则在resources文件夹下.
### 必须项
- jdbcUrl:路径,mysql路径 jdbc:mysql://localhost:3306/数据库
- driverClass:驱动,mysql驱动com.mysql.jdbc.Driver
- user:用户名
- password:密码
### 基本配置
- acquireIncrement:连接池无空闲连接可用时,一次性创建的新连接数,默认值3.
- initialPoolSize:连接池初始化时创建的连接数,默认值3
- maxPoolSize:连接池中拥有的最大连接数,默认值15
- minPoolSize:连接池保持的最小连接数
- maxIdleTime:连接的最大空闲时间,如果超过这个时间,某个数据库连接还没有被使用,则会断开这个连接,如果为0,则永远不会断开连接,默认值0
### 管理连接池的大小和连接的生存时间(扩展)
- maxConnectionAge:配置连接的生存时间,超过这个时间的连接将由连接池自动断开丢弃掉.当然正在使用的连接不会马上断开,而是等待它close再断开,配置为0的时候则不会对连接的生存时间进行限制,默认值0
### C3P0使用
>
    DataSource dataSource = new ComboPooledDataSource(); // 加载默认的配置
    DataSource dataSource = new ComboPooledDataSource("configName");// 加载配置文件中configName的配置
---
## DBCP连接池
- *.properties,位置为src目录下,IDEA启用maven支持则在resources文件夹下.
### 必须项
- driverClassName:驱动
- url:数据库
- username:用户名
- password:密码
### 基本项
- initialSize=10:初始化连接
- maxActive=50:最大连接数量
- maxIdle=20:最大空闲连接
- minIdle:最小空闲连接
- initialSize:初始化连接
### 优化配置
- logAbandoned:连接被泄漏时是否打印
- removeAbandoned:是够自动回收超时连接
- removeAbandonedTimeout:超时时间,单位秒
- maxWait:超时等待时间,单位毫秒
- timeBetweenEvictionRunsMillis:在空闲连接回收器线程运行期间休眠的时间值,单位毫秒
- numTestsPerEvictionRun:在每次空闲连接回收器线程(如果有)运行时检查的连接数量
### DBCP使用
>
    Properties properties = new Properties();
    properties.load(C3p0Test.class.getClassLoader().getResourceAsStream("dbcp.properties"));
    DataSource source = BasicDataSourceFactory.createDataSource(properties);