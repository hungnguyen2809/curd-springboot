package hungnguyen2809.learnspring.controllers;

import hungnguyen2809.learnspring.common.ResponseObject;
import hungnguyen2809.learnspring.services.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping(path = "/api/v1/upload")
public class FileUploadController {
    @Autowired
    private StorageService storageService;

    @PostMapping("")
    public ResponseEntity<ResponseObject> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            String generateFileName = storageService.storeFile(file);
            return ResponseEntity.status(HttpStatus.OK).body(
                    ResponseObject.success(generateFileName, "Upload file successfully")
            );
        } catch (Exception exception) {
            return ResponseEntity.status(200).body(
                    ResponseObject.error(null, "Upload file failed " + exception.getMessage())
            );
        }
    }

    @GetMapping("/files/{fileName:.+}")
    public ResponseEntity<byte[]> readDetailFile(@PathVariable String fileName) {
        try {
            byte[] bytes = storageService.readFileContent(fileName);
            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).contentType(MediaType.IMAGE_PNG).body(bytes);
        } catch (Exception exception) {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("")
    public ResponseEntity<ResponseObject> getUploadFiles() {
        try {
            // convert filename to url (send request "readDetailFile")
            List<String> urls = storageService.loadAll().map((path -> {
                return MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
                        "readDetailFile", path.getFileName().toString()).build().toUri().toString();
            })).collect(Collectors.toList());
            return ResponseEntity.ok().body(
                    ResponseObject.success(urls, "List files successfully")
            );
        } catch (Exception exception) {
            return ResponseEntity.ok(
                    ResponseObject.error(new String[]{}, "List files failed")
            );
        }
    }
}
