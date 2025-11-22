package com.testapp;

import com.monframework.annotation.Controller;
import com.monframework.annotation.Route;

@Controller("/api")
public class TestController1 {
    
    @Route(value = "/users")
    public String getUsers() {
        System.out.println("Liste des utilisateurs - Controller1");
        return "Liste des utilisateurs: [User1, User2, User3]";
    }
    
    @Route(value = "/products")
    public String listProducts() {
        System.out.println("Liste des produits - Controller1");
        return "Produits disponibles: [ProduitA, ProduitB]";
    }
    
    @Route(value = "/count")
    public Integer getCount() {
        System.out.println("Récupération du compteur");
        return 42;
    }
    
    @Route(value = "/void-method")
    public void voidMethod() {
        System.out.println("Méthode void exécutée");
    }
}