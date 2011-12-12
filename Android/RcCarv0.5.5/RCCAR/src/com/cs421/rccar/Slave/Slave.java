package com.cs421.rccar.Slave;

import android.bluetooth.BluetoothDevice;
import android.os.Handler;
import android.util.Log;

import com.cs421.rccar.Controller.Driveable;
import com.cs421.rccar.UI.MainActivity;
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
	protected long lastMessageReceived;
	private IntervalTimer mTimer;
	protected Command currentState;
	
	/**
	 * Default constructor for the Slave class
	 * @param h the message handler
	 */
	public Slave(Handler h)
	{
		mReceiver = new BluetoothReceiver(h);
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
    	mTimer = new IntervalTimer();
    	mTimer.start();
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
}