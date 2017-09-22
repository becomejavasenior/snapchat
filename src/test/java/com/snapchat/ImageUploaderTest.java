package com.snapchat;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Created by apolonxviii on 22.09.17.
 */
public class ImageUploaderTest {

    @BeforeClass
    public static void runServer() throws Exception {
        ServerInit.main(null);
    }

    @AfterClass
    public static void stopServer() throws Exception {
        ServerInit.stopServer();
    }

    @Test
    public void uploadImage() throws Exception {
        String[] args = {"-f /Users/apolonxviii/Pictures/BecomeJavaSenior-Logo-5-mini.png", "-t 3000"};
        ImageUploader.main(args);
    }
}
