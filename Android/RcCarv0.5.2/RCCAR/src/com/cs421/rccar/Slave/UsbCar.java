package com.cs421.rccar.Slave;

import com.cs421.rccar.Controller.Driveable;
import com.cs421.rccar.Util.Command;

//import android.hardware.usb.UsbDevice;

/**
 * This class acts as a high-level representation for an
 * RC Car.  It receives drive Commands from Slave.java and
 * then converts this to serial data, which it then sends
 * through UsbDevice mUsbDevice.
 * 
 * @author David Widen
 * @author Jessie Young
 * @author Thomas Cheng
 *
 */
public class UsbCar implements Driveable 
{
	//UsbDevice mUsbDevice;
	
	/**
	 * Default constructor for the USBCar class
	 * @param mDevice the USB Device to send commands to
	 */
	public UsbCar(/*UsbDevice mDevice*/)
	{
		
	}
	
	/**
	 * Method to send a drive Command to some receiver
	 * @param mCommand the drive command to send
	 */
	public void driveCommand(Command mCommand) {
		// TODO Auto-generated method stub

	}
	
	/**
	 * Method to perform all required initializations to start the 
	 * communication service for the car representation
	 */
	public void start()
	{
		
	}

}
