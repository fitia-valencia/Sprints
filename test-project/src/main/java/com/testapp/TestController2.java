package com.testapp;

import com.monframework.annotation.Controller;
import com.monframework.annotation.Route;

@Controller("/admin")
public class TestController2 {
    
    @Route(value = "/dashboard")
    public String showDashboard() {
        System.out.println("Tableau de bord admin - Controller2");
        return "Bienvenue sur le tableau de bord administrateur";
    }
    
    @Route(value = "/stats")
    public Object getStats() {
        System.out.println("Récupération des statistiques");
        return "Statistiques: 1500 utilisateurs, 300 produits";
    }
    
    @Route(value = "/null-method")
    public String nullMethod() {
        System.out.println("Méthode retournant null");
        return null;
    }
}