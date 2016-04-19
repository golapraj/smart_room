#include <SoftwareSerial.h>
#include <Servo.h>
#define trigPin 2
#define echoPin 4
#define led 5
#define led2 6

Servo myservo;

int pos = 0;


#define DEBUG true
 
SoftwareSerial esp8266(7,8); //TX,RX
                              
void setup()
{
  Serial.begin(9600);
  esp8266.begin(9600); 

  pinMode(10,OUTPUT);
  digitalWrite(10,HIGH);
  
  pinMode(11,OUTPUT);
  digitalWrite(11,LOW);
  
  pinMode(12,OUTPUT);
  digitalWrite(12,LOW);
  
  pinMode(13,OUTPUT);
  digitalWrite(13,LOW);

   pinMode(trigPin, OUTPUT);
  pinMode(echoPin, INPUT);
  
  pinMode(led, OUTPUT);
  digitalWrite(led,LOW);
  
  pinMode(led2, OUTPUT);
  digitalWrite(led2,HIGH);

  myservo.attach(3);
  myservo.write(pos);
   



  sendCommand("AT+RST\r\n",2000,DEBUG);
  sendCommand("AT+CWMODE=3\r\n",1000,DEBUG);
  sendCommand("AT+CWJAP=\"room\",\"12345678\"\r\n",3000,DEBUG);
  delay(5000);
  sendCommand("AT+CIFSR\r\n",1000,DEBUG); 
  sendCommand("AT+CIPMUX=1\r\n",1000,DEBUG);
  sendCommand("AT+CIPSERVER=1,1090\r\n",1000,DEBUG);
  sendCommand("AT+CIFSR\r\n",1000,DEBUG);
  
  Serial.println("Server Ready");
}
 
void loop()
{
 
  if(esp8266.available()) 
  {
 
    char pid[] = "+IPD,";
    if(esp8266.find(pid))
    {
     delay(500); 
     int connectionId = esp8266.read()-48;
     char pin[] = "pin=";    
     esp8266.find(pin);
          
     int pinNumber = (esp8266.read()-48);
     int secondNumber = (esp8266.read()-48);
     if(secondNumber>=0 && secondNumber<=9)
     {
      pinNumber*=10;
      pinNumber +=secondNumber;
       digitalWrite(led,LOW);
      if(pinNumber==10)
      {
         servomotor();
      }
      else { digitalWrite(pinNumber, !digitalRead(pinNumber));  }    
     }


     
     String content;
     content = "Pin ";
     content += pinNumber;
     content += " is ";
     
     if(digitalRead(pinNumber))
     {
       content += "ON";
     }
     else
     {
       content += "OFF";
     }
     
     sendHTTPResponse(connectionId,content);
     
     String closeCommand = "AT+CIPCLOSE="; 
     closeCommand+=connectionId;
     closeCommand+="\r\n";
     
     sendCommand(closeCommand,1000,DEBUG); // close connection
    }
  }

//sonar code
  long duration, distance;
  digitalWrite(trigPin, LOW);  
  delayMicroseconds(2); 
  digitalWrite(trigPin, HIGH);
  delayMicroseconds(10);
  digitalWrite(trigPin, LOW);
  duration = pulseIn(echoPin, HIGH);
  distance = (duration/2) / 29.1;
  if (distance < 50) { 
    digitalWrite(led,HIGH);
  digitalWrite(led2,LOW);
}
  else {
    digitalWrite(led,LOW);
    digitalWrite(led2,HIGH);
  }
  if (distance >= 200 || distance <= 0){
    //Serial.println("Out of range");
  }
  else {
    //Serial.print(distance);
    //Serial.println(" cm");
  }
}


void servomotor()
{
         if(pos == 0)
          {
           int i=0;
             for(;i<=90;i++)
             {
             myservo.write(i);
             pos=i;
             delay(10);
             }  
           }
          else if(pos == 90)
           {
              int i=90;
               for(;i>=0;i--)
               {
               myservo.write(i);
               pos=i;
               delay(10);
               }   
            }

            delay(500);
 }
 

String sendData(String command, const int timeout, boolean debug)
{
    String response = "";
    
    int dataSize = command.length();
    char data[dataSize];
    command.toCharArray(data,dataSize);
           
    esp8266.write(data,dataSize); 
    if(debug)
    {
      Serial.println("\r\n====== HTTP Response From Arduino ======");
      Serial.write(data,dataSize);
      Serial.println("\r\n========================================");
    }
    
    long int time = millis();
    
    while( (time+timeout) > millis())
    {
      while(esp8266.available())
      {
        
        
        char c = esp8266.read();
        response+=c;
      }  
    }
    
    if(debug)
    {
      Serial.print(response);
    }
    
    return response;
}
 

void sendHTTPResponse(int connectionId, String content)
{
     
     String httpResponse;
     String httpHeader;
     httpHeader = "HTTP/1.1 200 OK\r\nContent-Type: text/html; charset=UTF-8\r\n"; 
     httpHeader += "Content-Length: ";
     httpHeader += content.length();
     httpHeader += "\r\n";
     httpHeader +="Connection: close\r\n\r\n";
     httpResponse = httpHeader + content + " ";
     sendCIPData(connectionId,httpResponse);
}

 
void sendCIPData(int connectionId, String data)
{
   String cipSend = "AT+CIPSEND=";
   cipSend += connectionId;
   cipSend += ",";
   cipSend +=data.length();
   cipSend +="\r\n";
   sendCommand(cipSend,1000,DEBUG);
   sendData(data,1000,DEBUG);
}
 

String sendCommand(String command, const int timeout, boolean debug)
{
    String response = "";
           
    esp8266.print(command);
    
    long int time = millis();
    
    while( (time+timeout) > millis())
    {
      while(esp8266.available())
      {
        
        char c = esp8266.read();
        response+=c;
      }  
    }
    
    if(debug)
    {
      Serial.print(response);
    }
    
    return response;
}
