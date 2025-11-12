package com.testapp;

import com.monframework.scanner.SimpleRouteScanner;
import com.monframework.annotation.Route;
import java.lang.reflect.Method;
import java.util.Set;

public class TestMain {
    public static void main(String[] args) {
        try {
            Set<Method> methods = SimpleRouteScanner.findAnnotatedMethods("com.testapp");
            
            System.out.println("URLs discovered:");
            for (Method method : methods) {
                Route route = method.getAnnotation(Route.class);
                System.out.println("URL: " + route.url() + " -> Method: " + method.getName());
                
                // Exécuter la méthode pour tester
                Object instance = method.getDeclaringClass().newInstance();
                method.invoke(instance);
                System.out.println("---");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}