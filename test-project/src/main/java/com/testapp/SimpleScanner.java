package com.testapp;

import com.monframework.annotation.Route;
import java.lang.reflect.Method;

public class SimpleScanner {
    public static void main(String[] args) throws Exception {
        Class<?> clazz = Class.forName("com.testapp.TestController");
        
        System.out.println("Methods in TestController:");
        for (Method method : clazz.getDeclaredMethods()) {
            System.out.println("- " + method.getName());
            Route route = method.getAnnotation(Route.class);
            if (route != null) {
                System.out.println("  HAS @Route: " + route.url());
            }
        }
    }
}