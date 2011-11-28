/*
MotorController.pde: replaces the car's motor controller board by sending our own control signals to the motors
-goes through move sequence same as ArduinoCarTest's sequence
-forward/back: x.xx V and 0 V; reverse polarity to change direction 
-left/right: x.xx V and 0 V; reverse polarity to change direction 

Test script to see if can go through test sequence (similiar to Arduino car test) using different voltages (0-5 V) 

  Analog input, analog output, serial output
 
 Reads an analog input pin, maps the result to a range from 0 to 255
 and uses the result to set the pulsewidth modulation (PWM) of an output pin.
 Also prints the results to the serial monitor.
 
 Modified 24 Nov. 2011
 by Jessie Young
 based on code by Tom Igoe
 
 Expected output: PWM voltages from 0:.5:5 to be measured using multimeter 
 */

//need: 1 kOhm resistor (brown, black, red) 
const int forward =  2; //big red
const int backward = 3; //big blue 
const int left = 4; //small red
const int right = 5; //small green
const int led = 6; //on board led 

const int FROM_outputValue = 100; 
const int FROM_LOW = 0; 
const int TO_outputValue = 255; 
const int TO_LOW = 0; 
const int outputValue; //value to send to analogWrite (scaling factor--value beteween 0 and 100) 

void getLow()
{
  analogWrite(right, LOW);
  analogWrite(left, LOW);
  analogWrite(forward, LOW);
  analogWrite(backward, LOW);
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
  outputValue = map(outputValue, FROM_LOW, FROM_outputValue, TO_LOW, TO_outputValue);  
}

void loop() {
  Serial.println("FORWARD 2 Seconds");
  analogWrite(forward, outputValue);  // Latch the forward pin at outputValue
  delay(2000);  // Delay for 2 seconds
  getLow(); 
  
  delay(500);
  Serial.println("BACKWARD 2 Seconds");
  analogWrite(backward, outputValue);
  delay(2000);
  getLow(); 
  
  delay(500);
  Serial.println("LEFT 2 Seconds");
  analogWrite(left, outputValue);
  delay(2000);
  getLow(); 
  
  delay(500);
  Serial.println("RIGHT 2 Seconds");
  analogWrite(right, outputValue);
  delay(2000);
  getLow(); 
  
  delay(500);
  Serial.println("GO FORWARD LEFT 2 Seconds");
  analogWrite(forward, outputValue);
  analogWrite(left, outputValue);
  delay(2000);
  getLow(); 
  
  delay(500);
  Serial.println("GO FORWARD RIGHT 2 Seconds");
  analogWrite(forward, outputValue);
  analogWrite(right, outputValue);
  delay(2000);
  getLow(); 

}
