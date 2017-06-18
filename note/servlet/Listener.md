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