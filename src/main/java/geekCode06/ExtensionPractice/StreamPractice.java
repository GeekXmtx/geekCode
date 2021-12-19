package geekCode06.ExtensionPractice;

import lombok.Builder;
import lombok.Data;

import java.util.*;
import java.util.stream.Stream;

public class StreamPractice {
    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1,2,3,4,5,6,7,8,9,10);
        // 1.匹配

        // 遍历输出符合条件的元素
        list.stream().filter(x -> x > 3).forEach(System.out::println);
        // 匹配第一个
        Optional<Integer> findFirst = list.stream().filter(x -> x > 3).findFirst();
        // 匹配任意（适用于并行流）
        Optional<Integer> findAny = list.parallelStream().filter(x -> x > 3).findAny();
        // 是否包含符合特定条件的元素
        boolean anyMatch = list.stream().anyMatch(x -> x > 3);
        System.out.println("匹配第一个值：" + findFirst.get());
        System.out.println("匹配任意一个值：" + findAny.get());
        System.out.println("匹配存在大于3的值：" + anyMatch);

        //2.筛选
        list.stream().filter(x -> x > 5).forEach(System.out::println);

        List personList = new ArrayList<Person>();
        personList.add(Person.builder().name("hy").salary(1234).age(18).sex("男").area("北京").build());
        personList.add(Person.builder().name("sy").salary(10000).age(18).sex("男").area("北京").build());
        personList.add(Person.builder().name("ab").salary(6554).age(18).sex("男").area("北京").build());
        personList.add(Person.builder().name("lh").salary(8464562).age(18).sex("男").area("北京").build());
        personList.add(Person.builder().name("gq").salary(123).age(18).sex("男").area("北京").build());
        personList.add(Person.builder().name("xm").salary(132155).age(18).sex("男").area("北京").build());

        List<String> list1 = Arrays.asList("admin", "sss", "ddddd", "jhkss");
        Optional<String> max = list1.stream().max(Comparator.comparing(String::length));
        System.out.println("最长的字符串：" + max.get());

        List<Integer> list2 = Arrays.asList(7,8,9,4,11,6);
        // 自然排序
        Optional<Integer> max1 = list2.stream().max(Integer :: compareTo);
        // 自定义排序
        Optional<Integer> max2 = list2.stream().max(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1.compareTo(o2);
            }
        });
        System.out.println("自然排序的最大值：" + max1.get());
        System.out.println("自定义排序的最大值：" + max2.get());

    }
}

@Data
@Builder
class Person {
    private String name;  // 姓名
    private int salary; // 薪资
    private int age; // 年龄
    private String sex; //性别
    private String area;  // 地区

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
}


