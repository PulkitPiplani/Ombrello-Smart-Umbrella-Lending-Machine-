import cv2
import time
import numpy as np
import os


def image_resize(image, width = None, height = None, inter = cv2.INTER_AREA):
    # initialize the dimensions of the image to be resized and
    # grab the image size
    dim = None
    (h, w) = image.shape[:2]

    # if both the width and height are None, then return the
    # original image
    if width is None and height is None:
        return image

    # check to see if the width is None
    if width is None:
        # calculate the ratio of the height and construct the
        # dimensions
        r = height / float(h)
        dim = (int(w * r), height)

    # otherwise, the height is None
    else:
        # calculate the ratio of the width and construct the
        # dimensions
        r = width / float(w)
        dim = (width, int(h * r))

    # resize the image
    resized = cv2.resize(image, dim, interpolation = inter)

    # return the resized image
    return resized
while True:
    y=50
    h=450
    x=100
    w=300
    #lowerum=np.array([0,0,0])
    #upperum=np.array([100,100,100])
    os.system("raspistill -o cam.jpg")
    img = cv2.imread("cam.jpg")
    img=image_resize(img,500,500)
    crop_img = img[y:y+h, x:x+w]
    #gray = cv2.inRange(crop_img, lowerum, upperum)
    gray=cv2.cvtColor(crop_img,cv2.COLOR_BGR2GRAY)
    cv2.imshow("",gray)
    contours, hierarchy = cv2.findContours(gray.copy(),cv2.RETR_EXTERNAL,cv2.CHAIN_APPROX_SIMPLE)
    areas=[cv2.contourArea(i) for i in contours]
    valid=[]
    for i in range(len(areas)):
        if(areas[i]>1000):
            valid.append(contours[i])
    for contour in valid:
        (x,y,w,h) = cv2.boundingRect(contour)
        cv2.rectangle(crop_img,(x,y),(x+h,y+h),(255,0,0),2)
        gray_cropped= gray[y:y+h, x:x+w]
    _,gray_cropped=cv2.threshold(gray_cropped, 165, 255, cv2.THRESH_BINARY)
    contours, hierarchy = cv2.findContours(gray_cropped.copy(),cv2.RETR_EXTERNAL,cv2.CHAIN_APPROX_SIMPLE)
    areas=[cv2.contourArea(i) for i in contours]
    valid=[]
    for i in range(len(areas)):
        if(600<areas[i]<10000):
            valid.append(contours[i])
    for contour in valid:
        (x,y,w,h) = cv2.boundingRect(contour)
        cv2.rectangle(crop_img,(x,y),(x+h,y+h),(255,0,0),2)
    cv2.imshow("Cropped Gray", gray_cropped)
    cv2.imshow("Grayscale", gray)
    cv2.imshow("cropped", crop_img)
    print(len(valid))
    cv2.waitKey(10000)
    cv2.destroyAllWindows()
