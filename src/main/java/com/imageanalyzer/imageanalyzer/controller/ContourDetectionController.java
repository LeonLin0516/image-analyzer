package com.imageanalyzer.imageanalyzer.controller;

import com.imageanalyzer.imageanalyzer.model.ImageInfo;
import com.imageanalyzer.imageanalyzer.service.ImageStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ContourDetectionController {

    private static final String before = "before.jpg";
    private static final String after = "after.jpg";
    private static final String endpoint = "http://localhost:5000/";

    @Autowired
    ImageStorageService storageService;

    @GetMapping("contour_detection")
    public String contourDetection() {
        return "contour_detection";
    }

    @RequestMapping("contour_detection")
    public String detectContour(@RequestParam("image") MultipartFile file) throws IOException, InterruptedException {

        // get the input image and create a File object
        File outputFile = new File("./images/" + before);

        // write the uploaded image to the output file
        FileOutputStream outputStream = new FileOutputStream(outputFile);
        outputStream.write(file.getBytes());
        outputStream.close();


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
        outputFile = new File("./images/" + after);

        // write the uploaded image to the output file
        outputStream = new FileOutputStream(outputFile);
        outputStream.write(response.body());
        outputStream.close();

        return "redirect:contour_detection/result";
    }

    @GetMapping("/images/{filename:.+}")
    public ResponseEntity<Resource> getImage(@PathVariable String filename) {
        Resource file = storageService.load(filename);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @GetMapping("contour_detection/result")
    public String getResult(Model model) {
        List<ImageInfo> imageInfoList = new ArrayList<>();
        imageInfoList.add(new ImageInfo(
            before, MvcUriComponentsBuilder.fromMethodName(ContourDetectionController.class, "getImage", before).build().toString()
        ));
        imageInfoList.add(new ImageInfo(
            after, MvcUriComponentsBuilder.fromMethodName(ContourDetectionController.class, "getImage", after).build().toString()
        ));
        return "redirect:/contour_detection";
    }


}
