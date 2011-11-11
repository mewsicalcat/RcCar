/**
* Arduino Output Test
* Thomas Cheng, David Widen, and Jessie Young

*/

// DEFINE PINS
#define forward 3
#define backward 5
#define left 6
#define right 9
// Note: Pins 3, 5, 6, 9, 10, and 11 have PWM
// Note: Pin 0 is Serial RX (receive) and Pin 1 is Serial TX (transmit)

void setup()
{
  pinMode(forward, OUTPUT);
  pinMode(backward, OUTPUT);
  pinMode(left, OUTPUT);
  pinMode(right, OUTPUT);
}

void loop()
{
  Serial.print("Initializing Test");
  
  Serial.print("FORWARD 2 Seconds");
  digitalWrite(forward, HIGH);  // Latch the forward pin at HIGH
  delay(2000);  // Delay for 2 seconds
  digitalWrite(forward, LOW);  // Latch it back to a LOW
  
  delay(500);
  Serial.print("BACKWARD 2 Seconds");
  digitalWrite(backward, HIGH);
  delay(2000);
  digitalWrite(backward, LOW);
  
  delay(500);
  Serial.print("LEFT 2 Seconds");
  digitalWrite(left, HIGH);
  delay(2000);
  digitalWrite(left, LOW);
  
  delay(500);
  Serial.print("RIGHT 2 Seconds");
  digitalWrite(right, HIGH);
  delay(2000);
  digitalWrite(right, LOW);
  
  delay(500);
  Serial.print("GO FORWARD LEFT 2 Seconds");
  digitalWrite(forward, HIGH);
  digitalWrite(left, HIGH);
  delay(2000);
  digitalWrite(forward, LOW);
  digitalWrite(left, LOW);
  
  delay(500);
  Serial.print("GO FORWARD RIGHT 2 Seconds");
  digitalWrite(forward, HIGH);
  digitalWrite(right, HIGH);
  delay(2000);
  digitalWrite(forward, LOW);
  digitalWrite(right, LOW);
}
