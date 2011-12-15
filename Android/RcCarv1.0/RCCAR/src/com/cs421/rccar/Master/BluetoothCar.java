package com.cs421.rccar.Master;

import java.util.Set;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.Handler;
import android.util.Log;

import com.cs421.rccar.Controller.Driveable;
import com.cs421.rccar.UI.MainActivity;
import com.cs421.rccar.Util.Command;
import com.cs421.rccar.Util.Bluetooth.BluetoothReceiver;
import com.cs421.rccar.Util.Bluetooth.BluetoothState;

/**
 * Receives input from the Master.java class
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
	private Handler mHandler;
	private BluetoothAdapter mAdapter;
	private BluetoothReceiver mReceiver;
	private StringBuffer mOutputStringBuffer;
	private Command currentDriveCommand;
	private CarTimer timer;
	private boolean isRunning;
	
	/**
	 * Constructor for the BluetoothCar class
	 * @param h the handler for message passing
	 */
	public BluetoothCar(Handler h)
	{
		mHandler = h;
		isRunning = false;
		timer = null;
		currentDriveCommand = Command.STOP;
	}
	
	/**
     * {@inheritDoc}
     */
	public void driveCommand(Command mCommand) 
	{
        if (MainActivity.DEBUG)
        {
        	Log.d("BluetoothCar", "start sendMessage");
        }
        
    	// Check that we're actually connected before trying anything
        if (mReceiver.getState() != BluetoothState.STATE_CONNECTED) 
        {
        	currentDriveCommand = Command.STOP;
        	return;
        }
        
        byte[] send = new byte[1];
        send[0] = mCommand.getBytes();
        
        //send the byte[]
        mReceiver.write(send);
        
        if (MainActivity.DEBUG)
        {
        	Log.d("BluetoothCar", "wrote message: " + mCommand);
        }  
        
        // Reset out string buffer size to zero, update the current state of the car
        mOutputStringBuffer.setLength(0);
        currentDriveCommand = mCommand;
	}
	
	/**
     * {@inheritDoc}
     */
	public synchronized void start()
	{
		if (MainActivity.DEBUG)
		{
			Log.d("BluetoothCar", "start()");
		}
		
		mAdapter = BluetoothAdapter.getDefaultAdapter();
		setupReceiver();
		isRunning = true;
		timer = null;
		timer = new CarTimer();
		timer.start();
		
		connectDevice();
		
		if (MainActivity.DEBUG)
		{
			Log.d("BluetoothCar", "finished start()");
		}
	}
    
    /**
     * Initializes the BluetoothReceiver and makes a connection to this device's paired device through
     * the BluetoothCommunicationService
     */
    private void setupReceiver()
    {
		if (MainActivity.DEBUG)
		{
			Log.d("BluetoothCar", "setupReceiver()");
		}
    	
    	mReceiver = new BluetoothReceiver(mHandler);
    	mReceiver.start();

        // Initialize the buffer for outgoing messages
        mOutputStringBuffer = new StringBuffer("");
    }
    
    /**
     * Connects this device to its paired device
     */
    private void connectDevice()
    {
		if (MainActivity.DEBUG)
		{
			Log.d("BluetoothCar", "connectDevice()");
		}
    	
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
     * {@inheritDoc}
     */
    public synchronized boolean stop()
    {
		if (MainActivity.DEBUG)
		{
			Log.d("BluetoothCar", "stop()");
		}
    	
    	isRunning = false;
    	timer = null;
    	return mReceiver.stop();
    }
    
    /**
     * {@inheritDoc}
     */
    public synchronized BluetoothState getState()
    {
    	return mReceiver.getState();
    }
    
    /**
     * Simple object to handle sending the current drive command
     * to the connected Bluetooth device at regular intervals
     * 
     * @author David Widen
     * @author Jessie Young
     * @author Thomas Cheng
     *
     */
    private class CarTimer extends Thread
    {	
    	private long previousTime;
    	private long currentTime;
    	private final int INCREMENT = 50;
    	
    	/**
    	 * Default constructor, initializes the current time and
    	 * the increment which the thread re-sends the current
    	 * drive Command
    	 */
    	public CarTimer()
    	{
    		super();
    		previousTime = System.currentTimeMillis();
    	}
    	
    	/**
         * {@inheritDoc}
         */
    	public void run()
    	{
    		if (MainActivity.DEBUG)
    		{
    			Log.d("CarTimer", "CarTimer.run()");
    		}
    		
    		while (isRunning)
    		{	
    			currentTime = System.currentTimeMillis();
    			
    			if (currentTime - previousTime > INCREMENT)
    			{	
    				if (mReceiver.getState() == BluetoothState.STATE_CONNECTED)
    				{	
    					driveCommand(currentDriveCommand);
    					previousTime = currentTime;
    				}
    			}
    			
    			try
    			{
    				synchronized (this)
    				{
    					wait(INCREMENT);
    				}
    			}
    			catch (InterruptedException e)
    			{
    				if (MainActivity.DEBUG)
    				{
    					Log.d("CarTimer", "" + e);
    				}
    				
    				break;
    			}
    		}
    		
    		if (MainActivity.DEBUG)
    		{
    			Log.d("CarTimer", "CarTimer stopped running)");
    		}
    	}
    }
    
}
