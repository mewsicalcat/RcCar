package com.cs421.rccar.Master;

import android.bluetooth.BluetoothDevice;
import android.os.Handler;

import com.cs421.rccar.Controller.Driveable;
import com.cs421.rccar.Util.BluetoothState;
import com.cs421.rccar.Util.Command;

/**
 * This class serves as a communication interface between
 * the MasterButtonActivity/MasterAccelerometerActivity
 * and the Driveable object.  This class receives input
 * from its containing activity and passes that command
 * to a driveable object
 * 
 * @author David Widen
 * @author Jessie Young
 * @author Thomas Cheng
 *
 */
public class Master 
{
	private Driveable mDriveable;
	
	/**
	 * Default constructor for the Master class
	 * @param h the message handler 
	 */
	public Master(Handler h)
	{
		mDriveable = new BluetoothCar(h);
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
    public boolean disconnectFromSlave()
    {
    	return mDriveable.stop();
    }
    
    /**
     * Sends a car command to mDriveable
     * @param mCommand
     */
    public void sendCarCommand(Command mCommand)
    {
    	mDriveable.driveCommand(mCommand);
    }
    
    /**
     * Inherited method to run when the Application starts running
     * Enables Bluetooth if it is not already enabled
     */
    public void start()
    {
    	mDriveable.start();
    }
    
    public synchronized BluetoothState getState()
    {
    	return mDriveable.getState();
    }
   
}