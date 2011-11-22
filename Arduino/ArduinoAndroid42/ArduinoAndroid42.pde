#include <SPI.h>
#include <Adb.h>

// Adb connection.
Connection * connection;

// Elapsed time for ADC sampling
long lastTime;

// Event handler for the connection. 
void adbEventHandler(Connection * connection, adb_eventType event, uint16_t length, uint8_t * data)
{
  int i;

  if (event == ADB_CONNECTION_RECEIVE)
    for (i=0; i<length; i++)
      Serial.print(data[i], HEX);

}

void setup()
{

  // Initialise serial port
  Serial.begin(57600);
 
    // Initialise the ADB subsystem.  
    ADB::init();

    // Open an ADB stream to the phone's shell. Auto-reconnect
    connection = ADB::addConnection("tcp:1337", true, adbEventHandler);  //4567
}

void loop()
{ 
  ADB::poll();
  uint16_t data = 12345; 
  
// if (connection->status == ADB_OPEN)
    connection->write(2, (uint8_t*) &data); //convert to pointer? 
  
  // Poll the ADB subsystem.
  while(connection->status == ADB_RECEIVING || connection->status == ADB_WRITING) 
  {    
    ADB::poll();
  }
}

