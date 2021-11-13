package geekCode01.test2;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 自定义一个 Classloader，加载一个 Hello.xlass 文件，执行 hello 方法，
 * 此文件内容是一个 Hello.class 文件所有字节（x=255-x）处理后的文件。文件群里提供。
 */
public class TestClassLoader extends ClassLoader {

    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        // 指定类加载器加载调用
        TestClassLoader testClassLoader = new TestClassLoader();
        Class<?> TClass = Class.forName("Hello", true, testClassLoader);
        Object obj = TClass.newInstance();
        Method method = obj.getClass().getMethod("hello");
        method.invoke(obj);
    }


    @Override
    public Class<?> findClass(String name) throws ClassNotFoundException {
        // 读取resources目录下的文件
        String loadPath = ClassLoader.getSystemClassLoader().getResource("Hello.xlass").getPath();
        byte[] classData = getClassData(loadPath);
        if (classData == null) {
            throw new ClassNotFoundException();
        } else {
            return defineClass(name, classData, 0, classData.length);
        }
    }

    //获取文件class的byte数组
    private byte[] getClassData(String loadPath) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            InputStream inputStream = new FileInputStream(loadPath);
            int bufferSize = 4096;
            byte[] buffer = new byte[bufferSize];
            int bytesNumRead = 0;
            while ((bytesNumRead = inputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, bytesNumRead);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return decode(byteArrayOutputStream.toByteArray());
    }

    //解密 文件内容是一个Hello.xlass文件所有字节（x=255-x）处理后的文件 所以需要对文件进行解密操作
    private byte[] decode(byte[] byteArr) {
        byte[] bytes = new byte[byteArr.length];
        for (int i = 0; i < byteArr.length; i++) {
            bytes[i] = (byte) (255 - byteArr[i]);
        }
        return bytes;
    }
}

