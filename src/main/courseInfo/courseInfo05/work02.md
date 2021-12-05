# 2.（必做）写代码实现 Spring Bean 的装配，方式越多越好（XML、Annotation 都可以）, 提交到 GitHub。

Spring给我们注入对象有三种方式：

    1）基于Annotation的装配
    2）在XML中进行显示配置
    3）Java代码装配bean

    
### 1）基于Annotation的装配

Spring中定义了一系列的注解，常用的注解如下所示：

    @Component: 可以使用此注解描述Spring中的Bean，但它是一个泛化的概念，仅仅表示一个组件，并且可以作用于任何层次。

    @Repository: 用于将数据访问层（DAO层）的类标识为Spring中的Bean，功能和Component注解相同。

    @Service: 通过作用于业务层（Service层），用于将业务层的类标识为Spring中的Bean，功能和Component注解相同。

    @Controller: 通常作用于控制层（如Spring mvc中的Controller），用于将控制层的类标识为Spring 中的Bean，功能和Component注解相同。

    @Autowired: 用于对Bean的属性变量、属性的 setter 方法及构造方法进行标注，配合对应的注解处理器完成Bean的自动配置工作。默认按照Bean的类型进行装配。

    @Resource: 其作用与Autowired一样。区别在于 @Autowired 默认按照Bean类型装配，而 @Resource 默认按照Bean实例名称进行装配。
    @Resource有两个重要属性：name和type。Spring将name属性解析为Bean实例名称，type解析为Bean的实例类型。
    如果指定 name 属性，则按实例名称进行装配；如果指定 type 属性，则按Bean类型进行装配；
    如果都不指定，则先按Bean实例名称装配，如果不能匹配，再按照Bean类型进行装配；如果都无法匹配，则抛出NoSuchBeanDefinitionException异常。

    @Qualifier: 与 @Autowired 注解配合使用，会将默认的按Bean类型装配修改为按Bean的实例名称装配，Bean的实例名称由 @Qualifier 注解参数指定。
    
######   1.构造实例


```
package geekCode05.assembleBean.assembleBeanByAnnotation;

public interface UserDao {
    public void save();
}

```

```
package geekCode05.assembleBean.assembleBeanByAnnotation;

import org.springframework.stereotype.Repository;

@Repository("userDao")
public class UserDaoImpl implements UserDao{

    @Override
    public void save() {
        System.out.println("userDao......save....");
    }

}

```

```
package geekCode05.assembleBean.assembleBeanByAnnotation;

public interface UserService {
    public void save();
}

```

```
package geekCode05.assembleBean.assembleBeanByAnnotation;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("userService")
public class UserServiceImpl implements UserService{
    @Resource(name="userDao")
    private UserDao userDao;
    @Override
    public void save() {
        this.userDao.save();
        System.out.println("userservice...save...");
    }

}
```

```
package geekCode05.assembleBean.assembleBeanByAnnotation;

import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

@Controller("userController")
public class UserController {
    @Resource(name="userService")
    private UserService userService;
    public void save(){
        this.userService.save();
        System.out.println("userController...save...");
    }
}

```
    
######    2.添加xml的配置文件beans.xml
```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:mvc="http://www.springframework.org/schema/mvc"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans-4.2.xsd
    http://www.springframework.org/schema/mvc
    http://www.springframework.org/schema/mvc/spring-mvc-4.2.xsd
    http://www.springframework.org/schema/context
    http://www.springframework.org/schema/context/spring-context-4.2.xsd
http://www.springframework.org/schema/mvc ">

    <!--基于Xml的装配-->
    <!-- 使用构造器注入方式装配user实例 -->
    <bean id="constructorUser" class="geekCode05.assembleBean.assembleBeanByXml.User">
        <constructor-arg index="0" value="极客"/>
        <constructor-arg index="1" value="小茗"/>
        <constructor-arg index="2">
            <list>
                <value>"小茗"</value>
                <value>"极客"</value>
            </list>
        </constructor-arg>
    </bean>

    <!-- 使用设值注入方式装配User实例 -->
    <bean id="propertyUser" class="geekCode05.assembleBean.assembleBeanByXml.User">
        <property name="username" value="小茗同学"></property>
        <property name="password" value="geekXm"></property>
        <property name="list">
            <list>
                <value>"极客"</value>
                <value>"小茗"</value>
            </list>
        </property>
    </bean>

    <!--基于Annotation的装配-->
    <!-- 使用context命名空间，在配置文件中开启相应的注解处理器 -->
    <context:annotation-config/>
    <!-- 使用 context 命名空间，通知 Spring 扫描指定包下所有Bean类，进行注解解析。如果不用这种方式，可以像下面注释的一样，分别定义3个Bean实例 -->
    <context:component-scan base-package="geekCode05.assembleBean.assembleBeanByAnnotation"/>

    <!-- <bean id="userDao" class="geekCode05.assembleBean.assembleBeanByAnnotation"/>
    <bean id="userService" class="geekCode05.assembleBean.assembleBeanByAnnotation"/>
    <bean id="userController" class="geekCode05.assembleBean.assembleBeanByAnnotation"/> -->
</beans>
```

######  3.测试

```
package geekCode05.assembleBean.assembleBeanByAnnotation;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AnnotationAssembleTest {
    public static void main(String[] args) {
        String xmlPath = "beans.xml";
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(xmlPath);
        UserController userController = (UserController) applicationContext.getBean("userController");
        userController.save();
    }
}

```

######  4.结果

```
userDao......save....
userservice...save...
userController...save...
```

### 2）在XML中进行显示配置

###### 1.新建一个User实体类，包含三个属性username，password，list


```
package geekCode05.assembleBean.assembleBeanByXml;
import java.util.List;
/**
 * 基于xml的装配
 *
 * Spring 提供了两种基于xml的装配方式：设值注入（Setter Injection）和构造注入（Constructor Injection）。
 * 在Spring实例化Bean的过程中，Spring 首先会调用Bean的默认构造方法来实例化Bean对象，然后通过反射的方式调用setter方法来注入属性值。因此，设值注入要求一个Bean必须满足两点要求
 *
 *     Bean类必须提供一个默认的无参的构造方法
 *     Bean类必须为需要注入的属性提供对应的setter方法
 */
public class User {
    private String username;
    private String password;
    private List<String> list;

    public User(String username, String password, List<String> list) {
        this.username = username;
        this.password = password;
        this.list = list;
    }

    public User() {}

    public String getUsername() {
        return username;
    }


    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public List<String> getList() {
        return list;
    }
    public void setList(List<String> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "User [username=" + username + ", password=" + password + ", list=" + list + "]";
    }

}

```

###### 2.添加xml的配置文件beans.xml

```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:mvc="http://www.springframework.org/schema/mvc"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans-4.2.xsd
    http://www.springframework.org/schema/mvc
    http://www.springframework.org/schema/mvc/spring-mvc-4.2.xsd
    http://www.springframework.org/schema/context
    http://www.springframework.org/schema/context/spring-context-4.2.xsd
http://www.springframework.org/schema/mvc ">

    <!--基于Xml的装配-->
    <!-- 使用构造器注入方式装配user实例 -->
    <bean id="constructorUser" class="geekCode05.assembleBean.assembleBeanByXml.User">
        <constructor-arg index="0" value="极客"/>
        <constructor-arg index="1" value="小茗"/>
        <constructor-arg index="2">
            <list>
                <value>"小茗"</value>
                <value>"极客"</value>
            </list>
        </constructor-arg>
    </bean>

    <!-- 使用设值注入方式装配User实例 -->
    <bean id="propertyUser" class="geekCode05.assembleBean.assembleBeanByXml.User">
        <property name="username" value="小茗同学"></property>
        <property name="password" value="geekXm"></property>
        <property name="list">
            <list>
                <value>"极客"</value>
                <value>"小茗"</value>
            </list>
        </property>
    </bean>

    <!--基于Annotation的装配-->
    <!-- 使用context命名空间，在配置文件中开启相应的注解处理器 -->
    <context:annotation-config/>
    <!-- 使用 context 命名空间，通知 Spring 扫描指定包下所有Bean类，进行注解解析。如果不用这种方式，可以像下面注释的一样，分别定义3个Bean实例 -->
    <context:component-scan base-package="geekCode05.assembleBean.assembleBeanByAnnotation"/>

    <!-- <bean id="userDao" class="geekCode05.assembleBean.assembleBeanByAnnotation"/>
    <bean id="userService" class="geekCode05.assembleBean.assembleBeanByAnnotation"/>
    <bean id="userController" class="geekCode05.assembleBean.assembleBeanByAnnotation"/> -->
</beans>
```
###### 3.测试

```
package geekCode05.assembleBean.assembleBeanByXml;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class XmlBeanAssembleTest {
    public static void main(String[] args) {
        String xmlPath = "beans.xml";

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(xmlPath);

        System.out.println(applicationContext.getBean("constructorUser"));
        System.out.println(applicationContext.getBean("propertyUser"));

    }
}

```

###### 4.结果


```
User [username=极客, password=小茗, list=["小茗", "极客"]]
User [username=小茗同学, password=geekXm, list=["极客", "小茗"]]
```

### 3）Java代码装配bean

尽管在很多情况下通过组件扫描和自动装配来实现Spring的自动化配置是更为推荐的方式，但有时候自动化配置的方案行不通，因此需要明确配置 Spring。比如说你想要将第三方库中的组件装配到你的应用中，在这种情况下，是没办法在它的类上添加 @Component 和 @Autowired 注解的，因此就不能使用自动化装配的方案了。

###### 装配JavaConfig配置类即可。


```
package geekCode05.assembleBean.assembleBeanByCode;

public class UserVO {
    private String userName;
    private String age;
    private String sex;
    private String userNum;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "UserVO{" +
                "userName='" + userName + '\'' +
                ", age='" + age + '\'' +
                ", sex='" + sex + '\'' +
                ", userNum='" + userNum + '\'' +
                '}';
    }

    public String getUserNum() {
        return userNum;
    }

    public void setUserNum(String userNum) {
        this.userNum = userNum;
    }
}



```


```
package geekCode05.assembleBean.assembleBeanByCode;

public interface UserService {
    UserVO getUserInfo();
}

```


```
package geekCode05.assembleBean.assembleBeanByCode;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("userServiceImpl")
public class UserServiceImpl implements UserService {

    public UserVO getUserInfo() {
        UserVO userVO = new UserVO();
        userVO.setUserNum("小茗");
        userVO.setAge("18");
        userVO.setUserNum("001");
        userVO.setSex("1");
        return userVO;
    }

}

```


```
package geekCode05.assembleBean.assembleBeanByCode;

public class UserRoleVO {

    private String userRoleName;
    private String userRoleNum;

    @Override
    public String toString() {
        return "UserRoleVO{" +
                "userRoleName='" + userRoleName + '\'' +
                ", userRoleNum='" + userRoleNum + '\'' +
                ", userNum='" + userNum + '\'' +
                '}';
    }

    public String getUserRoleName() {
        return userRoleName;
    }

    public void setUserRoleName(String userRoleName) {
        this.userRoleName = userRoleName;
    }

    public String getUserRoleNum() {
        return userRoleNum;
    }

    public void setUserRoleNum(String userRoleNum) {
        this.userRoleNum = userRoleNum;
    }

    public String getUserNum() {
        return userNum;
    }

    public void setUserNum(String userNum) {
        this.userNum = userNum;
    }

    private String userNum;
}

```


```
package geekCode05.assembleBean.assembleBeanByCode;

public interface UserRoleService {
    UserRoleVO getUserRoleInfo();
}

```


```
package geekCode05.assembleBean.assembleBeanByCode;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("userServiceImpl")
public class UserServiceImpl implements UserService {

    public UserVO getUserInfo() {
        UserVO userVO = new UserVO();
        userVO.setUserName("小茗");
        userVO.setAge("18");
        userVO.setUserNum("001");
        userVO.setSex("1");
        return userVO;
    }

}


```


```
package geekCode05.assembleBean.assembleBeanByCode;

public class UserInfo {

    private UserService userService;

    private UserRoleService userRoleService;

    public UserInfo(){}

    public UserInfo(UserService userService, UserRoleService userRoleService){
        this.userService = userService;
        this.userRoleService = userRoleService;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public UserRoleService getUserRoleService() {
        return userRoleService;
    }

    public void setUserRoleService(UserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }

    public void getUserInfo(){
        System.out.println("UserInfo getUserInfo begin");
        System.out.println(userService.getUserInfo());
        System.out.println(userRoleService.getUserRoleInfo());
        System.out.println("UserInfo getUserInfo end");
    }

}

```


```
package geekCode05.assembleBean.assembleBeanByCode;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

    @Bean(name = "userServiceImpl")
    public UserService setUserService(){
        return new UserServiceImpl();
    }

    @Bean(name = "userRoleServiceImpl")
    public UserRoleService setUserRoleService(){
        return new UserRoleServiceImpl();
    }
    @Bean(name = "userInfo")
    public UserInfo setUserInfo(UserService userService,UserRoleService userRoleService){
        UserInfo userInfo = new UserInfo();
        userInfo.setUserService(userService);
        userInfo.setUserRoleService(userRoleService);
        return userInfo;
    }

}

```


```
package geekCode05.assembleBean.assembleBeanByCode;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= SpringConfig.class)
public class AssembleBeanByCodeTest {

    @Autowired
    @Qualifier("userInfo")
    private UserInfo userInfo;

    @Test
    public void getInfo(){
        userInfo.getUserInfo();
    }
}
```


###### 结果：

```
UserInfo getUserInfo begin
UserVO{userName='小茗', age='18', sex='1', userNum='001'}
UserRoleVO{userRoleName='大佬', userRoleNum='110', userNum='110'}
UserInfo getUserInfo end
```









