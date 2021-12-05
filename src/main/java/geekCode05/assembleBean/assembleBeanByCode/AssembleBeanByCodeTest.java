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