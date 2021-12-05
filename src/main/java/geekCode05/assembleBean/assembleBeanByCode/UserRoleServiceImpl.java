package geekCode05.assembleBean.assembleBeanByCode;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("userRoleServiceImpl")
public class UserRoleServiceImpl implements UserRoleService {

    public UserRoleVO getUserRoleInfo() {
        UserRoleVO userRoleVO = new UserRoleVO();
        userRoleVO.setUserNum("110");
        userRoleVO.setUserRoleName("大佬");
        userRoleVO.setUserRoleNum("110");
        return userRoleVO;
    }
}
