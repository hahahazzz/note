# struts2基本环境搭建

#### struts2核心过滤器配置

- 在web.xml中的web-app节点下添加

        <filter>
            <filter-name>struts2</filter-name>
            <filter-class>org.apache.struts2.dispatcher.filter.StrutsPrepareAndExecuteFilter</filter-class>
        </filter>
        <filter-mapping>
            <filter-name>struts2</filter-name>
            <url-pattern>/*</url-pattern>
        </filter-mapping>

#### xml文件配置

- 文件名

    - struts.xml,位于src目录下.IDEA在resources目录下.

- 文件内容组成

        <?xml version="1.0" encoding="UTF-8"?>
        <!DOCTYPE struts PUBLIC
                "-//Apache Software Foundation//DTD Struts Configuration 2.5//EN"
                "http://struts.apache.org/dtds/struts-2.5.dtd">
        <struts>
            <!--dev模式-->
            <constant name="struts.devMode" value="true"/>
            <package name="struts01" namespace="/struts01" extends="struts-default">
                <action name="HelloAction" method="hello">
                    <result name="success">/hello.jsp</result>
                </action>
            </package>
        </struts>

---

#### 节点说明

- include : 可以引入其他struts配置文件

- package : 将Action配置封装,可以在package中配置很多action

    - name属性:package的名字,标识作用,不可重复.

    - namespace属性:给aciton的访问路径定义一个命名空间.

    - entends属性:继承一个指定包.

    - abstract属性:package是否是抽象的.标识性属性.标识该package不能独立运行,专门用来继承.

- action : 配置Action类

    - name属性:决定了Action的访问资源名

    - class属性:Action的完整类名.

    - method属性:指定调用Action中的哪个方法来处理请求,默认为execute

- result : 结果配置

    - name属性:标识结果处理的名称,与Action方法的返回值对应.默认为success

    - type属性:指定调用哪一个Result类处理结果.默认使用转发dispatcher(在struts-default有默认配置).

    - 标签体:填写页面的相对路径.

- default-action-ref : 如果找不到package下的action,会使用此项配置的Action进行处理

    - 需要在其他action之前配置

---

#### struts2常量配置

1. struts2默认常量配置位置

    - org.apache.struts2下的default.properties

2. 修改struts2常量配置(顺序为加载顺序,推荐第2种)

    1. 新建struts.properties文件(与struts.xml同级),在文件内配置需要修改的常量.

    2. 在struts.xml中的struts(根节点)下使用constant来修改配置.

    3. 在web.xml中配置,在web-app(根节点)下使用context-param进行配置

3. 常量配置

        <!--dev模式,默认false-->
        <constant name="struts.devMode" value="true"/>
        <!--国际化,解决post提交乱码问题-->
        <constant name="struts.custom.i18n.resources" value="UTF-8"/>
        <!--指定访问action的后缀名-->
        <constant name="struts.action.extension" value="action,,"/>
        <!--允许动态方法调用,默认是false-->
        <constant name="struts.enable.DynamicMethodInvocation" value="true"/>

---

#### struts2动态方法调用

- 首先配置```<constant name="struts.enable.DynamicMethodInvocation" value="true"/>```

##### 第一种方式

- action节点无需配置method属性

- 调用:actionName!method,如DynamicAction!add

    - struts 2.5报404错误,Message为Method add for action DynamicAction is not allowed!解决方法
        在package节点下添加```<global-allowed-methods>regex:.*</global-allowed-methods>```,与action配置同级

##### 第二种方式:通配符

- 在aciton的name字符串中插入```*```,在method中可以获取到name中匹配的内容.以类似{1}的方式获取

- 调用:actionNamemethod,如DynamicActionadd