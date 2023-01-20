
/**
 *
 * 没有 try-with-resource 之前，如果有多个资源的关闭，要写多个 try-catch ，代码会很繁琐
 *
 * 运行结果：
 * Exception in thread "main" java.lang.RuntimeException: Initial
 * 	at TryWithResourceDemo.main(TryWithResourceDemo.java:35)
 * 	Suppressed: java.lang.RuntimeException: Foo2
 * 		at TryWithResourceDemo.close(TryWithResourceDemo.java:27)
 * 		at TryWithResourceDemo.main(TryWithResourceDemo.java:36)
 * 	Suppressed: java.lang.RuntimeException: Foo1
 * 		at TryWithResourceDemo.close(TryWithResourceDemo.java:27)
 * 		at TryWithResourceDemo.main(TryWithResourceDemo.java:36)
 * 	Suppressed: java.lang.RuntimeException: Foo0
 * 		at TryWithResourceDemo.close(TryWithResourceDemo.java:27)
 * 		at TryWithResourceDemo.main(TryWithResourceDemo.java:36)
 * @author zz
 * @date
 */
public class TryWithResourceDemo implements AutoCloseable {
    private final String name;
    public TryWithResourceDemo(String name) { this.name = name; }

    @Override
    public void close() {
        throw new RuntimeException(name);
    }

    public static void main(String[] args) {
        // try-with-resources
        try (TryWithResourceDemo foo0 = new TryWithResourceDemo("Foo0");
             TryWithResourceDemo foo1 = new TryWithResourceDemo("Foo1");
             TryWithResourceDemo foo2 = new TryWithResourceDemo("Foo2")) {
            throw new RuntimeException("Initial");
        }
    }
}

