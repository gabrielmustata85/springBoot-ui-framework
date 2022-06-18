package com.ui.automation.framework.helpers;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import java.net.URL;


/**
 * The type Class helper.
 */
public class ClassHelper {

    private final static Logger logger = Logger
            .getLogger(ClassHelper.class);

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        BasicConfigurator.configure();
        logger.info(getClassLoader().toString());
        logger.info(getClassPath());
    }

    /**
     *
     * @return the class loader
     */
    public static ClassLoader getClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

    /**
     *
     * @return the class path
     */
    public static String getClassPath() {
        String classpath = "";
        URL resource = getClassLoader().getResource("");
        if (resource != null) {
            classpath = resource.getPath();
        }
        return classpath;
    }

    /**
     *
     * @param className the class name
     * @return the class
     */
    public static Class<?> loadClass(String className) {
        return loadClass(className, true);
    }

    /**
     *
     * @param className     the class name
     * @param isInitialized the is initialized
     * @return the class
     */
    public static Class<?> loadClass(String className, boolean isInitialized) {
        Class<?> cls;
        try {
            cls = Class.forName(className, isInitialized, getClassLoader());
        } catch (ClassNotFoundException e) {
            logger.error("Class error！", e);
            throw new RuntimeException(e);
        }
        return cls;
    }

    /**
     *
     * @param type the type
     * @return the boolean
     */
    public static boolean isInt(Class<?> type) {
        return type.equals(int.class) || type.equals(Integer.class);
    }

    /**）
     *
     * @param type the type
     * @return the boolean
     */
    public static boolean isLong(Class<?> type) {
        return type.equals(long.class) || type.equals(Long.class);
    }

    /**
     *
     * @param type the type
     * @return the boolean
     */
    public static boolean isDouble(Class<?> type) {
        return type.equals(double.class) || type.equals(Double.class);
    }

    /**
     *
     * @param type the type
     * @return the boolean
     */
    public static boolean isString(Class<?> type) {
        return type.equals(String.class);
    }
}
