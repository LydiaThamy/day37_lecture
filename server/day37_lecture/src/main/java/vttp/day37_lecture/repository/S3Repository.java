package vttp.day37_lecture.repository;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.github.f4b6a3.ulid.UlidCreator;

import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

@Repository
public class S3Repository {

    @Autowired
    private S3Client s3;

    public void saveAudio(MultipartFile file, String description) {

        Map<String, String> userData = new HashMap<>();
        userData.put("description", description);

        String ulid = UlidCreator.getUlid()
                .toString().substring(0, 8);

        try {
            PutObjectRequest putReq = PutObjectRequest.builder()
                    .bucket("vttp")
                    .key("audio/%s".formatted(ulid))
                    .contentType(file.getContentType())
                    .metadata(userData)
                    .acl(ObjectCannedACL.PUBLIC_READ)
                    .build();

            PutObjectResponse response = s3.putObject(
                    putReq, RequestBody.fromInputStream(
                            file.getInputStream(), file.getSize()));
                            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
