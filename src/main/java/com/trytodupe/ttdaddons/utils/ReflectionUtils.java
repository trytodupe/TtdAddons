package com.trytodupe.ttdaddons.utils;

import java.lang.reflect.Field;

public class ReflectionUtils {
    public static void setFieldByIndex(Object object, int index, Object value) {
        try {
            Field field = object.getClass().getDeclaredFields()[index];
            field.setAccessible(true);
            field.set(object, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setFieldByIndex(Class clazz, int index, Object object, Object value) {
        try {
            Field field = clazz.getDeclaredFields()[index];
            field.setAccessible(true);
            field.set(object, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Object getFieldByIndex(Object object, int index) {
        try {
            Field field = object.getClass().getDeclaredFields()[index];
            field.setAccessible(true);
            return field.get(object);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Object getFieldByName(Class clazz, String name, Object object) {
        try {
            Field field = clazz.getDeclaredField(name);
            field.setAccessible(true);
            return field.get(object);
        } catch (Exception exception) {
            return null;
        }
    }

    public static Object getFieldByName(Object obj, String name) {
        try {
            Field field = obj.getClass().getDeclaredField(name);
            field.setAccessible(true);
            return field.get(obj);
        } catch (Exception exception) {
            return null;
        }
    }

    public static void setFieldByName(Object object, String name, Object value) {
        try {
            Field field = object.getClass().getDeclaredField(name);
            field.setAccessible(true);
            field.set(object, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}