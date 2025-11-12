package com.monframework.core;

import com.monframework.annotation.Controller;
import com.monframework.annotation.Component;
import com.monframework.annotation.Route;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ApplicationContext {
    private Map<String, Object> beans = new HashMap<>();
    private Map<String, Method> routes = new HashMap<>();
    private static ApplicationContext instance;
    
    public static ApplicationContext init(String basePackage) throws Exception {
        instance = new ApplicationContext();
        instance.scanAndInitialize(basePackage);
        return instance;
    }
    
    private void scanAndInitialize(String basePackage) throws Exception {
        System.out.println("=== INITIALISATION DE L'APPLICATION ===");
        System.out.println("Scanning package: " + basePackage);
        
        Set<Class<?>> classes = ClassScanner.findAllClasses(basePackage);
        
        for (Class<?> clazz : classes) {
            if (clazz.isAnnotationPresent(Component.class) || 
                clazz.isAnnotationPresent(Controller.class)) {
                
                Object instance = clazz.newInstance();
                String beanName = getBeanName(clazz);
                beans.put(beanName, instance);
                
                System.out.println("✓ Composant chargé: " + clazz.getSimpleName());
                
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
                
                System.out.println("  → Route mappée: " + fullUrl + " -> " + method.getName() + 
                                 " (retour: " + method.getReturnType().getSimpleName() + ")");
            }
        }
    }
    
    public void executeRoute(String url) throws Exception {
        Method method = routes.get(url);
        if (method == null) {
            System.out.println("404 - URL non trouvée: " + url);
            return;
        }
        
        Object controller = beans.get(method.getDeclaringClass().getSimpleName());
        
        // Utiliser PrintWriter pour l'affichage formaté
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        
        printWriter.println("=== EXÉCUTION DE LA ROUTE: " + url + " ===");
        printWriter.println("Méthode: " + method.getName());
        printWriter.println("Type de retour: " + method.getReturnType().getSimpleName());
        
        // Exécuter la méthode et récupérer le résultat
        Object result = method.invoke(controller);
        
        printWriter.println("Résultat de l'exécution:");
        
        if (result instanceof ModelView) {
            ModelView modelView = (ModelView) result;
            printWriter.println("✓ Retour ModelView:");
            printWriter.println("  - Vue: " + modelView.getView());
            printWriter.println("  - Données: " + modelView.getData());
            printWriter.println("  → Redirection vers: " + modelView.getView());
            
            // Afficher le résultat formaté
            System.out.println(stringWriter.toString());
            
            // Simuler l'affichage de la page JSP
            simulateJspRender(modelView);
            
        } else if (method.getReturnType() == void.class) {
            printWriter.println("✓ Méthode exécutée (void)");
            System.out.println(stringWriter.toString());
        } else if (result instanceof String) {
            printWriter.println("✓ Retour String: \"" + result + "\"");
            System.out.println(stringWriter.toString());
        } else if (result != null) {
            printWriter.println("✓ Retour: " + result.toString() + " (" + result.getClass().getSimpleName() + ")");
            System.out.println(stringWriter.toString());
        } else {
            printWriter.println("✓ Retour: null");
            System.out.println(stringWriter.toString());
        }
    }
    
    // Simuler le rendu d'une page JSP
    private void simulateJspRender(ModelView modelView) {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("SIMULATION AFFICHAGE " + modelView.getView().toUpperCase());
        System.out.println("=".repeat(50));
        
        // Afficher les données disponibles pour la JSP
        if (!modelView.getData().isEmpty()) {
            System.out.println("Données disponibles dans la page:");
            for (Map.Entry<String, Object> entry : modelView.getData().entrySet()) {
                System.out.println("  - " + entry.getKey() + " = " + entry.getValue());
            }
        }
        
        // Simuler le contenu de la page JSP
        System.out.println("\nContenu de la page " + modelView.getView() + ":");
        System.out.println("<html>");
        System.out.println("<head><title>" + modelView.getView() + "</title></head>");
        System.out.println("<body>");
        System.out.println("  <h1>Bienvenue sur " + modelView.getView() + "</h1>");
        
        // Afficher les données dynamiquement
        for (Map.Entry<String, Object> entry : modelView.getData().entrySet()) {
            System.out.println("  <p>" + entry.getKey() + ": " + entry.getValue() + "</p>");
        }
        
        System.out.println("</body>");
        System.out.println("</html>");
        System.out.println("=".repeat(50));
    }
    
    // Nouvelle méthode pour exécuter et retourner le résultat
    public Object executeAndReturn(String url) throws Exception {
        Method method = routes.get(url);
        if (method == null) {
            throw new RuntimeException("Route non trouvée: " + url);
        }
        
        Object controller = beans.get(method.getDeclaringClass().getSimpleName());
        return method.invoke(controller);
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
            Method method = routes.get(url);
            System.out.println("• " + url + " -> " + method.getName() + 
                             " (" + method.getReturnType().getSimpleName() + ")");
        }
    }
    
    public static ApplicationContext getInstance() {
        return instance;
    }
}