package com.vibent.vibentback.image;


import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class S3Config {

    @Value("${vibent.aws.s3.accessKey}")
    private String ACCESS_KEY;

    @Value("${vibent.aws.s3.secretKey}")
    private String SECRET_KEY;

    @Bean
    public AmazonS3 getAmazonS3() {
        AWSCredentials credentials = new BasicAWSCredentials(
                ACCESS_KEY,
                SECRET_KEY
        );

        return AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.EU_WEST_3)
                .build();
    }
}