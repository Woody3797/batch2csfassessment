package ibf2022.batch2.csf.backend.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import ibf2022.batch2.csf.backend.models.Bundle;
import ibf2022.batch2.csf.backend.services.UploadService;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;

@Controller
@RequestMapping
@CrossOrigin(origins = "*")
public class UploadController {

    @Autowired
    private UploadService uploadService;

	// TODO: Task 2, Task 3, Task 4
	@PostMapping(path = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public ResponseEntity<String> uploadForm(@RequestPart String name, @RequestPart String title, @RequestPart String comments, @RequestPart MultipartFile archive) {
        try {
            String key = uploadService.uploadIntoS3AndMongo(name, title, comments, archive);

            return ResponseEntity.status(201).contentType(MediaType.APPLICATION_JSON).body(Json.createObjectBuilder()
            .add("bundleId", key)
            .build().toString());
        } catch (Exception e) {
            return ResponseEntity.status(500).contentType(MediaType.APPLICATION_JSON).body(Json.createObjectBuilder()
            .add("error", e.getMessage())
            .build().toString());
        }
    }

	// TODO: Task 5
    @GetMapping(path = "/bundle/{bundleId}")
    @ResponseBody
    public ResponseEntity<String> getBundleByBundleId(@PathVariable String bundleId) {
        Bundle bundle = uploadService.getBundleByBundleId(bundleId);
        if (bundle != null) {
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(bundle.toJson().toString());
        } else {
            return ResponseEntity.status(404).body(Json.createObjectBuilder()
            .add("error", "bundleId " + bundleId + " not found")
            .build().toString());
        }
        
    }
	

	// TODO: Task 6
    @GetMapping(path = "/bundles", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> getBundles() {
        List<Bundle> bundles = uploadService.getBundles();
        JsonArrayBuilder jab = Json.createArrayBuilder();
        for (Bundle b : bundles) {
            jab.add(b.toJson());
        }
        JsonArray jArr = jab.build();

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(jArr.toString());
    }
}
