package com.projet.visa.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.client.j2se.MatrixToImageWriter;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
public class QRCodeGenerator {
   public static void generateQRCode (String data , String filePath) throws Exception {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
       BitMatrix bitMatrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, 300, 300);
       if(filePath.isEmpty()) {
            throw new Exception("Besoin d'un chemin clair pour sauvegarder l'image");
        }

        Path path = Paths.get(filePath);
        Path parent = path.getParent();
        if(parent != null) {
            Files.createDirectories(parent);
        }

        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
        System.out.println("QR Code généré : " + filePath);
   }
}