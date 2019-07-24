package org.smart4j.framework.utils;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * 类工具类
 */
public final class ClassUtils {

    /**
     * 类加载器
     */
    public static ClassLoader getClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

    /**
     * 加载类
     */
    public static Class loadClass(String clsName, boolean isInitialized) {
        Class<?> cls;
        try {
            cls = Class.forName(clsName, isInitialized, getClassLoader());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException();
        }
        return cls;
    }

    /**
     * 获取指定包名下所有的类
     * 默认为根路径下所有类
     */
    public static Set<Class<?>> getClassSet(String packageName) {
        Set<Class<?>> cls = new HashSet<>();
        Enumeration<URL> urls = null;
        try {
            urls = getClassLoader().getResources(packageName.replace(".", "/"));
            while (urls.hasMoreElements()) {
                URL url = urls.nextElement();
                if (url != null) {
                    // 获取此URL协议的名称
                    String protocol = url.getProtocol();
                    // 如果协议为file
                    if (protocol.equals("file")) {
                        // 得到此URL的部分路径
                        String packagePath = url.getPath().replaceAll("%20", " ");
                        addClass(cls, packagePath, packageName);
                    } else if (protocol.equals("jar")) {
                        JarURLConnection urlConnection = (JarURLConnection) url.openConnection();
                        if (urlConnection != null) {
                            JarFile jarFile = urlConnection.getJarFile();
                            if (jarFile != null) {
                                Enumeration<JarEntry> jarEntryEnumeration = jarFile.entries();
                                while (jarEntryEnumeration.hasMoreElements()) {
                                    JarEntry jarEntry = jarEntryEnumeration.nextElement();
                                    String name = jarEntry.getName();
                                    if (name.endsWith(".class")) {
                                        String className = name.substring(0, name.lastIndexOf("."));
                                        doAddClass(cls, className);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cls;
    }

    /**
     * 获取类
     *
     * @param ClassSet
     * @param className
     */
    private static void doAddClass(Set<Class<?>> ClassSet, String className) {
        Class loadClass = loadClass(className, false);
        ClassSet.add(loadClass);
    }

    /**
     * 获取所有的类，并进行添加
     *
     * @param classSet
     * @param packagePath 项目根路径
     * @param packageName 包名
     */
    private static void addClass(Set<Class<?>> classSet, String packagePath, String packageName) {
        File[] files = new File(packagePath).listFiles(new FileFilter() {
            @Override
            // 过滤出所有文件和目录
            public boolean accept(File f) {
                return (f.isFile() && f.getName().endsWith(".class")) || f.isDirectory();
            }
        });
        for (File file : files) {
            String fileName = file.getName();
            // 如果是文件
            if (file.isFile()) {
                String className = fileName.substring(0, fileName.lastIndexOf("."));
                if (StringUtils.isNotEmpty(className)) {
                    // 得到文件全路径 包名+文件名
                    className = packageName + "." + className;
                }
                doAddClass(classSet, className);
            } else {
                // 如果为目录，则获取目录后的路径和包名,递归调用
                String subPackagePath = fileName;
                if (StringUtils.isNotEmpty(subPackagePath)) {
                    subPackagePath = packagePath + "/" + fileName;
                }
                String subPackageName = fileName;
                if (StringUtils.isNotEmpty(subPackageName)) {
                    subPackageName = packageName + "." + subPackageName;
                }
                addClass(classSet, subPackagePath, subPackageName);
            }
        }
    }
}
