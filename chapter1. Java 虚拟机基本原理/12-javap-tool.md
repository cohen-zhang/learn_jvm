# 常用工具

## javap：反编译 Java Class 文件为字节码


使用 javap 命令反编译 [Foo](./Foo.java) 文件

```java
/**
 * 编译(javac Foo.java)并用 javap -c Foo 反编译查看编译后的字节码
 * @author zz
 * @date
 */
public class Foo {
    private int tryBlock;
    private int catchBlock;
    private int finallyBlock;
    private int methodExit;

    public void test() {
        for (int i = 0; i < 100; i++) {
            try {
                tryBlock = 0;
                if (i < 50) {
                    continue;
                } else if (i < 80) {
                    break;
                } else {
                    return;
                }
            } catch (Exception e) {
                catchBlock = 1;
            } finally {
                finallyBlock = 2;
            }
        }
        methodExit = 3;
    }
}
```


```shell
# 1。类文件的基本信息
practice git:(master) ✗ javap -p -v Foo 
Classfile /Users/zz/workspace/learn_jvm/chapter1. Java 虚拟机基本原理/practice/Foo.class
  Last modified 2023-1-20; size 629 bytes
  MD5 checksum fe3f6201174549748f172ac5857ac942
  Compiled from "Foo.java"
public class Foo
  minor version: 0
  major version: 52
  flags: ACC_PUBLIC, ACC_SUPER
Constant pool: # 2。 常量池和各种引用
   #1 = Methodref          #8.#24         // java/lang/Object."<init>":()V
   #2 = Fieldref           #7.#25         // Foo.tryBlock:I
   #3 = Fieldref           #7.#26         // Foo.finallyBlock:I
   #4 = Class              #27            // java/lang/Exception
   #5 = Fieldref           #7.#28         // Foo.catchBlock:I
   #6 = Fieldref           #7.#29         // Foo.methodExit:I
   #7 = Class              #30            // Foo
   #8 = Class              #31            // java/lang/Object
   #9 = Utf8               tryBlock
  #10 = Utf8               I
  #11 = Utf8               catchBlock
  #12 = Utf8               finallyBlock
  #13 = Utf8               methodExit
  #14 = Utf8               <init>
  #15 = Utf8               ()V
  #16 = Utf8               Code
  #17 = Utf8               LineNumberTable
  #18 = Utf8               test
  #19 = Utf8               StackMapTable
  #20 = Class              #27            // java/lang/Exception
  #21 = Class              #32            // java/lang/Throwable
  #22 = Utf8               SourceFile
  #23 = Utf8               Foo.java
  #24 = NameAndType        #14:#15        // "<init>":()V
  #25 = NameAndType        #9:#10         // tryBlock:I
  #26 = NameAndType        #12:#10        // finallyBlock:I
  #27 = Utf8               java/lang/Exception
  #28 = NameAndType        #11:#10        // catchBlock:I
  #29 = NameAndType        #13:#10        // methodExit:I
  #30 = Utf8               Foo
  #31 = Utf8               java/lang/Object
  #32 = Utf8               java/lang/Throwable
{
  # 3。字段区域
  private int tryBlock;
    descriptor: I
    flags: ACC_PRIVATE

  private int catchBlock;
    descriptor: I
    flags: ACC_PRIVATE

  private int finallyBlock;
    descriptor: I
    flags: ACC_PRIVATE

  private int methodExit;
    descriptor: I
    flags: ACC_PRIVATE

  public Foo();
    descriptor: ()V
    flags: ACC_PUBLIC
    Code:
      stack=1, locals=1, args_size=1
         0: aload_0
         1: invokespecial #1                  // Method java/lang/Object."<init>":()V
         4: return
      LineNumberTable:
        line 6: 0

# 4。 方法区域
  public void test();
    descriptor: ()V
    flags: ACC_PUBLIC
    Code: # 代码区域
      stack=2, locals=4, args_size=1 # 操作数栈、局部变量数目、参数个数
         0: iconst_0
         1: istore_1
         2: iload_1
         3: bipush        100
         5: if_icmpge     75
         8: aload_0
         9: iconst_0
        10: putfield      #2                  // Field tryBlock:I
        13: iload_1
        14: bipush        50
        16: if_icmpge     27
        19: aload_0
        20: iconst_2
        21: putfield      #3                  // Field finallyBlock:I
        24: goto          69
        27: iload_1
        28: bipush        80
        30: if_icmpge     41
        33: aload_0
        34: iconst_2
        35: putfield      #3                  // Field finallyBlock:I
        38: goto          75
        41: aload_0
        42: iconst_2
        43: putfield      #3                  // Field finallyBlock:I
        46: return
        47: astore_2
        48: aload_0
        49: iconst_1
        50: putfield      #5                  // Field catchBlock:I
        53: aload_0
        54: iconst_2
        55: putfield      #3                  // Field finallyBlock:I
        58: goto          69
        61: astore_3
        62: aload_0
        63: iconst_2
        64: putfield      #3                  // Field finallyBlock:I
        67: aload_3
        68: athrow
        69: iinc          1, 1
        72: goto          2
        75: aload_0
        76: iconst_3
        77: putfield      #6                  // Field methodExit:I
        80: return
      Exception table: # 异常表
         from    to  target type
             8    19    47   Class java/lang/Exception
            27    33    47   Class java/lang/Exception
             8    19    61   any
            27    33    61   any
            47    53    61   any
      LineNumberTable: # 行数表
        line 13: 0
        line 15: 8
        line 16: 13
        line 26: 19
        line 18: 27
        line 26: 33
        line 21: 46
        line 23: 47
        line 24: 48
        line 26: 53
        line 27: 58
        line 26: 61
        line 27: 67
        line 13: 69
        line 29: 75
        line 30: 80
      StackMapTable: number_of_entries = 7 
        frame_type = 252 /* append */
          offset_delta = 2
          locals = [ int ]
        frame_type = 24 /* same */
        frame_type = 13 /* same */
        frame_type = 69 /* same_locals_1_stack_item */
          stack = [ class java/lang/Exception ]
        frame_type = 77 /* same_locals_1_stack_item */
          stack = [ class java/lang/Throwable ]
        frame_type = 7 /* same */
        frame_type = 250 /* chop */
          offset_delta = 5
}
SourceFile: "Foo.java"

```