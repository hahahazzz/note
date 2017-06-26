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
        jedis.close();
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

        > redis默认以protect模式启动,执行1或3之后,在启动时添加启动参数

            ./bin/redis-server ./redis.conf --protected-mode no

- Jedis连接池

        JedisPoolConfig config = new JedisPoolConfig();
        config.setMinIdle(10);  // 最小闲置数
        config.setMaxIdle(30);  // 最大闲置数
        config.setMaxTotal(50); // 最大连接数
        JedisPool pool = new JedisPool(config, "xxx.xxx.xxx.xxx", 6379);
        Jedis resource = pool.getResource();
        resource.set("key", "value");
        resource.close();
---

### Redis数据结构

- Redis是一种高级的key-value存储系统,其中value支持五种数据类型
    1. 字符串(String)
    2. 哈希(hash)
    3. 字符串列表(list)
    4. 字符串集合(set)
    5. 有序字符串集合(sorted set)
    ---
    - 关于key的定义,需要注意几点:
        1. key不要太长,最好不超过1024个字节,这不仅小号内存,还降低查找效率,
        2. key不要太短,太短会降低key的可读性.
        3. 在项目中,key最好有一个统一的命名规范.
---

### Redis数据操作
- String

    - set key value   // 存
    - get key         // 取
    - getset key newValue // 先取值,然后设置新值
    - del key         // 删除指定key
    ---
    1. 字符串是Redis中最基础的数据存储类型,它在Redis中是二进制安全的,这意味着该类型存入和获取的数据相同.
    2. 在Redis中,字符串类型的value最多可以容纳的长度是512M.

    ---
    
    - 数值增减

            // 将指定key的value原子性的递增1
            // 如果key不存在,其初始值为0,在incr之后,值为1
            // 如果value的值不能转为整型,该操作将执行失败并返回相应的错误信息.
            incr key

            // 将指定key的value原子性的递减1
            // 如果key不存在,其初始值为0,在decr之后,值为-1
            // 如果value的值不能转为整型,该操作将执行失败并返回相应的错误信息.
            decr kye

        - 扩展

                // 将指定key的value原子性的递增increment
                // 如果key不存在,其初始值为0,在incrby之后,值为increment
                // 如果value的值不能转为整型,该操作将执行失败并返回相应的错误信息.
                incrby num increment

                // 将指定key的value原子性的递增decrement
                // 如果key不存在,其初始值为0,在decrby之后,值为decrement
                // 如果value的值不能转为整型,该操作将执行失败并返回相应的错误信息.
                decrby num decrement

                // 拼接字符串(该操作会打印字符串长度)
                // 如果key存在,则在原有的value后追加指定的value
                // 如果key不存在,则重新创建一个key-value
                append key value
---

- List

    - Redis中,List类型是按照插入顺序排序的字符串链表.和普通数据结构中的普通链表一样,我们可以在其头部(left)和尾部(right)添加新的元素.

    - 插入时,如果key不存在,Redis将为该key创建一个新的链表.

    - 如果链表中所有的元素均被移除,那么该key也会被从数据库中删除.

    - List中可以包含的最大元素数量是4294967295.

    - 从元素插入和删除的效率视角来看,如果我们是在链表的两头插入或删除数据,这将是非常高效的操作,即使链表中已经存储了百万条记录,该操作也可以在常量时间内完成.要说明的是,如果元素插入或删除作用于链表中间,那将会是非常低效的.

    1. lpush key value1 value2...

        - 在指定的key所关联的list的头部插入所有的values
        - 如果该key不存在，该命令在插入的之前创建一个与该key关联的空链表，之后再向该链表的头部插入数据。
        - 插入成功，返回元素的个数。

    2. rpush key value1、value2…

        - 在该list的尾部添加元素

    3. lrange key start end

        - 获取链表中从start到end的元素的值，start、end可为负数，若为-1则表示链表尾部的元素，-2则表示倒数第二个，依次类推
        - lrange key 0 -1表示查询所有元素

    4. lpushx key value

        - 仅当参数中指定的key存在时(如果与key管理的list中没有值时，则该key是不存在的)在指定的key所关联的list的头部插入value。

    5. rpushx key value
    
        - 在该list的尾部添加元素

    6. lpop key
    
        - 返回并弹出指定的key关联的链表中的第一个元素，即头部元素。

    7. rpop key
    
        - 从尾部弹出元素。

    8. rpoplpush resource destination
    
        - 将链表中的尾部元素弹出并添加到头部

    9. llen key
    
        - 返回指定的key关联的链表中的元素的数量。

    10. lset key index value
        
        - 设置链表中的index的脚标的元素值，0代表链表的头元素，-1代表链表的尾元素。

    11. lrem key count value

        - 删除count个值为value的元素
        - 如果count大于0，从头向尾遍历并删除count个值为value的元素
        - 如果count小于0，则从尾向头遍历并删除
        - 如果count等于0，则删除链表中所有等于value的元素。

    12. linsert key before|after pivot value
        
        - 在pivot元素前或者后插入value这个元素。