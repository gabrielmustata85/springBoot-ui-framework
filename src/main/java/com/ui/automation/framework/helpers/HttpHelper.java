package com.ui.automation.framework.helpers;


import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

/**
 * The type Http helper.
 */
public class HttpHelper {
    private final static Logger logger = Logger
            .getLogger(HttpHelper.class);

    /**
     * Send get list.
     *
     * @param urlString the url string
     * @return the list
     */
    public static List<String> sendGet(String urlString) {
        return sendGet(urlString, new HashMap<String, String>(), "UTF-8", 16000);
    }

    /**
     * Send get list.
     *
     * @param urlString the url string
     * @param charset   the charset
     * @return the list
     */
    public static List<String> sendGet(String urlString, String charset) {
        return sendGet(urlString, new HashMap<String, String>(), charset, 16000);
    }

    /**
     * Send get list.
     *
     * @param urlString the url string
     * @param charset   the charset
     * @param timeout   the timeout
     * @return the list
     */
    public static List<String> sendGet(String urlString, String charset, int timeout) {
        return sendGet(urlString, new HashMap<String, String>(), charset, timeout);
    }

    /**
     * Send get list.
     *
     * @param urlString the url string
     * @param headerMap the header map
     * @return the list
     */
    public static List<String> sendGet(String urlString, Map<String, String> headerMap) {
        return sendGet(urlString, headerMap, "UTF-8", 16000);
    }

    /**
     * Send get list.
     *
     * @param urlString the url string
     * @param headerMap the header map
     * @param timeout   the timeout
     * @return the list
     */
    public static List<String> sendGet(String urlString, Map<String, String> headerMap, int timeout) {
        return sendGet(urlString, headerMap, "UTF-8", timeout);
    }

    /**
     * Send get list.
     *
     * @param urlString the url string
     * @param headerMap the header map
     * @param charset   the charset
     * @param timeout   the timeout
     * @return the list
     */
    public static List<String> sendGet(String urlString, Map<String, String> headerMap, String charset, int timeout) {
        List<String> ret = new ArrayList<>();
        URL url = null;
        int tryCount = 0;
        boolean isFetchOK = false;
        while (tryCount < 3) {
            try {
                url = new URL(urlString);
                logger.info(urlString);
                URLConnection httpConn = url.openConnection();
                httpConn.setConnectTimeout(timeout);
                httpConn.setReadTimeout(timeout);
                if (headerMap.size() > 0) {
                    for (Object o : headerMap.entrySet()) {
                        Map.Entry entry = (Map.Entry) o;
                        String key = (String) entry.getKey();
                        String val = (String) entry.getValue();
                        httpConn.addRequestProperty(key, val);
                    }
                }
                BufferedReader br;
                if (headerMap.get("Accept-Encoding") != null && headerMap.get("Accept-Encoding").contains("gzip")) {
                    br = new BufferedReader(new InputStreamReader(new GZIPInputStream(httpConn.getInputStream()), charset));
                } else {
                    br = new BufferedReader(new InputStreamReader(httpConn.getInputStream(), charset));
                }
                String line = null;
                while ((line = br.readLine()) != null) {
                    isFetchOK = true;
                    ret.add(line);
                }
                br.close();
                if (isFetchOK) {
                    break;
                }
            } catch (IOException e) {
                tryCount++;
                logger.error("error in sendGet", e);
            }
        }
        return ret;
    }

    /**
     * Send post list.
     *
     * @param urlPath the url path
     * @return the list
     */
    public List<String> sendPost(String urlPath) {
        return sendPost(urlPath, null, new HashMap<String, String>(), "UTF-8");
    }

    /**
     * Send post list.
     *
     * @param urlPath  the url path
     * @param postBody the post body
     * @return the list
     */
    public List<String> sendPost(String urlPath, String postBody) {
        return sendPost(urlPath, postBody, new HashMap<String, String>(), "UTF-8");
    }

    /**
     * Send post list.
     *
     * @param urlPath   the url path
     * @param postBody  the post body
     * @param headerMap the header map
     * @return the list
     */
    public List<String> sendPost(String urlPath, String postBody, Map<String, String> headerMap) {
        return sendPost(urlPath, null, headerMap, "UTF-8");
    }

    /**
     * Send post list.
     *
     * @param urlPath   the url path
     * @param postBody  the post body
     * @param headerMap the header map
     * @param charset   the charset
     * @return the list
     */
    public List<String> sendPost(String urlPath, String postBody, Map<String, String> headerMap, String charset) {
        List<String> result = new ArrayList<>();
        int tryCount = 0;
        boolean isFetchOK = false;
        while (tryCount < 3) {
            URL url = null;
            try {
                url = new URL(urlPath);
                HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
                httpConn.setDoOutput(true);
                httpConn.setDoInput(true);
                httpConn.setUseCaches(false);
                httpConn.setRequestMethod("POST");
                if (headerMap.size() > 0) {
                    for (Object o : headerMap.entrySet()) {
                        Map.Entry entry = (Map.Entry) o;
                        String key = (String) entry.getKey();
                        String val = (String) entry.getValue();
                        httpConn.addRequestProperty(key, val);
                    }
                }
                httpConn.connect();
                DataOutputStream dos = new DataOutputStream(httpConn.getOutputStream());
                if (postBody != null) {
                    dos.writeBytes(postBody);
                }
                dos.flush();
                dos.close();
                int resultCode = httpConn.getResponseCode();
                if (HttpURLConnection.HTTP_OK == resultCode) {
                    isFetchOK = true;
                    String line;
                    BufferedReader br;
                    if (headerMap.get("Accept-Encoding") != null && headerMap.get("Accept-Encoding").contains("gzip")) {
                        br = new BufferedReader(new InputStreamReader(new GZIPInputStream(httpConn.getInputStream()), charset));
                    } else {
                        br = new BufferedReader(new InputStreamReader(httpConn.getInputStream(), charset));
                    }
                    while ((line = br.readLine()) != null) {
                        result.add(line);
                    }
                    br.close();
                }
                if (isFetchOK) {
                    break;
                }
            } catch (IOException e) {
                tryCount++;
                logger.error("error in sendPost", e);
            }
        }
        return result;
    }
}
