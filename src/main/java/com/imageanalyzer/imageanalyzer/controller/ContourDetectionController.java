package com.imageanalyzer.imageanalyzer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Controller
public class ContourDetectionController {

    private static final String fileName = "final.jpg";
    private static final String endpoint = "http://localhost:5000/";

    @GetMapping("contour_detection")
    public String contourDetection() {
        return "contour_detection";
    }

    @PostMapping("contour_detection")
    public String detectContour(@RequestParam("image") MultipartFile file) throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request;
        HttpResponse<byte[]> response;
        int statusCode;

        System.out.println("content type = " + file.getContentType());
        request = HttpRequest
                .newBuilder(URI.create(endpoint + "test"))
                .header("content-type", file.getContentType())
                .POST(HttpRequest.BodyPublishers.ofByteArray(file.getBytes()))
                .build();
        response = client.send(request, HttpResponse.BodyHandlers.ofByteArray());
        statusCode = response.statusCode();

        System.out.println("code = " + statusCode);

        // get the input image and create a File object
        File outputFile = new File("src/main/resources/static/images/" + fileName);

        // write the uploaded image to the output file
        FileOutputStream outputStream = new FileOutputStream(outputFile);
        outputStream.write(file.getBytes());
        outputStream.close();

        return "redirect:/contour_detection";
    }

}
