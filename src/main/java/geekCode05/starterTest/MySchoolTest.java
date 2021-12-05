package geekCode05.starterTest;

import geekCode05.starterTest.starter.MySchool;
import geekCode05.starterTest.starter.SchoolAutoConfiguration;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = SchoolAutoConfiguration.class)
public class MySchoolTest {

    @Autowired
    MySchool mySchool;

    @Test
    public void test() {
        System.out.println(mySchool.toString());
    }
}
