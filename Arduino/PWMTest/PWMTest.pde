/**
* Tests maximum output on pwm pins to see if can source enough current
* Thomas Cheng, David Widen, and Jessie Young

*/

// DEFINE PINS
//working pwm pins 
#define forward 3 //red 
#define backward 5 //green
#define left 6 //blue
#define right 10 //white 
// Note: Pins 3, 5, 6, 9, 10, and 11 have PWM
// Note: Pin 0 is Serial RX (receive) and Pin 1 is Serial TX (transmit)

void setup()
{
  pinMode(forward, OUTPUT);
  pinMode(backward, OUTPUT);
  pinMode(left, OUTPUT);
  pinMode(right, OUTPUT);
  Serial.begin(57600); 

}

void loop()
{ 
  Serial.println("FORWARD 2 Seconds");

}
