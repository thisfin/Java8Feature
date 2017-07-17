package win.sourcecode.java.feature;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by wenyou on 2017/7/17.
 */
@Slf4j
public class Main {
    public static void main(String[] args) {
        SystemOutToSlf4j.enableForClass(Main.class);

        System.out.println(Main.class);

        new Main().out();
    }

    private void out() {
        System.out.println("hello, world!");
        log.info("hello, world!");
    }
}
