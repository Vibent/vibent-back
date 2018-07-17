package com.vibent.vibentback.image;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/image")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ImageController {

    ImageService imageService;

    @RequestMapping(method = RequestMethod.GET, value = "/profile/{userRef:.+}.jpg", produces = {"image/jpeg", "image/jpg"})
    byte[] getProfileImage(@PathVariable String userRef) {
        log.info("Get image with userRef : {}", userRef);
        return imageService.getProfileImage(userRef);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/profile/upload/{userRef:.+}", consumes = "multipart/form-data")
    void uploadProfileImage(@PathVariable String userRef, @RequestParam("file") MultipartFile file) {
        log.info("Upload image with name {} for userRef {}", file.getOriginalFilename(), userRef);
        imageService.uploadProfileImage(userRef, file);
    }

}
