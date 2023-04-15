package com.spring.jwt.service.files;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.Stream;

public interface FileService {
    String store(MultipartFile file) throws IOException;
    byte[] downloadImageFromFileSystem (String filename) throws IOException;
}
