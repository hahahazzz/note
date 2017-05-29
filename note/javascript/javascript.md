# JavaScript
## JavaScript的组成
- ECMAScript:整个JavaScript的核心,包含基本语法,变量,关键字,保留字,数字类型,语句,函数等
- DOM:Document Object Model,文档对象模型,包含整个HTML页面的内容
- BOM:Browser Object Model,浏览器对象模型,包含整个浏览器相关的内容
---
## 数据类型(基本数据类型)
- undefined/null/string/boolean/number
- undefined表示定义了未赋值,null表示不存在
---
## 注意点
- 变量使用var定义
- 如果在函数中使用var定义了一个变量,那么这个变量是一个局部变量,如果没有var,则该变量属于全局变量   
---
## JavaScript的引入
- 直接在head标签内定义
>
    <script type="text/javascript">
        function test() {
            alert("test");
        }
    </script>
- 通过标签引入
>
    <script src="../js/user-register.js" type="text/javascript"></script>
- 行内引入,其中的javascript:可以缺省
>
    <input type="button" id="btn_submit" name="submit" value="提交" onclick="javascript:alert(location.port)"/>
---
## History对象
- history对象包含浏览器的历史,可通过window.history访问
- back():回退到上一页
- forward():跳转到下一页
- go():回退或者跳转指定页数
---
## Location对象
- Location对象包含有关当前URL的信息,可通过window.location访问
- 一些例子
>
    location.hostname 返回 web 主机的域名 
    location.pathname 返回当前页面的路径和文件名 
    location.port 返回 web 主机的端口 （80 或 443） 
    location.protocol 返回所使用的 web 协议（http:// 或 https://）
    location.href 属性返回当前页面的 URL 
    location.pathname 属性返回 URL 的路径名
---
## 页面中几个常用的事件
- onsubmit  表单提交
- onclick 点击
- ondbclick 双击
- onload 某个页面或图像加载完成
- onfocus 获得焦点
- onblur 失去焦点
- onmouseover 鼠标移入
- onmouseout 鼠标移出