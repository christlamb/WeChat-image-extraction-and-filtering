//此程序用于通过照片比例来判断是否为屏幕截图
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ScreenshotDetector {
    public static void main(String[] args) {
        File sourceDir = new File("E:\\test\\output\\other_images");
        File[] files = sourceDir.listFiles();

        // 创建目标文件夹
        File screenshotDir = new File("E:\\test\\output\\other_images\\screenshots");
        if (!screenshotDir.exists()) {
            screenshotDir.mkdirs();
        }

        File otherDir = new File("E:\\test\\output\\other_images\\other_photos");
        if (!otherDir.exists()) {
            otherDir.mkdirs();
        }

        if (files != null) {
            for (File file : files) {
                if (file.isFile() && isImageFile(file)) {
                    try {
                        if (isScreenshot(file)) {
                            moveFile(file, screenshotDir);
                            System.out.println(file.getName() + " is likely a phone screenshot and moved to screenshots.");
                        } else {
                            moveFile(file, otherDir);
                            System.out.println(file.getName() + " is not a phone screenshot and moved to other_photos.");
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

    private static boolean isScreenshot(File file) throws IOException {
        BufferedImage img = ImageIO.read(file);
        int width = img.getWidth();
        int height = img.getHeight();
        double ratio = (double) width / height;

        return (ratio >= 0.46 && ratio <= 0.48) || // 6:13 (approx)
               (ratio >= 0.52 && ratio <= 0.59) || // 9:16
               (ratio >= 0.52 && ratio <= 0.55) || // 9:19
                (ratio < 0.5625)||
                (ratio > 1.78)||
               (ratio >= 0.47 && ratio <= 0.49);  // 9:18.5
    }

    private static void moveFile(File file, File targetDir) throws IOException {
        File newFile = new File(targetDir, file.getName());
        if (!file.renameTo(newFile)) {
            throw new IOException("Failed to move file: " + file.getName());
        }
    }
}