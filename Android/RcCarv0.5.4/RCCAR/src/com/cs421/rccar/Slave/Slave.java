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
import com.cs421.rccar.Util.BluetoothCommunicationService;
import com.cs421.rccar.Util.BluetoothReceiver;
import com.cs421.rccar.Util.BluetoothState;
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
public class Slave implements SlaveController
{
	private Driveable mDriveable;
	private BluetoothReceiver mReceiver;	
	protected long lastMessageReceived;
	private IntervalTimer mTimer;
	protected Command currentState;
	
    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_WRITE = 3;
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
	}
	
	/**
	 * 
	 */
	public void driveCommand(Command mCommand)
	{
		currentState = mCommand;
		lastMessageReceived = System.currentTimeMillis();
		mDriveable.driveCommand(mCommand);
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
    	mTimer = new IntervalTimer();
    	mTimer.start();
    	
    	startConnection();
	}
	
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
		mTimer = null;
		return disconnectFromHost();
	}
	
	public void newMessageReceived()
	{
		lastMessageReceived = System.currentTimeMillis();
	}
	
	public long getLastTimeReceived()
	{
		return lastMessageReceived;
	}
	
    //Message handler to receive messages from the BluetoothReceiver object
    private final Handler mHandler = new Handler() 
    {
        @Override
        public void handleMessage(Message msg) 
        {
        	Log.d("SLAVE", "handleMessage");
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
	                //String readMessage = new String(readBuf, 0, msg.arg1);
	               
	                Log.d("SLAVE", "message received and tried to set!");
	                
	                mDriveable.driveCommand(Command.commandInflater(readBuf[0]));
	                SlaveActivity.setText("Message received at: " + System.currentTimeMillis() + " with directive: " + Command.commandInflater(readBuf[0]).toString());
	                
	                break;
	            case MESSAGE_DEVICE_NAME: break;
            }
        }
    };
	
	private class IntervalTimer extends Thread
	{
		private final int INTERVAL = 100;
		
		public IntervalTimer()
		{
			
		}
		
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
					if (currentState != Command.STOP)
					{
						currentState = Command.STOP;
						mDriveable.driveCommand(currentState);
					}
				}
			}
		}
	}

	public void sendPicture(byte[] data)
	{
		// TODO Auto-generated method stub
	}

	public void sendVideo(byte[] data)
	{
		// TODO Auto-generated method stub
	}

	public BluetoothState getState()
	{
		// TODO Auto-generated method stub
		return mDriveable.getState();
	}
}