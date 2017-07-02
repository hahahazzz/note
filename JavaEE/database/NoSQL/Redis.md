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
        ---
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
---

- Set

    - 在Redis中,我们可以将Set类型看作没有排序的字符集合,和List类型一样,我们也可以在该类型的数据值上执行添加删除或判断某一元素是否存在等操作,这些操作的时间都是常量时间.

    - Set可包含的最大元素是4294967295.

    - Set集合中不允许出现重复的元素.

    - 和List类型相比,Set类型在功能上还存在着一个非常重要的特性,即在服务器端完成多个Set之间的聚合计算操作.如unions,intersections和differences,由于这些操作均在服务端完成,因此效率极高.也节省了大量网络IO开销.

    ---

    1. sadd key value1、value2…
    
        - 向set中添加数据，如果该key的值已有则不会重复添加

    2. smembers key
    
        - 获取set中所有的成员

    3. scard key
    
        - 获取set中成员的数量

    4. sismember key member
    
        - 判断参数中指定的成员是否在该set中，1表示存在，0表示不存在或者该key本身就不存在

    5. srem key member1、member2…
    
        - 删除set中指定的成员
    
    6. srandmember key
    
        - 随机返回set中的一个成员

    7. sdiff sdiff key1 key2
    
        - 返回key1与key2中相差的成员，而且与key的顺序有关。即返回差集。

    8. sdiffstore destination key1 key2
    
        - 将key1、key2相差的成员存储在destination上

    9. sinter key[key1,key2…]
    
        - 返回交集

    10. sinterstore destination key1 key2
    
        - 将返回的交集存储在destination上

    11. sunion key1、key2
    
        - 返回并集。

    12. sunionstore destination key1 key2
    
        - 将返回的并集存储在destination上
---

- sortedset

    - Sorted-Sets和Sets类型极为相似，它们都是字符串的集合，都不允许重复的成员出现在一个Set中。它们之间的主要差别是Sorted-Sets中的每一个成员都会有一个分	数(score)与之关联，Redis正是通过分数来为集合中的成员进行从小到大的排序。

    - 尽管Sorted-Sets中的成员必须是唯一的，但是分数(score)却是可以重复的。

    - 在Sorted-Set中添加、删除或更新一个成员都是非常快速的操作，其时间复杂度为	集合中成员数量的对数.

    - 由于Sorted-Sets中的成员在集合中的位置是有序的，因此,即便是访问位于集合中部的成员也仍然是非常高效的。事实上，Redis所具有的这一特征在很多其它类型的数据库中是很难实现的，换句话说，在该点上要想达到和Redis同样的高效，在其它数据库中进行建模是非常困难的.

    ---

    1. zadd key score member score2 member2 … 
    
        - 将所有成员以及该成员的分数存放到sorted-set中

    2. zcard key
    
        - 获取集合中的成员数量

    3. zcount key min max
    
        - 获取分数在[min,max]之间的成员

    4. zincrby key increment member
    
        - 设置指定成员的增加的分数。

    5. zrange key start end [withscores]
    
        - 获取集合中脚标为start-end的成员，[withscores]参数表明返回的成员包含其分数。

    6. zrangebyscore key min max [withscores] [limit offset count]
    
        - 返回分数在[min,max]的成员并按照分数从低到高排序。
        
        - [withscores]：显示分数；
        
        - [limit offset count]：offset，表明从脚标为offset的元素开始并返回count个成员。

    7. zrank key member
    
        - 返回成员在集合中的位置。

    8. zrem key member[member…]
    
        - 移除集合中指定的成员，可以指定多个成员。

    9. zscore key member
    
        - 返回指定成员的分数
---

- hash

    - Redis中的Hashes类型可以看成具有String Key和String Value的map容器。所以该类型非常适合于存储值对象的信息.

    - 如果Hash中包含很少的字段，那么该类型的数据也将仅占用很少的磁盘空间。每一个Hash可以存储4294967295个键值对.

    ---

    1. hset key field value
    
        - 为指定的key设定field/value对(键值对)

    2. hgetall key
    
        - 获取key中的所有filed-vaule

    3. hget key field
    
        - 返回指定的key中的field的值

    4. hmset key fields
    
        - 设置key中的多个filed/value

    5. hmget key fileds
    
        - 获取key中的多个filed的值

    6. hexists key field
    
        - 判断指定的key中的filed是否存在

    7. hlen key
    
        - 获取key所包含的field的数量

    8. hincrby key field increment
    
        - 设置key中filed的值增加increment
---

- keys的通用操作

    - keys pattern

        - 获取所有与pattern匹配的key,返回所有与该key匹配的keys.

        - *代表一个或多个字符,?代表任意一个字符.

    - del key1 key2 ...

        - 删除指定的key

    - exists key

        - 判断该key是否存在,1代表存在,0代表不存在

    - rename key newKey

        - 重命名key为newKey

    - expire key

        - 设置过期事件,单位:秒

    - ttl key

        - 获取key所剩的超时时间.
        
        - 如果没有设置超时,返回-1,返回-2表示超时不存在.

    - type key

        - 获取key的类型

        - 返回格式:字符串

        - 字符串有string,list,set,hash,zset,如果key不存在返回none