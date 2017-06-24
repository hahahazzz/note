# Tomcat
## Web开发中常见概念
### B/S系统和C/S系统
- Browser/Server:浏览器 服务器 系统
- Client/Server:客户端 服务器 系统
### Web应用服务器
> 供向外部发布Web资源的服务器软件
### Web资源
> 存在于Web应用服务器可供外界访问的资源就是Web资源.
    - 存在于Web服务器内部的html,css,js,图片,音频,视频等.
- 静态资源
    - Web页面中供人们浏览的数据始终是不变的,如HTML,CSS,JS,图片,多媒体等
- 动态资源
    - Web页面中供人们浏览的数据是由程序产生的,不同时间点访问web页面看到的内容各不相同,如JSP/Servelt,ASP,PHP等.
---
## Web开发中常见的Web应用服务器
### weblogic
- Oracle公司的大型收费Web服务器,支持全部JavaEE规范
### websphere
- IBM公司的大型收费Web服务器,支持全部JavaEE规范
### Tomcat
- Apache开源组织下的开源免费的中小型Web服务器,支持JavaEE中的servlet和jsp规范.
---
## Tomcat目录结构
- bin : 可执行文件
- conf : 配置文件
    - server.xml : tomcat核心配置文件
    - tomcat-users.xml : 用户权限配置文件
    - web.xml : 所有web项目默认配置文件
- lib : jar包,类库
- logs : 日志
- temp : 临时文件
- webapps : 默认情况下发布web项目所存放的目录
- work : tomcat处理jsp的工作目录
### Web应用的目录结构
    |- 项目名称(webapps文件夹)
        |- 静态资源,HTML,CSS,JS
        |- WEB-INF,不能通过浏览器直接访问
            |- web.xml 当前web项目的核心配置,servlet2.5必须有,3.0可以省略
            |- lib 当前web项目所需的第三方jar存放位置
            |- classes java源码编译后生成class的存放位置