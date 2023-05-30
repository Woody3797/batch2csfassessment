package ibf2022.batch2.csf.backend.repositories;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.model.S3ObjectSummary;

import jakarta.json.Json;

@Repository
public class ImageRepository {

    @Autowired
    private AmazonS3 s3;

	//TODO: Task 3
	// You are free to change the parameter and the return type
	// Do not change the method's name
    @SuppressWarnings("null")
	public List<URL> upload(String key, MultipartFile archive) throws IOException {
        // Unzip archive and create temp File
        List<MultipartFile> files = unzipMultipartFile(archive);
        String[] strArr;
        List<URL> urlList = new ArrayList<>();
        for (MultipartFile file : files) {
            // Add object's metadata
            ObjectMetadata metadata = new ObjectMetadata();
            strArr = file.getOriginalFilename().split("\\.");
            String contentType = "image/" + strArr[strArr.length-1];
            metadata.setContentType(contentType);
            metadata.setContentLength(file.getSize());

            // woodybucket - bucket name
            // key - key
            // file.getInputStream() - actual bytes
            // metadata
            PutObjectRequest putReq = new PutObjectRequest("woodybucket", key + "/" + file.getOriginalFilename(), file.getInputStream(), metadata);

            // Make the file publically accessible
            putReq = putReq.withCannedAcl(CannedAccessControlList.PublicRead);
            PutObjectResult result = s3.putObject(putReq);
            URL url = s3.getUrl("woodybucket", key + "/" + file.getOriginalFilename());
            urlList.add(url);
            System.out.println(">> result: " + url);
        }

        return urlList;
	}

    // unzip MultipartFile into list of individual MultipartFiles
    List<MultipartFile> unzipMultipartFile(MultipartFile file) throws IOException {
        List<MultipartFile> multipartFiles = new ArrayList<>();
        ZipInputStream zis = new ZipInputStream(file.getInputStream());
        ZipEntry entry;
        while ((entry = zis.getNextEntry()) != null) {
            if (!entry.isDirectory()) {
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int count;
                while ((count = zis.read(buffer)) != -1) {
                    bos.write(buffer, 0, count);
                }
                MultipartFile multipartFile = new MockMultipartFile(entry.getName(), entry.getName(), null, bos.toByteArray());
                multipartFiles.add(multipartFile);
            }
        }
        zis.close();
        return multipartFiles;
    }
}
