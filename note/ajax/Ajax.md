# Ajax

## 同步和异步

### 同步
- 客户端发送请求到服务器端,在服务器返回响应之前,客户端一直处于等待状态.
### 异步
- 客户端发送请求到服务器,无路服务器端是否返回响应,客户端随时处理其他事情,不需等待服务器做出响应.

### Ajax运行原理
> 页面发起请求,会将请求发送给浏览器内核中的Ajax引擎,Ajax引擎会提交请求到服务器端,在这段时间里,客户端可以进行任意操作.服务器将数据返回给Ajax引擎后,会触发关联的事件,从而执行自定义的js逻辑代码完成某种页面功能.

- 原生Ajax
1. 创建Ajax引擎对象
2. 为Ajax引擎对象版定监听
3. 绑定提交地址
4. 发送请求
5. 接收响应数据
    >
        var xmlHttp = new XMLHttpRequest();
        xmlHttp.onreadystatechange = function () {
            if (xmlHttp.readyState == 4 && xmlHttp.status == 200) {
                var res = xmlHttp.responseText;
                // 处理结果res
            }
        };
        xmlHttp.open("GET", "${pageContext.request.contextPath}/ajax01", true);
        xmlHttp.send();

    - 如果是POST请求
    >
        xmlHttp.open("POST", "${pageContext.request.contextPath}/ajax01", true);
        xmlHttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
        xmlHttp.send("name=Bill&age=10");

- jQuery中的Ajax
    - $.ajax(url,[settings])
    - load(url,[data],[callback])
    - $.get(url,[data],[fn],[type])
    - $.getJSON(url,[data],[fn])
    - $.getScript(url,[callback])
    - $.post(url,[data],[fn],[type])

        - type:xml,html,script,json,text,_default 
    >
        // get方式
        $.get("${pageContext.request.contextPath}/ajax02",
            {"username": "zhangsan", "password": "123456"},
            function (body) {
                alert(body);
            },
            "text"
        );

        // post方式
        $.post(
            "${pageContext.request.contextPath}/ajax02",
            {"username": "zhangsan", "password": "123456"},
            function (body) {
                alert(body);
            },
            "text"
        );

### $.ajax({option1:value1,option2:value2,...})
- 常用opetion:
    1. async
        - 是否异步,默认true,异步
    2. data
        - 发送到服务器端的参数,建议json
    3. dataType
        - 服务器返回的数据类型,常用text和json
    4. success
        - 成功响应执行的函数,对应的类型是function类型
    5. type
        - 请求方式,GET/POST
    6. url
        - 请求的服务器端地址

    >
        $.ajax({
            url: "${pageContext.request.contextPath}/ajax02",
            async: true,
            type: "POST",
            data: {"username": "zhangsan", "password": "123456"},
            success: function (data) {
                alert(data);
            },
            error: function () {

            },
            dataType: "text"
        });