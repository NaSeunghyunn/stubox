package com.nastudy.stubox.domain;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
public class FileUploader {

    public String uploadProfile(MultipartFile profileImage) {
        String uploadFolder = "/Users/nas/Desktop";
        String profileImageFolder = "/upload/profileImage/" + LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE).toString();
        String path = uploadFolder + profileImageFolder;

        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        String fileName = UUID.randomUUID() + profileImage.getOriginalFilename();
        File file = new File(path, fileName);
        try {
            profileImage.transferTo(file);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        return profileImageFolder + "/" + file.getName();
    }
}
