package com.cs420.rccar.Slave;

import java.io.IOException;
import java.util.Set;
import java.util.UUID;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.widget.TextView;

import com.cs420.rccar.Controller.Driveable;
import com.cs420.rccar.Util.Command;

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
	
	/*****************************/
	/* Temp Data */
	private static UUID MY_UUID = UUID.fromString("437ee550-fde6-11e0-be50-0800200c9a66");
	private BluetoothAdapter mBluetoothAdapter;
	private ConnectThread mConnectThread;
	private static final int REQUEST_ENABLE_BT = 3;
	private static TextView mTextView;
	
	
	/**
	 * Default constructor for the Slave class
	 */
	public Slave()
	{
		
	}
	
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
	
	/********************************************************/
	/********************************************************/
	/* EXTRAS */
	public void setText(TextView t)
	{
		mTextView = t;
	}
	
	public void init()
    {
		if (initAdapter())
		{
			enableAdapter();
			findPairedDevice();
		}
		else
		{
			throw new NullPointerException("ERROR!  BluetoothAdapter failed to load");
		}
		
		connectToHost();
    }
	
	public boolean initAdapter()
    {
    	/*	Should go in constructor */
    	mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		if (mBluetoothAdapter == null) 
		{
		    //text.setText("FAILURE");
		    return false;
		}
		else
		{
			//text.setText("SUCCESS");
			return true;
		}
    }
    
    public void enableAdapter()
    {
		if (!mBluetoothAdapter.isEnabled()) 
		{
		    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
		    (new Activity()).startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);	//something terrible could happen
		    //text.setText("ACTIVATED BLUETOOTH");
		}
		else
		{
		    //text.setText("BLUETOOTH ALREADY ENABLED");
		}
    }
    
    public void findPairedDevice()
    {
    	Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
		// If there are paired devices
		if (pairedDevices.size() > 0) 
		{
		    // Loop through paired devices
		    for (BluetoothDevice device : pairedDevices) 
		    {
		    	mDevice = device;
		    	Slave.mTextView.setText(device.getName() + ": " + device.getAddress());
		    }
		}
    }
	
	
	public void setAdapter(BluetoothAdapter adapter)
	{
		mBluetoothAdapter = adapter;
	}
	
	public void connectToHost()
	{
		mConnectThread = new ConnectThread(mDevice);
		mConnectThread.start();
	}
	
	private class ConnectThread extends Thread {
	    private final BluetoothSocket mmSocket;
	    private final BluetoothDevice mmDevice;
	 
	    public ConnectThread(BluetoothDevice device) {
	        // Use a temporary object that is later assigned to mmSocket,
	        // because mmSocket is final
	        BluetoothSocket tmp = null;
	        mmDevice = device;
	 
	        // Get a BluetoothSocket to connect with the given BluetoothDevice
	        try {
	            // MY_UUID is the app's UUID string, also used by the server code
	            tmp = device.createRfcommSocketToServiceRecord(MY_UUID);
	        } catch (IOException e) { }
	        mmSocket = tmp;
	    }
	 
	    public void run() {
	        // Cancel discovery because it will slow down the connection
	        mBluetoothAdapter.cancelDiscovery();
	 
	        try {
	            // Connect the device through the socket. This will block
	            // until it succeeds or throws an exception
	            mmSocket.connect();
	        } catch (IOException connectException) {
	            // Unable to connect; close the socket and get out
	            try {
	                mmSocket.close();
	            } catch (IOException closeException) { }
	            return;
	        }
	 
	        // Do work to manage the connection (in a separate thread)
	        //manageConnectedSocket(mmSocket);
	    }
	 
	    /** Will cancel an in-progress connection, and close the socket */
	    public void cancel() {
	        try {
	            mmSocket.close();
	        } catch (IOException e) { }
	    }
	}
}
