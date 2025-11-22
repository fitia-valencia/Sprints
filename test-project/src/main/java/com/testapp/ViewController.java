package com.testapp;

import com.monframework.annotation.Controller;
import com.monframework.annotation.Route;
import com.monframework.core.ModelView;

@Controller("/pages")
public class ViewController {
    
    @Route(value = "/home")
    public ModelView showHome() {
        System.out.println("Affichage de la page d'accueil");
        
        ModelView modelView = new ModelView("home.jsp");
        modelView.addObject("title", "Page d'Accueil")
                .addObject("message", "Bienvenue sur notre site!")
                .addObject("users", 1500)
                .addObject("active", true);
        
        return modelView;
    }
    
    @Route(value = "/test")
    public ModelView showTest() {
        System.out.println("Affichage de la page test.jsp");
        
        ModelView modelView = new ModelView("test.jsp");
        modelView.addObject("pageTitle", "Page de Test")
                .addObject("content", "Ceci est une page de test générée dynamiquement")
                .addObject("timestamp", new java.util.Date());
        
        return modelView;
    }
}