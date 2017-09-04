# struts2访问流程

### struts2访问流程

- struts2获取访问的url

- 与struts.xml中配置的package的namespace进行匹配,如果匹配上了,则在该package下进行aciton的匹配.

- 匹配到了action之后,获取配置的class对象,调用对应的方法.获取返回结果

- 将返回结果与配置的result进行匹配.进行相应的动作

---

### struts2的请求处理过程

#### org.apache.struts2.dispatcher.filter.StrutsPrepareAndExecuteFilter简称filter

- 一次请求到达filter后,filter会找到一个ActionMapper类,这个类会处理请求的信息,如action,参数等.

- ActionMapper处理完请求之后,会返回给filter一个ActionMapping对象,这个对象封装了请求信息.

- filter接收到了ActionMapping对象之后,会判断这个请求是否需要struts2去处理.

- 如果需要处理,filter会把这个ActionMapping转交给一个叫做ActionProxy处理

- ActionProxy会去ConfigurationManager(会读取struts.xml的配置信息)中查找对应的请求对象信息

- ConfigurationManager获取到配置信息之后,会把配置信息返回给ActionProxy.

- 之后,ActionProxy会将请求交给ActionInvocation对象,同时还带了Aciton对象和一些Interceptors

- ActionInvocation执行动作的顺序为Interceptor1,Interceptor2.Interceptor3,Action,Interceptor3,Interceptor2,Interceptor1.

- 执行Action会有一个String返回值,并且返回结果会被封装为Result对象.根据此对象进行对应的动作,如重定向等.之后会走上一条中Action之后的功能.