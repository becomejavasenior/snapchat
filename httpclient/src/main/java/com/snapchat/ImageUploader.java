package com.snapchat;

import org.apache.commons.cli.*;
import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.FileEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.stream.Collectors;

/**
 * Created by apolonxviii on 22.09.17.
 */
public class ImageUploader {
    public static void main(String[] args) throws Exception {
        //Test data -f /Users/apolonxviii/Pictures/BecomeJavaSenior-Logo-5-mini.png -t 3000
        CommandLine cmd = parseParameters(args);
        String inputFilePath = cmd.getOptionValue("file");
        String time = cmd.getOptionValue("time");

        System.out.println("arg file: " + inputFilePath);
        System.out.println("arg time: " + time);

        File file = new File(inputFilePath);
        String path = postFile(file, time);

        //Handle sleep time to check how image validating is working
        //4000 or more to be shure that image is not avalaible already
        Thread.sleep(4000);
        getFile(path);
    }

    private static String postFile(File file, String time) throws IOException, URISyntaxException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        URI uri = new URIBuilder()
                .setScheme("http")
                .setHost("localhost")
                .setPort(8082)
                .setPath("/image")
                .addParameter("time", time)
                .build();
        HttpPost httppost = new HttpPost(uri);
        FileEntity entity = new FileEntity(file, ContentType.create("application/octet-stream"));
        httppost.setEntity(entity);

        CloseableHttpResponse response = httpclient.execute(httppost);
        String result;
        try (InputStream stream = response.getEntity().getContent()) {
            result = new BufferedReader(new InputStreamReader(stream))
                    .lines().collect(Collectors.joining("\n"));
            System.out.println("response from server: " + result);
            System.out.println(response.getStatusLine());
        }
        return result;
    }

    private static void getFile(String path) throws URISyntaxException, IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        URI uri = new URIBuilder()
                .setScheme("http")
                .setHost("localhost")
                .setPort(8082)
                .setPath("/image/" + path)
                .build();
        HttpGet httpGet = new HttpGet(uri);

        CloseableHttpResponse response = httpclient.execute(httpGet);
        try {
            InputStream stream = response.getEntity().getContent();
            byte[] file = IOUtils.toByteArray(stream);
            System.out.println("response from server: " + file.length);
            System.out.println(response.getStatusLine());
        } finally {
            response.close();
        }
    }

    private static CommandLine parseParameters(String[] args) throws ParseException {
        Options options = new Options();

        Option input = new Option("f", "file", true, "input image file path");
        input.setRequired(true);
        options.addOption(input);

        Option output = new Option("t", "time", true, "time to leave in ms");
        output.setRequired(true);
        options.addOption(output);

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);
        return cmd;
    }
}
