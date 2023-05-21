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

## 常见问题

是否可以缓存异常实例，在需要用到的时候直接抛出？

- 允许但不可以，这样会导致异常的栈信息丢失，误导开发者，不利于排查问题

显式地捕获异常 和 throws 关键字的选择？

- 如果你能处理异常，就显式地捕获它

## 参考

- 《阿里巴巴Java开发规范》
