#include <SoftwareSerial.h>
#define rx_pin 12
#define tx_pin 13
SoftwareSerial BTserial(rx_pin,tx_pin);

float flex1=A0;
float flex2=A1;
float flex3=A2;

void setup() {
  Serial.begin(9600);
  pinMode(A0, INPUT);
  pinMode(A1, INPUT);
  pinMode(A2, INPUT);
}


float func(float flex_i)
{
  float temp = analogRead(flex_i);
  Serial.println("Value of "+ String(flex_i) + " is ");
  Serial.println(temp);
  delay(1000);
}

int check()
{
  float val1 = analogRead(flex1);
  float val2 = analogRead(flex2);
  float val3 = analogRead(flex3);

  Serial.println(val1);
  Serial.println(val2);
  Serial.println(val3);

  int c1=0;
  int c2=0;
  int c3=0;
  
  if (370<val1 and val1<420)
  { c1=1; }
  if (290<val2 and val2<340)
  { c2=1; }
  if (330<val3 and val3<380)
  { c3=1; }

  Serial.println("c1 is ");
  Serial.println(c1);
  Serial.println("c2 is ");
  Serial.println(c2);
  Serial.println("c3 is ");
  Serial.println(c3);

  int fin = c1 + c2 + c3;
  Serial.println("fin is ");
  Serial.println(fin);
  return fin;
}

void loop() {
  int begin=0;
  //change value of begin to 1 or 0 to begin checking

  begin=BTserial.read();

  while(begin==1)
  {
    int x = check();
    Serial.println("x is ");
    Serial.println(x);
    begin=0;
    
    if (x==3)
    {Serial.println("perfect");}
    else
    {Serial.println("broken");}
    delay(2000);
  }
  //Serial.println("out of while");
  delay(1500);
}
