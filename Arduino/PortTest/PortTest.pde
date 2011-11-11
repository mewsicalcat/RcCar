#include <SPI.h>
#include <Adb.h>

Connection * connection;

// Event handler for the shell connection. 
void adbEventHandler(Connection * connection, adb_eventType event, uint16_t length, uint8_t * data)
{
  int i;

  if (event == ADB_CONNECTION_RECEIVE)
    for (i=0; i<length; i++)
      Serial.print(data[i]);

}

void setup()
{

  // Initialise serial port
  Serial.begin(57600);

  // Initialise the ADB subsystem.  
  ADB::init();

  // Open an ADB stream to the phone's shell. Auto-reconnect
  connection = ADB::addConnection("tcp:4567", true, adbEventHandler);  
 
}

void loop()
{
  ADB::poll();
  byte ch;
  uint16_t data = 123; 
  // Check for incoming serial data
  if (Serial.available() > 0)
  {
    // read the incoming byte:
    ch = Serial.read();
    
    // Write to port
    if (connection->status == ADB_OPEN)
      connection->write(2, (uint8_t*)&data);
    else
      Serial.print("port not open");

  }

  // Poll the ADB subsystem.
  while(connection->status == ADB_RECEIVING || connection->status == ADB_WRITING) 
  {    
    ADB::poll();
  }
}

