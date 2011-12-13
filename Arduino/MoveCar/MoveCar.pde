/* Jessie Young, David Widen, Thomas Cheng
   Johns Hopkins University, Object Oriented Software Engineering 2011
   Group 2 
   MoveCar.pde: C++ code to create ADB connection, receive move command, and send output to motor controller 
*/
#include <SPI.h>
#include <Adb.h>

// DEFINE PINS
const int forward =  2; 
const int backward = 5; 
const int left = 6; 
const int right = 3; 

// Adb connection.
Connection * connection;

// Elapsed time for ADC sampling
long lastTime;

//int state; 

void setLow()
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
    Serial.println(data[i], HEX);
      
    uint8_t cmd = data[0]; //get user's command
    
    switch(cmd)
    {
      case 0: 
        Serial.println("FORWARD!");
        setLow(); 
        digitalWrite(forward, HIGH);
        delay(100);    // Delay the output a little bit to preven
        break; 
      case 1: 
        Serial.println("BACKWARD!");
        setLow(); 
        digitalWrite(backward, HIGH); 
        delay(100);  
        break; 
      case 2: 
        Serial.println("LEFT!");
        setLow(); 
        digitalWrite( left, HIGH);    
        delay(100);      
        break; 
      case 3: 
        Serial.println("RIGHT!");
        setLow(); 
        digitalWrite(right, HIGH);   
         delay(100);       
        break; 
      case 4: 
        Serial.println("FORWARDLEFT!");
        setLow(); 
        digitalWrite(forward, HIGH);
        digitalWrite( left, HIGH);   
        delay(100); 
        break; 
      case 5: 
        Serial.println("FORWARDRIGHT!");
        setLow(); 
        digitalWrite(forward, HIGH);
        digitalWrite(right, HIGH);
        delay(100); 
        break; 
      case 6: 
        Serial.println("BACKWARDLEFT!");
        setLow(); 
        digitalWrite(backward, HIGH);
        digitalWrite(left, HIGH); 
       delay(100);         
        break; 
      case 7: 
        Serial.println("BACKWARDRIGHT!");
        setLow(); 
        digitalWrite(backward, HIGH);
        digitalWrite(right, HIGH);  
        delay(100); 
        break; 
      case 8: 
       Serial.println("STOP!"); 
        setLow();        
        delay(100);        
        break; 
      default: 
        Serial.println("Invalid input...");
        break;  
    }//end switch   
  }
}

void setup()
{
  Serial.begin(57600); //initialize serial port 
  pinMode(forward, OUTPUT);
  pinMode(backward, OUTPUT);
  pinMode(left, OUTPUT);
  pinMode(right, OUTPUT);
  setLow(); //
  Serial.println("Setup: all LOW");

  // Initialise the ADB subsystem.  
  ADB::init();

  // Open an ADB stream to the phone's shell. Auto-reconnect
  connection = ADB::addConnection("tcp:1337", true, adbEventHandler);  
}

void loop()
{ 
  ADB::poll();
  
  // Poll the ADB subsystem.
  while(connection->status == ADB_RECEIVING || connection->status == ADB_WRITING) 
  {    
    ADB::poll();
  }
}


