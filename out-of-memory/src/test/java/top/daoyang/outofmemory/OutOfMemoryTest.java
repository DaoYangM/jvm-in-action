package top.daoyang.outofmemory;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code OutOfMemory} 实战测试
 */
public class OutOfMemoryTest {

    /**
     * VM Args: -Xms20m -Xmx20m -XX:+HeapDumpOnOutOfMemoryError
     */
    @Test
    public void heapOOM() {

        class OOMObject {}

        List<OOMObject> list = new ArrayList<>();

        while (true) { list.add(new OOMObject()); }
    }

    /**
     * Java7以后，字符串常量池从永久代中移动到堆中
     * VM Args: -Xms5m -Xmx5m
     */
    @Test
    public void StringConstantPoolOOM() {
        // 保留强引用，避免被FULL GC回收
        List<String> container = new ArrayList<>();
        short i = 0;
        while (true) {
            container.add(String.valueOf(i).intern());
        }
    }

}
