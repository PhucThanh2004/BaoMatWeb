package vn.iotstar.utils;

import java.util.UUID;

public class Utils {
    public String generateUniqueFileName(String originalFileName) {
        String extension = "";
        int lastDotIndex = originalFileName.lastIndexOf(".");
        if (lastDotIndex > 0) {
            extension = originalFileName.substring(lastDotIndex);
        }
        return UUID.randomUUID().toString() + extension;
    }
}
