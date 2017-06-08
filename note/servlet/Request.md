# request
## 通过request获得请求行
- 获得客户端的请求方式
    String getMethod()
- 获得请求的资源
    - String getRequestURI()
    - StringBuffer getRequestURL()
    - String getContextPath() // Web应用的名称
    - String getQueryString() // get方式提交url地址后的参数字符串
    - String getRemoteAddr()  // 获得客户端的IP地址
    ### 示例
        // 请求地址,GET
        http://localhost:8080/Web15/demo1Servlet?username=zhangsan&password=222

        // POST
        http://localhost:8080/Web15/demo1Servlet
        //body
        username:zhangsan
        password:123456

        // 代码
        StringBuilder sb = new StringBuilder();
        sb.append("method=").append(request.getMethod()).append("\n");
        sb.append("URI=").append(request.getRequestURI()).append("\n");
        sb.append("URL=").append(request.getRequestURL()).append("\n");
        sb.append("queryName=").append(request.getQueryString());
        response.getWriter().write(sb.toString());

        // 结果:GET方式
        method=GET
        URI=/Web15/demo1Servlet
        URL=http://localhost:8080/Web15/demo1Servlet
        queryName=username=zhangsan&password=123456

        // 结果:POST方式
        method=POST
        URI=/Web15/demo1Servlet
        URL=http://localhost:8080/Web15/demo1Servlet
        queryName=null

## 通过request获取请求头
- long getDateHeader(String name)
- String getHeader(String name)
- Enumeration getHeaderNames()
- Enumeration getHeaders(String name)
- int getIntHeader(String name)

## 通过request获得请求体
> 请求体中的内容是通过POST提交的请求参数,格式是:
>
    username=zhangsan&password=123456
- String getParameter(String name)
- String[] getParameterValues(String name)
- Enumeration<String> getParameterNames()
- Map<String, String[]> getParameterMap()

## request的其他功能
> request对象是一个域对象,所以也有如下方法
>
    setAttribute(String name, Object o)
    getAttribute(String name)
    removeAttribute(String name)
- request请求转发
    - 获得请求转发器
    >
        RequestDispatcher dispatcher = request.getRequestDispatcher(String path);
    - 通过转发器对象转发
    >
        dispatcher.forward(request, response);
---
## ServletContext域和Request域的生命周期比较
- ServletContext
    - 创建:服务器启动
    - 销毁:服务器关闭
    - 域的作用范围:整个Web应用
- Request
    - 创建:访问时创建
    - 销毁:响应结束
    - 域的作用范围:一次请求中

## 转发与重定向的区别
- 重定向两次请求,转发一次请求
- 重定向地址栏会变化,转发地址不变
- 重定向可以访问外部网站,转发只能访问站点内部资源
- 转发的性能要优于重定向

## 客户端地址与服务器端地址写法
- 客户端地址
    > 是客户端去访问服务器的地址,服务器外部的地址
    > 特点:写上Web应用的名称
- 服务器端地址
    > 服务器内部资源跳转的地址
    > 特点:不需要写Web应用的名称