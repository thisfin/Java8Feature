package win.sourcecode.java.feature.stream;

import org.junit.Test;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * Created by wenyou on 2017/7/17.
 */
public class StreamCreate {
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
        Stream.of("a", "b", "c", "c", "b").distinct().forEach(s -> System.out.println(s));

        Stream.of("a", "abc", "ab").sorted(Comparator.comparing(String::length).reversed()).forEach(s -> System.out.println(s));
    }

    @Test
    public void ju() {
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
}
