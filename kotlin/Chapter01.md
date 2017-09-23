# 第一章 : 开始使用Kotlin

Kotlin是一种JVM语言.所以编译器将会产出Java字节码.正因为这样,Kotlin可以调用Java代码,反之亦然.所以啊,你的机器上需要安装JDK.

#### IntelliJ和Kotlin

使用Vim/nano不是每个人写代码的首选.在没有IDE的代码补全/智能感知/文件添加快捷键以及代码重构的帮助下,搞项目是十分蛋疼的事情.

现在的JVM世界,萌新的首选集成开发环境就是ItelliJ了.这玩意他爹就是Kotlin他爹:JetBrains公司.但ItelliJ也不是唯一的选择.

IntelliJ有两个版本:Ultimate 和 Community.Community是免费的.这本书里的代码用免费版的就足够了.

从IntelliJ 15.0 开始,IntelliJ就集成了Kotlin,如果版本旧了也可以通过plugin来更新.

新建Kotlin项目:
- 点击Create new project,在左侧选择Kotlin,紧接着下一步配置一下项目路径和项目名称.
- 或者选择gradle/maven,然后选择右侧的Kotlin对应条目.
-选择Kotlin文件夹,右击新建package,输入你的package名
- 右击新建的package,新建Kotlin File/Class,输入文件名.
- 在新建的文件中输入

    fun main(args:Array<String>){
        println("Hello World!")
    }
- 完工.点击Run按钮.