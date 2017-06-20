# Filter

> 定义一个过滤器,实现Filter接口,在web.xml中配置即可.
>
    <filter>
        <filter-name>Demo1Filter</filter-name>
        <filter-class>com.dmh.web.filter.Demo1Filter</filter-class>
    </filter>
    <filter-mapping>
        <filter-name>Demo1Filter</filter-name>
        <!--对所有资源都进行过滤-->
        <url-pattern>/*</url-pattern>
    </filter-mapping>
- Filter的执行顺序由定义在web.xml中的filter-mapping顺序决定
- Filter的url-pattern有三种匹配方式
    1. 具体资源路径匹配
        > 
            <url-pattern>/servlet</url-pattern>
            <!--或者指定过滤的Servlet名称-->
            <servlet-name>/ser1</servlet-name>
            <servlet-name>/ser2</servlet-name>
            <servlet-name>/ser3</servlet-name>

    2. 目录匹配
        > 
            <url-pattern>/s/*</url-pattern>

    3. 扩展名匹配
        > 
            <url-pattern>*.a</url-pattern>

## 生命周期
- 与ServletContext一样,服务器启动时创建,服务器关闭时销毁.

## Filter的API
> Filter接口有三个方法,并且这三个方法都与Filter的生命周期相关.
### init(FilterConfig)
- Filter的初始化方法,Filter对象创建时执行

### doFilter(ServletRequest, ServletResponse, FilterChain)
- Filter的核心方法,如果某资源被配置到此Filter进行过滤,那么每次访问该资源都会执行doFilter方法

### destroy()
- Filter销毁时执行

## dispatcher:访问的方式
1. REQUEST
    - 默认值,代表直接访问某个资源时执行filter
2. FORWARD
    - 转发时才执行filter
3. INCLUDE
    - 包含资源时执行filter
4. ERROR
    - 发生错误时,进行跳转还是执行filter