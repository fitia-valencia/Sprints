package com.monframework.core;

import com.monframework.annotation.Controller;
import com.monframework.annotation.Component;
import com.monframework.annotation.Route;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ApplicationContext {
    private Map<String, Object> beans = new HashMap<>();
    private Map<String, Method> routes = new HashMap<>();
    private static ApplicationContext instance;
    
    // Ajouter 'throws Exception' à la méthode init
    public static ApplicationContext init(String basePackage) throws Exception {
        instance = new ApplicationContext();
        instance.scanAndInitialize(basePackage);
        return instance;
    }
    
    private void scanAndInitialize(String basePackage) throws Exception {
        System.out.println("=== INITIALISATION DE L'APPLICATION ===");
        System.out.println("Scanning package: " + basePackage);
        
        Set<Class<?>> classes = ClassScanner.findAllClasses(basePackage);
        
        // Scanner les composants
        for (Class<?> clazz : classes) {
            if (clazz.isAnnotationPresent(Component.class) || 
                clazz.isAnnotationPresent(Controller.class)) {
                
                Object instance = clazz.newInstance();
                String beanName = getBeanName(clazz);
                beans.put(beanName, instance);
                
                System.out.println("✓ Composant chargé: " + clazz.getSimpleName());
                
                // Si c'est un contrôleur, scanner ses routes
                if (clazz.isAnnotationPresent(Controller.class)) {
                    scanControllerRoutes(clazz, instance);
                }
            }
        }
        
        System.out.println("=== INITIALISATION TERMINÉE ===");
        listAllComponents();
        listAllRoutes();
    }
    
    private void scanControllerRoutes(Class<?> controllerClass, Object instance) {
        Controller controllerAnnotation = controllerClass.getAnnotation(Controller.class);
        String baseUrl = controllerAnnotation.value();
        
        for (Method method : controllerClass.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Route.class)) {
                Route routeAnnotation = method.getAnnotation(Route.class);
                String fullUrl = baseUrl + routeAnnotation.value();
                routes.put(fullUrl, method);
                
                System.out.println("  → Route mappée: " + fullUrl + " -> " + method.getName());
            }
        }
    }
    
    private String getBeanName(Class<?> clazz) {
        if (clazz.isAnnotationPresent(Component.class)) {
            Component component = clazz.getAnnotation(Component.class);
            if (!component.value().isEmpty()) {
                return component.value();
            }
        }
        return clazz.getSimpleName();
    }
    
    public void listAllComponents() {
        System.out.println("\n=== COMPOSANTS CHARGÉS ===");
        for (String beanName : beans.keySet()) {
            Object bean = beans.get(beanName);
            System.out.println("• " + beanName + " : " + bean.getClass().getSimpleName());
        }
    }
    
    public void listAllRoutes() {
        System.out.println("\n=== ROUTES DISPONIBLES ===");
        for (String url : routes.keySet()) {
            System.out.println("• " + url + " -> " + routes.get(url).getName());
        }
    }
    
    public void executeRoute(String url) throws Exception {
        Method method = routes.get(url);
        if (method == null) {
            System.out.println("404 - URL non trouvée: " + url);
            return;
        }
        
        Object controller = beans.get(method.getDeclaringClass().getSimpleName());
        method.invoke(controller);
        System.out.println("✓ Méthode exécutée: " + method.getName());
    }
    
    public static ApplicationContext getInstance() {
        return instance;
    }
}