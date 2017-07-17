package win.sourcecode.java.feature.stream;

import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by wenyou on 2017/7/17.
 */
public class StringFeature {
    @Test
    public void join() {
        System.out.println(String.join(", ", "a", "b", "c", "d"));

        List<String> list = new LinkedList<String>() {{
            this.add("a");
            this.add("b");
            this.add("c");
            this.add("d");
        }};
        System.out.println(String.join(", ", list));
    }
}
