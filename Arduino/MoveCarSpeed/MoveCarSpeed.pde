#include <SPI.h>
#include <Adb.h>
//#include "Command.h" //placed in Arduino's 'libraries' directory 
 
// DEFINE PINS (pins = lower case) 

//need: 1 kOhm resistor (brown, black, red) 
const int forward =  2; //big red
const int backward = 3; //big blue 
const int left = 4; //small red
const int right = 5; //small green
const int led = 6; //on board led 

int speed = 5; //int from 0-10 for speed of car

// Adb connection.
Connection * connection;

// Elapsed time for ADC sampling
long lastTime;

//int state; 

void getLow()
{
  digitalWrite(right, LOW);
  digitalWrite(left, LOW);
  digitalWrite(forward, LOW);
  digitalWrite(backward, LOW);
}
// Event handler for the connection. 
void adbEventHandler(Connection * connection, adb_eventType event, uint16_t length, uint8_t * data)
{
  int i;

  if (event == ADB_CONNECTION_RECEIVE)
  {
//    for (i=0; i<length; i++)
      Serial.println(data[i], HEX);
      digitalWrite(led, HIGH); //blink led if connection is good 
      
    uint8_t cmd = data[0]; //get user's command
    
    switch(cmd)
    {
      case 0: 
        Serial.println("FORWARD!");
        getLow(); 
        digitalWrite(forward, HIGH);
        delay(250); 
        break; 
      case 1: 
        Serial.println("BACKWARD!");
        getLow(); 
        digitalWrite(backward, HIGH); 
        delay(250);  
        break; 
      case 2: 
        Serial.println("LEFT!");
        getLow(); 
        digitalWrite( left, HIGH);    
        delay(250);      
        break; 
      case 3: 
        Serial.println("RIGHT!");
        getLow(); 
        digitalWrite(right, HIGH);   
         delay(250);       
        break; 
      case 4: 
        Serial.println("FORWARDLEFT!");
        getLow(); 
        digitalWrite(forward, HIGH);
        digitalWrite( left, HIGH);   
        delay(250); 
        break; 
      case 5: 
        Serial.println("FORWARDRIGHT!");
        getLow(); 
        digitalWrite(forward, HIGH);
        digitalWrite(right, HIGH);
               delay(250); 
        break; 
      case 6: 
        Serial.println("BACKWARDLEFT!");
        getLow(); 
        digitalWrite(backward, HIGH);
        digitalWrite(left, HIGH); 
       delay(250);         
        break; 
      case 7: 
        Serial.println("BACKWARDRIGHT!");
        getLow(); 
        digitalWrite(backward, HIGH);
        digitalWrite(right, HIGH);  
               delay(250); 
        break; 
      case 8: 
       Serial.println("STOP!"); 
        getLow();        
        delay(250);        
        break; 
      default: 
        Serial.println("Invalid input...");
        break;  
    }//end switch
      
    digitalWrite(led, LOW); //toggle off ss
  }
  
  

  //move car

}

void setup()
{
  Serial.begin(57600); //initialize serial port 
  pinMode(forward, OUTPUT);
  pinMode(backward, OUTPUT);
  pinMode(left, OUTPUT);
  pinMode(right, OUTPUT);
  pinMode(led, OUTPUT); //debug led attached to pin 13

  digitalWrite(led, HIGH); //starts off not blinking 

  getLow(); //put here? 
  Serial.println("Setup: all LOW"); 
  

  

  // Initialise the ADB subsystem.  
  ADB::init();

  // Open an ADB stream to the phone's shell. Auto-reconnect
  connection = ADB::addConnection("tcp:1337", true, adbEventHandler);  
}

void loop()
{ 

  ADB::poll();
  //uint16_t data = 12345; 

  // if (connection->status == ADB_OPEN)
  //connection->write(2, (uint8_t*) &data); //convert to pointer? 

  // Poll the ADB subsystem.
  while(connection->status == ADB_RECEIVING || connection->status == ADB_WRITING) 
  {    
    ADB::poll();
  }
}


