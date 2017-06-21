# Linux

## Linux的分类

### 1.根据市场需求不同,分为两个方向
1. 图形化界面版
    - 注重用户体验,类似Windows操作系统,但目前成熟度不足.
2. 服务器版
    - 没有好看的界面,是以在控制台窗口中输入命令操作系统的,类似于DOS,是架设服务器的最佳选择.    
### 2.根据原生程度,分为两种
1. 内核版本
    - 在Linus领导下的内核开发小组开发维护的系统内核的版本号.
2. 发行版本
    - 一些组织或公司在内核版基础上进行二次开发而重新发行的版本.
---

## Linux的基本命令

### 目录
- Linux的目录结构
>
    |- / 根目录
        |- bin binaries,存放二进制可执行文件
        |- sbin super user binaries,存放二进制可执行文件,只有root用户才能访问
        |- etc etcetera,存放系统配置文件
        |- usr unix shared resources,用于存放共享的系统资源
        |- home 存放用户文件的根目录
        |- root 超级用户目录
        |- dev devices,用于存放设备文件
        |- lib library,存放跟文件系统中的程序运行所需要的共享库及内核模块
        |- mnt mount,系统管理员安装临时文件系统的安装点
        |- boot 存放用于系统引导时使用的各种文件
        |- tmp temporary,用于存放运行时需要改变数据的文件
        |- var variable,用于存放运行时需要改变数据的文件
        |- ...
- cd命令
    - cd usr 切换到改目录下usr目录
    - cd ../ 切换到上一层目录
    - cd / 切换到系统根目录
    - cd ~ 切换到用户主目录
    - cd - 切换到上一个所在目录

- 目录操作
    - 新建目录  mkdir dirName

    - 查看目录 ls [-al]
        - ls -l可以缩写为ll

    - 查找目录 find 目录 参数
        - 查找root下与test相关的目录(文件)
            - find /root -name 'test'

    - 修改目录名称 mv oldName newName
        - 也可以对各种文件压缩包等进行重命名

    - 移动目录 mv dirName path
        - 也可以对各种文件压缩包等进行剪切操作

    - 拷贝目录 cp -r dirName path
        - r代表递归
        - 也可以copy文件压缩包等,copy文件和压缩包不用r参数

    - 删除目录  rm [-rf] dirName
        - 也可以删除其他文件压缩包等.
        - 可以直接加-rf参数.
---
### 文件
- 文件操作命令
    - 文件的创建 touch fileName,创建了一个空文件

    - 文件的查看 
        1. cat fileName
        2. more fileName
            - 空格翻页,回车下一行,带有百分比,按q退出查看
        3. lese fileName
            - 可以使用翻页键
        4. tail -10 fileName
            - 查看文件后10行
            - ctrl+c结束查看
        > tail -f fileName,可以对该文件进行动态监控,例如Tomcat日志文件,会随着程序的运行而变化,可以使用tail -f catalina-xxx.log监控文件的变化

    - 文件的修改
        1. vim 文件

- 压缩文件的操作
    > Linux中打包文件一般都是一.tar结尾的,压缩的命令一般是以.gz结尾的.一般情况下打包和压缩是一起进行的,打包并压缩后的后缀名一般为.tar.gz

    - 命令:tar -zcvf 打包压缩后的文件名 要打包压缩的文件
        - z:调用gzip压缩命令进行压缩
        - c:打包文件
        - v:显示运行过程
        - f:指定文件名

- 压缩文件的解压
    - tar [-xvf] 压缩文件
        - x:解压
        - 示例:将xxx.tar.gz文件解压到根目录/usr下
        >
            tar -xvf xxx.tar.gz -C /usr
---

## Linux权限命令
- 权限是Linux中的重要概念,每个文件/目录都有具体的权限,通过ls -l命令,我们可以查看某个目录下的文件或目录的权限
- 修改目录/文件的权限
    - chmod
    - 示例
    >
        chomd u=wrx,g=rw,o=r aaa.txt
        chmod 761 aaa.txt

---
## 其他命令
- 显示当前所在位置
    - pwd

- 搜索
    - grep 要搜索的字符串 要搜索的文件

- 管道命令
    - | 将前一个命令的输出作为本次目录的输入

- 查看进程
    - ps -ef 查看当前系统中运行的进程

- 杀死进程
    - kill -9 pid