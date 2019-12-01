from time import sleep
import RPi.GPIO as GPIO
GPIO.setmode(GPIO.BOARD)
velostat1=12
velostat2=16
"""velostat3=8
velostat4=4
velostat5=0"""
GPIO.setup(velostat1,GPIO.IN)
GPIO.setup(velostat2,GPIO.IN)
"""GPIO.setup(velostat3,GPIO.IN,pull_up_down=GPIO.PUD_UP)
GPIO.setup(velostat4,GPIO.IN,pull_up_down=GPIO.PUD_UP)
GPIO.setup(velostat5,GPIO.IN,pull_up_down=GPIO.PUD_UP)"""
while(1):
    if GPIO.input(velostat1)==1:
        print("umbrella in compartment 1")
    if GPIO.input(velostat1)==0:
        print("no umbrella")
    
    if GPIO.input(velostat2)==1:
        print("umbrella in compartment 2")
    if GPIO.input(velostat2)==0:
        print("no umbrella")
    
    """if GPIO.input(velostat3)==0:
        print("umbrella in compartment 3")
    if GPIO.input(velostat4)==0:
        print("umbrella in compartment 4")
    if GPIO.input(velostat5)==0:
        print("umbrella in compartment 5")"""
    sleep(1)
    

