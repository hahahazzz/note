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

    - struts.xml

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