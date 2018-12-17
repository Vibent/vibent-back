package com.vibent.vibentback.image;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;

@Data
@Service
public class S3Utils {

    @Value("${vibent.aws.s3.bucketName}")
    private String bucketName;
    @Value("${vibent.aws.s3.bucketBaseUrl}")
    private String bucketBaseUrl;

    private AmazonS3 amazonS3;

    @Autowired
    public S3Utils(@Value("${vibent.aws.s3.accessKey}") String accessKey,
                   @Value("${vibent.aws.s3.secretKey}") String secretKey) {
        AWSCredentials credentials = new BasicAWSCredentials(
                accessKey,
                secretKey
        );

        this.amazonS3 = AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.EU_WEST_3)
                .build();
    }

    public void uploadObject(String key, File object) {
        amazonS3.putObject(new PutObjectRequest(bucketName, key, object));
    }

    public void deleteObject(String key) {
        amazonS3.deleteObject(new DeleteObjectRequest(bucketName, key));
    }
}
