# Redis

### 什么是Redis

- Redis是用C语言开发的一个开源的高性能键值对(key-value)数据库,它通过提供多重键值对数据类型来适应不同场景下的存储需求,目前为止,Redis支持的键值数据类型如下

    1. 字符串类型
    2. 散列类型
    3. 列表类型
    4. 集合类型
    5. 有序集合类型

### Redis应用场景

- 缓存(数据查询,短链接,新闻内容,商品内容等).(最多使用)
- 聊天室的在线好友列表
- 任务队列.(秒杀,抢购,12306)
- 应用排行榜
- 网络访问统计
- 数据过期处理(可以精确到毫秒)
- 分布式集群架构中的session分离.

### Redis安装

- 1.安装gcc

    yum install gcc-c++

- 2.编译Redis
    - 进入redis解压目录,执行make
    - 如果报如下错误,可以尝试执行 make MALLOC=libc

            cd src && make all
            make[1]: Entering directory `/usr/local/redis-3.2.9/src'
                CC adlist.o
            In file included from adlist.c:34:0:
            zmalloc.h:50:31: fatal error: jemalloc/jemalloc.h: No such file or directory
            #include <jemalloc/jemalloc.h>
                                        ^
            compilation terminated.
            make[1]: *** [adlist.o] Error 1
            make[1]: Leaving directory `/usr/local/redis-3.2.9/src'
            make: *** [all] Error 2

- 3.编译成功之后,执行如下命令安装redis到/usr/local/redis目录

        make PREFIX=/usr/local/redis install

- 4.复制解压目录中的redis.conf到安装目录

        cp redis.conf /usr/local/redis

- 5.开放6379端口

    - 参考[Linux#Port](../linux/Port.md)

- 6.启动redis

    - 执行redis安装目录中bin/redis-server

### Redis启动与关闭

#### 1.启动
- 前端模式
    
    - 直接运行bin/redis-server将以前端模式启动,前端模式启动的缺点是启动完成后,不能再进行其他操作,如果要操作必须使用Ctrl+c,同时redis-server程序结束.

- 后端模式

    - 修改redis.conf配置文件,daemonize yes 以后端模式启动
        - 文件内容太长,可以输入/关键字,然后回车进行查找
        >        
            vim /usr/local/redis/redis.conf

    - 启动时,指定配置文件

            ./bin/redis-server ./redis.conf

    - Redis默认端口6379,通过当前服务进行查看
    
            ps -ef | grep -i redis

#### 2.关闭

- 向redis安装目录下的bin目录下redis-cli传递shutdown命令

        ./bin/redis-cli shutdown

### Redis远程访问

- java代码示例

        // 参数分别是远程IP地址和端口
        Jedis jedis = new Jedis("192.168.189.128", 6379);
        // 获取已存在的username
        String username = jedis.get("username");
        System.out.println(username);
        jedis.set("addr","jiangsu");
        System.out.println(jedis.get("addr"));

        输出:
            zhangsan
            jiangsu

    - 依赖包

            <dependency>
                <groupId>redis.clients</groupId>
                <artifactId>jedis</artifactId>
                <version>2.9.0</version>
            </dependency>
            <dependency>
                <groupId>org.apache.commons</groupId>
                <artifactId>commons-pool2</artifactId>
                <version>2.4.2</version>
            </dependency>

- 可能遇到的问题

        redis.clients.jedis.exceptions.JedisConnectionException: java.net.ConnectException: Connection refused: connect
    
    - 解决方案:编辑redis.conf文件中的bind条目
        1. 直接注释,局域网内所有计算机都能访问
        2. bind localhost,只能本机访问
        3. bind 局域网指定IP,只有指定IP才能访问
        4. redis默认以protect模式启动,在启动时添加启动参数

                ./bin/redis-server ./redis.conf --protected-mode no