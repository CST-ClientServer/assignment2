package com.comp3940.assignment.Utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.lang.reflect.*;

public class JavaReflectTest {
    @SuppressWarnings({"rawtypes"})
    public static void log(String className) {
        try {
            Class c = Class.forName(className);
            Constructor[] constructors = c.getDeclaredConstructors();

            // print constructor info
            for (Constructor constructor : constructors) {
                System.out.println("Constructor Name: " + constructor);
                System.out.println("Constructor Param Count: " + constructor.getParameterCount());
                for (Parameter param : constructor.getParameters()) {
                    System.out.println("Constructor Param: " + param);
                }
                System.out.println();
            }
            System.out.println();

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
        } catch (ClassNotFoundException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public static void logMethod(String className, String methodName) {
        try {
            Class<?> c = Class.forName(className);
            Method method = c.getDeclaredMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            method.setAccessible(true);
            System.out.println("Method Name: " + method.getName());
            System.out.println("Return Type: " + method.getReturnType());
            System.out.println("Parameter Count: " + method.getParameterCount());
            for (Parameter param : method.getParameters()) {
                System.out.println("Parameter: " + param);
            }
            System.out.println("Called method: " + method.getName());
            System.out.println();
        } catch (ClassNotFoundException ex) {
            System.err.println("Class not found: " + ex.getMessage());
        } catch (NoSuchMethodException ex) {
            System.err.println("Method not found: " + ex.getMessage());
        }
    }

    public static void logAddDirectoryToHtml(String className, String methodName) {
        try {
            Class<?> c = Class.forName(className);
            Method method = c.getDeclaredMethod(methodName, String.class, String[].class);
            method.setAccessible(true);

            System.out.println("Method Name: " + method.getName());
            System.out.println("Return Type: " + method.getReturnType());
            System.out.println("Parameter Count: " + method.getParameterCount());
            for (Parameter param : method.getParameters()) {
                System.out.println("Parameter: " + param);
            }
            System.out.println("Called method: " + method.getName());
            System.out.println();
        } catch (ClassNotFoundException ex) {
            System.err.println("Class not found: " + ex.getMessage());
        } catch (NoSuchMethodException ex) {
            System.err.println("Method not found: " + ex.getMessage());
        }

    }

}
