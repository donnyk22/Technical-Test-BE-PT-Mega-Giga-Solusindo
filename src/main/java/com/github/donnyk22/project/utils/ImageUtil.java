package com.github.donnyk22.project.utils;

import java.util.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

public class ImageUtil {

    private static final Logger logger = LoggerFactory.getLogger(ImageUtil.class);

    public static String ToBase64(MultipartFile file) throws Exception{
        try {
            byte[] fileBytes = file.getBytes();
            return Base64.getEncoder().encodeToString(fileBytes);
        } catch (Exception e) {
            logger.error("Failed to convert file: " + e.getMessage());
            throw new Exception("Failed to convert file: " + e.getMessage());
        }
    }

}
