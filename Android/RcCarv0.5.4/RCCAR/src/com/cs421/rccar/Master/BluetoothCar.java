package com.cs421.rccar.Master;

import java.util.Set;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.Handler;
import android.util.Log;

import com.cs421.rccar.Controller.Driveable;
import com.cs421.rccar.Util.BluetoothReceiver;
import com.cs421.rccar.Util.BluetoothState;
import com.cs421.rccar.Util.Command;

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
	//private static final int REQUEST_ENABLE_BT = 1;
	
	private Handler mHandler;
	private BluetoothAdapter mAdapter;
	private BluetoothReceiver mReceiver;
	private StringBuffer mOutputStringBuffer;
	
	/**
	 * Constructor for the BluetoothCar class
	 * @param BTD the Bluetooth Device to send drive commands to
	 */
	public BluetoothCar(Handler h)
	{
		mHandler = h;
	}
	
	/**
	 * Method to send a drive Command to the BluetoothDevice
	 * @param mCommand the drive command to send
	 */
	public void driveCommand(Command mCommand) 
	{
    	Log.d("ARG", "start sendMessage");
        
    	// Check that we're actually connected before trying anything
        if (mReceiver.getState() != BluetoothState.STATE_CONNECTED) 
        {
            return;
        }
        
        byte[] send = new byte[1];
        send[0] = mCommand.getBytes();
        
        //send the byte[]
        mReceiver.write(send);
        Log.d("ARG", "wrote message: " + mCommand);
           
        // Reset out string buffer to zero and clear the edit text field
        mOutputStringBuffer.setLength(0);
	}
	
	/**
	 * Starts running the Bluetooth Receiver
	 */
	public void start()
	{
		mAdapter = BluetoothAdapter.getDefaultAdapter();
		setupReceiver();
	}
    
    /**
     * Initializes the BluetoothReceiver and makes a connection to this device's paired device through
     * the BluetoothCommunicationService
     */
    public void setupReceiver()
    {
    	mReceiver = new BluetoothReceiver(mHandler);
    	mReceiver.start();

        // Initialize the buffer for outgoing messages
        mOutputStringBuffer = new StringBuffer("");
        connectDevice();
    }
    
    /**
     * Connects this device to its paired device
     */
    private void connectDevice()
    {
    	Set<BluetoothDevice> pairedDevices = mAdapter.getBondedDevices();	//get paired Android phones
    	if (pairedDevices.size() > 0)										//if there are any paired phones
    	{
    		for (BluetoothDevice device : pairedDevices)
    		{
    			mReceiver.connect(device);								//connect to that phone
    		}
    	}
    }
    
    /**
     * Stops the Bluetooth Receiver
     */
    public boolean stop()
    {
    	return mReceiver.stop();
    }
}
