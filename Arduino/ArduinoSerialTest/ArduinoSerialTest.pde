#include <SPI.h>
#include <Adb.h>

// Adb connection.
Connection * connection;

// Elapsed time for ADC sampling
long lastTime;

// Event handler for the shell connection. 
void adbEventHandler(Connection * connection, adb_eventType event, uint16_t length, uint8_t * data)
{
  Serial.print("inside adbEventHandler..."); 
  // Data packets contain two bytes, one for each servo, in the range of [0..180]
  if (event == ADB_CONNECTION_RECEIVE)
  {
    // create data first! output to debug screen
    Serial.println("Adb connected...");
    Serial.println("received data...data[0] and data[1]");
    Serial.println((int)data[0]);  
    Serial.println((int)data[1]); 
  }
  else
  {
    Serial.println("Adb not connected..."); 
  }
}

void setup()
{

  // Initialise serial port
  Serial.begin(57600);
 
    // Initialise the ADB subsystem.  
    ADB::init();

    // Open an ADB stream to the phone's shell. Auto-reconnect
    connection = ADB::addConnection("tcp:1234", true, adbEventHandler);  
    Serial.print("Attempted to add adb connection..."); 
}

void loop()
{ 
  for (int i = 0; i < 5; i++)
  {
    Serial.println(i); 
    connection->write(2, (uint8_t*)&i); //convert to pointer? 
    
    //	ADB_UNUSED = 0,
    //	ADB_CLOSED,
    //	ADB_OPEN,
    //	ADB_OPENING,
    //	ADB_RECEIVING,
    //	ADB_WRITING
    
    Serial.print("Connection status: ");
    Serial.println(connection->status);  
    delay(1000); 
  }
  // Poll the ADB subsystem.
  Serial.println("Polling adb..."); 
  ADB::poll();
}

