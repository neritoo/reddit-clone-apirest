package com.gavilan.redditapirest.service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Date;

/**
 * @author: Eze Gavilán
 **/

@Service
public class UploadImageService {

    private AmazonS3 s3Client;

    @Value("${amazon.bucketName}")
    private String bucketName;
    @Value("${amazon.accessKey}")
    private String accessKey;
    @Value("${amazon.secretKey}")
    private String secretKey;

    @PostConstruct
    public void initializeAmazon() {
        AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);
        this.s3Client = new AmazonS3Client(credentials);
    }

    public String subir(MultipartFile archivo) throws IOException {

        String fileName = "";
        try {
            File file = convertMultiPartToFile(archivo);
            fileName = generateFileName(archivo);
            uploadFileTos3bucket(fileName, file);
            file.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileName;
    }

    public Resource cargar(String nombreFoto) throws MalformedURLException {
        S3Object object = s3Client.getObject(bucketName, nombreFoto);

        return new InputStreamResource(object.getObjectContent());
    }

    public boolean eliminar(String nombreFoto) {

        if (nombreFoto != null && nombreFoto.length() > 0) {
            String fileName = nombreFoto.substring(nombreFoto.lastIndexOf("/") + 1);
            s3Client.deleteObject(new DeleteObjectRequest(bucketName, fileName));
            return true;
        }

        return false;
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

    private String generateFileName(MultipartFile multiPart) {
        return new Date().getTime() + "-" + multiPart.getOriginalFilename().replace(" ", "_");
    }

    private void uploadFileTos3bucket(String fileName, File file) {
        s3Client.putObject(new PutObjectRequest(bucketName, fileName, file)
                .withCannedAcl(CannedAccessControlList.PublicRead));
    }


}
