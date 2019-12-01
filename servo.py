import RPi.GPIO as GPIO
import time
GPIO.setwarnings(False)
GPIO.setmode(GPIO.BOARD)
GPIO.setup(3 , GPIO.OUT)
pwm=GPIO.PWM(3,50)
pwm.start(0)
while True:
    pwm.ChangeDutyCycle(5)
    time.sleep(0.5)
    pwm.ChangeDutyCycle(11)
    time.sleep(0.5)    
pwm.stop()
GPIO.cleanup()