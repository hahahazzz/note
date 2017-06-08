# 会话技术
> 从打开一个浏览器访问某个站点,到关闭这个浏览器的整个过程,称为一次会话.会话技术就是记录这次会话中客户端的状态与数据的.

> 会话技术分为Cookie和Session
---
# Cookie
> 数据存储在客户端本地,减少服务器端的存储压力,安全性不好,客户端可以清除Cookie
> Cookie会以响应头的形式发送给客户端
> Cookie默认是会话级别,即会话结束Cookie失效
- Cookie使用

        // 添加
        Cookie cookie = new Cookie("name", "zhangsan");
        response.addCookie(cookie);
        // 获取
        Cookie[] cookies = request.getCookies();

    - 注意:Cookie不能存储中文
## 设置Cookie在客户端的持久化时间
> 如果不设置持久化时间,cookie会存储在浏览器内存中,浏览器关闭,cookie信息销毁(会话级别的Cookie),如果设置了持久化时间,cookie信息会被持久化到浏览器的磁盘文件中.
- cookie.setMaxAge(int expiry); // 单位:秒
    - 设置为0代表删除该cookie,key和路径要相同
## 设置Cookie的携带路径
- cookie.setPath(String path);
    - 如果不设置携带路径,那么该cookie信息会在访问产生该cookie的web资源所在的路径都携带cookie信息.
    >
        String contextPath = request.getContextPath();
        cookie.setPath(contextPath);    // 访问contextPath路径下的所有资源都携带此cookie
        cookie.setPath(contextPath + "/cookieServlet"); // 访问contextPath下的cookieServlet才携带此cookie

        cookie.setPath("/");    // 访问服务器下的所有资源都携带这个cookie

---
# Session
> 将数据存储到服务器端,安全性相对好,增加服务器的压力.