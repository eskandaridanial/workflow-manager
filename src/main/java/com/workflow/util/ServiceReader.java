package com.workflow.util;

import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Properties;

@Component
public class ServiceReader {

    public static String serviceUrl(String key){
        try {
            Properties properties = new Properties();
            properties.load(new InputStreamReader(new FileInputStream(new File("services.properties")) , "UTF-8"));
            String value = properties.getProperty(key);
            return value.split(" ")[0];
        } catch (Exception e){
            System.out.println("nothing found");
        }
        return null;
    }
}
