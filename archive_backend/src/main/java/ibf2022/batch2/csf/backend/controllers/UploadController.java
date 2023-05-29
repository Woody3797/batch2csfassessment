package ibf2022.batch2.csf.backend.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping
public class UploadController {

	// TODO: Task 2, Task 3, Task 4
	@PostMapping(path = "/upload")
    public ResponseEntity<String> uploadForm(@RequestPart String name, @RequestPart MultipartFile archive) {
        

        
        return null;
    }

	// TODO: Task 5
	

	// TODO: Task 6

}
