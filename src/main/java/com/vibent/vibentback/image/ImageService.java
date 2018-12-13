package com.vibent.vibentback.image;

import com.vibent.vibentback.common.error.VibentError;
import com.vibent.vibentback.common.error.VibentException;
import com.vibent.vibentback.user.ConnectedUserUtils;
import com.vibent.vibentback.user.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ImageService {

    private final S3Utils s3Utils;
    private final ConnectedUserUtils userUtils;

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

    private static Boolean isJpeg(File filename) {
        try {
            DataInputStream ins = new DataInputStream(new BufferedInputStream(new FileInputStream(filename)));
            boolean valid = ins.readInt() == 0xffd8ffe0;
            ins.close();
            return valid;
        } catch (IOException e) {
            throw new VibentException(VibentError.UPLOAD_IMAGE_FAILED);
        }
    }

    public void uploadProfileImage(MultipartFile file) {
        String uploadLocation = "profile/" + userUtils.getConnectedUserRef() + ".jpg";
        File profilePic = multipartToFile(file);
        if (!isJpeg(profilePic)) {
            throw new VibentException(VibentError.INCORRECT_IMAGE_FORMAT);
        }
        try {
            s3Utils.uploadObject(uploadLocation, profilePic);
            User user = userUtils.getConnectedUser();
            user.setProfilePicLocation(s3Utils.getBucketBaseUrl() + uploadLocation);
            userUtils.updateConnectedUser();
        } catch (Exception e) {
            e.printStackTrace();
            throw new VibentException(VibentError.UPLOAD_IMAGE_FAILED);
        }
    }
}
