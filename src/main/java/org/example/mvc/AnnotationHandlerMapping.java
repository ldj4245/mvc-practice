package org.example.mvc;

import org.example.mvc.annotation.Controller;
import org.example.mvc.annotation.RequestMapping;
import org.example.mvc.controller.HandlerKey;
import org.example.mvc.controller.HomeController;
import org.example.mvc.controller.RequestMethod;
import org.reflections.Reflections;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class AnnotationHandlerMapping implements HandlerMapping {

    private final Object[] basePackage;
    private Map<HandlerKey, AnnotationHandler> handlers = new HashMap<>();


    public AnnotationHandlerMapping(Object ...basePackage){
        this.basePackage = basePackage;
    }

    public void initialize(){

        Reflections reflections = new Reflections(basePackage);

        // HomeController

        Set<Class<?>> clazzesWithControllerAnnotation = reflections.getTypesAnnotatedWith(Controller.class);

        clazzesWithControllerAnnotation.forEach(clazz ->
                Arrays.stream(clazz.getDeclaredMethods()).forEach(declaredMethod -> {
                    RequestMapping requestMapping = declaredMethod.getDeclaredAnnotation(RequestMapping.class);

                    Arrays.stream(getRequestMethods(requestMapping))
                            .forEach(requestmethod -> handlers.put(
                                    new HandlerKey(requestmethod, requestMapping.value()), new AnnotationHandler(clazz,declaredMethod)
                            ));
                }));



    }

    private RequestMethod[] getRequestMethods(RequestMapping requestMapping) {
        return requestMapping.method();
    }

    @Override
    public Object findHandler(HandlerKey handlerKey) {
        return handlers.get(handlerKey);
    }
}
