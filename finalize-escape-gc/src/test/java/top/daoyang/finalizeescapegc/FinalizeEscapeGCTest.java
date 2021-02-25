package top.daoyang.finalizeescapegc;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * 使用{@code finalize}拯救对象，注意{@code finalize} 方法只会被调用一次
 */
public class FinalizeEscapeGCTest {

    static FinalizeEscapeGC finalizeEscapeGCHolder;

    static class FinalizeEscapeGC {
        @Override
        protected void finalize() throws Throwable {
            super.finalize();
            System.out.println("finalize method executed!");
            // 拯救对象
            finalizeEscapeGCHolder = this;
        }
    }

    @Test
    @SneakyThrows
    public void finalizeEscapeGC() {
         finalizeEscapeGCHolder = new FinalizeEscapeGC();
         finalizeEscapeGCHolder = null;

         // 拯救第一次
         System.gc();
         Thread.sleep(500);

         if (finalizeEscapeGCHolder != null) {
             System.out.println("Save the object");
         } else {
             System.out.println("no, i am dead");
         }

        finalizeEscapeGCHolder = null;

         // finalize 方法只会被调用一次，所以对象将不会被再次拯救
        System.gc();
        Thread.sleep(500);

        Assertions.assertNull(finalizeEscapeGCHolder);

        System.out.println("no, i am dead");
    }
}
