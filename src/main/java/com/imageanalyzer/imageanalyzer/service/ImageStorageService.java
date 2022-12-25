package com.imageanalyzer.imageanalyzer.service;


import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface ImageStorageService {

    void init();

    void save(MultipartFile file);

    Resource load(String filename);

    Stream<Path> loadAll();

    boolean delete(String filename);

    void deleteAll();
}
