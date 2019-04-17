# Spring boot 的自动化配置

## 视图
借助于Spring boot的自动配置，当我们将Thymeleaf添加到项目的类路径下，就启用了Spring Boot的自动配置。  

> 当应用运行时，Spring Boot将会探测到类路径中的Thymeleaf，然后会自动配置视图解析器、模板解析器以及模板引擎，
这些都是在Spring MVC中使用Thymeleaf所需要的。因此，在我们的应用中，不需要使用显式Spring配置的方式来定义
Thymeleaf。

## 自动配置的模板
自动配置的解析器会在指定的目录下查找Thymeleaf模板，这个目录也就是相对于根类路径下的templates目录下，
所以在Maven或Gradle项目中，我们需要将home.html放到“src/main/ resources/templates”中。

## 静态内容
当采用Spring Boot的Web自动配置来定义Spring MVC bean时，这些bean中会包含一个资源处理器（resource handler），
它会将“/**”映射到几个资源路径中。这些资源路径包括（相对于类路径的根）：  

*   /META-INF/resources/
*   /resources/
*   /static/
*   /public/

## 持久化
当Spring Boot探测到Spring的JDBC模块和H2在类路径下的时候，自动配置就会发挥作用，将会
自动配置*JdbcTemplatebean和H2DataSourcebean*。Spring Boot再一次为我们处理了所有的Spring配置。
  
## 数据库模式
只要将sql脚本命名为**schema.sql**并将其放到类的根路径下（也就是Maven或Gradle项目的“src/main/resources”目录下）
，当应用启动的时候，就会找到这个文件并进项数据加载。