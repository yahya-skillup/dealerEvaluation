package com.comparison.price.service;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;

import java.io.InputStreamReader;

public class JsonReaderUtil {
    public static String readJsonFromClasspath(String filename) throws Exception {
        ClassPathResource resource = new ClassPathResource(filename);
        try (InputStreamReader reader = new InputStreamReader(resource.getInputStream())) {
            return FileCopyUtils.copyToString(reader);
        }
    }
}