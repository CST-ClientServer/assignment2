package com.comp3940.assignment.Utils;

import java.lang.reflect.*;

public class JavaReflectTest {
    @SuppressWarnings({"rawtypes"})
    public static void main(String[] args) throws ClassNotFoundException {
        Class c = Class.forName("com.comp3940.assignment.UploadServer.UploadServlet");
        Constructor[] constructors = c.getDeclaredConstructors();

        // print constructor info
        for(Constructor constructor : constructors) {
            System.out.println("Constructor Name: " + constructor);
            System.out.println("Constructor Param Count: " + constructor.getParameterCount());
            for (Parameter param : constructor.getParameters()) {
                System.out.println("Constructor Param: " + param);
            }
            System.out.println();
        }

        // print method info
        Method[] methods = c.getDeclaredMethods();
        for (Method method : methods) {
            System.out.println("Method Name: " + method);
            System.out.println("Method Return: " + method.getReturnType());
            System.out.println("Method Param Count: " + method.getParameterCount());
            for (Parameter param : method.getParameters()) {
                System.out.println("Method Param: " + param);
            }
            System.out.println();
        }
    }
}
