package com.cs420.rccar.Master;

import com.cs420.rccar.Controller.Driveable;
import com.cs420.rccar.Util.Command;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

/**
 * This class serves as a communication interface between
 * the MasterButtonActivity/MasterAccelerometerActivity
 * and the Driveable object.  This class receives input
 * from its containing activity and passes that command
 * to mDriveable
 * 
 * @author David Widen
 * @author Jessie Young
 * @author Thomas Cheng
 *
 */
public class Master 
{
	private BluetoothSocket mBluetoothSocket;
	private Driveable mDriveable;
	private BluetoothDevice mBluetoothDevice;
	
	/**
	 * Default constructor for the Master class
	 */
	public Master()
	{
		
	}
	
	/**
	 * Connects this device to another Android device via Bluetooth
	 * @param BTD the Bluetooth device to connect to
	 * @return true if successful connection, false otherwise
	 */
    public boolean connectToSlave(BluetoothDevice BTD)
    {
    	return true;
    }
    
    /**
     * Disconnects from the currently connected Android device
     * @param BTD the Bluetooth device to disconnect from
     * @return true if successfully disconnected, false otherwise
     */
    public boolean disconnectFromSlave(BluetoothDevice BTD)
    {
    	return true;
    }
    
    /**
     * Sends a car command to mDriveable
     * @param mCommand
     */
    public void sendCarCommand(Command mCommand)
    {
    	
    }
	
}
