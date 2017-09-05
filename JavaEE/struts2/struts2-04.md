# struts2中Action的创建方式

#### 1. 创建一个类,可以是POJO

- 不用继承任何类,也不需要实现任何接口

- 优点:使struts2代码侵入性更低.

#### 2. 创建一个类,实现Aciton接口

- 实现execute方法

- 提供了Action方法的规范

- Action接口预置了一些字符串

#### 3. 创建一个类,继承ActionSupport类

- ActionSupport类实现了Action/Validateable/ValidationAware/TextProvider/LocaleProvider/Serializable接口.

- 如果我们需要使用这些接口时,就不需要自己去实现了