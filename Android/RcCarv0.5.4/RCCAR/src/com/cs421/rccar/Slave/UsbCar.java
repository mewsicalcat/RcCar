package com.cs421.rccar.Slave;

import java.io.IOException;

import org.microbridge.server.Server;

import android.util.Log;

import com.cs421.rccar.Controller.Driveable;
import com.cs421.rccar.UI.MainActivity;
import com.cs421.rccar.Util.BluetoothState;
import com.cs421.rccar.Util.Command;


/**
 * This class acts as a high-level representation for an
 * RC Car.  It receives drive Commands from Slave.java and
 * then converts this to serial data, which it then sends
 * through UsbDevice mUsbDevice.
 * 
 * @author David Widen
 * @author Jessie Young
 * @author Thomas Cheng
 *
 */
public class UsbCar implements Driveable 
{	
	private Server server;
	
	/**
	 * Default constructor for the USBCar class
	 * @param mDevice the USB Device to send commands to
	 */
	public UsbCar()
	{
		server = null;
		try
		{
			if (MainActivity.DEBUG)
			{	
				Log.e("ServoControl", "going to request a new Server (blocking call)");
				Log.e("ServoControl", "onCreate has begun");
			}
			
			server = new Server(1337);
			
			if (MainActivity.DEBUG)
			{
				Log.e("Server", server + ""); 
				Log.e("ServoControl", "acquired server, starting thread");
			}
			
			server.start();
			
			if (MainActivity.DEBUG)
			{	
				Log.e("ServoControl", "thread started");
			}

		} 
		catch (IOException e)
		{
			if (MainActivity.DEBUG)
			{
				Log.e("microbridge", "Unable to start TCP server", e);
			}
			
			System.exit(-1);
		}
	}
	
	/**
	 * Method to send a drive Command to some receiver
	 * @param mCommand the drive command to send
	 */
	public void driveCommand(Command mCommand) 
	{
		byte[] data = new byte[1];
		data[0] = mCommand.getBytes();
		
    	try
		{
    		server.send(data);
		} 
    	catch (IOException e)
		{
    		if (MainActivity.DEBUG)
    		{	
    			Log.e("microbridge", "problem sending TCP message", e);
    		}
		}		
	}
		
	/**
	 * Method to perform all required initializations to start the 
	 * communication service for the car representation
	 */
	public void start()
	{
		
	}
	
	/**
	 * Stops the UsbCar serial connection
	 */
	public boolean stop()
	{
		/* MUST TEST!! */
		server.stop();
		return true;
	}

	/**
	 * @Inherit
	 */
	public BluetoothState getState()
	{
		return null;
	}
}