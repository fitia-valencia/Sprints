package com.monframework.core;

import com.monframework.annotation.Controller;
import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class ControllerScanner {
    public static Set<Class<?>> findAnnotatedClasses(String packageName) throws Exception {
        Set<Class<?>> controllers = new HashSet<>();
        String path = packageName.replace('.', '/');
        File directory = new File("target/classes/" + path);
        
        if (directory.exists()) {
            scanDirectory(directory, packageName, controllers);
        }
        return controllers;
    }
    
    private static void scanDirectory(File dir, String packageName, Set<Class<?>> controllers) throws Exception {
        for (File file : dir.listFiles()) {
            if (file.isDirectory()) {
                scanDirectory(file, packageName + "." + file.getName(), controllers);
            } else if (file.getName().endsWith(".class")) {
                String className = packageName + "." + file.getName().replace(".class", "");
                checkController(className, controllers);
            }
        }
    }
    
    private static void checkController(String className, Set<Class<?>> controllers) throws Exception {
        Class<?> clazz = Class.forName(className);
        if (clazz.isAnnotationPresent(Controller.class)) {
            controllers.add(clazz);
            System.out.println("Contrôleur trouvé: " + className);
        }
    }
}