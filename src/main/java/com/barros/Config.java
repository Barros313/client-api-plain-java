package com.barros;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    private final Properties properties = new Properties();

    public Config() {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                System.err.println("No config.properties file");
                return;
            }

            properties.load(input);
        } catch (IOException | IllegalArgumentException | NullPointerException exp) {
            System.err.println(exp.getMessage());
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }
}
