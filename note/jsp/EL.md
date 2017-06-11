# EL技术
## EL表达式概述
> EL(Express Language)表达式可以嵌入在JSP页面内部,减少JSP脚本的编写,EL出现的目的是要替代JSP页面中脚本的编写.

## EL从域中取出数据
### EL最主要的作用是获得四大域中的数据
- 格式 : ${EL表达式}
    - EL获得pageContext域中的值 : ${pageScope.key}
    - EL获得request域中的值 : ${requestScope.key}
    - EL获得session域中的值 : ${sessionScope.key}
    - EL获得application域中的值 : ${applicationScope.key}
    - EL从四个域中获得某个值 : ${key}

---
## EL11个内置对象
### 获取JSP中域中的数据
- pageScope
- requestScope
- sessionScope
- applicationScope

### 接收参数
- param,paramValues
    - request.getParameter(),request.getParameterValues();

- header,headerValues
    - request.getHeader(),request.getHeaders();

- initParam
    - getServletContext().getInitParameter():

- cookie
    - request.getCookies()---cookie.getName()---cookie.getValue()

- pageContext
    - 开发中的webContext,可以获得其他八大对象.