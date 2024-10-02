//此程序用于通过#ffffff颜色在照片中的比例来判断是否为屏幕截图
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class ColorPercentageDetector {
    public static void main(String[] args) {
        File sourceDir = new File("E:\\test\\output\\other_images\\other_photos");
        File[] files = sourceDir.listFiles();

        // 创建目标文件夹
        File targetDir = new File("E:\\test\\output\\other_images\\other_photos\\white");
        if (!targetDir.exists()) {
            targetDir.mkdirs();
        }

        if (files != null) {
            for (File file : files) {
                if (file.isFile() && isImageFile(file)) {
                    try {
                        if (isColorPercentageGreaterThan(file)) {
                            System.out.println(file.getName() + " has more than configured white or #f5f5f5 pixels. Moving to white folder.");
                            moveFileToDirectory(file, targetDir);
                        } else {
                            System.out.println(file.getName() + " has 10% or less white or #f5f5f5 pixels.");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private static boolean isImageFile(File file) {
        String fileName = file.getName().toLowerCase();
        return fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") ||
               fileName.endsWith(".png") || fileName.endsWith(".gif");
    }

    private static boolean isColorPercentageGreaterThan(File file) throws IOException {
        BufferedImage img = ImageIO.read(file);
        int width = img.getWidth();
        int height = img.getHeight();
        int totalPixels = width * height;
        int whitePixels = 0;
        int lightGrayPixels = 0;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixel = img.getRGB(x, y);
                if (isWhite(pixel)) {
                    whitePixels++;
                } else if (isLightGray(pixel)) {
                    lightGrayPixels++;
                }
            }
        }

        double whitePercentage = ((double) whitePixels / totalPixels) * 100;
        double lightGrayPercentage = ((double) lightGrayPixels / totalPixels) * 100;

        return whitePercentage > 10 || lightGrayPercentage > 5; // 组合条件，白色 > 10% 或 #ededed > 5%
    }

    private static boolean isWhite(int rgb) {
        return rgb == 0xFFFFFFFF; // 判断是否为白色（ARGB）
    }

    private static boolean isLightGray(int rgb) {
        return rgb == 0xFFEDEDED; // 判断是否为#ededed（ARGB）
    }

    private static void moveFileToDirectory(File file, File targetDir) throws IOException {
        File newFile = new File(targetDir, file.getName());
        Files.move(file.toPath(), newFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
    }
}