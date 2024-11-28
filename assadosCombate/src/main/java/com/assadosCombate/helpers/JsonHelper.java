package com.assadosCombate.helpers;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonHelper {
    public JsonHelper() {
    }

    public static String convertToJson(Object value) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            return objectMapper.writeValueAsString(value);
        } catch (JsonProcessingException var3) {
            return null;
        }
    }

    public static boolean isJsonValid(String jsonInString) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.readTree(jsonInString);
            return true;
        } catch (JsonProcessingException var2) {
            return false;
        }
    }
}
