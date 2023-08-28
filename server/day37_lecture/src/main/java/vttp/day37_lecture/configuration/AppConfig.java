package vttp.day37_lecture.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.regions.Region;

@Configuration
public class AppConfig {

    @Value("${s3.key.access}")
    private String accessKey;

    @Value("${s3.key.secret}")
    private String secretKey;

    @Bean
    public S3Client createS3() {
        AwsBasicCredentials cred = AwsBasicCredentials.create(accessKey, secretKey);
        return S3Client.builder()
                .region(Region.of("sgp1"))
                .credentialsProvider(StaticCredentialsProvider.create(cred))
                .build();
    }

}
