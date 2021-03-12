## 即时通讯插件简介
    很多聊天功能都是基于第三方聊天api，比如融x，而且第三方运营成本比较高。那为何不研发一款高并发聊天插件呢？
    
    环境 springboot2.x、jdk8、maven
    框架 netty、fastjson

## 使用说明
    1.通讯插件源码下载到本地。命令行输入`maven install`。项目工程中引入插件依赖。
    `<dependency>
        <groupId>io.github.yangyouwang</groupId>
        <artifactId>springboot-starter-im</artifactId>
        <version>0.0.1-SNAPSHOT</version>
    </dependency>`
   ![引入jar](https://raw.githubusercontent.com/YangYouWang/springboot-starter-im/master/demo/img/1.jpg "1.png")
   
    2.resource中application.yml配置插件通讯端口。
    `im:
      netty:
        port: 8888 # 配置聊天端口` 
   ![配置端口](https://raw.githubusercontent.com/YangYouWang/springboot-starter-im/master/demo/img/2.jpg "2.png")
   
    3.启动类main方法加入启动插件代码
    `NettyBooter nettyBooter = SpringUtil.getBean(NettyBooter.class);
            nettyBooter.start();`
   ![配置端口](https://raw.githubusercontent.com/YangYouWang/springboot-starter-im/master/demo/img/3.jpg "3.png")
   
    4.控制台打印出：启动 Netty 成功。默认访问路径`ws://localhost:8888/im/ws`
   ![启动成功](https://raw.githubusercontent.com/YangYouWang/springboot-starter-im/master/demo/img/4.jpg "4.png")
   ![启动成功](https://raw.githubusercontent.com/YangYouWang/springboot-starter-im/master/demo/img/5.jpg "5.png")
              
 ## 如何在程序中获取消息
 
     代码加入事件监听（参数是DataContentEvent，而不是DataContent）
    `@Component
           public class ChatMsgListener {
               @EventListener
               public void getData(DataContentEvent dataContentEvent) {
                   System.out.println("收到消息了" + dataContentEvent.getDataContent());
               }
         }`
         
   ![加入事件](https://raw.githubusercontent.com/YangYouWang/springboot-starter-im/master/demo/img/6.jpg "6.png")
   ![控制台输出](https://raw.githubusercontent.com/YangYouWang/springboot-starter-im/master/demo/img/7.jpg "7.png")
   
    开源不易，切勿白嫖。

 **革命尚未成功，同志仍须努力**