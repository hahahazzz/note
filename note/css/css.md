# CSS选择器
## 选择器分类
### 元素选择器
- 语法:选择器名称和标签名称相同
>
    div {
        color: red;
        background: #1922dc;
        border: 2px solid #0099ff;
        font-size: 20px;
        font-family: Roboto;
    }
- 使用:页面的标签会自动应用
>
    <div>这里的div会被自动应用定义的样式</div>
### 类选择器
- 语法:选择器名称以.开头
>
    .div {
        background: #1922dc;
        font-family: Roboto;
        font-size: 20px;
    }
- 使用:在标签的class属性里指定,不需要开头的.
>
    <div class="div">
        这是div标签的内容,div被指定了class属性
    </div>
### ID选择器
- 语法:选择器名称以#开头
>
    #div {
        font-size: 20px;
        background: cornflowerblue;
    }
- 使用:指定标签的id属性为选择器名称,不需要#
>
    <div id="div">
        这个div的id被指定为选择器div
    </div>
### 层级选择器
- 语法:多层标签,标签之间以空格分隔
>
    div p {
        font-size: 20px;
        background: chocolate;
    }

    body p {
        font-size: 30px;
        color: darkblue;
    }
- 使用:
>
    <body>
        <div>
            <p>
                本行文本应用的是div p样式指定的格式
            </p>
        </div>
        <p>
            本行文本指定的是body p样式指定的格式
        </p>
    </body>
### 属性选择器
- 语法:标签名[属性名='属性值']{样式定义}
>
    input[type='text'] {
        background: cover;
        font-size: 20px;
    }

    input[type='password'] {
        background: cornsilk;
        font-size: 30px;
    }
- 使用:自动应用
>
    <input type="text" name="username"><br/>
    <input type="password" name="password">
---
## CSS引入
### 内部引入
- 直接在页面head标签内定义css style
>
    <style type="text/css">
    </style>
### 行内引入
- 在标签内直接定义style属性,属性值如果有多个,则用;隔开
>
    <div style="font-size: 10px;color: red">
        行内定义的style
    </div>
### 外部引入
- 在head标签内添加,href为路径,type可以缺省
>
    <link rel="stylesheet" href="/css/style.css" type="text/css">
---
## CSS浮动
### 浮动的框可以向左或者向右滑动,直到它的外边缘碰到包含框或另一个浮动框的边框为止,由于浮动框不在文档的普通流中,所以文档的普通流中的块框表现得就像浮动框不存在一样.
- 设置选择器的float属性
- 清除浮动:设置style的clear属性
>
    <head>
        <meta charset="UTF-8">
        <title>CSS浮动</title>
        <style type="text/css">
            #div1 {
                border: 1px solid red;
                width: 300px;
                height: 540px;
                float: left;
            }

            #div2 {
                border: 1px solid green;
                width: 300px;
                height: 150px;
                float: left;
            }

            #div3 {
                border: 1px solid blue;
                width: 300px;
                height: 150px;
                float: left;
            }

            #clear {
                clear: both;
            }
        </style>
    </head>
    <body>
        <div id="div1"></div>
        <div id="clear"></div>
        <div id="div2"></div>
        <div id="div3"></div>
    </body>