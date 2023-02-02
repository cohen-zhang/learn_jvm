# JVM 层面理解 synchronized 关键字

> synchronized 在指令层面是加了 一个 monitor-enter + **多个** monitor-exit

抽象理解 monitor-enter 和 monitor-exit 作用：

被锁的对象，拥有一个锁计数器(为了同一个线程获取同一把锁) 和 一个指向持有该锁的进程的指针。

- 进入 monitor-enter ：
- 进入 monitor-exit :

## 锁的分类

### 1。 重量级锁

- 最常见的锁实现：**阻塞型**
- Java 线程的阻塞、唤醒是依赖操作系统实现的
- 红绿灯比喻：阻塞是熄火停车； 自旋是怠速停车等红灯
- 自旋不公平：占着茅坑不拉屎💩



### 2。 轻量级锁

### 3。 偏向锁


《Java 并发编程实战》
14 | Lock和Condition（上）：隐藏在并发包中的管程



