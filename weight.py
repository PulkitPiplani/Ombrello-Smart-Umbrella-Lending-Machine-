import serial
import time
ser = serial.Serial("/dev/ttyUSB0", 9600, timeout=5)
while True:
    a=[]
    for x in range(3):
        input = str(ser.read())
        a.append(input[2])
    print(a[0])
    time.sleep(0.4)