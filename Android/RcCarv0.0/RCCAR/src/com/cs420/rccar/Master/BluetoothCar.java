package com.cs420.rccar.Master;

import com.cs420.rccar.Controller.Driveable;
import com.cs420.rccar.Util.Command;

import android.bluetooth.BluetoothDevice;

/**
 * This class receives input from the Master.java class
 * and packages that input (a drive Command object) into
 * data sent via Bluetooth to mBluetoothDevice.  It is a 
 * high-level representation of an RC Car
 * 
 * @author David Widen
 * @author Jessie Young
 * @author Thomas Cheng
 *
 */
public class BluetoothCar implements Driveable 
{
	BluetoothDevice mBluetoothDevice;

	/**
	 * Constructor for the BluetoothCar class
	 * @param BTD the Bluetooth Device to send drive commands to
	 */
	public BluetoothCar(BluetoothDevice BTD)
	{
		
	}
	
	/**
	 * Method to send a drive Command to the BluetoothDevice
	 * @param mCommand the drive command to send
	 */
	public void driveCommand(Command mCommand) 
	{
		// TODO Auto-generated method stub

	}

}
