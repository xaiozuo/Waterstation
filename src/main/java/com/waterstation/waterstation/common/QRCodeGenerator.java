package com.waterstation.waterstation.common;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Administrator
 */
public class QRCodeGenerator {
    public static void generateQrCode(String data, String filePath,String deviceName) {
        try {
            Map<EncodeHintType, Object> hints = new HashMap<>();
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

            BitMatrix bitMatrix = new MultiFormatWriter().encode(data, BarcodeFormat.QR_CODE, 256, 280, hints);

            BufferedImage image = toBufferedImage(bitMatrix);

            // 添加水印
            Graphics2D graphics = image.createGraphics();
            graphics.setColor(Color.BLACK);
            graphics.setFont(new Font("宋体", Font.PLAIN, 16));
            String waterMarkContent = deviceName;
//            graphics.drawString("北京市朝阳区红军营南路15号瑞普大厦禾溪数字遥感有限公司", 10, 246);
            java.util.List<String> waterMarkLines = splitWaterMarkContent(waterMarkContent, graphics);

            int y = 256;
            for (String line : waterMarkLines) {
                int textWidth = graphics.getFontMetrics().stringWidth(line);
                int x = (256 - textWidth) / 2;  // 计算居中的 x 坐标
                graphics.drawString(line, x, y);
                y += graphics.getFontMetrics().getHeight();
            }
            graphics.dispose();

            ImageIO.write(image, filePath.substring(filePath.lastIndexOf('.') + 1), new File(filePath));

//            MatrixToImageWriter.writeToPath(bitMatrix, filePath.substring(filePath.lastIndexOf('.') + 1), new File(filePath).toPath());
        } catch (WriterException | IOException e) {
            e.printStackTrace();
        }
    }
    public static java.util.List<String> splitWaterMarkContent(String waterMarkContent, Graphics2D graphics) {
        java.util.List<String> lines = new ArrayList<>();
        int maxLineWidth = 256 - 20; // 图片宽度减去左右边距

        int start = 0;
        while (start < waterMarkContent.length()) {
            int end = start;
            int currentLineWidth = 0;

            while (end < waterMarkContent.length()) {
                char c = waterMarkContent.charAt(end);
                int charWidth = graphics.getFontMetrics().charWidth(c);
                if (currentLineWidth + charWidth > maxLineWidth) {
                    break;
                }
                currentLineWidth += charWidth;
                end++;
            }

            lines.add(waterMarkContent.substring(start, end));
            start = end;
        }

        return lines;
    }
    public static BufferedImage toBufferedImage(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, matrix.get(x, y)? 0xFF000000 : 0xFFFFFFFF);
            }
        }
        return image;
    }
}