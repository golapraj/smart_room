#include <SoftwareSerial.h>
#include <Servo.h>

Servo myservo;
int pos = 0;
int DoorPin = 13;
int LightPin = 12;
int FanPin = 11;

SoftwareSerial BL(0,1);

void setup() {
  // put your setup code here, to run once:
  pinMode(DoorPin, OUTPUT);
  pinMode(LightPin, OUTPUT);
  pinMode(FanPin, OUTPUT);
  BL.begin(9600);
  myservo.attach(13);
}

void loop() {
  // put your main code here, to run repeatedly:
  if (BL.available()) {
  int Received = BL.read();
  if (Received == '0')
  {
    pos = 0;
    myservo.write(pos);
    delay(100);  
  }
    //digitalWrite(DoorPin, HIGH);
  else if (Received == '1')
  {
     pos = 90;
    myservo.write(pos);
    delay(100);  
  }
    //digitalWrite(DoorPin, LOW);

  if (Received == '2')
    digitalWrite(LightPin, HIGH);
  else if (Received == '3')
    digitalWrite(LightPin, LOW);

  if (Received == '4')
    digitalWrite(FanPin, HIGH);
  else if (Received == '5')
    digitalWrite(FanPin, LOW);
  }
}
