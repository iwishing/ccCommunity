# <font size=6>萌圈社区</font> #

> [<font color=#F4606C>项目代码</font>](https://github.com/iwishing/ccCommunity)  
> 开发周期：  
> 起始时间：2020.4.17  
> 结束时间：  
### 官方文档
[<font color=#F4606C>Spring官方文档</font>](https://spring.io/guides)  
[<font color=#F4606C>SpringMvc文档</font>](https://docs.spring.io/spring/docs/current/spring-framework-reference/web.html)  
[<font color=#F4606C>Bootstrap官方文档</font>](https://v3.bootcss.com/getting-started)  
[<font color=#F4606C>Github OAuth</font>](https://developer.github.com/apps/building-oauth-apps/creating-an-oauth-app/)  
[<font color=#F4606C>java发送post请求的okHttp</font>](]https://square.github.io/okhttp/)  
[<font color=#F4606C>fastJson</font>](https://github.com/alibaba/fastjson/wiki/Quick-Start-CN)  
[<font color=#F4606C>菜鸟教程</font>](https://www.runoob.com/)  
[<font color=#F4606C>阿里云短信验证</font>](https://dysms.console.aliyun.com/dysms.htm?spm=5176.8195934.1283918..144a30c9hgoyME&aly_as=mUKUUN9uw&accounttraceid=7ef6e803652b42e8b92e9562c84966acbzzq#/overview)    
[<font color=#F4606C>lombok官网</font>](https://projectlombok.org/)  
[<font color=#F4606C>thymeleaf官网</font>](https://www.thymeleaf.org/)    
[<font color=#F4606C>mybatis中文文档</font>](https://mybatis.org/mybatis-3/zh/)    
[<font color=#F4606C>devtools</font>](https://docs.spring.io/spring-boot/docs/2.1.0.BUILD-SNAPSHOT/reference/htmlsingle/#using-boot-devtools)    
[<font color=#F4606C>liveReload</font>](https://docs.spring.io/spring-boot/docs/2.1.0.BUILD-SNAPSHOT/reference/htmlsingle/#using-boot-devtools-livereload)    
[<font color=#F4606C>jQuery官方文档</font>](https://api.jquery.com/jQuery.getJSON/)  
[<font color=#F4606C>moment官方文档，日期格式化用</font>](http://momentjs.cn/docs/#/parsing/string-format/)   
[<font color=#F4606C>editor.md富文本编辑器</font>](https://pandao.github.io/editor.md/#download)   
[<font color=#F4606C>腾讯云对象存储</font>](https://cloud.tencent.com/document/product/436/10199)   
[<font color=#F4606C>阿里矢量图形库</font>](https://www.iconfont.cn/)   


### 参考资料
[<font>关于OkhttpClient请求超时问题参考的文章</font>](https://blog.csdn.net/do168/article/details/51848895)  
[<font color=#F4606C>ajax的datatype和使用方法</font>](https://blog.csdn.net/blackcat88/article/details/89487915)    
[<font color=#F4606C>controller加上二级映射静态资源加载问题</font>](https://blog.csdn.net/xia4820723/article/details/49659263)  
[<font color=#F4606C>lombok基本使用</font>](https://www.jianshu.com/p/2543c71a8e45)  
[<font color=#F4606C>mybatis多对多映射</font>](https://www.jianshu.com/p/58b92011130b)  
[<font color=#F4606C>拦截器，资源映射器，页面跳转</font>](https://www.cnblogs.com/yangxiansen/p/7859991.html)  
[<font color=#F4606C>@EnableWebMvc注解</font>](https://www.cnblogs.com/lvbinbin2yujie/p/10624584.html)  


### 开发工具与环境
* Idea：2019.3.1  
* 系统：windows10 家庭版  
* Jdk: 8  
* git: 2.21.0.windows.1  
* spring：5.0.3 release  
* [<font color=#F4606C>postman插件</font>](https://www.extfans.com/web-development/gadgdddeaeobeapfhikdkglgbolmfdea/)  
### 报错与解决
[授权登录github经常connection reset](https://blog.csdn.net/fuckingone/article/details/105151628)  

### 注意事项
一.第一次使用lombok可能要安装插件，我是使用idea直接搜索lombok安装
二.使用spring devtools配置热部署
    1.进官网，引入maven坐标
    2.在settings->compiler 勾选build project automatically
    3.按住ctrl+shift+alt+?(windows系统是这样)，选择registry，将compiler.automake.allow.when.app.running勾上
三.devtools是当代码有变化的时候，自动编译变化的部分，实质是两个classloader在起作用，一个是管理各种jar包那种，不会改变的class，
    另一个就是管理我们编写的可能改变的class，当检测到代码有变化，他会自动编译重启，并将第一部分去掉，重启更快，当然，
    得服务器在运行的时候改变
四.liveReload插件，是chrome插件，当检测到服务器有变化，则自动刷新前端的页面，这两配合可以不用频繁重启刷新，大大提高开发效率




### 遗留问题
使用枚举表示错误信息