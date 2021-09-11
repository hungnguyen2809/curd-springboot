package hungnguyen2809.learnspring.services;

import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public class ImageStorageService implements StorageService {
    private final Path storageFolder = Paths.get("uploads");

    public ImageStorageService() {
        try {
            Files.createDirectories(storageFolder);
        } catch (IOException exception) {
            throw new RuntimeException("Cannot initialize storage ", exception);
        }
    }

    private boolean isImageFiles(MultipartFile file) {
        String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
        assert fileExtension != null;
        return Arrays.asList(new String[]{"png", "jpg", "jpeg"}).contains(fileExtension.trim().toLowerCase());
    }

    @Override
    public String storeFile(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new RuntimeException("Failed to store empty file.");
            }
            if (!isImageFiles(file)) {
                throw new RuntimeException("You can only upload image file");
            }

            float oneMegabytes = 1048576.0f;
            float fileSize = file.getSize() / oneMegabytes;
            if (fileSize > 5f) {
                throw new RuntimeException("File must be size <= 5Mb");
            }

            String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
            String generateFileName = UUID.randomUUID().toString().replace("-", "");
            generateFileName = generateFileName + "." + fileExtension;
            Path destinationFilePath = storageFolder.resolve(Paths.get(generateFileName)).normalize()
                    .toAbsolutePath();

            if (!destinationFilePath.getParent().equals(storageFolder.toAbsolutePath())) {
                throw new RuntimeException("Cannot store file outside current directory");
            }

            //copy file to destinationFilePath
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFilePath, StandardCopyOption.REPLACE_EXISTING);
            }

            return generateFileName;
        } catch (IOException exception) {
            throw new RuntimeException("Failed to file ", exception);
        }
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(storageFolder, 1).filter(path -> !path.equals(storageFolder)).map(storageFolder::relativize);
        } catch (IOException exception) {
            throw new RuntimeException("Failed to load stored files ", exception);
        }
    }

    @Override
    public byte[] readFileContent(String fileName) {
        try {
            Path file = storageFolder.resolve(fileName);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return StreamUtils.copyToByteArray(resource.getInputStream());
            } else {
                throw new RuntimeException("Could not read file " + fileName);
            }
        } catch (IOException exception) {
            throw new RuntimeException("Could not read file " + fileName, exception);
        }
    }

    @Override
    public void deleteAllFiles() {

    }
}
