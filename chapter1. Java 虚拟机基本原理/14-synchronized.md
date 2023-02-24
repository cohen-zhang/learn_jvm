# JVM 层面理解 synchronized 关键字

> 总结：synchronized 在指令层面是加了 一个 monitor-enter + **多个** monitor-exit

抽象理解 monitor-enter 和 monitor-exit 作用：
被锁的对象，拥有一个锁计数器(为了同一个线程获取同一把锁) 和 一个指向持有该锁的进程的指针。

- 进入 monitor-enter ： if(目标对象的计数器 == 0) {计数器 = 计数器+1;}
- 进入 monitor-exit : {计数器 = 计数器+1;} if(目标对象的计数器 == 0)  {锁已经被释放掉}

```shell

  public void foo(Object lock) {
    synchronized (lock) {
      lock.hashCode();
    }
  }
  // 上面的Java代码将编译为下面的字节码
  public void foo(java.lang.Object);
    Code:
       0: aload_1
       1: dup
       2: astore_2
       3: monitorenter
       4: aload_1
       5: invokevirtual java/lang/Object.hashCode:()I
       8: pop
       9: aload_2
      10: monitorexit
      11: goto          19
      14: astore_3
      15: aload_2
      16: monitorexit
      17: aload_3
      18: athrow
      19: return
    Exception table:
       from    to  target type
           4    11    14   any
          14    17    14   any


```

> 相关引用： 异常处理

## 锁的分类

### 1。 重量级锁

- 最常见的锁实现：**阻塞型**
- Java 线程的阻塞、唤醒是依赖操作系统实现的
- 红绿灯比喻：阻塞是熄火停车； 自旋是怠速停车等红灯
- 自旋不公平：占着茅坑不拉屎💩



### 2。 轻量级锁

### 3。 偏向锁


##《Java 并发编程实战》课程联动

14 | Lock和Condition（上）：隐藏在并发包中的管程


## 问题
1. 怎么通过源码看一个锁（比如 ReentrantLock）是不是阻塞型的？
2. 



