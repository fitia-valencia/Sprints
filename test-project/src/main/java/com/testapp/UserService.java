package com.testapp;

import com.monframework.annotation.Component;

@Component
public class UserService {
    
    public void createUser() {
        System.out.println("UserService: Création d'utilisateur");
    }
}