//此程序用于将转码后的微信图片从源文件夹中挑选出来
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class CopyImages {
    public static void main(String[] args) {
        File sourceDir = new File("E:/test/out");
        File targetDir = new File("E:/test/output");
        
        if (!targetDir.exists()) {
            targetDir.mkdir();  // 创建目标文件夹
        }
        
        findAndCopyImages(sourceDir, targetDir);
    }

    private static void findAndCopyImages(File directory, File targetDir) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    if (file.getName().equals("8db92f0d6a38812215396d13b2c77076")) {
                        copyImageFiles(file, targetDir);
                    } else {
                        findAndCopyImages(file, targetDir);
                    }
                }
            }
        }
    }

    private static void copyImageFiles(File sourceDir, File targetDir) {
        File[] imageFiles = sourceDir.listFiles((dir, name) -> {
            return name.endsWith(".jpg") || name.endsWith(".png") || name.endsWith(".jpeg") || name.endsWith(".gif");
        });
        
        if (imageFiles != null) {
            for (File image : imageFiles) {
                try {
                    Files.copy(image.toPath(), new File(targetDir, image.getName()).toPath(), StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}