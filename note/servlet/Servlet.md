# Servlet
## 什么是Servlet
> Servlet（Server Applet），全称Java Servlet，未有中文译文。是用Java编写的服务器端程序。其主要功能在于交互式地浏览和修改数据，生成动态Web内容。

> 狭义的Servlet是指Java语言实现的一个接口，广义的Servlet是指任何实现了这个Servlet接口的类，一般情况下，人们将Servlet理解为后者。

> Servlet运行于支持Java的应用服务器中。从实现上讲，Servlet可以响应任何类型的请求，但绝大多数情况下Servlet只用来扩展基于HTTP协议的Web服务器。
最早支持Servlet标准的是JavaSoft的Java Web Server。此后，一些其它的基于Java的Web服务器开始支持标准的Servlet。
## Servlet技术点
### Servlet生命周期
- Servlet何时创建
    - 默认第一次访问Servlet时创建该对象.
- Servlet何时销毁
    - 服务器关闭Servlet就销毁了.
- 每次访问必执行的方法
    - service(ServletRequest servletRequest, ServletResponse servletResponse)
### servlet技术
- Servlet接口中的方法
    - void init(ServletConfig servletConfig) throws ServletException;
        - Servlet对象创建的时候执行
        - servletConfig代表的是该Servlet对象的配置信息.
    - ServletConfig getServletConfig();
        - 
    - void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException;
        - 每次请求都会执行
        - servletRequest代表请求,可以认为servletRequest内部封装的就是http的请求信息.
        - servletResponse代表响应,可以认为要封装的是响应的信息.
    - String getServletInfo();
        - 
    - void destroy();
        - Servlet对象销毁的时候执行.
### filter技术
- 过滤器
### listener技术
- 监听器
---
## Tomcat引擎
### 有请求到达时
- 解析请求的资源地址
- 创建Servlet对象
- 创建代表请求的ServletRequest对象和代表响应的ServletResponse对象,每次访问都会创建一对新的request和response对象
---
## Servlet配置
### 基本配置
    <servlet>
        <servlet-name>TestServlet</servlet-name>
        <servlet-class>com.dmh.web.TestServlet</servlet-class>
        <load-on-startup>3</load-on-startup>
    </servlet>
    <servlet-mapping>
        <servlet-name>TestServlet</servlet-name>
        <url-pattern>/testServlet</url-pattern>
    </servlet-mapping>

- 其中url-pattern的配置方式
    - 完全匹配:访问的资源与配置的资源完全相同才能访问
        - <url-pattern>/testServlet</url-pattern>
    - 目录匹配
        - <url-pattern>/aaa/bbb/ccc/*</url-pattern>
    - 扩展名匹配
        - <url-pattern>*.aa</url-pattern>
    
    ## 第二种与第三种不要混合使用
### 服务器启动实例化Servlet配置
- 在Servlet的配置中,加上load-on-startup,即可让Servlet在服务器启动时就创建.
- 配置的数字越小,优先级越高
### 缺省Servlet
- 可以将url-pattern配置为一个/,代表该Servlet是缺省的Servlet
- 什么是缺省的Servlet?
    - 当所访问的资源地址与所有的Servlet都不匹配时,缺省的Servlet负责处理.
    - Web应用中所有的资源的响应都是Servlet负责,包括静态资源.
### 欢迎页面
---
## ServletContext
### 什么是ServletContext
- ServletContext代表的是一个Web应用的环境(上下文)对象.
- ServletContext对象内部封装的是该Web应用的信息.
- ServletContext对象一个Web应用只有一个.
- ServletContext对象的生命周期
    - 创建:该Web应用被加载(服务器启动或应用被发布).
    - 销毁:Web应用被卸载(服务器关闭,移除该Web应用).
### 如何获得ServletContext
    getServletConfig().getServletContext();
    getServletContext();
    request.getServletContext();
### ServletContext的作用
- 获得Web应用全局的初始化参数
    - web.xml中配置初始化参数
    >
        <web-app>
            <context-param>
                <param-name>namespace</param-name>
                <param-value>value</param-value>
            </context-param>
        </web-app>
    - 初始化参数的获取
    >
        String value = getServletContext().getInitParameter("key");

- 获得Web应用中任何资源的绝对路径
    - getServletContext().getRealPath()是相对于Web应用的根目录
    - ClassLoader是相对于字节码的根目录,即WEB-INF的classes目录.
- ServletContext是一个域对象
    - 什么是域对象
        - 存储数据的区域就是域对象.
    - 什么是域
    - ServletContext域对象的作用范围
        - 整个Web应用
        - 所有的web资源都可以随意地向ServletContext域中存取数据,数据可以共享
        >
            ServletContext context = getServletContext();
            context.setAttribute("key", "value");   // 存储
            String value = (String) context.getAttribute("key"); // 获取
---
## 域对象的通用方法
- setAttribute(String key,Object value);
- getAttribute(String key);
- removeAttribute(String key);