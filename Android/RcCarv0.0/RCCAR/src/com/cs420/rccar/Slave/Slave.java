package com.cs420.rccar.Slave;

import com.cs420.rccar.Controller.Driveable;
import com.cs420.rccar.Util.Command;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

/**
 * This class acts as a container for the UsbCar.java class
 * and passes information received via Bluetooth connection
 * from mDevice to its Driveable object, mDriveable.
 * 
 * @author David Widen
 * @author Jessie Young
 * @author Thomas Cheng
 *
 */
public class Slave
{
	private BluetoothSocket mBluetoothSocket;
	private Driveable mDriveable;
	private BluetoothDevice mDevice;
	
	/**
	 * Send data to the Master device
	 * @param data the information to send
	 */
	public void sendData(String data)
	{
		
	}
	
	/**
	 * Receive data from the Master device
	 * @param data the information received
	 */
	public void receiveData(String data)
	{
		
	}
	
	/**
	 * Disconnect this device from the Master device
	 */
	public void disconnectFromHost()
	{
		
	}
	
	/**
	 * Send a drive command to the UsbCar
	 * @param mCommand the drive command to send
	 */
	public void sendCarCommand(Command mCommand)
	{
		
	}
}
