package com.snapchat.image;

import com.snapchat.image.Image;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by apolonxviii on 22.09.17.
 */
public class ImageHolder {

    static private Map<String, Image> imageMap = new HashMap<>();

    public static Image getImage(String path) {
        return imageMap.get(path);
    }

    public static String putImages(Image img) {
        String imgPath = getRandomPath();
        img.setPath(imgPath);
        imageMap.put(imgPath, img);
        return imgPath;
    }

    public static void remove(String path) {
        imageMap.remove(path);
    }

    public static Map<String, Image> getMap() {
        return imageMap;
    }

    private static String getRandomPath() {
        SecureRandom random = new SecureRandom();
        byte bytes[] = new byte[32];
        random.nextBytes(bytes);
        Encoder encoder = Base64.getUrlEncoder().withoutPadding();
        return encoder.encodeToString(bytes);
    }


}
