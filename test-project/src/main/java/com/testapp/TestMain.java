package com.testapp;

import com.monframework.core.ApplicationContext;

public class TestMain {
    public static void main(String[] args) {
        try {
            // Initialisation automatique au démarrage
            ApplicationContext context = ApplicationContext.init("com.testapp");
            
            System.out.println("\n=== TESTS D'EXÉCUTION ===");
            
            // Tester quelques routes
            context.executeRoute("/api/users");
            context.executeRoute("/admin/dashboard");
            context.executeRoute("/api/unknown");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}