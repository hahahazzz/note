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