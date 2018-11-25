package com.secrething.common.core;

import com.secrething.common.util.ConcurrentHashMap;
import com.secrething.common.util.ConcurrentMap;
import javassist.*;

import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by liuzz on 2018/11/25 上午12:35.
 */
public abstract class MapWriter {
    private static final ConcurrentMap<Class, MapWriter> writerCache = new ConcurrentHashMap<>();
    private static final ConcurrentMap<ClassLoader, ClassPool> pools = new ConcurrentHashMap<>();
    private static final AtomicInteger idx = new AtomicInteger(0);

    public static MapWriter getWriter(Class clzz) throws Exception {
        if (Object.class != clzz.getSuperclass() || clzz.getInterfaces().length > 0) {
            throw new IllegalArgumentException("clzz super class not support");
        }

        MapWriter writer = writerCache.get(clzz);
        if (null == writer) {
            synchronized (writerCache) {
                writer = writerCache.get(clzz);
                if (null == writer) {
                    String clzzName = "MapWriter$" + idx.getAndIncrement();
                    ClassLoader loader = Thread.currentThread().getContextClassLoader();
                    ClassPool classPool = pools.putIfAbsent(loader, (h) -> {
                        ClassPool pool = new ClassPool(true);
                        pool.appendClassPath(new LoaderClassPath(loader));
                        return pool;
                    });
                    CtClass ctClass = classPool.makeClass(clzzName);
                    String objClassName = clzz.getName();
                    ctClass.setSuperclass(classPool.get(MapWriter.class.getName()));
                    StringBuilder toMapBuilder = new StringBuilder("public Object toMap(Object o) throws Exception{");
                    toMapBuilder.append("java.util.HashMap m = new java.util.HashMap();");
                    toMapBuilder.append("if(o instanceof " + objClassName + "){");
                    toMapBuilder.append(objClassName).append(" obj = (" + objClassName + ")o;");


                    Field[] fields = clzz.getDeclaredFields();
                    for (Field f : fields) {
                        toMapBuilder.append(build(f, clzz));
                    }
                    toMapBuilder.append("}");
                    toMapBuilder.append("return m;");
                    toMapBuilder.append("}");
                    CtMethod met = CtNewMethod.make(toMapBuilder.toString(), ctClass);
                    ctClass.addMethod(met);
                    Class<?> clz = ctClass.toClass();
                    writer = (MapWriter) clz.getConstructor().newInstance();
                    writerCache.putIfAbsent(clzz, writer);
                }
            }
        }

        return writer;

    }

    public static void main(String[] args) throws Exception {
    }

    private static String build(Field f, Class clzz) throws NoSuchMethodException {
        DocField e = f.getAnnotation(DocField.class);
        if (null == e){
            return "";
        }
        String fName = f.getName();
        String key =fName;
        if (!isBlank(e.key())){
            key = e.key();
        }
        String getName = fName.substring(0, 1).toUpperCase() + fName.substring(1);
        String methodName;
        if (f.getType() == boolean.class || f.getType() == Boolean.class) {
            try {
                if (null != clzz.getDeclaredMethod("is" + getName)) {
                    methodName = "is" + getName;
                } else {
                    throw new IllegalArgumentException("method[get" + getName + "(),is" + getName + "()] not exits");
                }
            } catch (NoSuchMethodException eee) {
                methodName = getString(clzz, getName);
            }
        } else {
            methodName = getString(clzz, getName);
        }
        String put = "obj." + methodName + "()";
        if (f.getType() == boolean.class) {
            put = "Boolean.valueOf(obj." + methodName + "())";
        } else if (f.getType() == int.class) {
            put = "Integer.valueOf(obj." + methodName + "())";
        } else if (f.getType() == short.class) {
            put = "Short.valueOf(obj." + methodName + "())";
        } else if (f.getType() == char.class) {
            put = "Character.valueOf(obj." + methodName + "())";
        } else if (f.getType() == long.class) {
            put = "Long.valueOf(obj." + methodName + "())";
        } else if (f.getType() == float.class) {
            put = "Float.valueOf(obj." + methodName + "())";
        } else if (f.getType() == double.class) {
            put = "Double.valueOf(obj." + methodName + "())";
        } else if (f.getType() == byte.class) {
            put = "Byte.valueOf(obj." + methodName + "())";
        }
        return "m.put(\"" + key + "\"," + put + ");";

    }

    private static boolean isBlank(CharSequence cs) {
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private static String getString(Class clzz, String getName) {
        String methodName;
        try {
            if (null != clzz.getDeclaredMethod("get" + getName))
                methodName = "get" + getName;
            else {
                throw new IllegalArgumentException("method[get" + getName + "(),is" + getName + "()] not exits");
            }
        } catch (NoSuchMethodException ee) {
            throw new IllegalArgumentException("method[get" + getName + "(),is" + getName + "()] not exits");
        }
        return methodName;
    }

    public abstract Object toMap(Object obj) throws Exception;
}
