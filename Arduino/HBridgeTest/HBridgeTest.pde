//black: forward
//red: backward
//black: left
//red: right

const int forward =  2; //big red--CONNECT TO PIN 2 ON H BRIDGE
const int backward = 3; //big blue--CONNECT TO PIN 7 ON H BRIDGE
const int left = 4; //small red
const int right = 5; //small green

//const int led = 6; //on board led 

void getLow()
{
  digitalWrite(right, LOW);
  digitalWrite(left, LOW);
  digitalWrite(forward, LOW);
  digitalWrite(backward, LOW);
}


void setup() {
  // initialize serial communications at 9600 bps:
  Serial.begin(57600); 
  pinMode(forward, OUTPUT);
  pinMode(backward, OUTPUT);
  pinMode(left, OUTPUT);
  pinMode(right, OUTPUT);
  getLow(); 
  Serial.println("Starting loop..."); 
   // map it to the range of the analog out:
//  outputValue = map(500, FROM_LOW, FROM_outputValue, TO_LOW, TO_outputValue);  
}

void loop() {
  getLow(); 
  Serial.println("FORWARD 2 Seconds");
  digitalWrite(forward, HIGH);  // Latch the forward pin at HIGH
  delay(4000);  // Delay for 2 seconds
  
  getLow(); 
  delay(500);
  Serial.println("BACKWARD 2 Seconds");
  digitalWrite(backward, HIGH);
  delay(4000);
  
    getLow(); 
  delay(500);
  Serial.println("LEFT 2 Seconds");
  digitalWrite(left, HIGH);
  delay(4000);
  
    getLow(); 
  delay(500);
  Serial.println("RIGHT 2 Seconds");
  digitalWrite(right, HIGH);
  delay(4000);
  
}
