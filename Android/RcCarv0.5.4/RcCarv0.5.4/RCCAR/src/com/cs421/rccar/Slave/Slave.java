package com.cs421.rccar.Slave;

import android.bluetooth.BluetoothDevice;
import android.os.Handler;

import com.cs421.rccar.Controller.Driveable;
import com.cs421.rccar.Util.BluetoothReceiver;
import com.cs421.rccar.Util.Command;

/**
 * This class acts as a container for the UsbCar.java class
 * and passes information received via Bluetooth connection
 * to a Driveable object
 * 
 * @author David Widen
 * @author Jessie Young
 * @author Thomas Cheng
 *
 */
public class Slave
{
	private Driveable mDriveable;
	private BluetoothReceiver mReceiver;	
	
	/**
	 * Default constructor for the Slave class
	 * @param h the message handler
	 */
	public Slave(Handler h)
	{
		mReceiver = new BluetoothReceiver(h);
		mDriveable = new UsbCar();
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
	 * @return true if connected, false otherwise
	 */
	public boolean disconnectFromHost()
	{
		return mReceiver.stop();
	}
	
	/**
	 * Send a drive command to the UsbCar
	 * @param mCommand the drive command to send
	 */
	public void sendCarCommand(Command mCommand)
	{
		mDriveable.driveCommand(mCommand);
	}
	
	/**
	 * Start the Bluetooth Receiver
	 */
	public void start()
	{
    	mReceiver.start();
	}
	
	/**
	 * Connect an external Bluetooth device to this one
	 * @param mDevice the external Bluetooth device
	 */
	public void connect(BluetoothDevice mDevice)
	{
		mReceiver.connect(mDevice);
	}
	
	/**
	 * Stop the Bluetooth connection
	 * @return true if successfully stopped, false otherwise
	 */
	public boolean stop()
	{
		return disconnectFromHost();
	}
	
//	public void sendPicture(byte[] data)
//	{
//		mReceiver.write(data); 
//	}
}