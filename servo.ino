#include <Servo.h>

Servo myservo;

int pos = 0;    // variable to store the servo position

void setup()
{
  myservo.attach(8);
}

void rotate()
{
  for (pos = 0; pos <= 180; pos += 1) 
  {
   myservo.write(pos);
   delay(15);   
  }
  for (pos = 180; pos >= 0; pos -= 1) 
  {
   myservo.write(pos);
   delay(15);
  }
}

void lock()
{
  myservo.write(180)
}

void lock()
{
  myservo.write(0)
}

void loop() 
{
  rotate();
  delay(50);
}
