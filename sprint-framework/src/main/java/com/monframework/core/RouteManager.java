package com.monframework.core;

import com.monframework.annotation.Controller;
import com.monframework.annotation.Route;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class RouteManager {
    private Map<String, Method> routeMap = new HashMap<>();
    
    public void scanControllers(String packageName) throws Exception {
        Set<Class<?>> controllers = ControllerScanner.findAnnotatedClasses(packageName);
        
        for (Class<?> controller : controllers) {
            Controller controllerAnnotation = controller.getAnnotation(Controller.class);
            String baseUrl = controllerAnnotation.value();
            
            // Scanner les méthodes annotées @Route
            for (Method method : controller.getDeclaredMethods()) {
                Route routeAnnotation = method.getAnnotation(Route.class);
                if (routeAnnotation != null) {
                    String fullUrl = baseUrl + routeAnnotation.value();
                    routeMap.put(fullUrl, method);
                    System.out.println("Mapped: " + fullUrl + " -> " + method.getName());
                }
            }
        }
    }
    
    public void executeRoute(String url) throws Exception {
        Method method = routeMap.get(url);
        if (method == null) {
            System.out.println("404 - URL non trouvée: " + url);
            return;
        }
        
        // Créer instance et exécuter
        Object instance = method.getDeclaringClass().newInstance();
        method.invoke(instance);
        System.out.println("✓ Méthode exécutée: " + method.getName());
    }
    
    public void listAllRoutes() {
        System.out.println("=== Routes disponibles ===");
        for (String url : routeMap.keySet()) {
            System.out.println("URL: " + url + " -> " + routeMap.get(url).getName());
        }
    }
}