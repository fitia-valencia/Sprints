package com.monframework.core;

import java.util.HashMap;
import java.util.Map;

public class ModelView {
    private String view; // Nom de la vue (ex: "test.jsp")
    private Map<String, Object> data; // Données pour la vue
    
    public ModelView(String view) {
        this.view = view;
        this.data = new HashMap<>();
    }
    
    // Ajouter des données à la vue
    public ModelView addObject(String key, Object value) {
        this.data.put(key, value);
        return this;
    }
    
    // Getters
    public String getView() {
        return view;
    }
    
    public Map<String, Object> getData() {
        return data;
    }
    
    public Object getData(String key) {
        return data.get(key);
    }
    
    @Override
    public String toString() {
        return "ModelView{view='" + view + "', data=" + data + "}";
    }
}