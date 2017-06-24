# Listener

## 什么是监听器
> 监听器就是监听某个对象的状态变化的组件

## 监听器的相关概念
- 事件源
    - 被监听的对象-三个域对象
        1. request
        2. session
        3. servletContext
- 监听器
    - 监听事件源对象
    - 事件源对象的状态变化都会触发监听器
- 响应行为
    - 监听器监听到事件源的状态变化时所涉及的功能代码

## 监听器有哪些
- 第一纬度
    - 按照被监听的对象划分:ServletRequest域;HttpSession域;ServletContext域
- 第二纬度
    - 按照监听的内容划分:监听域对象的创建域销毁的;监听域对象的属性变化的.

||ServletContext域|HttpSession域|ServletRequest域|
|:--:|:--:|:--:|:--:|
|域对象的创建与销毁|ServletContextListener|HttpSessionListener|ServletRequestListener|
|域对象的属性变化|ServletContextAttributeListener|HttpSessionAttributeListener|ServletRequestAttributeListener|

## 监听三大域对象的创建域销毁
### ServletContextListener:监听ServletContext域的创建与销毁.
- Servlet域生命周期
    - 服务器启动时创建
    - 服务器关闭时销毁
- 监听器的创建
    - 实现监听器接口
    - 重写监听器方法
    - 配置web.xml
- 作用
    - 初始化工作,例如初始化对象,初始化数据,加载数据库驱动,连接池的初始化等
    - 加载一些初始化的配置,例如spring的配置文件
    - 任务调度,例如定时器,Timer/TimerTask

## 与Session中绑定的对象相关的监听器(对象感知监听器)

- 即将要被绑定到session中的对象有几种状态
    1. 绑定状态:一个对象被放到session域中
    2. 解绑状态:一个对象被从session中移除
    3. 钝化状态:将session内存中的对象持久化到磁盘
    4. 活化状态:将磁盘上的对象再次恢复到session中

### 绑定与解绑监听器HttpSessionBindingListener
>
    public void valueBound(HttpSessionBindingEvent httpSessionBindingEvent) {

    }

    public void valueUnbound(HttpSessionBindingEvent httpSessionBindingEvent) {

    }

    - 用于对象,比如Person类

### 钝化与活化监听器HttpSessionActivationListener
- 可以通过配置文件指定对象钝化时间
    - 对象多长时间不用被钝化
    - 在META-INF下创建context.xml
    >
        <Context>
        <!-- maxIdleSwap:session中的对象多长时间不使用就钝化 -->
        <!-- directory:钝化后的对象的文件写到磁盘的哪个目录下  配置钝化的对象文件在work/catalina/localhost/钝化文件 -->
            <Manager className="org.apache.catalina.session.PersistentManager" maxIdleSwap="1"><!--1分钟不用被钝化-->
                <Store className="org.apache.catalina.session.FileStore" directory="dir" />
            </Manager>
        </Context>

## 邮件服务器

### 邮件服务器的基本概念

- 邮件客户端
    - 可以值安装在电脑上的,也可以是网页形式的.
- 邮件服务器
    - 起到邮件的接受与推送作用
- 邮件发送协议
    - 协议:数据传输的约束
    - 邮件接收协议:POP3,IMAP
    - 邮件发送协议:SMTP