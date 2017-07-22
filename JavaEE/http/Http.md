# HTTP
## HTTP协议
> HTTP(HyperText Transfer Protocol，缩写：HTTP)是互联网上应用最为广泛的一种网络协议。设计HTTP最初的目的是为了提供一种发布和接收HTML页面的方法
## HTTP协议组成
> HTTP协议由Http请求和Http响应组成,当在浏览器中输入网址访问某个网站时,浏览器将会把你的请求封装为一个Http请求发送给服务器站点,服务器接收到请求后会组织响应数据,封装成一个Http响应返回给浏览器,没有请求就没有响应.
---
## HTTP请求组成
- 请求方式 请求路径 HTTP协议版本
- 请求头,格式: key : value
- 空行
- 请求体
### 示例:(Google Chrome 58.0.3029.110 (64-bit))
    POST / HTTP/1.1
    Accept:text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8
    Accept-Encoding:gzip, deflate, br
    Accept-Language:zh-CN,zh;q=0.8,en;q=0.6
    Cache-Control:max-age=0
    Connection:keep-alive
    Content-Length:30
    Content-Type:application/x-www-form-urlencoded
    Cookie:Idea-1a42a0a0=aa005b3b-20da-4bc4-8cb6-c18f575bc204; JSESSIONID=2F147BFBC5803EA33CA0CA73D58E3BAD
    DNT:1
    Host:localhost:8080
    Origin:http://localhost:8080
    Referer:http://localhost:8080/
    Upgrade-Insecure-Requests:1
    User-Agent:Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36

    username=admin&password=123456
### HTTP 1.1与1.0的区别
- HTTP/1.0:发送请求,创建一次连接,获得一个web资源,连接断开.
- HTTP/1.1:发送请求,创建一次连接,获得多个web资源,保持连接.
### 请求头字段
- Referer
    - 浏览器通知服务器,当前请求来自何处,如果是直接访问,则不会有这个头,常用于防盗链.
- User-Agent
    - 浏览器通知服务器,客户端浏览器与操作系统相关信息.
- Connection
    - 保持连接状态,Keep-Alive 连接中,close 已关闭.
- Host
    - 请求的服务器主机名.
- Content-Length
    - 请求体的长度.
- Content-Type
    - 如果是POST请求,会有这个请求头,默认值为application/x-www-form-urlencoded,表示请求体内容使用url编码.
- Accept
    - 浏览器可支持的MIME类型.文件类型的一种描述方式.
        - MIME格式:大类型/小类型[;参数]
            >
                text/html   // html文件
                text/css    // css文件
                text/javascript // js文件
                image/*     // 所有图片文件
- Cookie
    - 与会话有关的技术,用于存放浏览器的Cookie信息.
- Accept-Encoding
    - 浏览器通知服务器,浏览器支持的数据压缩格式.如:GZIP压缩
- Accept-Language
    - 浏览器通知服务器,浏览器支持的语言.
---
## HTTP响应
- 响应行
    - HTTP版本 状态码 响应消息
    - 常见状态码
        - 200:一切正常
        - 302:重定向
        - 304:取本地缓存
        - 404:没有该资源
        - 500:服务器端错误
- 响应头
- 空行
- 响应体
### 响应头字段
- Location
    - 指定响应的路径,需要与状态码302配合使用完成跳转.
- Content-Type
    - 响应正文类型(MIME类型),取值text/html;charset=UTF-8
- Content-Disposition
    - 通过浏览器以下载方式解析正文,取值:attachment;filename=xx.zip
- Set-Cookie
    - 与会话相关的技术,服务器向浏览器写入cookie
- Content-Encoding
    - 服务器使用的压缩模式,取值:gzip
- Content-length
    - 响应正文长度
- Refresh
    - 定时刷新,格式:秒数;url=路径,url可省略,默认值为当前页面.
- Server
    - 指的是服务器名称,默认值:Apache-Coyote/1.1,可以通过conf/server.xml配置进行修改.
- Last-Modified
    - 服务器通知浏览器,文件的最后修改时间,与If-Modified-Since一起使用.
### 示例
    HTTP/1.1 200
    Set-Cookie: JSESSIONID=2DD903513B37D84BD207C2B5D73B5CA8; Path=/; HttpOnly
    Content-Type: text/html;charset=UTF-8
    Content-Length: 251
    Date: Sun, 04 Jun 2017 09:06:03 GMT