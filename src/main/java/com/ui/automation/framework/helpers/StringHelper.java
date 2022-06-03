package com.ui.automation.framework.helpers;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * General convenience methods for working with Strings
 */
public class StringHelper {
    private final static Logger logger = Logger.getLogger(StringHelper.class);

    private static final String[] ESCAPES;

    static {
        int size = '>' + 1; // '>' is the largest escaped value
        ESCAPES = new String[size];
        ESCAPES['<'] = "&lt;";
        ESCAPES['>'] = "&gt;";
        ESCAPES['&'] = "&amp;";
        ESCAPES['\''] = "&#039;";
        ESCAPES['"'] = "&#034;";
    }

    /**
     *
     * @param str the str
     * @return the boolean
     */
    public static boolean isEmpty(String str) {
        if (str != null) {
            str = str.trim();
        }
        return StringUtils.isEmpty(str);
    }

    /**
     * @param str the str
     * @return the boolean
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * Check whether the string is Integer format or not
     *
     * @param str the str
     * @return true or false
     */
    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

    /**
     * Check whether the string is double format or not
     *
     * @param str the str
     * @return true or false
     */
    public static boolean isDouble(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[.\\d]*$");
        return pattern.matcher(str).matches();
    }

    /**
     * Check whether the string is email format or not
     *
     * @param str the str
     * @return true or false
     */
    public static boolean isEmail(String str) {
        Pattern pattern = Pattern
                .compile("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$");
        return pattern.matcher(str).matches();
    }

    /**
     * Check if the string is a number or not
     *
     * @param str String to be checked
     * @return boolean boolean
     */
    public static boolean isNumber(String str) {

        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            logger.error(e.toString());
            return false;
        }

    }

    /**
     * Check if the string is letter or not
     *
     * @param str String to be checked
     * @return boolean boolean
     */
    public static boolean isLetter(String str) {
        return str.matches("^[A-Za-z]+$");
    }

    /**
     * Extract number string.
     *
     * @param str the str
     * @return the string
     */
    public static String extractNumber(String str) {
        String regex = "\\d+(\\.\\d+)?";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(str);
        while (m.find()) {
            if (!"".equals(m.group())) {
                return m.group();
            }
        }
        return "";
    }

    /**
     * Url encode string. url
     *
     * @param str the str
     * @return the string
     */
    public static String urlEncode(String str) {
        try {
            logger.info("转码前:" + str);
            String value = URLEncoder.encode(str, "UTF-8");
            logger.info("转码后:" + value);
            return value;
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage(),e);
        }
        return null;
    }

    /**
     * Url decode string.
     *
     * @param str the str
     * @return the string
     */
    public static String urlDecode(String str) {
        //logger.info("String:" + str);
        try {
            String value = URLDecoder.decode(str, "UTF-8");
            //logger.info("Value:" + value);
            return value;
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage(),e);
        }
        return null;
    }

    /**
     * Change string 2 boolean.
     *
     * @param string the string
     * @return the boolean
     */
    public static boolean changeString2boolean(String string) {
        return !"false".equalsIgnoreCase(string);
    }

    /**
     * Count the occurrences of the substring in string s.
     *
     * @param str string to search in. Return 0 if this is null.
     * @param sub string to search for. Return 0 if this is null.
     * @return the int
     */
    public static int countOccurrencesOf(String str, String sub) {
        if (str == null || sub == null || str.length() == 0
                || sub.length() == 0) {
            return 0;
        }
        int count = 0, pos = 0, idx = 0;
        while ((idx = str.indexOf(sub, pos)) != -1) {
            ++count;
            pos = idx + sub.length();
        }
        return count;
    }

    /**
     * This String util method removes single or double quotes from a string if
     * its quoted. for input string = "mystr1" output will be = mystr1 for input
     * string = 'mystr2' output will be = mystr2
     *
     * @param str String value to be unquoted.
     * @return value unquoted, null if input is null.
     */
    public static String unquote(String str) {
        if (str != null
                && ((str.startsWith("\"") && str.endsWith("\"")) || (str
                .startsWith("'") && str.endsWith("'")))) {
            return str.substring(1, str.length() - 1);
        }
        return str;
    }

    /**
     * Check a String ends with another string ignoring the case.
     *
     * @param str    the str
     * @param suffix the suffix
     * @return true or false
     */
    public static boolean endsWithIgnoreCase(String str, String suffix) {

        if (str == null || suffix == null) {
            return false;
        }
        if (str.endsWith(suffix)) {
            return true;
        }
        if (str.length() < suffix.length()) {
            return false;
        } else {
            return str.toLowerCase().endsWith(suffix.toLowerCase());
        }
    }

    /**
     * Check a String starts with another string ignoring the case.
     *
     * @param str    the str
     * @param prefix the prefix
     * @return true or false
     */
    public static boolean startsWithIgnoreCase(String str, String prefix) {

        if (str == null || prefix == null) {
            return false;
        }
        if (str.startsWith(prefix)) {
            return true;
        }
        if (str.length() < prefix.length()) {
            return false;
        } else {
            return str.toLowerCase().startsWith(prefix.toLowerCase());
        }
    }

    /**
     * Checks if the String contains any character in the given set of
     * characters.
     * A <code>null</code> String will return <code>false</code>. A
     * <code>null</code> or zero length search array will return
     * <code>false</code>.
     * StringHelper.containsAny(null, *)                = false
     * StringHelper.containsAny("", *)                  = false
     * StringHelper.containsAny(*, null)                = false
     * StringHelper.containsAny(*, [])                  = false
     * StringHelper.containsAny("zzabyycdxx",['z','a']) = true
     * StringHelper.containsAny("zzabyycdxx",['b','y']) = true
     * StringHelper.containsAny("aba", ['z'])           = false
     *
     * @param str         the String to check, may be null
     * @param searchChars the chars to search for, may be null
     * @return the <code>true</code> if any of the chars are found, <code>false</code> if no match or null input
     * @since 2.4
     */
    public static boolean containsAny(String str, char[] searchChars) {
        int csLength = str.length();
        int searchLength = searchChars.length;
        for (int i = 0; i < csLength; i++) {
            char ch = str.charAt(i);
            for (int j = 0; j < searchLength; j++) {
                if (searchChars[j] == ch) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Trim <i>all</i> whitespace from the given String: leading, trailing, and
     * inbetween characters.
     *
     * @param str the String to check
     * @return the trimmed String
     * @see Character#isWhitespace java.lang.Character#isWhitespacejava.lang.Character#isWhitespacejava.lang.Character#isWhitespacejava.lang.Character#isWhitespace
     */
    public static String trimAllWhitespace(String str) {
        if (isEmpty(str)) {
            return str;
        }
        StringBuilder sb = new StringBuilder(str);
        int index = 0;
        while (sb.length() > index) {
            if (Character.isWhitespace(sb.charAt(index))) {
                sb.deleteCharAt(index);
            } else {
                index++;
            }
        }
        return sb.toString();
    }

    /**
     * Remove special char string.
     *
     * @param str the str
     * @return the string
     * @throws PatternSyntaxException the pattern syntax exception
     */
    public static String removeSpecialChar(String str) throws PatternSyntaxException {
        // Allow just letters and numbers // String regEx = "[^a-zA-Z0-9]";
        // Remove all special characters
        String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }

    /**
     * Capital first letter string.
     *
     * @param str the str
     * @return the string
     */
    public static String capitalFirstLetter(String str) {
        return str.toUpperCase().substring(0, 1) + str.substring(1);
    }

    /**
     * Uncapitalizes a String changing the first letter to title case as per
     * {@link Character#toLowerCase(char)}. No other letters are changed.
     * StringHelper.uncapitalFirstLetter(null)  = null
     * StringHelper.uncapitalFirstLetter("")    = ""
     * StringHelper.uncapitalFirstLetter("Cat") = "cat"
     * StringHelper.uncapitalFirstLetter("CAT") = "cAT"
     *
     * @param str the String to uncapitalFirstLetter, may be null
     * @return the uncapitalized String, <code>null</code> if null String input
     * @see #capitalFirstLetter(String) #capitalFirstLetter(String)#capitalFirstLetter(String)#capitalFirstLetter(String)#capitalFirstLetter(String)
     * @since 2.0
     */
    public static String unCapitalFirstLetter(String str) {
        if (str == null) {
            return null;
        }
        StringBuilder result = new StringBuilder(str);

        if (result.length() > 0) {
            result.setCharAt(0, Character.toLowerCase(result.charAt(0)));
        }

        return result.toString();
    }

    /**
     * Join string.
     *
     * @param target    the target
     * @param separator the separator
     * @return the string
     */
    public static String join(Object[] target, String separator) {
        final StringBuilder sb = new StringBuilder();
        if (target.length > 0) {
            sb.append(target[0]);
            for (int i = 1; i < target.length; i++) {
                sb.append(separator);
                sb.append(target[i]);
            }
        }
        return sb.toString();
    }

    /**
     * Join string.
     *
     * @param target    the target
     * @param separator the separator
     * @return the string
     */
    public static String join(Iterable<?> target, String separator) {
        StringBuilder sb = new StringBuilder();
        Iterator<?> it = target.iterator();
        if (it.hasNext()) {
            sb.append(it.next());
            while (it.hasNext()) {
                sb.append(separator);
                sb.append(it.next());
            }
        }
        return sb.toString();

    }

    /**
     * This method is used to split the given string into different tokens at
     * the occurrence of specified delimiter
     *
     * @param str       The string that needs to be broken
     * @param delimeter The delimiter used to break the string
     * @return a string array
     */
    public static String[] getTokensArray(String str, String delimeter) {
        if (str != null) {
            return str.split(delimeter);
        }
        return new String[0];
    }

    /**
     * This method is used to split the given string into different tokens at
     * the occurrence of specified delimiter
     *
     * @param str       The string that needs to be broken
     * @param delimeter The delimiter used to break the string
     * @return a instance of java.util.List with each token as one item in list
     */
    public static List<String> getTokensList(String str, String delimeter) {
        if (str != null) {
            return Arrays.asList(str.split(delimeter));
        }
        return new ArrayList<String>();
    }

    /**
     * Gets between string.
     *
     * @param origiString the origi string
     * @param beforeStr   the before str
     * @param afterStr    the after str
     * @return the tokens list
     */
    public static String getBetweenString(String origiString, String beforeStr, String afterStr) {
        String afterSearchStr = origiString.substring(origiString.indexOf(beforeStr) + beforeStr.length());
        return afterSearchStr.substring(0, afterSearchStr.indexOf(afterStr));
    }

    /**
     * This method can be used to trim all the String values in the string
     * array. For input {" a1 ", "b1 ", " c1"}, the output will be {"a1", "b1",
     * "c1"} Method takes care of null values
     *
     * @param values the values
     * @return A trimmed array
     */
    public static String[] trimArray(final String[] values) {
        for (int i = 0, length = values.length; i < length; i++) {
            if (values[i] != null) {
                values[i] = values[i].trim();
            }
        }
        return values;
    }

    /**
     * This method can be used to trim all the String values in the string list.
     * For input {" a1 ", "b1 ", " c1"}, the output will be {"a1", "b1", "c1"}
     * Method takes care of null values
     *
     * @param values the values
     * @return A trimmed list
     */
    public static List<String> trimList(final List<String> values) {
        List<String> newValues = new ArrayList<String>();
        for (String value : values) {
            String v = (String) value;
            if (v != null) {
                v = v.trim();
            }
            newValues.add(v);
        }
        return newValues;
    }

    /**
     * This method can be used to merge 2 arrays of string values. If the input
     * arrays are like this array1 = {"a", "b" , "c"} array2 = {"c", "d", "e"}
     * Then the output array will have {"a", "b" , "c", "d", "e"} Note This
     * takes care of eliminating duplicates and checks null values.
     *
     * @param array1 the array 1
     * @param array2 the array 2
     * @return A merged String Arrays
     */
    public static String[] mergeStringArrays(String array1[], String array2[]) {
        if (array1 == null || array1.length == 0) {
            return array2;
        }

        if (array2 == null || array2.length == 0) {
            return array1;
        }

        List<String> array1List = Arrays.asList(array1);
        List<String> array2List = Arrays.asList(array2);
        List<String> result = new ArrayList<String>(array2List);
        List<String> tmp = new ArrayList<String>(array2List);
        List<String> result2 = new ArrayList<String>(array1List);
        tmp.retainAll(array1List);
        result.removeAll(tmp);
        result2.addAll(result);
        return ((String[]) result2.toArray(new String[result2.size()]));
    }

    /**
     * Concatenate the given String arrays into one, with overlapping array
     * elements included twice.
     * The order of elements in the original arrays is preserved.
     *
     * @param array1 the first array (can be <code>null</code>)
     * @param array2 the second array (can be <code>null</code>)
     * @return the new array (<code>null</code> if both given arrays were <code>null</code>)
     */
    public static String[] concatenateStringArrays(String array1[],
                                                   String array2[]) {
        if (array1 == null || array1.length == 0) {
            return array2;
        }
        if (array2 == null || array2.length == 0) {
            return array1;
        }
        String[] newArr = new String[array1.length + array2.length];
        System.arraycopy(array1, 0, newArr, 0, array1.length);
        System.arraycopy(array2, 0, newArr, array1.length, array2.length);
        return newArr;
    }

    /**
     * get an integer array filled with random integer without duplicate [min,
     * max)
     *
     * @param min  the minimum value
     * @param max  the maximum value
     * @param size the capacity of the array
     * @return a integer array filled with random integer without duplicate
     */
    public static int[] getRandomIntWithoutDuplicate(int min, int max, int size) {
        int[] result = new int[size];

        int arraySize = max - min;
        int[] intArray = new int[arraySize];
        // init intArray
        for (int i = 0; i < intArray.length; i++) {
            intArray[i] = i + min;
        }
        // get random integer without duplicate
        for (int i = 0; i < size; i++) {
            int c = getRandomInt(min, max - i);
            int index = c - min;
            swap(intArray, index, arraySize - 1 - i);
            result[i] = intArray[arraySize - 1 - i];
        }

        return result;
    }

    /**
     * get a random Integer with the range [min, max)
     *
     * @param min the minimum value
     * @param max the maximum value
     * @return the random Integer value
     */
    public static int getRandomInt(int min, int max) {
        // include min, exclude max
        int result = min + new Double(Math.random() * (max - min)).intValue();
        return result;
    }

    private static void swap(int[] array, int x, int y) {
        int temp = array[x];
        array[x] = array[y];
        array[y] = temp;
    }

    /**
     * Escapes the characters in a <code>String</code> using XML entities.
     *
     * @param src the <code>String</code> to escape, may be null
     * @return a new escaped <code>String</code>, <code>null</code> if null string input
     */
    public static String escape(String src) {

        if (src == null) {
            return src;
        }

        // First pass to determine the length of the buffer so we only allocate
        // once
        int length = 0;
        for (int i = 0; i < src.length(); i++) {
            char c = src.charAt(i);
            String escape = getEscape(c);
            if (escape != null) {
                length += escape.length();
            } else {
                length += 1;
            }
        }

        // Skip copy if no escaping is needed
        if (length == src.length()) {
            return src;
        }

        // Second pass to build the escaped string
        StringBuilder buf = new StringBuilder(length);
        for (int i = 0; i < src.length(); i++) {
            char c = src.charAt(i);
            String escape = getEscape(c);
            if (escape != null) {
                buf.append(escape);
            } else {
                buf.append(c);
            }
        }
        return buf.toString();
    }

    /**
     * Escapes the characters in a {@code String} using XML entities only if the
     * passed boolean is {@code true}.
     *
     * @param shouldEscape the should escape
     * @param src          the {@code String} to escape.
     * @return a new escaped {@code String} if {@code shouldEscape} is set to {@code true}, an unchanged {@code String} otherwise.
     */
    public static String escape(boolean shouldEscape, String src) {
        if (shouldEscape) {
            return escape(src);
        }
        return src;
    }

    private static String getEscape(char c) {
        if (c < ESCAPES.length) {
            return ESCAPES[c];
        } else {
            return null;
        }
    }

    /**
     * Find list.
     *
     * @param target the target
     * @param patten the patten
     * @return the list
     */
    public static List<String> find(String target, String patten) {
        Pattern p = Pattern.compile(patten);
        Matcher matcher = p.matcher(target);
        List<String> lists = new ArrayList<String>();
        while (matcher.find()) {
            String name = matcher.group();
            lists.add(name);
        }
        return lists;
    }
}
