/*
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
//black: forward
//red: backward
//black: left
//red: right

//need: 1 kOhm resistor (brown, black, red) 
const int forward =  5; //big red
const int backward = 6; //big blue 
const int left = 10; //small red
const int right = 11; //small green

const int FROM_HIGH = 100; 
const int FROM_LOW = 0; 
const int TO_HIGH = 255; 
const int TO_LOW = 0; 
int outputValue; //value to send to analogWrite (voltage between 0 and 5 V) 

void setup() {
  // initialize serial communications at 9600 bps:
  Serial.begin(9600); 
  pinMode(forward, OUTPUT);
  pinMode(backward, OUTPUT);
  pinMode(left, OUTPUT);
  pinMode(right, OUTPUT);
}

void loop() {
  
  for (int i = 0; i < FROM_HIGH; i+=5) //TODO: change to i++ for motor speed testing 
  { 
  
    // map it to the range of the analog out:
  outputValue = map(i, FROM_LOW, FROM_HIGH, TO_LOW, TO_HIGH);  
  
  
  // Test ability to vary analog output on all 4 pins 
  analogWrite(forward, outputValue); 
  analogWrite(backward, outputValue);    
  analogWrite(left, outputValue);  
  analogWrite(right, outputValue);  

  // print the results to the serial monitor:   
  Serial.print("\t output = ");      
  Serial.println(outputValue);   

  // wait 10 milliseconds before the next loop
  // for the analog-to-digital converter to settle
  // after the last reading:
  delay(2000); //see when motor starts moving (TODO: increase so can wait while take multimeter readings)  
  } 
                  
}
