package top.daoyang.stackoverflow;

import org.junit.jupiter.api.Test;

/**
 * 测试{@code StackOverflow} 异常
 */
public class StackOverflowErrorTest {

    /**
     * VM Args: -Xss128k
     */
    @Test
    public void testStackOverflow() {
        class SOF {
            private long aLong = Long.MIN_VALUE;

            public void stackLeak() {
                aLong++;
                stackLeak();
            }
        }

        new SOF().stackLeak();
    }
}
