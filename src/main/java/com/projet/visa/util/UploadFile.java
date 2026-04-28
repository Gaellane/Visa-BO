package com.projet.visa.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.web.multipart.MultipartFile;

public class UploadFile {

    public static void upload(String uploadDir , String fileName ,  MultipartFile file) throws Exception{
        if(file.isEmpty()) {
            throw new Exception("Le fichier est vide");
        }

        try {
            File dir = new File(uploadDir);
            if(!dir.exists()) dir.mkdirs();

            Path path = Paths.get(uploadDir , fileName);
            Files.write(path , file.getBytes());
        } catch(Exception e) {
            throw new Exception ("Imposible d'uploader le fichier " ,e);
        }
    }
}