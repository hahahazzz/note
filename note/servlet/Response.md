# Response
## Response的运行流程
    -> 客户端发起的请求到服务器
    -> Tomcat内核解析请求的资源
    -> 将请求的的信息封装成Request对象,并同时创建一个Response对象
    -> 创建Servlet对象
    -> 调用service方法
    -> service方法结束,方法返回后,tomcat内核会去该response缓冲区中获取设置的内容
        > respon.getWriter().write()方法是将内容写入到response的缓冲区
    -> tomcat从response中获取设置的内容,封装为一个http响应(响应行,响应头,相应体)
---
## 通过Response设置响应行
### 设置响应行的状态码
- setStatus(int code)

## 通过Response设置响应头
### add表示添加,set表示设置
- addHeader(String key,String value)
- addIntHeader(String name,int value)
- addDateHeader(String name,long date)
- setHeader(String name,String value)
- setIntHeader(String name,int value)
- setDateHeader(String name,long date)
    - addHeader在key相同的情况下,value会被合并在一起
    - setHeader在key相同的情况下,value会覆盖
### 重定向
- 特点
    - 访问服务器两次
    - 地址栏的地址发生变化
## 通过Response设置响应体
### 设置响应文本
- PrintWriter getWriter()
    - 获得字符流,通过字符流的write(String s)方法可以将字符串设置到response的缓冲区中,随后tomcat会将response缓冲区中的内容组装成Http响应返回给浏览器段.
    - 乱码,设置response的Content-Type头为"text/html;charset=UTF-8"
    >
        response.setContentType("text/html;charset=UTF-8");

- 响应头设置字节
    >
        ServletOutputStream stream = response.getOutputStream();
    - 获得字节流,通过字节流的write(byte[])可以向response缓冲区写入字节,再由tomcat服务器将字节内容组成http响应返回给浏览器
## 文件下载
- 文件下载的实质就是文件拷贝,将文件从服务器端拷贝到浏览器端,所以文件下载需要IO技术将服务器端的文件使用InputStream读取到,再使用ServletOutputStream写到response缓冲区中.