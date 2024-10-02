//此程序用于统计图片的比例
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AspectRatioCounter {
    public static void main(String[] args) {
        File sourceDir = new File("E:\\test\\output\\other_images");
        File[] files = sourceDir.listFiles();
        
        Map<String, Integer> ratioCount = new HashMap<>();

        if (files != null) {
            for (File file : files) {
                if (file.isFile() && isImageFile(file)) {
                    try {
                        BufferedImage img = ImageIO.read(file);
                        int width = img.getWidth();
                        int height = img.getHeight();
                        String ratio = getAspectRatio(width, height);
                        
                        ratioCount.put(ratio, ratioCount.getOrDefault(ratio, 0) + 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        // 找到出现频率最高的比例
        String mostCommonRatio = null;
        int maxCount = 0;
        
        for (Map.Entry<String, Integer> entry : ratioCount.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                mostCommonRatio = entry.getKey();
            }
        }

        if (mostCommonRatio != null) {
            System.out.println("Most common aspect ratio: " + mostCommonRatio + " (count: " + maxCount + ")");
        } else {
            System.out.println("No valid images found.");
        }
    }

    private static boolean isImageFile(File file) {
        String fileName = file.getName().toLowerCase();
        return fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") ||
               fileName.endsWith(".png") || fileName.endsWith(".gif");
    }

    private static String getAspectRatio(int width, int height) {
        double gcd = gcd(width, height);
        return (width / (int)gcd) + ":" + (height / (int)gcd);
    }

    private static double gcd(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }
}