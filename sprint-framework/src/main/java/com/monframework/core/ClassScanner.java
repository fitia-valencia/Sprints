package com.monframework.core;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class ClassScanner {
    public static Set<Class<?>> findAllClasses(String packageName) throws Exception {
        Set<Class<?>> classes = new HashSet<>();
        String path = packageName.replace('.', '/');
        File directory = new File("target/classes/" + path);
        
        if (directory.exists()) {
            scanDirectory(directory, packageName, classes);
        }
        return classes;
    }
    
    private static void scanDirectory(File dir, String packageName, Set<Class<?>> classes) throws Exception {
        for (File file : dir.listFiles()) {
            if (file.isDirectory()) {
                scanDirectory(file, packageName + "." + file.getName(), classes);
            } else if (file.getName().endsWith(".class")) {
                String className = packageName + "." + file.getName().replace(".class", "");
                classes.add(Class.forName(className));
            }
        }
    }
}