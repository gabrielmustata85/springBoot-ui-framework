package com.ui.automation.framework.helpers;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.*;

/**
 * The type Json helper.
 */
public class JsonHelper {
    private final static Logger logger = Logger
            .getLogger(JsonHelper.class);

    private static final TypeReference<?> MAP_TYPE = new MapTypeReference();

    private static final TypeReference<?> LIST_TYPE = new ListTypeReference();

    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.configure(com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        objectMapper.configure(com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
    }

    public static Map<String, Object> parseMap(String json) {
        try {
            return objectMapper.readValue(json, MAP_TYPE);
        } catch (Exception ex) {
            throw new IllegalArgumentException("Cannot parse JSON:" + json, ex);
        }
    }

    public static List<Object> parseList(String json) {
        try {
            return objectMapper.readValue(json, LIST_TYPE);
        } catch (Exception ex) {
            throw new IllegalArgumentException("Cannot parse JSON:" + json, ex);
        }
    }

    /**
     * verify if is json
     *
     * @param str the str
     * @return the boolean
     */
    private static boolean isJson(String str) {
        return str.startsWith("{") && str.contains(":") && str.endsWith("}") || str.startsWith("[") && str.endsWith("]");
    }

    private static void recursionGetFields(JsonNode node, Map<String, String> pairMap, ObjectMapper mapper, String parentKey) {
        Iterator<Map.Entry<String, JsonNode>> fieldsIterator = node.fields();
        while (fieldsIterator.hasNext()) {
            Map.Entry<String, JsonNode> field = fieldsIterator.next();
            String value = StringHelper.unquote(String.valueOf(field.getValue()));
            pairMap.put(field.getKey(), value);
            if (!parentKey.equals("")) {
                pairMap.put(parentKey + "." + field.getKey(), value);
            }
            if (isJson(value)) {
                if (parentKey.equals("")) {
                    recursionGetPairs(pairMap, mapper, value, field.getKey());
                } else {
                    recursionGetPairs(pairMap, mapper, value, parentKey + "." + field.getKey());
                }
            }
        }
    }

    private static void recursionGetPairs(Map<String, String> pairMap, ObjectMapper mapper, String json, String parentKey) {
        try {
            JsonNode rootNode = mapper.readTree(json);
            recursionGetFields(rootNode, pairMap, mapper, parentKey);
            if (parentKey.equals("")) {
                return;
            }
            Iterator<JsonNode> elements = rootNode.elements();
            if (elements.getClass().getDeclaringClass().isAssignableFrom(ArrayList.class)) {
                int i = 0;
                while (elements.hasNext()) {
                    JsonNode children = elements.next();
                    recursionGetFields(children, pairMap, mapper, parentKey + "[" + i + "]");
                    i++;
                }
            } else {
                while (elements.hasNext()) {
                    JsonNode children = elements.next();
                    recursionGetFields(children, pairMap, mapper, parentKey);
                }
            }
        } catch (IOException e) {
            logger.error(e.getMessage(),e);
        }
    }

    /**
     * Parse json to pairs map.
     *
     * @param json the json
     * @return the map
     */
    public static Map<String, String> parseJsonToPairs(String json) {
        Map<String, String> pairMap = new HashMap<String, String>();
        if (isJson(json)) {
            recursionGetPairs(pairMap, objectMapper, json, "");
        }
        return pairMap;
    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        String jsonStr =
                "{\n" +
                        "    \"store\": {\n" +
                        "        \"book\": [\n" +
                        "            {\n" +
                        "                \"category\": \"reference\",\n" +
                        "                \"author\": \"Nigel Rees\",\n" +
                        "                \"title\": \"Sayings of the Century\",\n" +
                        "                \"price\": 8.95\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"category\": \"fiction\",\n" +
                        "                \"author\": \"Evelyn Waugh\",\n" +
                        "                \"title\": \"Sword of Honour\",\n" +
                        "                \"price\": 12.99\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"category\": \"fiction\",\n" +
                        "                \"author\": \"Herman Melville\",\n" +
                        "                \"title\": \"Moby Dick\",\n" +
                        "                \"isbn\": \"0-553-21311-3\",\n" +
                        "                \"price\": 8.99\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"category\": \"fiction\",\n" +
                        "                \"author\": \"J. R. R. Tolkien\",\n" +
                        "                \"title\": \"The Lord of the Rings\",\n" +
                        "                \"isbn\": \"0-395-19395-8\",\n" +
                        "                \"price\": 22.99\n" +
                        "            }\n" +
                        "        ],\n" +
                        "        \"bicycle\": {\n" +
                        "            \"color\": \"red\",\n" +
                        "            \"price\": 19.95\n" +
                        "        }\n" +
                        "    },\n" +
                        "    \"expensive\": 10\n" +
                        "}";
        Map<String, String> maps = parseJsonToPairs(jsonStr);
        System.out.println(maps.size());

    }

    private static class MapTypeReference extends TypeReference<Map<String, Object>> {

    }

    private static class ListTypeReference extends TypeReference<List<Object>> {

    }

}
