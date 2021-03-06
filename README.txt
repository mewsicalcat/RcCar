Updated 12/14/2011
As of Iteration 6 (final submission) 

The most up-to-date version of the Android project is in the RcCar > Android > RcCarv1.0 directory. The most updated Arduino code is in the Arduino directory's MoveCar.pde sketch. 

Official projects are in RcCarvX.Y.Z files, the ServoControl project, and the ArduinoAndroid42.pde located in the Arduino folder. The rest of the Arduino sketches in the folder serve as tests for various functionalities. 

The most recent files have the highest version numbers.

Version 0.5 - 0.5.3.1 are stable versions developed during prototyping phases (Between Iterations 2 and 3)
Versions 0.0 - 0.2 are stable versions developed during planning phases (Iteration 2)

The ServoControl Android project and the Arduino sketches are the most up to date files. 


RCCARTest is a test suite of Unit Tests which test the following classes:
Master.java
SlaveActivity.java

Future tests are intended and will be implemented to test the following:
Loss of Bluetooth connection
Proper status of Bluetooth connection

Integration tests will be developed once we have successfully send data
from the Android to the Arduino via the SERIAL port.  Integration tests will
consist of the following:

Upon us successfully setting up the applications, we will hit a button
which launches our test.  The master android will send a series of drive
commands to the slave application, which will then send them to the Arduino
which will instruct the car to move.  There will be a set order and length
of time for these commands.  The phones will indicate successfuly completion.

ServoControl is an Android application that creates a single activity when launched that displays a GUI-based joystick. An OnTouch method sends test byes, the number '42', to the connected Arduino microcontroller when any user input (movement of the joystick) is received. 

The Arduino Uno microcontroller runs the ArduinoAndroid42.pde program and receives the test data passed from the Android device and displays it on a serial output screen. In this case, '42' is displayed every time the Android application receives and sends input to the microcontroller. 

To RUN APPLICATION:

Install RcCar 0.5.4 on two Android devices, ServoControl on one of the devices, and ArduinoAndroid42 on the Arduino Uno microcontroller. 
Run the RcCar 0.5.4 application on each device.
For one device, click "Launch Slave" to launch the slave activity
On the other device, click "Launch Master" to launch the master activity

On the Android running the master activity:
Click one of the 9 buttons to send a command via Bluetooth

On the Android running the slave activity:
The screen updates with the current time in milliseconds that the command was received
The screen updates with the current command that was received via Bluetooth

To test communication between the ServoControl app and the ArduinoAndroid42 sketch, install and run ServoControl on an Android device, and upload the ArduinoAndroid42 sketch to the Arduino microcontroller. Connect the Arduino to the USB host shield and open up a serial terminal in the Arduino IDE. You should see a string of '42's appear on the screen as the user moves his or her finger around on the screen of the Android device. 
