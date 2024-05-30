package com.waterstation.waterstation.common;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class QRCodeGenerator {
    public static void generateQrCode(String data, String filePath) {
        try {
            Map<EncodeHintType, Object> hints = new HashMap<>();
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

            BitMatrix bitMatrix = new MultiFormatWriter().encode(data, BarcodeFormat.QR_CODE, 256, 256, hints);

            MatrixToImageWriter.writeToPath(bitMatrix, filePath.substring(filePath.lastIndexOf('.') + 1), new File(filePath).toPath());
        } catch (WriterException | IOException e) {
            e.printStackTrace();
        }
    }
}