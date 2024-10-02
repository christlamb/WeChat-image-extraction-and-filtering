//此程序用于利用照片像素大小区分缩略图还是原图
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

public class ImageSeparator {
    public static void main(String[] args) {
        File sourceDir = new File("E:/test/output/");
        File smallImagesDir = new File("E:/test/output/small_images");
        File otherImagesDir = new File("E:/test/output/other_images");

        if (!smallImagesDir.exists()) {
            smallImagesDir.mkdir();  // 创建小图像文件夹
        }
        
        if (!otherImagesDir.exists()) {
            otherImagesDir.mkdir();  // 创建其他图像文件夹
        }

        separateImages(sourceDir, smallImagesDir, otherImagesDir);
    }

    private static void separateImages(File sourceDir, File smallImagesDir, File otherImagesDir) {
        File[] files = sourceDir.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile() && isImageFile(file)) {
                    try {
                        Path filePath = file.toPath();
                        BasicFileAttributes attrs = Files.readAttributes(filePath, BasicFileAttributes.class);

                        if (attrs.size() < 200 * 200) {  // 小于200KB
                            file.renameTo(new File(smallImagesDir, file.getName()));
                        } else {
                            file.renameTo(new File(otherImagesDir, file.getName()));
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
}