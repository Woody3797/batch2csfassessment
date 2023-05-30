package ibf2022.batch2.csf.backend.services;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.UUID;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import ibf2022.batch2.csf.backend.models.Bundle;
import ibf2022.batch2.csf.backend.repositories.ArchiveRepository;
import ibf2022.batch2.csf.backend.repositories.ImageRepository;

@Service
public class UploadService {
    
    @Autowired
    private ArchiveRepository archiveRepository;

    @Autowired
    private ImageRepository imageRepository;

    public String uploadIntoS3AndMongo(String name, String title, String comments, MultipartFile archive) throws IOException {
        String key = UUID.randomUUID().toString().substring(0, 8);

        List<URL> urlList = imageRepository.upload(key, archive);
        String result = archiveRepository.recordBundle(key, name, title, comments, urlList);
        System.out.println(result);

        return key;
    }

    public Bundle getBundleByBundleId(String bundleId) {
        Document doc = archiveRepository.getBundleByBundleId(bundleId);
        Bundle bundle = Bundle.convertFromDoc(doc);

        return bundle;
    }
}
