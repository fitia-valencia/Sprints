package com.testapp;

import com.monframework.core.ApplicationContext;

public class TestMain {
    public static void main(String[] args) {
        try {
            ApplicationContext context = ApplicationContext.init("com.testapp");
            
            System.out.println("\n=== TESTS MODELVIEW ===");
            
            // Tester les routes ModelView
            context.executeRoute("/pages/test");
            context.executeRoute("/pages/home");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}