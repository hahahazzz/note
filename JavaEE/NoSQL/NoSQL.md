# NoSQL

### NoSQL概述

> NoSQL,Not Only SQL,即不仅仅是SQL,是一项全新的数据库概念,泛指非关系型数据库. 

### NoSQL四大分类

#### 1. 键值(Key-Value)存储数据库
- 相关产品
    - Tokyo Cabinet/Tyrant,Redis,Voldemort,Berkeley DB
- 典型应用
    - 内容缓存,主要用于处理大量数据的高访问负载.
- 数据模型
    = 一系列键值对
- 优势
    - 快速查询
- 劣势
    - 存储的数据缺少结构化

#### 2. 列存储初数据库
- 相关产品
    - Cassandra,HBase,Riak
- 典型应用
    - 分布式的文件系统
- 数据模型
    - 以列簇式存储,将统一列数据存在一起
- 优势
    - 查找速度快,可扩展性强,更容易进行分布式扩展
- 劣势
    - 功能相对局限

#### 3. 文档型数据库
- 相关产品
    - CouchDB,MongoDB
- 典型应用
    - Web应用(与key-value类似,value是结构化的)
- 数据模型
    - 一系列键值对
- 优势
    - 数据结构要求不严格
- 劣势
    - 查询性能不搞,而且缺乏同意的查询语法

#### 4. 图形(Graph)数据库
- 相关产品
    - Neo4J,InfoGrid,Infinite Graph
- 典型应用
    - 社交网络
- 数据模型
    - 图结构
- 优势
    - 利用图结构相关算法
- 劣势
    - 需要对整个图做计算才能得出结果,不容易做分布式的集群方案

### NoSQL特点

#### 在大数据存储上具备关系型数据库无法比拟的性能优势.

#### 1.易扩展
- NoSQL数据库种类繁多,但是一个共同的特点都是去掉关系数据库的关系型特性.数据之间无关系,这样就非常容易扩展,也无形之间在架构的层面上带来了可扩展的能力.

#### 2.大数据量,高性能
- NoSQL数据库都具有非常高的读写性能,尤其在大数据量下,同样表现优异,这得益于它的无关系性,数据库的结构简单.

#### 3.灵活的数据模型
- NoSQL无需事先为存储的数据建立字段,随时可以存储自定义的数据格式,而在关系数据库里,增删字段是一件非常麻烦的事情.特别是非常大数据量的表,这点在大数据量的Web2.0时代尤其明显.

#### 4.高可用
- NoSQL在不太影响性能的情况下,就可以方便的实现高可用的架构.例如Cassandra,HBase模型,通过复制模型也能实现高可用.