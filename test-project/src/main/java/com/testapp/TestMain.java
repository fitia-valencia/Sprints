package com.testapp;

import com.monframework.core.ApplicationContext;

public class TestMain {
    public static void main(String[] args) {
        try {
            // Initialisation automatique
            ApplicationContext context = ApplicationContext.init("com.testapp");
            
            System.out.println("\n" + "=".repeat(50));
            System.out.println("=== TESTS AVEC GESTION DES VALEURS DE RETOUR ===");
            System.out.println("=".repeat(50) + "\n");
            
            // Tester différentes méthodes avec retours variés
            context.executeRoute("/api/users");        // Retour String
            context.executeRoute("/api/products");     // Retour String  
            context.executeRoute("/api/count");        // Retour Integer
            context.executeRoute("/api/void-method");  // Retour void
            context.executeRoute("/admin/dashboard");  // Retour String
            context.executeRoute("/admin/stats");      // Retour Object
            context.executeRoute("/admin/null-method"); // Retour null
            
            // Test avec URL non trouvée
            context.executeRoute("/api/unknown");
            
            System.out.println("\n" + "=".repeat(50));
            System.out.println("=== TESTS AVEC executeAndReturn() ===");
            System.out.println("=".repeat(50) + "\n");
            
            // Tester la méthode qui retourne directement le résultat
            Object result1 = context.executeAndReturn("/api/users");
            System.out.println("Résultat direct: " + result1);
            
            Object result2 = context.executeAndReturn("/api/count");
            System.out.println("Résultat direct: " + result2 + " (type: " + result2.getClass().getSimpleName() + ")");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}