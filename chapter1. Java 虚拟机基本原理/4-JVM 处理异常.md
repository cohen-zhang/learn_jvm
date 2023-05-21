# JVM 处理异常

-[ ] Suppressed 异常 和 try-with-resources 的语法糖 Demo

## 异常的类层级结构

![异常的类层级结构](https://raw.githubusercontent.com/zhongmingwu/java-learning-examples/master/images/exception-class-hierarchy.png)

异常类里顶级大佬：Throwable ；两个小弟是 Error 和 Exception 。

其中 Exception 有个儿子 RuntimeException，表示运行时异常，表示程序虽然无法继续运行，但是 JVM 能够恢复运行，比如 NullPointerException、ArrayIndexOutOfBoundsException 等等。

## JVM 如何捕获异常？

为什么说异常实例的构造十分昂贵，因为它需要收集栈信息（栈桢所指向方法名，以及方法所在的类名、文件名，和在代码中的第几行触发异常），这个过程十分耗时。

在编译生成的字节码中，每个方法都会有一个异常表，用来存储方法中可能抛出的异常，以及对应的异常处理器。它由这几个部分组成：from、to、target、type。

举例：

```java
public class ExceptionTest {
    public static void main(String[] args) {
        try {
            int i = 1 / 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

编译后的字节码：

```java
public class ExceptionTest {
    public ExceptionTest();
    Code:
       0: aload_0
       1: invokespecial #1                  // Method java/lang/Object."<init>":()V
       4: return

    public static void main(java.lang.String[]);
    Code:
       0: iconst_1
       1: iconst_0
       2: idiv
       3: istore_1
       4: goto          15
       7: astore_1
       8: aload_1
       9: invokevirtual #2                  // Method java/lang/Exception.printStackTrace:()V
      12: goto          15
      15: return
    Exception table:
       from    to  target type
           0     4     7   Class java/lang/Exception
}
```

在上面的字节码中，我们可以看到异常表中有一个异常处理器，它的作用是当字节码执行到第 0 行时，如果抛出了 Exception 类型的异常，就跳转到第 7 行执行。

针对有 finally 语句块的情况，字节码会在异常表中添加一个 finally 语句块的异常处理器，它的作用是当字节码执行到第 0 行时，如果抛出了 Exception 类型的异常，就跳转到第 7 行执行，如果没有抛出异常，就跳转到第 15 行执行。

finally 代码块的编译后的字节码：

```java
public class ExceptionTest {
    public static void main(String[] args) {
        try {
            int i = 1 / 0;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("finally");
        }
        
    }
}
```

编译后的字节码：

```java
public class ExceptionTest {
    public ExceptionTest();
    Code:
       0: aload_0
       1: invokespecial #1                  // Method java/lang/Object."<init>":()V
       4: return

    public static void main(java.lang.String[]);
    Code:
       0: iconst_1
       1: iconst_0
       2: idiv
       3: istore_1
       4: goto          18
       7: astore_1
       8: aload_1
       9: invokevirtual #2                  // Method java/lang/Exception.printStackTrace:()V
      12: getstatic     #3                  // Field java/lang/System.out:Ljava/io/PrintStream;
      15: ldc           #4                  // String finally
      17: invokevirtual #5                  // Method java/io/PrintStream.println:(Ljava/lang/String;)V
      20: return
    Exception table:
       from    to  target type
           0     4     7   Class java/lang/Exception // 
           0     4    18   any
}
```

编译结果包含几份 finally 语句块的异常处理器，它们的作用是当字节码执行到第 0 行时，如果抛出了 Exception 类型的异常，就跳转到第 7 行执行，如果没有抛出异常，就跳转到第 18 行执行。

## Java 7 异常处理的改进 Suppressed 异常和 try-with-resources

在 Java 7 中，引入了 try-with-resources 语法糖，它可以自动关闭资源，比如 IO 流、数据库连接等等。

```java
public class ExceptionTest {
    public static void main(String[] args) {
        try (FileInputStream fis = new FileInputStream("test.txt")) {
            fis.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

编译后的字节码：

```java
public class ExceptionTest {
    public ExceptionTest();
    Code:
       0: aload_0
       1: invokespecial #1                  // Method java/lang/Object."<init>":()V
       4: return

    public static void main(java.lang.String[]);
    Code:
       0: new           #2                  // class java/io/FileInputStream
       3: dup
       4: ldc           #3                  // String test.txt
       6: invokespecial #4                  // Method java/io/FileInputStream."<init>":(Ljava/lang/String;)V
       9: astore_1
      10: aload_1
      11: invokevirtual #5                  // Method java/io/FileInputStream.read:()I
      14: pop
      15: goto          29
      18: astore_2
      19: aload_1
      20: aload_2
      21: invokevirtual #6                  // Method java/lang/Throwable.addSuppressed:(Ljava/lang/Throwable;)V
      24: goto          29
      27: astore_3
      28: athrow
      29: return
    Exception table:
       from    to  target type
          10    15    18   Class java/lang/Throwable
          10    15    27   any
          18    24    27   any
}
```

运行结果：

```java
java.io.FileNotFoundException: test.txt (系统找不到指定的文件。)
    at java.io.FileInputStream.open0(Native Method)
    at java.io.FileInputStream.open(FileInputStream.java:195)
    at java.io.FileInputStream.<init>(FileInputStream.java:138)
    at ExceptionTest.main(ExceptionTest.java:7)
```

try-with-resources 还会使用 Suppressed 异常来记录关闭资源时抛出的异常，避免原始异常被掩盖。

## 常见问题

Q：是否可以缓存异常实例，在需要用到的时候直接抛出？

- 允许但不可以，这样会导致异常的栈信息丢失，误导开发者，不利于排查问题

Q：除了 RuntimeException 和 Error 属于非检查异常。其它的都 Exception 都属于检查异常。需要显式地捕获异常 或 throws 关键字的选择？

- 如果你能处理异常，就显式地捕获它
- 如果你不能处理异常，就 throws 它

Q：检查异常和非检查异常的区别？

- 检查异常：编译器会检查是否有处理异常的代码，如果没有，编译器会报错。也会在运行过程中抛出。
- 非检查异常：编译器不会检查是否有处理异常的代码

## 参考

- 《阿里巴巴Java开发规范》
