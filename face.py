import cv2
import os
import shutil

# 创建目标文件夹
def create_directory(directory):
    if not os.path.exists(directory):
        os.makedirs(directory)

def has_multiple_faces(image_path):
    # 加载人脸检测模型
    face_cascade = cv2.CascadeClassifier(cv2.data.haarcascades + 'haarcascade_frontalface_default.xml')

    # 读取图像
    image = cv2.imread(image_path)
    gray_image = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
    
    # 检测人脸
    faces = face_cascade.detectMultiScale(gray_image, scaleFactor=1.1, minNeighbors=5)
    
    return len(faces) >= 2

def main():
    source_dir = "E:\\test\\output\\h"
    target_dir_with_faces = "E:\\test\\output\\h\\with_faces"
    target_dir_without_faces = "E:\\test\\output\\h\\without_faces"

    create_directory(target_dir_with_faces)
    create_directory(target_dir_without_faces)

    for file_name in os.listdir(source_dir):
        if file_name.lower().endswith(('.jpg', '.jpeg', '.png', '.gif')):
            file_path = os.path.join(source_dir, file_name)
            if has_multiple_faces(file_path):
                print(f"{file_name} has 2 or more faces. Moving to with_faces folder.")
                shutil.move(file_path, os.path.join(target_dir_with_faces, file_name))
            else:
                print(f"{file_name} has less than 2 faces. Moving to without_faces folder.")
                shutil.move(file_path, os.path.join(target_dir_without_faces, file_name))

if __name__ == "__main__":
    main()