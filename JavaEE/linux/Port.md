# CentOS开放端口

> 以开启mysql3306端口为例

    /sbin/iptables -I INPUT -p tcp --dport 3306 -j ACCEPT
    /etc/rc.d/init.d/iptables save ---将修改永久保存到防火墙中

### [CentOS 7](http://www.jianshu.com/p/225a853350d9)

> 参考文章[http://www.jianshu.com/p/225a853350d9](http://www.jianshu.com/p/225a853350d9)

>  
    1.systemctl start firewalld 开启防火墙

    2.firewall-cmd --zone=public --add-port=3306/tcp --permanent

        命令含义：

        --zone #作用域

        --add-port=80/tcp #添加端口，格式为：端口/通讯协议

        --permanent #永久生效，没有此参数重启后失效

    3.firewall-cmd --reload