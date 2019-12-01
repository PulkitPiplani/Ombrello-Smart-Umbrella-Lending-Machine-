import numpy as np
import cv2

im = cv2.imread('u.jpg')
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
im=image_resize(im,500,500)

imgray = cv2.cvtColor(im,cv2.COLOR_BGR2GRAY)
cv2.imshow('image',imgray)
cv2.waitKey(0)

th2=cv2.adaptiveThreshold(imgray,150,cv2.ADAPTIVE_THRESH_MEAN_C, cv2.THRESH_BINARY,11,2)
contours, hierarchy = cv2.findContours(th2,cv2.RETR_TREE,cv2.CHAIN_APPROX_SIMPLE)
cv2.drawContours(im,contours,-1,(255,255,255),1)

#(B,G,R)

cv2.imshow('image',im)
cv2.waitKey(0)
cv2.destroyAllWindows()