package com.testapp;

import com.monframework.annotation.Component;

@Component("productManager")
public class ProductService {
    
    public void addProduct() {
        System.out.println("ProductService: Ajout de produit");
    }
}