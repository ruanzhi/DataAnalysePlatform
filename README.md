# DataAnalysePlatform
一个分布式项目的Demo

bhz-com  :项目公共模块，里面包含：
             常量类，实体类，工具类，以及公共的web层，service层，dao层
bhz-dat-netty: 这个是netty服务端，接收客户端发送过来的数据，进行处理
bhz-mst:  mst应用的web层
bhz-mst-facade: mst应用service层抽象的接口，web层需要引用，service层需要实现
                 通过接口，web层通过dubbo方式访问服务
bhz-mst-service： mst应用的service层，其中包含dao层数据访问，通过dubbo暴露服务
bhz-site-netty：模拟的一个netty客户端发送数据到netty服务端
sys项目下的三个模块bhz-sys，bhz-sys-facade,bhz-sys-service和mst项目是三个模块关系一样

使用到的技术：
     前端：ext组件
     后台：
       （1）集成单点登录功能（cas）
       （2）通过dubbox+zookeeper方式提供服务
       （3）fastDfs分布式文件系统，存储文件
       （4）netty使用
