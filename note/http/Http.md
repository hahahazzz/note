# HTTP
## HTTP协议
> HTTP(HyperText Transfer Protocol，缩写：HTTP)是互联网上应用最为广泛的一种网络协议。设计HTTP最初的目的是为了提供一种发布和接收HTML页面的方法
## HTTP协议组成
> HTTP协议由Http请求和Http响应组成,当在浏览器中输入网址访问某个网站时,浏览器将会把你的请求封装为一个Http请求发送给服务器站点,服务器接收到请求后会组织响应数据,封装成一个Http响应返回给浏览器,没有请求就没有响应.
## HTTP请求组成
- 第一部分:请求方式 请求路径 HTTP协议版本
- 第二部分:请求头,格式: key : value
- 第三部分:空行
- 第四部分:请求体
    >
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
- HTTP/1.1:发送请求,创建一次连接,获得多个web资源,连接断开.