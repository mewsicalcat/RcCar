package com.cs421.rccar.Util;

import android.bluetooth.BluetoothDevice;
import android.os.Handler;


/**
 * Handles Bluetooth communication between two paired Bluetooth devices by using a
 * BluetoothCommunicationService object.  Allows messages to be sent and received
 * using thread-safe operations
 * 
 * @author David Widen
 * @author Thomas Cheng
 * @author Jessie Young
 *
 */
public class BluetoothReceiver
{
	private Handler mHandler;
	private BluetoothCommunicationService mCommService;
	
	/**
	 * Constructs the BluetoothReceiver
	 * @param h the message handler from the current activity on the screen
	 */
	public BluetoothReceiver(Handler h)
	{
		mHandler = h;
	}
	
	/**
	 * Starts the BluetoothCommunicationService
	 */
	public void start()
	{
		setupCommService();
	}
	
    /**
     * Initializes the BluetoothCommunicationService and makes a connection to this device's paired device
     */
    public void setupCommService()
    {
    	mCommService = new BluetoothCommunicationService(mHandler);
    	mCommService.start();
    }
    
    /**
     * Stop the established Bluetooth Communication Service
     * @return true if stopped
     */
    public boolean stop()
    {
    	return mCommService.stop();
    }
    
    /**
     * Sends data over the BluetoothConnection to this device's paired device
     * @param data the byte array of data to send
     */
    public void write(byte[] data)
    {
    	mCommService.write(data);
        //mOutputStringBuffer.setLength(0);
    }
    
    /**
     * Gets the current state of the BluetoothCommunicationService object
     * @return the state of the BluetoothCommunicationService
     */
    public BluetoothState getState()
    {
    	return mCommService.getState();
    }
    
    /**
     * Starts the Receiver's connection
     * @param device the remote device to connect to
     */
    public void connect(BluetoothDevice device)
    {
    	mCommService.connect(device);
    }
}