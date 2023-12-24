package org.example.util.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDateTime;

public  class JsonHandler {
    private JsonHandler() {
    }

    public static Gson getGsonFormat() {
        return new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeDeserializer())
                .create();
    }

    public static String getJson(HttpServletRequest req) throws IOException {
        BufferedReader reader = req.getReader();
        StringBuilder stringBuilder = new StringBuilder();
        String line;

        try {
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append('\n');
            }
        } finally {
            reader.close();
        }

        return stringBuilder.toString();
    }

}
