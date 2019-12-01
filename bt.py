import serial
import time
port = serial.Serial("/dev/rfcomm0", baudrate=9600)
 
# reading and writing data from and to arduino serially.                                      
# rfcomm0 -> this could be different
while True:
    rcv = str(port.readline())
    #x=rcv.index("'")
    #y=rcv.index('r')
    #rcv=rcv[x+1:y-1]
    print(rcv)
    time.sleep(1)