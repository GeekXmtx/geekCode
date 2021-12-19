package geekCode06.ExtensionPractice;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class CreateStreamMethod {
    public static void main(String[] args) {
        //1、通过 java.util.Collection.stream() 方法用集合创建流
        List<String> list = Arrays.asList("a", "b", "c");
        // 创建一个顺序流
        Stream<String> stream = list.stream();
        // 创建一个并行流
        Stream<String> parallelStream = list.parallelStream();
        //2、使用java.util.Arrays.stream(T[] array)方法用数组创建流
        int[] array = {1, 3, 5, 7, 9};
        IntStream intStream = Arrays.stream(array);
        //3、使用Stream的静态方法：of()、iterate()、generate()
        Stream<Integer> stream1 = Stream.of(1, 2, 3, 4, 5);
        Stream<Integer> stream2 = Stream.iterate(0, (x) -> x+3).limit(4);
        stream2.forEach(System.out::println);
        Stream<Double> stream3 = Stream.generate(Math:: random).limit(3);
        stream3.forEach(System.out::println);
    }
}
