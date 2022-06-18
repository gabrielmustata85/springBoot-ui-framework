package com.ui.automation.framework.helpers;


import org.apache.commons.lang3.ArrayUtils;

public class ArrayHelper {

    /**
     *
     * @param array the array
     * @return the boolean
     */
    public static boolean isNotEmpty(Object[] array) {
        return !ArrayUtils.isEmpty(array);
    }

    /**
     *
     * @param array the array
     * @return the boolean
     */
    public static boolean isEmpty(Object[] array) {
        return ArrayUtils.isEmpty(array);
    }

    /**
     *
     * @param array1 the array 1
     * @param array2 the array 2
     * @return the object [ ]
     */
    public static Object[] concat(Object[] array1, Object[] array2) {
        return ArrayUtils.addAll(array1, array2);
    }

    /**
     *
     * @param <T>   the type parameter
     * @param array the array
     * @param obj   the obj
     * @return the boolean
     */
    public static <T> boolean contains(T[] array, T obj) {
        return ArrayUtils.contains(array, obj);
    }
}
