package com.cs421.rccar.Controller;

import com.cs421.rccar.Util.BluetoothState;
import com.cs421.rccar.Util.Command;

/**
 * This interface defines a single method which allows
 * Android devices to send a drive Command to some
 * implementation defined device, such as a USB port
 * or via Bluetooth
 * 
 * @author David Widen
 * @author Jessie Young
 * @author Thomas Cheng
 *
 */
public interface Driveable 
{
	/**
	 * Method to send a drive Command to some receiver
	 * as defined by the class which implements this Interface
	 * @param mCommand the drive command to send
	 */
	public void driveCommand(Command mCommand);
	
	/**
	 * Method to perform all required initializations to start the 
	 * communication service for the car representation
	 */
	public void start();
	
	/**
	 * Method to stop the current connection between devices
	 */
	public boolean stop();
	
	/**
	 * 
	 */
	public BluetoothState getState();
}
