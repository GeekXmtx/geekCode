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
