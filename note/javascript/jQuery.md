# jQuery
## 选择器
- $("#id")获取指定id的元素
- $(".cls")获取指定class的元素
- $("p")获取所有p标签
- $("selector1,selector2,selector3")将多个匹配到的元素合并到一起作为结果
- $("*") 匹配所有元素
    >以下部分摘自[http://hemin.cn/jq/](http://hemin.cn/jq/)
- $("ancestor descendant"") 在给定的祖先元素下匹配所有的后代元素
    >
        <form>
            <label>Name:</label>
            <input name="name" />
            <fieldset>
                <label>Newsletter:</label>
                <input name="newsletter" />
            </fieldset>
        </form>
        <input name="none" />

        jquery代码
            $("form input")
        结果
            <input name="name" />, <input name="newsletter" /> 
- $("parent > child") 在给定的父元素下匹配所有的子元素
    >
        <form>
            <label>Name:</label>
            <input name="name" />
            <fieldset>
                <label>Newsletter:</label>
                <input name="newsletter" />
            </fieldset>
        </form>
        <input name="none" />

        jQuery 代码:
            $("form > input")
        结果:
            [ <input name="name" /> ]
- $("prev + next")匹配所有紧接在 prev 元素后的 next 元素
    >
        <form>
            <label>Name:</label>
            <input name="name" />
            <fieldset>
                <label>Newsletter:</label>
                <input name="newsletter" />
            </fieldset>
        </form>
        <input name="none" />
        jQuery 代码:
            $("label + input")
        结果:
            [ <input name="name" />, <input name="newsletter" /> ]
- $("prev ~ siblings") 匹配 prev 元素之后的所有同辈 siblings 元素
    >
        <form>
            <label>Name:</label>
            <input name="name" />
            <fieldset>
                <label>Newsletter:</label>
                <input name="newsletter" />
            </fieldset>
        </form>
        <input name="none" />
        jQuery 代码:
            $("form ~ input")
        结果:
            [ <input name="none" /> ]
## 页面加载事件
- window.onload存在事件覆盖的情况,而jQuery则不存在,并且是顺序执行
- 语法(3种):
    >
        jQuery(document).ready(function () {
            jQuery("#btn").val("test")
        });

        $(document).ready(function () {
            $("#btn").val("test")
        });

        $(function () {
            $("#btn").val("test")
        });
---
## 注意点
- jQuery对象无法操作JS里的属性和方法
- DOM对象无法操作jQuery里的属性和方法
## jQuery对象转DOM对象
- 方式1
    >
        var ele=$("#id").get(0);
- 方式2
    >
        var ele=$("#id")[0];
## DOM对象转为jQuery对象
- 直接用$()包含即可
    >
        var ele=document.getElementById("btn");
        $(ele)
---
## jQuery对象操作
### 数组遍历
- 方式一:
    >
        each(callback)
- 方式二:
    >
        $.each(object,[callback])
### 文档处理操作
- 追加内容
    - append:a.append(b),将b追加到a的末尾
    - appendTo:a.appendTo(b),将a追加到b的末尾
## jQuery校验表单
- 引入jQuery,再引入jquery.validate
- 示例代码:
    >
        <head>
            <meta charset="UTF-8">
            <title>表单校验</title>
            <script src="../js/jquery-3.2.1.min.js" type="text/javascript"></script>
            <script src="../js/jquery.validate.js" type="text/javascript"></script>
            <script src="../js/messages_zh.js" type="text/javascript"></script>
            <script type="text/javascript">
                $(function () {
                    $("#register").validate({
                        rules: {
                            username: {
                                required: true,
                                minlength: 6,
                                maxlength: 16
                            },
                            password: {
                                required: true,
                                minlength: 6,
                                maxlength: 16
                            },
                            repassword: {
                                required: true,
                                equalTo: "[name='password']"
                            }
                        },
                        messages: {
                            username: {
                                required: "用户名必填",
                                minlength: "用户名最少6个字符",
                                maxlength: "用户名最长16个字符",
                            },
                            password: {
                                required: "密码必填",
                                minlength: "密码最少6个字符",
                                maxlength: "密码最长16个字符",
                            },
                            repassword: {
                                required: "确认密码必填",
                                equalTo: "两次密码不一致",
                            }
                        }
                    });
                });
            </script>
        </head>
        <body>
        <form id="register">
            用户名:<input type="text" name="username"><br/>
            密码:<input type="password" name="password"><br>
            确认密码:<input type="password" name="repassword"><br>
            <input type="submit" value="提交">
        </form>
        </body>