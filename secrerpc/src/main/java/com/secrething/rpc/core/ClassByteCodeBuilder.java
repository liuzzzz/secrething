package com.secrething.rpc.core;

import com.secrething.common.util.ConcurrentHashMap;
import com.secrething.common.util.Validate;
import javassist.*;
import org.apache.commons.lang3.StringUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Idroton on 2018/9/9 1:46 PM.
 */
public class ClassByteCodeBuilder {
    private static final ConcurrentHashMap<ClassLoader, ClassPool> poolCache = new ConcurrentHashMap<>();
    private final ClassLoader classLoader;
    private final ClassPool classPool;
    private CtClass ctClass;
    private String className, superClassName;
    private Set<String> interfaces, fields, methods, constructors;

    public ClassByteCodeBuilder() {
        this.classLoader = Thread.currentThread().getContextClassLoader();
        this.classPool = poolCache.putIfAbsent(classLoader, (h) -> {
            ClassPool cp = new ClassPool(true);
            cp.appendClassPath(new LoaderClassPath(classLoader));
            return cp;
        });
    }

    public ClassByteCodeBuilder clazz(String className) {
        this.className = className;
        return this;
    }

    public ClassByteCodeBuilder addConstructor(String constructorCode) {
        if (null == constructors) {
            constructors = new HashSet<>();
        }
        constructors.add(constructorCode);
        return this;
    }

    public ClassByteCodeBuilder superClass(String superClassName) {
        this.superClassName = superClassName;
        return this;
    }

    public ClassByteCodeBuilder superClass(Class clzz) {
        this.superClassName = clzz.getName();
        return this;
    }

    public ClassByteCodeBuilder addInterface(String interfaceName) {
        if (null == interfaces) {
            interfaces = new HashSet<>();
        }
        interfaces.add(interfaceName);
        return this;
    }

    public ClassByteCodeBuilder addInterface(Class<?> interfase) {
        return addInterface(interfase.getName());
    }

    public ClassByteCodeBuilder addMethod(String methodCode) {
        if (null == methods) {
            methods = new HashSet<>();
        }
        methods.add(methodCode);
        return this;
    }

    public ClassByteCodeBuilder addField(String fieldCode) {
        if (null == fields) {
            fields = new HashSet<>();
        }
        fields.add(fieldCode);
        return this;
    }

    public <T> Class<T> toClass() throws Exception {
        if (null != ctClass) {
            ctClass.detach();
        }
        ctClass = classPool.makeClass(this.className);

        if (Validate.notEmpty(constructors)) {
            for (String constructor : constructors) {
                CtConstructor ctConstructor = CtNewConstructor.make(constructor, ctClass);
                ctClass.addConstructor(ctConstructor);
            }
        }
        if (StringUtils.isNotBlank(this.superClassName)) {
            CtClass spClzz = classPool.get(superClassName);
            ctClass.setSuperclass(spClzz);
        }
        if (Validate.notEmpty(interfaces)) {
            for (String interfas : interfaces) {
                CtClass interfs = classPool.get(interfas);
                ctClass.addInterface(interfs);
            }
        }
        if (Validate.notEmpty(fields)) {
            for (String fieldCode : fields) {
                CtField field = CtField.make(fieldCode, ctClass);
                ctClass.addField(field);
            }
        }
        if (Validate.notEmpty(methods)) {
            for (String methodCode : methods) {
                CtMethod method = CtNewMethod.make(methodCode, ctClass);
                ctClass.addMethod(method);
            }
        }
        return ctClass.toClass(this.classLoader, this.getClass().getProtectionDomain());
    }


}
