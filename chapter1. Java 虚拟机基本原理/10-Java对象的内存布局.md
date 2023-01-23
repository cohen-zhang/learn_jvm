# 内存布局

### 下一步行动项
- [ ] 



### 引子：单身狗没有"对象"怎么变一个？ 

1. new()
2. 反射机制
3. Object.clone()
4. 反序列化
5. Unsafe.allocateInstance

### 重新认识下构造器

1. 构造器的作用？
2. Java 对构造器的约束
3. 

```java

// Foo foo = new Foo(); 编译而成的字节码
  0 new Foo
  3 dup
  4 invokespecial Foo()
  7 astore_1

```