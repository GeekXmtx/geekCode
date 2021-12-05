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
