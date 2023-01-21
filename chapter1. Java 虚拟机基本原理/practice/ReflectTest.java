import java.lang.reflect.Method;

/**
 * @author zz
 * 打印一下反射调用到目标方法时的栈轨迹
 */
public class ReflectTest {
    public static void target(int i) {
        new Exception("#" + i).printStackTrace();
    }
    public static void main(String[] args) throws Exception {
        Class klass = Class.forName("Test");
        Method method = klass.getMethod("target", int.class);
        for (int i = 0; i < 20; i++) {
            method.invoke(null, i);
        }
    }
}
