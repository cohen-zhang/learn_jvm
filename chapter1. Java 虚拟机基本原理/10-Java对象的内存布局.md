# Java 对象的内存布局

> **其实就是对象实例字段在内存中的分布**

## 下一步行动项

- [ ] 

### 引子：单身狗没有"对象"怎么变一个？ 

1. new()
2. 反射机制
3. Object.clone()：
4. 反序列化
5. Unsafe.allocateInstance

#### 按初始化实例字段的方式不同分个类：

1） 通过直接复制已有的数据：
2） 通过调用构造器：
3）

### 重新认识下构造器

1. 构造器的作用？
2. Java 对构造器的约束： 至少有一个无参数的构造器
3. 构造器的调用时机： new 一个对象的时候
4. 构造器的调用顺序： 从上到下，从父到子
5. 构造器的调用方式： 通过 new 指令调用，或者反射机制调用，或者反序列化调用，或者clone调用，或者Unsafe.allocateInstance调用，或者调用父类的构造器

```shell

// Foo foo = new Foo(); 编译而成的字节码
  0 new Foo
  3 dup
  4 invokespecial Foo()
  7 astore_1

```

> 通过 new 指令新建出来的对象，它的内存已经有所有父类中的实例字段！

## 你对象的头（Object header）

1. 标记字段： 对象的运行数据，比如哈希码、GC 信息以及锁信息； 占 64位 8个字节。
2. 类型指针：指向该对象的类； 占 64位 ，占这么多内存，开销太大了，压缩！！！ 引入**压缩指针** 给丫压成4个字节！

## 压缩指针（-XX：+UseCompressedOops 默认开启）

举例：房车占2/3个停车位，

压缩原理：用房车的车号代替车位号进行**内存寻址**

前提：

字段对齐原一个原因：让字段只出现在同一个CPU的缓存行中，不出现跨缓存行的现象。

## 字段重排列顺序

### 名词解释

偏移量：字段地址与对象的起始地址差值。

1. rule_1: 占据 C 个字节的字段，那么该字段的偏移量需要对齐至 N*C

比如 long 类型为例子，仅有一个 long 类型的实例字段。 
在使用了 64位虚拟机，尽管对象头大小为12 字节，但是由于字段对齐的原因，实例字段的偏移量为16字节，所以对象的大小为 12 + 16 = 28 字节。

```shell

2. rule_2： 子类所继承字段的偏移量，需要与他老子的一致
   
```java
class A {
  long l;
  int i；
}

class B extends A {
  long l;
  int i;
}
```

```shell
# 启用压缩指针时，B类的字段分布
B object internals:
 OFFSET  SIZE   TYPE DESCRIPTION
      0     4        (object header)
      4     4        (object header)
      8     4        (object header)
     12     4    int A.i                                       0 # 将A类的int字段放在了 long 字段之前
     16     8   long A.l                                       0
     24     8   long B.l                                       0
     32     4    int B.i                                       0
     36     4        (loss due to the next object alignment) # 由于对象整体大小需要对齐至8N，最后需要4字节的空白填充
```

虚共享：暂时可不搞懂

**问题：**
最近研究String时遇到一个跟Java内存相关的问题：常量池里到底有没有存放对象？

**回答：**
常量池主要存放两大类常量：字面量（Literal）和符号引用（Symbolic Reference）；
如果常量池里有一个“hello”的字面量，这个字面量算是一个对象吗？如果不算对象，那么它所指向的对象又存放在哪里呢
作者回复: String literal指向的对象存放在JVM的String pool里，这个pool是一个特殊的对象数组，里面存放的是String对象的引用，而不是对象本身。String pool是一个固定大小的对象数组，不会自动扩容，当String pool满了之后，再往里面添加String literal，就会导致OOM。