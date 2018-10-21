package com.vibent.vibentback.image;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import com.vibent.vibentback.common.error.VibentError;
import com.vibent.vibentback.common.error.VibentException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ImageService {

    @Value("${vibent.aws.s3.bucketName}")
    private String BUCKET_NAME;

    @NonNull
    private final AmazonS3 s3;

    public byte[] getProfileImage(String id) {
        try {
            S3Object s3object = s3.getObject(BUCKET_NAME, "profile/" + id);
            S3ObjectInputStream inputStream = s3object.getObjectContent();
            return IOUtils.toByteArray(inputStream);
        } catch (AmazonS3Exception | IOException e) {
            throw new VibentException(VibentError.GET_IMAGE_FAILED);
        }
    }

    public File multipartToFile(MultipartFile multipart) {
        File tmpFile = new File(System.getProperty("java.io.tmpdir") + System.getProperty("file.separator") +
                multipart.getOriginalFilename());
        try {
            multipart.transferTo(tmpFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tmpFile;
    }

    public void uploadProfileImage(String id, MultipartFile file) {
        try {
            s3.putObject(new PutObjectRequest(BUCKET_NAME, "profile/" + id, multipartToFile(file)));
        } catch (Exception e) {
            e.printStackTrace();
            throw new VibentException(VibentError.UPLOAD_IMAGE_FAILED);
        }
    }
}
