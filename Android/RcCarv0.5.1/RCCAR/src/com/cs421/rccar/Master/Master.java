package com.cs421.rccar.Master;

import java.io.IOException;
import java.util.Set;
import java.util.UUID;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.widget.TextView;

import com.cs421.rccar.Controller.Driveable;
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
	private static BluetoothSocket mBluetoothSocket;
	private Driveable mDriveable;
	private BluetoothDevice mBluetoothDevice;
	private BluetoothAdapter mBluetoothAdapter;

	
	/* Extra data for Version 0.1 testing */
	public static TextView text;
	private static int REQUEST_ENABLE_BT = 1;
	private AcceptThread mAcceptThread;
	private static String NAME = "RcCar";
	private static UUID MY_UUID = UUID.fromString("437ee550-fde6-11e0-be50-0800200c9a66");
	public static boolean goAhead = true;
	
	/**
	 * Default constructor for the Master class
	 */
	public Master()
	{

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
    public boolean disconnectFromSlave(BluetoothDevice BTD)
    {
    	return true;
    }
    
    /**
     * Sends a car command to mDriveable
     * @param mCommand
     */
    public void sendCarCommand(Command mCommand)
    {
    	
    }
   
    
    /************************************************************************/
    /************************************************************************/
    /*	Extra Methods/Classes for version 0.1 testing */
    
    
    /**
     * Sets the BluetoothServerSocket used for communication
     * @param socket the socket
     */
    public static void setServerSocket(BluetoothSocket socket)
    {
    	if (socket != null)
    		mBluetoothSocket = socket;
    	else
    		throw new NullPointerException("Error: BluetoothSocket is null");
    }
    
    /**
     * Helper method to set a static text view
     * @param tv the textView to set
     */
    public void setText(TextView tv)
    {
    	text = tv;
    }
    
    /**
     * Helper method to initialize all data fields of the Master class
     */
    public void init()
    {
    	
		if (initAdapter())
		/*{
			enableAdapter();
			findPairedDevice();
		}
		else
		{
			throw new NullPointerException("ERROR!  BluetoothAdapter failed to load");
		}
		*/
		mAcceptThread = new AcceptThread();
		Master.goAhead = false;
		mAcceptThread.start();
		
    	/*while (true)
    	{
    		if (goAhead)
    			break;
    	}
    	*/
    	text.setText("SUCCESS!");
    }
    
    /**
     * Helper method called by init to initialize the BluetoothAdapter
     * @return true if successfully initialized, false otherwise
     */
    private boolean initAdapter()
    {
    	/*	Should go in constructor */
    	mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		if (mBluetoothAdapter == null) 
		{
		    text.setText("FAILURE");
		    return false;
		}
		else
		{
			text.setText("SUCCESS");
			return true;
		}
    }
    
    /**
     * Called to automatically enable Bluetooth for this Android device
     */
    private void enableAdapter()
    {
		if (!mBluetoothAdapter.isEnabled()) 
		{
		    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
		    (new Activity()).startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);	//something terrible could happen
		    text.setText("ACTIVATED BLUETOOTH");
		}
		else
		{
		    text.setText("BLUETOOTH ALREADY ENABLED");
		}
    }
    
    /**
     * Called to find any paired Android devices within Bluetooth range
     */
    private void findPairedDevice()
    {
    	Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
		// If there are paired devices
		if (pairedDevices.size() > 0) 
		{
		    // Loop through paired devices
		    for (BluetoothDevice device : pairedDevices) 
		    {
		    	text.setText(device.getName() + ": " + device.getAddress());
		    }
		}
    }
    
    //From developer website: http://developer.android.com/guide/topics/wireless/bluetooth.html
    /**
     * Class to encapsulate a thread which contains a BluetoothServerSocket
     */
    private class AcceptThread extends Thread 
    {
        private final BluetoothServerSocket mmServerSocket;
        int count = 0;
        
        public AcceptThread() 
        {
        	Master.text.setText("Constructed Accept Thread");
            // Use a temporary object that is later assigned to mmServerSocket,
            // because mmServerSocket is final
            BluetoothServerSocket tmp = null;
            try 
            {
                // MY_UUID is the app's UUID string, also used by the client code
                tmp = mBluetoothAdapter.listenUsingRfcommWithServiceRecord(NAME, MY_UUID);
            } 
            catch (IOException e) 
            { 
            	
            }
            mmServerSocket = tmp;
        }
     
        /**
         * Inherited method from the Thread class, called to run the thead object
         */
        public void run() {
            BluetoothSocket socket = null;
            // Keep listening until exception occurs or a socket is returned
            while (true) 
            {
            	//Master.text.setText("Run: " + count++);
                try 
                {
                    socket = mmServerSocket.accept();
                } 
                catch (IOException e) 
                {
                    break;
                }
                // If a connection was accepted
                if (socket != null) 
                {
                    // Do work to manage the connection (in a separate thread)
                    Master.setServerSocket(socket);
                    Master.goAhead = true;
                    this.stop();
                	//Master.text.setText("socket != null");
                    try
                    {
                    	mmServerSocket.close();
                    }
                    catch (IOException e)
                    {
                    	
                    }
                    break;
                }
                //Master.text.setText("Finished one check");
            }
        }
     
        /** 
         * Closes the current BluetoothSocket, and stops the thread 
         */
        public void cancel() 
        {
            try 
            {
                mmServerSocket.close();
            } 
            catch (IOException e) 
            { 
            	
            }
        }
    }
}
