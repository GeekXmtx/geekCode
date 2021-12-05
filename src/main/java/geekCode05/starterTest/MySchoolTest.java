package geekCode05.starterTest;

import geekCode05.starterTest.starter.MySchool;
import geekCode05.starterTest.starter.SchoolAutoConfiguration;

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
