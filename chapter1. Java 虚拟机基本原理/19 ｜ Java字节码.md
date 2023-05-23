# 19 ｜ Java字节码(基础篇)

Debug 的时候， Java 方法的栈桢分为操作数栈和局部变量区。通常来说，程序需要将变量从局部变量区加载至操作数栈中，进行一番运算之后再存储回局部变量区中。这个过程中，操作数栈和局部变量区的数据交换是通过字节码指令来完成的。

## Java 字节码长什么样？

Java 字节码是一种面向栈的指令集，也就是说，它的操作对象是操作数栈，而不是寄存器。这种设计是基于两个考虑：

- 一方面，栈式指令集可以减少指令码的长度。因为栈式指令集可以通过压栈和出栈两个操作来取操作数，而不是像寄存器指令集那样，需要通过 load 和 store 操作来访问内存，这样就可以用更短的指令码来表示一条指令。
- 另一方面，栈式指令集可以在不同平台上更快地执行。因为栈式指令集完全是在内存中完成操作的，而寄存器指令集需要和 CPU 寄存器打交道，所以栈式指令集更加容易移植。

Demo：

```java
public class Demo {
    public static void main(String[] args) {
        int i = 2;
        int j = 3;
        int k = i + j;
    }
}
```

编译后的字节码：

```shell
Classfile /Users/xxx/xxx/Demo.class
  Last modified 2021-1-1; size 327 bytes
  MD5 checksum 7b3b1b3b3b3b3b3b3b3b3b3b3b3b3b
  Compiled from "Demo.java"
public class Demo
    minor version: 0
    major version: 52
    flags: ACC_PUBLIC, ACC_SUPER
Constant pool: 


## 操作数栈