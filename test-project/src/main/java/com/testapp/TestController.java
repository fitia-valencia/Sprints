package com.testapp;

import com.monframework.annotation.Route;

public class TestController {
    
    @Route(url = "/users")
    public void getUsers() {
        System.out.println("Getting users...");
    }
    
    @Route(url = "/products")
    public void listProducts() {
        System.out.println("Listing products...");
    }
    
    public void notAnnotated() {
        System.out.println("This method has no URL");
    }
}