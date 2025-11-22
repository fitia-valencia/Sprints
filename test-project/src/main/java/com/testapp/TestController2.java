package com.testapp;

import com.monframework.annotation.Controller;
import com.monframework.annotation.Route;

@Controller("/admin")
public class TestController2 {
    
    @Route(value = "/dashboard")
    public void showDashboard() {
        System.out.println("Tableau de bord admin - Controller2");
    }
}