package com.secrething.rpc.registry;

import com.secrething.common.util.ConcurrentHashMap;
import com.secrething.common.util.ConcurrentMap;
import javassist.*;
import sun.misc.Unsafe;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Idroton on 2018/9/5 10:26 PM.
 */
public abstract class Wrapper {
    private static final AtomicInteger fix = new AtomicInteger();
    private static final ConcurrentMap<Class<?>, Wrapper> wrapperCache = new ConcurrentHashMap<>();
    private static final ConcurrentMap<ClassLoader, ClassPool> pools = new ConcurrentHashMap<>();

    public static Wrapper getWrapper(Class<?> clzz) {
        return wrapperCache.putIfAbsent(clzz, (h) -> makeWrapper(clzz));
    }

    private static Wrapper makeWrapper(Class<?> clzz) {

        try {
            String superName = Wrapper.class.getName();
            String name = superName + "$" + fix.getAndIncrement();
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            ClassPool classPool = pools.putIfAbsent(loader, (h) -> {
                ClassPool pool = new ClassPool(true);
                pool.appendClassPath(new LoaderClassPath(loader));
                return pool;
            });
            CtClass ctClass = classPool.makeClass(name);
            ctClass.setSuperclass(classPool.get(superName));
            StringBuilder invokeMethod = new StringBuilder();
            Method[] methods = clzz.getMethods();

            invokeMethod.append("public  Object invokeMethod(Object instance, String methodName, Class[] paramTypes, Object[] args) throws Exception{");
            invokeMethod.append("try{");
            invokeMethod.append(clzz.getName()).append(" obj = (").append(clzz.getName()).append(") instance;");
            for (int i = 0; i < methods.length; i++) {
                Method m = methods[i];
                if (m.getDeclaringClass() == Object.class)
                    continue;
                invokeMethod.append("if(\"").append(m.getName()).append("\".equals(methodName)");
                Class[] paramTypes = m.getParameterTypes();
                invokeMethod.append("&& ").append(paramTypes.length).append("== paramTypes.length");
                StringBuilder paramBuilder = new StringBuilder();
                for (int j = 0; j < paramTypes.length; j++) {
                    invokeMethod.append("&& \"").append(paramTypes[j].getName()).append("\".equals(paramTypes[").append(j).append("].getName())");
                    //paramBuilder.append("(").append(paramTypes[j].getName()).append(") ").append("args[").append(j).append("]");
                    paramBuilder.append(arg(paramTypes[j], "args[" + j + "]"));
                    if (j < paramTypes.length - 1) {
                        paramBuilder.append(",");
                    }
                }
                invokeMethod.append("){");
                Class returnType = m.getReturnType();
                if (Void.TYPE == returnType) {
                    invokeMethod.append("obj.").append(m.getName()).append("(").append(paramBuilder).append("); return null;");
                } else {
                    invokeMethod.append("return obj.").append(m.getName()).append("(").append(paramBuilder).append(");");
                }
                invokeMethod.append("}");
            }
            invokeMethod.append("}catch(Throwable e){");
            invokeMethod.append("throw new Exception(e);");
            invokeMethod.append("}");
            invokeMethod.append("throw new Exception(\"No Such Method:\" + methodName);");
            invokeMethod.append("}");
            CtMethod met = CtNewMethod.make(invokeMethod.toString(), ctClass);
            ctClass.addMethod(met);
            Class<?> clz = ctClass.toClass();
            return (Wrapper) clz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            //skip
        }
        return null;
    }

    private static String arg(Class<?> cl, String name) {
        if (cl.isPrimitive()) {
            if (cl == Boolean.TYPE)
                return "((Boolean)" + name + ").booleanValue()";
            if (cl == Byte.TYPE)
                return "((Byte)" + name + ").byteValue()";
            if (cl == Character.TYPE)
                return "((Character)" + name + ").charValue()";
            if (cl == Double.TYPE)
                return "((Number)" + name + ").doubleValue()";
            if (cl == Float.TYPE)
                return "((Number)" + name + ").floatValue()";
            if (cl == Integer.TYPE)
                return "((Number)" + name + ").intValue()";
            if (cl == Long.TYPE)
                return "((Number)" + name + ").longValue()";
            if (cl == Short.TYPE)
                return "((Number)" + name + ").shortValue()";
            throw new RuntimeException("Unknown primitive type: " + cl.getName());
        }
        return "(" + getName(cl) + ")" + name;
    }

    /**
     * get name.
     * java.lang.Object[][].class => "java.lang.Object[][]"
     *
     * @param c class.
     * @return name.
     */
    private static String getName(Class<?> c) {
        if (c.isArray()) {
            StringBuilder sb = new StringBuilder();
            do {
                sb.append("[]");
                c = c.getComponentType();
            }
            while (c.isArray());

            return c.getName() + sb.toString();
        }
        return c.getName();
    }

    public abstract Object invokeMethod(Object instance, String methodName, Class[] paramTypes, Object[] args) throws Exception;

    /*public  Object invokeMethod(Object instance, String methodName, Class<?>[] paramTypes, Object[] args){
        com.secrething.rpc.remote.RemoteHandler h = (com.secrething.rpc.remote.RemoteHandler) instance;
    }*/
    static Unsafe unsafe;

    static {
        try {
            unsafe = (Unsafe) Unsafe.class.getField("theUnsafe").get(null);
        } catch (Exception e) {

        }

    }

    public static void main(String[] args) throws Exception {

    }

    static String pack = "java.util";
    private static int i = 1;

    private static void contansPack(File file, StringBuilder sbff) throws Exception {
        if (file.exists() && file.isDirectory()) {
            File[] childrs = file.listFiles();
            for (File f : childrs) {
                contansPack(f, sbff);
            }
        }
        if (file.exists() && file.isFile()) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line = null;
                while ((line = reader.readLine()) != null) {
                    if (line.indexOf(pack) >= 0) {
                        String f = file.getPath().replaceAll("/users/zqin/git/", "");
                        System.out.println(f);
                        sbff.append(i).append(" ").append(f).append(";\n");
                        i++;
                        break;
                    }
                }
                reader.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
