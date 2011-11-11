package com.cs421.rccar.Master;

import java.util.Set;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;

import com.cs421.rccar.Controller.Driveable;
import com.cs421.rccar.Util.BluetoothCommunicationService;
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
	private static final int REQUEST_ENABLE_BT = 1;
	
	private Handler mHandler;
	private BluetoothAdapter mAdapter;
	private BluetoothCommunicationService mCommService;
	private StringBuffer mOutputStringBuffer;
	
	private final byte FORWARD = 0;
	private final byte BACKWARD = 1;
	private final byte LEFT = 2;
	private final byte RIGHT = 3;
	private final byte FORWARDLEFT = 4;
	private final byte FORWARDRIGHT = 5;
	private final byte BACKWARDLEFT = 6;
	private final byte BACKWARDRIGHT = 7;
	private final byte STOP = 8;
	
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
        if (mCommService.getState() != BluetoothState.STATE_CONNECTED) 
        {
            return;
        }
        
        byte[] send = new byte[1];
        
        switch (mCommand)
        {
        	case BACKWARD: send[0] = BACKWARD; break;
        	case BACKWARDRIGHT: send[0] = BACKWARDRIGHT; break;
        	case FORWARDLEFT: send[0] = FORWARDLEFT; break;
        	case BACKWARDLEFT: send[0] = BACKWARDLEFT; break;
        	case FORWARD: send[0] = FORWARD; break;
        	case FORWARDRIGHT: send[0] = FORWARDRIGHT; break;
        	case LEFT: send[0] = LEFT; break;
        	case RIGHT: send[0] = RIGHT; break;
        	case STOP: send[0] = STOP; break;
        	default: return;
        }
        
        //send the byte[]
        mCommService.write(send);
        Log.d("ARG", "wrote message: " + mCommand);
           
        // Reset out string buffer to zero and clear the edit text field
        mOutputStringBuffer.setLength(0);
	}
	
	public void start()
	{
		mAdapter = BluetoothAdapter.getDefaultAdapter();

        if (!mAdapter.isEnabled()) 
        {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            (new Activity()).startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
            setupCommService();
        } 
        else 
        {
            if (mCommService == null) 
            	setupCommService();
        }
	}
    
    /**
     * Initializes the BluetoothCommunicationService and makes a connection to this device's paired device
     */
    public void setupCommService()
    {
    	mCommService = new BluetoothCommunicationService(mHandler);

        // Initialize the buffer for outgoing messages
        mOutputStringBuffer = new StringBuffer("");
        connectDevice();
    }
    
    /**
     * Connects this device to its paired device
     */
    private void connectDevice()
    {
    	Set<BluetoothDevice> pairedDevices = mAdapter.getBondedDevices();
    	if (pairedDevices.size() > 0)
    	{
    		for (BluetoothDevice device : pairedDevices)
    		{
    			mCommService.connect(device);
    		}
    	}
    }

}
