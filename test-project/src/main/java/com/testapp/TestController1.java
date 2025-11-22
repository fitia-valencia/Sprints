package com.testapp;

import com.monframework.annotation.Controller;
import com.monframework.annotation.Route;

@Controller("/api")
public class TestController1 {
    
    @Route(value = "/users")
    public void getUsers() {
        System.out.println("Liste des utilisateurs - Controller1");
    }
    
    @Route(value = "/products")
    public void listProducts() {
        System.out.println("Liste des produits - Controller1");
    }
}