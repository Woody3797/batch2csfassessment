package ibf2022.batch2.csf.backend.repositories;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;

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
            // key - path of file
            // file.getInputStream() - actual bytes
            // metadata
            PutObjectRequest putReq = new PutObjectRequest("woodybucket", key + "/" + file.getOriginalFilename(), file.getInputStream(), metadata);

            // Make the file publically accessible
            putReq = putReq.withCannedAcl(CannedAccessControlList.PublicRead);
            PutObjectResult result = s3.putObject(putReq);
            URL url = s3.getUrl("woodybucket", key + "/" + file.getOriginalFilename());
            urlList.add(url);
            System.out.println(">> url: " + url + ", result: " + result);
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
