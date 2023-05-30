package ibf2022.batch2.csf.backend.controllers;

import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import ibf2022.batch2.csf.backend.repositories.ImageRepository;
import jakarta.json.Json;

@Controller
@RequestMapping
@CrossOrigin(origins = "*")
public class UploadController {

    @Autowired
    private ImageRepository imageRepository;

	// TODO: Task 2, Task 3, Task 4
	@PostMapping(path = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadForm(@RequestPart String name, @RequestPart String title, @RequestPart String comments, @RequestPart MultipartFile archive) {
        try {
            String key = imageRepository.upload(archive);
            System.out.println(key);

            return ResponseEntity.ok().body(Json.createObjectBuilder()
            .add("status", "upload successful at ").build().toString());
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Json.createObjectBuilder()
            .add("status", "upload error/unsuccessful").build().toString());
        }
    }

	// TODO: Task 5
	

	// TODO: Task 6

}
