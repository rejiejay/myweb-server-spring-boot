- maven 常用命令
    - mvn prepare-resources 资源拷贝本阶段可以自定义需要拷贝的资源
    - mvn compile 编译本阶段完成源代码编译
        - 一般都是用这个
    - mvn package -Dmaven.test.skip=true 打包本阶段根据 pom.xml 中描述的打包配置创建 JAR / WAR 包
    - mvn install 安装本阶段在本地 / 远程仓库中安装工程包
        - 这样其他项目引用本项目的jar包时不用去私服上下载jar包，直接从本地就可以拿到刚刚编译打包好的项目的jar包，很灵活，避免每次都需要重新往私服发布jar包的痛苦
    - mvn clean
    - mvn dependency:tree
    	- spring boot 用的就是这个命令，好像是会顺便下载倚赖包

- 关于 .classpath 是 eclipse 自动生成的，不用管它    

# 准备
删掉跨域 
src\main\java\cn\rejiejay\security\WebMvcConfiguration.java -> allowedOrigins   

# 发布
    - mvn package -Dmaven.test.skip=true
    - 参考网易云笔记文档即可