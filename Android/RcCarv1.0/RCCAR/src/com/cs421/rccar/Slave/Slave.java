package com.cs421.rccar.Slave;

import java.util.Set;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.cs421.rccar.Controller.Driveable;
import com.cs421.rccar.Controller.SlaveController;
import com.cs421.rccar.UI.MainActivity;
import com.cs421.rccar.UI.SlaveActivity;
import com.cs421.rccar.Util.Command;
import com.cs421.rccar.Util.Bluetooth.BluetoothCommunicationService;
import com.cs421.rccar.Util.Bluetooth.BluetoothReceiver;
import com.cs421.rccar.Util.Bluetooth.BluetoothState;

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
public class Slave implements SlaveController
{
	private Driveable mDriveable;
	private BluetoothReceiver mReceiver;	
	protected long lastMessageReceived;
	private IntervalTimer mTimer;
	protected Command currentState;
	
	/**
	 * The state of the connection has changed
	 */
    public static final int MESSAGE_STATE_CHANGE = 1;
    
    /**
     * The connection has read data
     */
    public static final int MESSAGE_READ = 2;
    
    /**
     * The connection has written data
     */
    public static final int MESSAGE_WRITE = 3;
    
    /**
     * The connection has received a device name
     */
    public static final int MESSAGE_DEVICE_NAME = 4;
	
	/**
	 * Default constructor for the Slave class
	 * @param h the message handler
	 */
	public Slave()
	{
		mReceiver = new BluetoothReceiver(mHandler);
		mDriveable = new UsbCar();
		mTimer = null;
		currentState = Command.STOP;
	}
	
	/**
	 * Sends a drive Command to the Arduino through the USB
	 * @param mCommand the current drive Command
	 */
	public synchronized void driveCommand(Command mCommand)
	{
		currentState = mCommand;
		lastMessageReceived = System.currentTimeMillis();
		mDriveable.driveCommand(mCommand);
	}
		
	/**
	 * Disconnects this Android device from the Master Android
	 * @return true if successfully disconnected, false otherwise
	 */
	public boolean disconnectFromHost()
	{
		return mReceiver.stop();
	}
	
	/**
	 * Sends a car command to the Arduino
	 * @param mCommand the car command to send
	 */
	public void sendCarCommand(Command mCommand)
	{
		driveCommand(mCommand);
	}
	
	/**
     * {@inheritDoc}
     */
	public void start()
	{
    	mReceiver.start();
    	mTimer = new IntervalTimer();
    	mTimer.start();
    	
    	startConnection();
	}
	
	/**
	 * Start the Bluetooth connection
	 */
	public void startConnection()
	{
    	
    	BluetoothAdapter mAdapter = BluetoothAdapter.getDefaultAdapter();
    	
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
		mTimer = null;
		return disconnectFromHost();
	}
	
	/**
	 * Update the message time memory
	 */
	public void newMessageReceived()
	{
		lastMessageReceived = System.currentTimeMillis();
	}
	
	/**
	 * Retrieve the last time a message was receivedS
	 * @return the time the last message was received
	 */
	public long getLastTimeReceived()
	{
		return lastMessageReceived;
	}
	
    //Message handler to receive messages from the BluetoothReceiver object
    private final Handler mHandler = new Handler() 
    {
    	/**
         * {@inheritDoc}
         */
        public void handleMessage(Message msg) 
        {
        	if (MainActivity.DEBUG)
        	{	
        		Log.d("SLAVE", "handleMessage");
        	}
        	
	        switch (msg.what) 
	        {
	            case MESSAGE_STATE_CHANGE:
	                switch (msg.arg1) 
	                {
	                	case BluetoothCommunicationService.STATE_CONNECTED: break;
	                	case BluetoothCommunicationService.STATE_CONNECTING: break;
	                	case BluetoothCommunicationService.STATE_LISTEN: break;
	                	case BluetoothCommunicationService.STATE_NONE: break;
	                }
	                break;
	            case MESSAGE_WRITE: break;
	            case MESSAGE_READ:
	                byte[] readBuf = (byte[]) msg.obj;
	                
	                if (MainActivity.DEBUG)
	                {	
	                	Log.d("SLAVE", "message received and tried to set!");
	                }
	                
	                driveCommand(Command.commandInflater(readBuf[0]));
	                SlaveActivity.setText("Message received at: " + System.currentTimeMillis() + " with directive: " + Command.commandInflater(readBuf[0]).toString());
	               
	                break;
	            case MESSAGE_DEVICE_NAME: break;
            }
        }
    };
	
    /**
     * Guarantees safe driving functionality by checking
     * if commands have been received from the Master device
     * during a given interval and stopping the car if 
     * required
     * 
     * @author David Widen
     * @author Jessie Young
     * @author Thomas Cheng
     *
     */
	private class IntervalTimer extends Thread
	{
		private final int INTERVAL = 100;
		
		public IntervalTimer()
		{
			
		}
		
		/**
	     * {@inheritDoc}
	     */
		public void run()
		{
			if (MainActivity.DEBUG)
			{
				Log.d("Slave", "run()");
			}
			
			while (true)
			{
				if (System.currentTimeMillis() - getLastTimeReceived() > INTERVAL)
				{
					currentState = Command.STOP;
					driveCommand(currentState);
					
					try
					{
						synchronized (this)
						{
							wait(INTERVAL);
						}
					}
					catch (InterruptedException e)
					{
						if (MainActivity.DEBUG)
						{
							Log.d("IntervalTimer", "" + e);
						}
						
						break;
					}
				}
			}
		}
	}

	/**
     * {@inheritDoc}
     */
	public void sendPicture(byte[] data)
	{
		// TODO Auto-generated method stub
	}

	/**
     * {@inheritDoc}
     */
	public void sendVideo(byte[] data)
	{
		// TODO Auto-generated method stub
	}

	/**
     * {@inheritDoc}
     */
	public BluetoothState getState()
	{
		// TODO Auto-generated method stub
		return mDriveable.getState();
	}
}