# learn_jvm


### 我是Java程序猿，我骄傲

1. “一次编写，到处运行”
2. 相对安全的内存管理和访问机制，避免了大多数的内存泄漏和指针越界问题
3. 热点代码检测和运行时编译及优化


> 为什么要学习JVM，因为想停止恐惧😱


1. 用中国古话讲，是要“知其然”，还要“知其所以然”； 到高级开发工程师阶段，Java 虚拟机原理是必须知道的，是了解 Java 程序如何被执行和优化的基础。
2. 记八股文就你我自己学数学时，只记公式（Java 类库 API）没有用，容易忘记； 只有理解公式推导的过程才是最重要的
3. 能更好理解 Java 语言特性，写出简洁、高效代码


Java 虚拟机知识框架图

1. Java 程序语言设计
2. Class 文件格式
3. Java类库 API
4. 三方库 API

注意区分 JDK 和 JRE：
JDK 包含 JRE，JRE 仅包含运行 Java 程序的必须组件（也就是说有 JRE 就可以开发和运行Java程序了）


···shell···
java -XX:+PrintFlagsFinal -XX:+UnlockDiagnosticVMOptions -version
···
