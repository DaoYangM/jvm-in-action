package top.daoyang.reference;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;

public class ReferenceTest {

    /**
     * 1MB Memory size
     */
    private static final int _1MB = 1024 * 1024;

    /**
     * 测试SoftReference在OOM的情况下是否会回收软引用对象
     * <p>
     * JVM -Xmx5m
     */
    @Test
    @SuppressWarnings("unused")
    public void testSoftReference() {
        // 分配10M的内存空间
        byte[] bigSize = new byte[_1MB * 2];

        ReferenceQueue<byte[]> referenceQueue = new ReferenceQueue<>();

        SoftReference<byte[]> softReference = new SoftReference<>(bigSize, referenceQueue);

        // 使得强应用变为软应用
        bigSize = null;

        // 再次分配内存时，会导致OOM，所以会回收软引用对象
        byte[] bigSizeNew = new byte[_1MB];

        // 对象被回收，所以软引用返回null
        Assertions.assertNull(softReference.get());

        Reference<? extends byte[]> poll = referenceQueue.poll();

        // 引用队列中存在软引用对象
        Assertions.assertNotNull(poll);
    }
}
