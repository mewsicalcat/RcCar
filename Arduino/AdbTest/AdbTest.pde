//http://romfont.com/category/android/

#include <SPI.h>
#include <Adb.h>

// Adb connection.
Connection * shell;


// Event handler for the shell connection.
void adbEventHandler(Connection * shell, adb_eventType event, 
  uint16_t length, uint8_t * data)
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
  shell = ADB::addConnection("shell:", true, adbEventHandler);  

}

void loop()
{ 
 
      ADB::poll();
    //	ADB_UNUSED = 0,
    //	ADB_CLOSED,
    //	ADB_OPEN,
    //	ADB_OPENING,
    //	ADB_RECEIVING,
    //	ADB_WRITING
    Serial.print("Connection status: ");
    Serial.println(shell->status);  
    if (shell->isOpen())
      Serial.print("shell is open!!!!!!!"); 
    delay(1000); 
  

}

