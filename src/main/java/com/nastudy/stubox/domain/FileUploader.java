package com.nastudy.stubox.domain;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import static com.amazonaws.services.s3.model.CannedAccessControlList.PublicRead;

@RequiredArgsConstructor
@Slf4j
@Service
public class FileUploader {

    private final AmazonS3Client amazonS3Client;
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String uploadProfileS3(MultipartFile profileImage) {
        String fileName = profileImage.getOriginalFilename();
        return putS3(profileImage, fileName);
    }

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

    private String putS3(MultipartFile profileImage, String fileName) {
        ObjectMetadata objectMetadata = getObjectMetadata(profileImage);
        try {
            amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, profileImage.getInputStream(), objectMetadata).withCannedAcl(PublicRead));
            log.info("File Upload : " + fileName);
            return amazonS3Client.getUrl(bucket, fileName).toString();
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private ObjectMetadata getObjectMetadata(MultipartFile uploadFile) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(uploadFile.getContentType());
        objectMetadata.setContentLength(uploadFile.getSize());
        return objectMetadata;
    }
}
