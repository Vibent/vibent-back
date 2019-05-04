package com.vibent.vibentback.image;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/image")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ImageController {

    private final ImageService imageService;

    @RequestMapping(method = RequestMethod.POST, value = "/profile/upload", consumes = "multipart/form-data")
    void uploadProfileImage(@RequestParam("file") MultipartFile file) {
        log.info("Upload image with name {}", file.getOriginalFilename());
        imageService.uploadProfileImage(file);
    }
}
