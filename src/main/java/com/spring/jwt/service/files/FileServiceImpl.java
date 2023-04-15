package com.spring.jwt.service.files;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;


@Service
public class FileServiceImpl implements FileService {
    private final Path rootLocation;

    @Autowired
    public FileServiceImpl(StorageProperties properties) {
        this.rootLocation = Paths.get(properties.getLocation());
    }

    @Override
    public String store(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file.");
            }
            String suffixes = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            String fileName = String.valueOf(Math.round(Math.random() * 9999)) + suffixes;
            Path destinationFile = this.rootLocation.resolve(
                            Paths.get(fileName))
                    .normalize().toAbsolutePath();
            if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
                // This is a security check
                throw new StorageException(
                        "Cannot store file outside current directory.");
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile,
                        StandardCopyOption.REPLACE_EXISTING);
                return fileName;
            }
        } catch (IOException e) {
            throw new StorageException("Failed to store file.", e);
        }
    }

    @Override
    public byte[] downloadImageFromFileSystem(String fileName) throws IOException {
        Path destinationFile = this.rootLocation.resolve(
                        Paths.get(fileName))
                .normalize().toAbsolutePath();
        byte[] images = Files.readAllBytes(new File(destinationFile.toUri()).toPath());
        return images;
    }
}
