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
