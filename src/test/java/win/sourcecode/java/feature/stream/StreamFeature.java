package win.sourcecode.java.feature.stream;

import org.junit.Test;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by wenyou on 2017/7/17.
 */
public class StreamFeature {
    @Test
    public void create() {
        // of
        Stream<String> stringStream = Stream.of("hello", "world");
        stringStream.forEach(s -> System.out.println(s));

        // generate
        Stream<String> echos = Stream.generate(() -> "echos").limit(5);
        echos.forEach(s -> System.out.println(s));

        Stream<Double> doubleStream = Stream.generate(Math::random).limit(5);
        doubleStream.forEach(s -> System.out.println(s));

        // iterate
        Stream.iterate(BigInteger.ZERO, n -> n.add(BigInteger.ONE)).limit(5).forEach(s -> System.out.println(s));

        // Pattern
        Pattern.compile("\\s").splitAsStream("hello, world!").forEach(s -> System.out.println(s));
    }

    @Test
    public void fileLines() {
        // File
        try (Stream<String> lines = Files.lines(Paths.get("/", "etc", "hosts"))) {
            lines.forEach(s -> System.out.println(">" + s));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void filter() {
        Stream.iterate(Integer.valueOf(0), n -> ++n).limit(10).filter(n -> n > 5).forEach(n -> System.out.println(n));

        Stream.of("hello", "world").map(s -> s.charAt(0)).forEach(s -> System.out.println(s));

        Stream<Character> charStream = Stream.of("foo", "bar").flatMap(s -> {
            List<Character> charList = new LinkedList<>();
            for (char c : s.toCharArray()) {
                charList.add(c);
            }
            return charList.stream();
        });
        charStream.forEach(s -> System.out.println(s));
    }

    @Test
    public void sub() {
        // skip
        Stream.of("a", "b", "c", "d").skip(2).forEach(s -> System.out.println(s));

        // concat
        Stream.concat(Stream.of("aa", "bb"), Stream.of("cc")).forEach(s -> System.out.println(s));

        // peek 获取元素时调用
        Stream.iterate(0, n -> ++n).peek(e -> System.out.println("<" + e)).limit(5); // 不打印
        Stream.iterate(0, n -> ++n).peek(e -> System.out.println(">" + e)).limit(5).toArray(); // 打印
    }

    @Test
    public void status() {
        // 去重
        Stream.of("a", "b", "c", "c", "b").distinct().forEach(s -> System.out.println(s));

        // 排序
        Stream.of("a", "abc", "ab").sorted(Comparator.comparing(String::length).reversed()).forEach(s -> System.out.println(s));
    }

    @Test
    public void ju() {
        // 判断
        Optional<String> stringOptional = Stream.of("a", "abc", "ab").max(String::compareToIgnoreCase);
        if (stringOptional.isPresent()) {
            System.out.println(stringOptional.get());
        }

        Optional<String> stringOptional1 = Stream.of("abc", "abd", "def").filter(s -> s.startsWith("a")).findFirst();
        if (stringOptional1.isPresent()) {
            System.out.println(stringOptional1.get());
        }

        // 并行 不一定是第一个
        Optional<String> stringOptional2 = Stream.of("abc", "abd", "def").filter(s -> s.startsWith("a")).findAny();
        if (stringOptional2.isPresent()) {
            System.out.println(stringOptional2.get());
        }

        boolean b = Stream.of("abc", "abd", "def").anyMatch(s -> s.startsWith("b"));
        System.out.println(b);
    }

    @Test
    public void optional() {
        // 条件
        new LinkedList<String>().stream().findFirst().ifPresent(s -> System.out.println(s));

        System.out.println(new LinkedList<String>().stream().findFirst().orElse("default"));

        // function
        System.out.println(new LinkedList<String>().stream().findFirst().orElseGet(() -> new String("default")));

        // exception
        try {
            System.out.println(new LinkedList<String>().stream().findFirst().orElseThrow(NoSuchElementException::new));
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Test
    public void reduce() {
        Stream.of("a", "b", "c").reduce((x, y) -> x + y).ifPresent(s -> System.out.println(s));

        // 初始值
        System.out.println(Stream.of("a", "b", "c").reduce("result: ", (x, y) -> x + y));

        // 并行
        System.out.println(Stream.iterate(Long.valueOf(0), l -> ++l).limit(10000).reduce(0L, (x, y) -> x + y, (i, j) -> i + j));

        HashSet<String> set1 = Stream.iterate(Integer.valueOf(0), i -> ++i).limit(5).map(i -> "value:" + i.toString()).collect(HashSet::new, HashSet::add, HashSet::addAll);
        System.out.println(set1.size());
        Set<String> set2 = Stream.iterate(Integer.valueOf(0), i -> ++i).limit(5).map(i -> "value:" + i.toString()).collect(Collectors.toSet());
        System.out.println(set2.size());
    }

    @Test
    public void intStream() {
        // 开区间
        System.out.println(IntStream.range(0, 3).count());
        // 闭区间
        System.out.println(IntStream.rangeClosed(0, 3).count());

        // 基础类型转换
        IntStream is = Stream.of("a", "ab", "abc").mapToInt(String::length);
        is.forEach(i -> System.out.println(i));
        Stream<Integer> si = IntStream.range(0, 3).boxed();
        si.forEach(i -> System.out.println(i));
    }
}
