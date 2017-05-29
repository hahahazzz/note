# jQuery
## 选择器
- $("#id")获取指定id的元素
- $(".cls")获取指定class的元素
- $("p")获取所有p标签
- $("selector1,selector2,selector3")将多个匹配到的元素合并到一起作为结果
- $("*") 匹配所有元素
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
### 页面加载事件
- window.onload存在事件覆盖的情况,而jQuery则不存在,并且是顺序执行
- 语法:
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