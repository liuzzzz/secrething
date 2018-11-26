package com.secrething.common.core;

import javassist.*;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by liuzz on 2018/11/25 上午12:35.
 */
@Slf4j
public abstract class MapWriter {
    private static final ConcurrentMap<Class, MapWriter> writerCache = new ConcurrentHashMap<>();
    private static final ConcurrentMap<ClassLoader, ClassPool> pools = new ConcurrentHashMap<>();
    private static final AtomicInteger idx = new AtomicInteger(0);
    private static final AtomicLong suffix = new AtomicLong(0);

    static MapWriter getWriter(final Class clzz) throws Exception {
        MapWriter writer = writerCache.computeIfAbsent(clzz, (k) -> {
            try {
                String clzzName = MapWriter.class.getName() + "$" + idx.getAndIncrement();
                ClassLoader loader = Thread.currentThread().getContextClassLoader();
                ClassPool classPool = pools.computeIfAbsent(loader, (h) -> {
                    ClassPool pool = new ClassPool(true);
                    pool.appendClassPath(new LoaderClassPath(loader));
                    return pool;
                });
                CtClass ctClass = classPool.makeClass(clzzName);
                String objClassName = clzz.getName();
                ctClass.setSuperclass(classPool.get(MapWriter.class.getName()));
                StringBuilder toMapBuilder = new StringBuilder("java.util.Map toMap(Object o) throws Exception{");
                StringBuilder parseBuilder = new StringBuilder("Object parseObject(java.util.Map map) throws Exception{");
                parseBuilder.append(objClassName).append(" obj = new ").append(objClassName).append("();");
                parseBuilder.append("if(null == map){return obj;}");
                toMapBuilder.append("java.util.HashMap m = new java.util.HashMap();");
                toMapBuilder.append("if(null == o){return m;}");
                toMapBuilder.append("if(o instanceof " + objClassName + "){");
                toMapBuilder.append(objClassName).append(" obj = (" + objClassName + ")o;");
                Class foreachClass = clzz;
                while (foreachClass != Object.class) {
                    Field[] fields = foreachClass.getDeclaredFields();
                    for (Field f : fields) {
                        toMapBuilder.append(buildGet(f, foreachClass));
                        parseBuilder.append(buildSet(f, foreachClass));
                    }
                    foreachClass = foreachClass.getSuperclass();

                }

                toMapBuilder.append("}");
                toMapBuilder.append("return m;");
                parseBuilder.append("return obj;");
                toMapBuilder.append("}");
                parseBuilder.append("}");
                CtMethod met = CtNewMethod.make(toMapBuilder.toString(), ctClass);
                CtMethod set = CtNewMethod.make(parseBuilder.toString(), ctClass);
                ctClass.addMethod(met);
                ctClass.addMethod(set);


                Class<?> clz = ctClass.toClass();
                return (MapWriter) clz.getConstructor().newInstance();
            } catch (Exception e) {
                log.error("MapWriter make error",e);
                return null;
            }

        });
        return writer;

    }

    private static String buildGet(Field f, Class clzz) throws NoSuchMethodException {
        Entry e = f.getAnnotation(Entry.class);
        if (null == e) {
            return "";
        }
        String fName = f.getName();
        String key = fName;
        if (!isBlank(e.key())) {
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

    private static String buildSet(Field f, Class clzz) throws NoSuchMethodException {
        Entry e = f.getAnnotation(Entry.class);
        if (null == e) {
            return "";
        }
        String fName = f.getName();
        String key = fName;
        if (!isBlank(e.key())) {
            key = e.key();
        }
        String setName = fName.substring(0, 1).toUpperCase() + fName.substring(1);
        String methodName = setString(clzz, setName, f);
        StringBuilder setBuilder = new StringBuilder();
        String vName = "v_" + suffix.getAndIncrement();
        setBuilder.append("Object ").append(vName).append(" = map.get(\"").append(key).append("\");");
        setBuilder.append("if(null != ").append(vName).append("){");
        if (f.getType() == boolean.class) {
            setBuilder.append("boolean v = Boolean.valueOf(").append(vName).append(".toString()).booleanValue();");
        } else if (f.getType() == int.class) {
            setBuilder.append("int v = Integer.valueOf(").append(vName).append(".toString()).intValue();");
        } else if (f.getType() == short.class) {
            setBuilder.append("short v = Short.valueOf(").append(vName).append(".toString()).shortValue();");
        } else if (f.getType() == char.class) {
            setBuilder.append("char v = Character.valueOf(").append(vName).append(".toString()).charValue();");
        } else if (f.getType() == long.class) {
            setBuilder.append("long v = Long.valueOf(").append(vName).append(".toString()).longValue();");
        } else if (f.getType() == float.class) {
            setBuilder.append("float v = Float.valueOf(").append(vName).append(".toString()).floatValue();");
        } else if (f.getType() == double.class) {
            setBuilder.append("double v = Double.valueOf(").append(vName).append(".toString()).doubleValue();");
        } else if (f.getType() == byte.class) {
            setBuilder.append("byte v = Byte.valueOf(").append(vName).append(".toString()).byteValue();");
        } else {
            setBuilder.append(f.getType().getName()).append(" v = (").append(f.getType().getName()).append(")").append(vName).append(";");
        }
        setBuilder.append("obj.").append(methodName).append("(v);");
        setBuilder.append("}\n");
        return setBuilder.toString();
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

    private static String setString(Class clzz, String setName, Field f) {
        String methodName;
        try {
            if (null != clzz.getDeclaredMethod("set" + setName, f.getType()))
                methodName = "set" + setName;
            else {
                throw new IllegalArgumentException("method[set" + setName + "(" + f.getType().getName() + ")] not exits");
            }
        } catch (NoSuchMethodException ee) {
            throw new IllegalArgumentException("method[set" + setName + "(" + f.getType().getName() + ")] not exits");
        }
        return methodName;
    }

    @SuppressWarnings("unchecked")
    public static <K, V> Map<K, V> map(Object o) {
        try {
            return MapWriter.getWriter(o.getClass()).toMap(o);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> T parse(Map map, Class<T> clzz) {
        try {
            return MapWriter.getWriter(clzz).toObject(map);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    abstract Map toMap(Object obj) throws Exception;

    abstract Object parseObject(Map map) throws Exception;

    @SuppressWarnings("unchecked")
    private <T> T toObject(Map map) throws Exception {
        return (T) parseObject(map);

    }

}
