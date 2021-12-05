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
