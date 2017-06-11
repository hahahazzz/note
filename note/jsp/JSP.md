# JSP技术

## JSP脚本
### <% java代码 %>
- 内部的java代码会被翻译到service方法内部
### <%=java表达式或变量 %>
- 会被翻译为service方法内部out.print()
### <%!java代码%>
- 会被翻译为servlet的成员内容

## JSP注释
> 不同注释可见范围不同
### HTML注释
>
    <!-- 注释内容 -->
- 可见范围
    - JSP源码
    - 翻译后的Servlet
    - 页面显示HTML源码
### java注释
>
    // 单行注释
    /* 多行注释 */
- 可见范围
    - JSP源码
    - 翻译后的Servlet源码

### JSP注释
>
    <%-- 注释内容 --%>
- 可见范围
    - JSP源码

## JSP运行原理
> JSP在第一次被访问的时候会被Web容器翻译成Servlet,再执行.
- 过程:
    第一次访问->JSP->Java->编译运行

---
## JSP指令
> JSP指令指的是指导JSP编译和运行的命令,JSP包括三大指令:

- page指令
    - 属性最多的一个指令,根据不同的属性,指导整个页面特性
    - 格式:<%@ page name1="value1" name2="value2"... %>
    - 常用属性:
        - pageEncoding:表示当前页面文件的编码
        - contentType:response.setContentType(String type);
        - session:在否在翻译时自动创建session
        - import:导入java代码的包
        - errorPage:当本页面出错之后跳转到的页面
        - isErrorPage:当前页面是一个处理错误的页面

- include指令
    - 页面包含(静态包含)指令,可以将一个JSP页面包含到另一个JSP页面中
    - 格式:<%@ include file="被包含的文件地址"%>

- taglib指令
    - 在JSP页面中引入标签库
    - 格式:<%@ taglib uri="标签库地址" prefix="前缀" %>

---
## JSP内置/隐式对象
> JSP被翻译成Servelt后,service方法中有9个对象定义并初始化完毕,我们可以在JSP脚本中直接使用这9个对象.

| 名称 | 类型 | 描述 |
|:--:|:--:|:--:|
| out | javax.servlet.jsp.JspWriter | 用于页面输出 |
| request|javax.servlet.http.HttpServletRequest|得到用户请求信息|
| response|javax.servlet.http.HttpServletResponse|服务器向客户端的回应信息|
| config|javax.servlet.ServletConfig|服务器配置,可以获得初始化参数|
| session|javax.servlet.http.HttpSession|用来保存用户的信息|
| application|javax.servlet.ServletContext|所有用户的共享信息|
| page|java.lang.Object|指当前页面转换后的Servlet类的实例|
| pageContext|javax.servlet.jsp.PageContext|JSP的页面容器|
| exception|java.lang.Throwable|表示JSP页面所发生的异常,在错误页中才起作用|

### out对象
- 类型:JspWriter
- out的作用是向客户端输出内容
- out缓冲区默认大小是8kb,可以设为0kb,代表关闭out缓冲区,内容直接写入到response缓冲区

### pageContext对象
- page对象与pageContext对象不是一回事.
- JSP页面的上下文对象,作用如下

    - pageContext是一个域对象
        - setAttribute(String key,Object value);
        - getAttribute(String key);
        - removeAttribute(String key);

    - pageContext可以向其他域中存取对象
        - setAttribute(String key,Object value,int scope);
        - getAttribute(String key,int scope);
        - removeAttribute(String key,int scope);
        - findAttribute(String key);
            - 依次从pageContext域,request域,session域,application域中获取,在某个域中获取后将不再向后寻找.

- 获得其他八大隐式对象
    >
        pageContext.getOut();
        pageContext.getRequest();
        pageContext.getResponse();
        pageContext.getSession();
        pageContext.getServletConfig();
        pageContext.getServletContext();
        pageContext.getPage();
        pageContext.getException();
---

## 四大域对象
### pageContext
- 当前JSP页面
### request
- 一次请求
### session 
- 一次会话
### application
- 整个Web应用
---

##动态包含与静态包含
### 静态包含
- <%@ include file=""%>
    - 将被包含的页面合并到包含的页面
### 动态包含
- <jsp:include page="">
    - 将包含的页面编译运行,再运行到需要被包含的页面时,寻找被包含的页面,编译运行被包含的页面