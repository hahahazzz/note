# XML
> 可扩展标记语言（英语：Extensible Markup Language，简称：XML），是一种标记语言。标记指计算机所能理解的信息符号，通过此种标记，计算机之间可以处理包含各种信息的文章等
> XML语法上和HTML比较相似,但HTML中的元素是固定的,而XML的标签是可以由用户自定义的.
## 语法
### XML文档声明
>
    <?xml version="1.0" encoding="utf-8" ?>
- 文档声明必须以```<?xml``` 开头,```?>```结尾
- 文档声明必须从文档第0行第0列开始
- 文档声明只有三个属性
    - version:指定XML文档版本,必须属性,因为我们不会选择1.1,只会选择1.0.
    - encoding:指定文档编码,可选属性,默认为utf-8.
### 元素element
- 元素是XML文档中最重要的组成部分
- 普通元素的结构开始标签、元素体、结束标签组成。例如：
    >    
        <hello>大家好</hello>
- 元素体：元素体可以是元素，也可以是文本，例如：
    > 
        <b><a>你好</a></b>
- 空元素：空元素只有开始标签，而没有结束标签，但元素必须自己闭合，例如：```<c/>```
- 元素命名：
    - 区分大小写
    - 不能使用空格，不能使用冒号
    - 不建议以XML、Xml, xml开头
- 格式化良好的XML文档，必须只有一个根元素
### 属性
- 属性是元素的一部分，它必须出现在元素的开始标签中
- 属性的定义格式：属性名：属性值，其中属性值必须使用单引或双引
- 一个元素可以有0、N个属性，但一个元素中不能出现同名属性
- 性名不能使用空格、冒号等特殊字符，且必须以字母开头
### CDATA区
>
    <![CDATA[
        内容
    ]]>
## 应用常见
- 配置文件
    >
        <?xml version="1.0" encoding="utf-8" ?>
        <c3p0-config>
            <named-config name="intergalactoApp">
                <property name="acquireIncrement">50</property>
                <property name="initialPoolSize">100</property>
                <property name="minPoolSize">50</property>
                <property name="maxPoolSize">1000</property>

                <!-- intergalactoApp adopts a different approach to configuring statement caching -->
                <property name="maxStatements">0</property>
                <property name="maxStatementsPerConnection">5</property>

                <!-- he's important, but there's only one of him -->
                <user-overrides user="master-of-the-universe">
                    <property name="acquireIncrement">1</property>
                    <property name="initialPoolSize">1</property>
                    <property name="minPoolSize">1</property>
                    <property name="maxPoolSize">5</property>
                    <property name="maxStatementsPerConnection">50</property>
                </user-overrides>
            </named-config>
        </c3p0-config>
- 存放数据
    >
        <?xml version="1.0" encoding="utf-8"?>
        <persons>
            <person>
                <name>小明</name>
                <age>20</age>
                <gender>男</gender>
            </person>
            <person>
                <name>小红</name>
                <age>18</age>
                <gender>女</gender>
            </person>
        </persons>
---
## DTD约束
> 常见的XML约束:DTD,Schema
### 什么是DTD
- DTD(Document Type Definition),文档类型定义,用来约束XML文档,规定XML文档中元素的名称,子元素的名称及顺序,元素的属性等.
---
## Schema约束
### 什么是Schema
- Schema是新的XML文档约束
- Schema要比DTD强大很多,是DTD的替代者
- Schema本身也是XML文档,但Schema文档扩展名为xsd,而不是xml
- Schema功能更强大,数据类型更完善
- Schema支持名称空间