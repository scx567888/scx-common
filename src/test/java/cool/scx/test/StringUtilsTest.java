package cool.scx.test;

import cool.scx.util.StringUtils;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.assertEquals;

public class StringUtilsTest {

    public static void main(String[] args) throws IOException, InterruptedException {
        test1();
        test2();
        test3();
        System.out.println("所有测试通过 !!!");
    }

    @Test
    public static void test1() {
        var correctResults = "?, ?, ?, ?";
        var finalResult = StringUtils.repeat("?", ", ", 4);
        assertEquals(correctResults, finalResult);
    }

    @Test
    public static void test2() {
        var correctResults = true;
        var finalResult = StringUtils.isBlank(" ");
        assertEquals(correctResults, finalResult);
    }

    @Test
    public static void test3() {
        var correctResults = true;
        var finalResult = StringUtils.isEmpty("");
        assertEquals(correctResults, finalResult);
    }

}
