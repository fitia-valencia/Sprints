package com.testapp;

import com.monframework.core.RouteManager;

public class TestMain {
    public static void main(String[] args) {
        try {
            RouteManager routeManager = new RouteManager();
            
            System.out.println("=== Scanning des contrôleurs ===");
            routeManager.scanControllers("com.testapp");
            
            routeManager.listAllRoutes();
            
            System.out.println("\n=== Tests d'exécution ===");
            routeManager.executeRoute("/api/users");
            routeManager.executeRoute("/api/products");
            routeManager.executeRoute("/admin/dashboard");
            routeManager.executeRoute("/api/unknown");
            routeManager.executeRoute("/invalid");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}