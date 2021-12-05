#  8.（必做）给前面课程提供的 Student/Klass/School 实现自动配置和 Starter。

### 为什么要自定义starter

　　在我们的日常开发工作中，经常会有一些独立于业务之外的配置模块，我们经常将其放到一个特定的包下，然后如果另一个工程需要复用这块功能的时候，需要将代码硬拷贝到另一个工程，重新集成一遍，麻烦至极。如果我们将这些可独立于业务代码之外的功配置模块封装成一个个starter，复用的时候只需要将其在pom中引用依赖即可，SpringBoot为我们完成自动装配
　　
### 　　实现：

schoolStart 中实现了自动配置，使用 maven install打包。然后放到了另外一个工程：code中，在pom中引入，最后测试通过

实现说明

在本周项目项目工程文件夹：starterTest中实现了自动配置，使用 maveninstall打包。然后放到了另外一个工程：code中，在pom中引入，最后测试通过

### 代码链接：

[link](https://github.com/GeekXmtx/geekCode/tree/master/src/main/java/geekCode05/starterTest)
