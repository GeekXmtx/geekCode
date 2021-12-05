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
