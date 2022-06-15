package com.ui.automation.framework.helpers;

public class CastHelper {
    /**
     *
     * @param obj the obj
     * @return the string
     */
    public static String castString(Object obj) {
        return castString(obj, "");
    }

    /**
     *
     * @param obj          the obj
     * @param defaultValue the default value
     * @return the string
     */
    public static String castString(Object obj, String defaultValue) {
        return obj != null ? String.valueOf(obj) : defaultValue;
    }

    /**
     *
     * @param obj the obj
     * @return the double
     */
    public static double castDouble(Object obj) {
        return castDouble(obj, 0);
    }

    /**
     *
     * @param obj          the obj
     * @param defaultValue the default value
     * @return the double
     */
    public static double castDouble(Object obj, double defaultValue) {
        double doubleValue = defaultValue;
        if (obj != null) {
            String strValue = castString(obj);
            if (StringHelper.isNotEmpty(strValue)) {
                try {
                    doubleValue = Double.parseDouble(strValue);
                } catch (NumberFormatException e) {
                    doubleValue = defaultValue;
                }
            }
        }
        return doubleValue;
    }

    /**
     *
     * @param obj the obj
     * @return the long
     */
    public static long castLong(Object obj) {
        return castLong(obj, 0);
    }

    /**
     *
     * @param obj          the obj
     * @param defaultValue the default value
     * @return the long
     */
    public static long castLong(Object obj, long defaultValue) {
        long longValue = defaultValue;
        if (obj != null) {
            String strValue = castString(obj);
            if (StringHelper.isNotEmpty(strValue)) {
                try {
                    longValue = Long.parseLong(strValue);
                } catch (NumberFormatException e) {
                    longValue = defaultValue;
                }
            }
        }
        return longValue;
    }

    /**
     *
     * @param obj the obj
     * @return the int
     */
    public static int castInt(Object obj) {
        return castInt(obj, 0);
    }

    /**
     *
     * @param obj          the obj
     * @param defaultValue the default value
     * @return the int
     */
    public static int castInt(Object obj, int defaultValue) {
        int intValue = defaultValue;
        if (obj != null) {
            String strValue = castString(obj);
            if (StringHelper.isNotEmpty(strValue)) {
                try {
                    intValue = Integer.parseInt(strValue);
                } catch (NumberFormatException e) {
                    intValue = defaultValue;
                }
            }
        }
        return intValue;
    }

    /**
     *
     * @param obj the obj
     * @return the boolean
     */
    public static boolean castBoolean(Object obj) {
        return castBoolean(obj, false);
    }

    /**
     *
     * @param obj          the obj
     * @param defaultValue the default value
     * @return the boolean
     */
    public static boolean castBoolean(Object obj, boolean defaultValue) {
        boolean booleanValue = defaultValue;
        if (obj != null) {
            booleanValue = Boolean.parseBoolean(castString(obj));
        }
        return booleanValue;
    }
}
