# BootStrap
## ViewPort 视口
>
    <!--设置文档兼容模式,表示使用IE浏览器的最新模式-->
    <meta http-equiv="x-ua-compatible" content="IE=edge">
    <!--设置视口宽度(值为设备的理想值),页面初始缩放值<理想宽度/可视宽度>-->
    <meta name="viewport" content="width=device-width,initial-scale=1">

在移动浏览器中,当页面宽度超出设备,浏览器内部虚拟的一个页面容器,会将页面缩放到设备那么大来展示.

- width
    - 设置layout viewport的宽度,为一个正整数,或字符串"width-device"表示采用设备的宽度
- initial-scale
     - 设置页面的初始缩放值,为一个数字,可以带小数
- minimum-scale 
    - 允许用户的最小缩放值,为一个数字,可以带小数
 maximum
    - 允许用户的最大缩放值,为一个数字,可以带小数
 - height
    - 设置layout viewport的高度,这个属性很少使用
 - user-scalable
    - 是否用户进行缩放,值为no不允许或者yes允许,如果设置了no,那么minimum和maximum将无效