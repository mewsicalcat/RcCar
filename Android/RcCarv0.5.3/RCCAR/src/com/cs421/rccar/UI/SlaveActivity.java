package com.cs421.rccar.UI;

import java.util.Set;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

import com.cs421.rccar.R;
import com.cs421.rccar.Slave.Slave;
import com.cs421.rccar.Util.BluetoothCommunicationService;

/**
 * This class receives input from the BluetoothCar.java
 * class and passes that input to mSlave.  This class
 * also handles the Bluetooth connection between this device
 * and the other Android device which is sending data.
 * 
 * @author David Widen
 * @author Jessie Young
 * @author Thomas Cheng
 *
 */
public class SlaveActivity extends Activity 
{
	private Slave mSlave;
    // Message types sent from the BluetoothChatService Handler
    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_WRITE = 3;
    public static final int MESSAGE_DEVICE_NAME = 4;
    public static final int MESSAGE_TOAST = 5;
    
	private static final int REQUEST_ENABLE_BT = 3;
    
	private BluetoothCommunicationService mCommService;
	private StringBuffer mOutputStringBuffer;
	private static TextView t;
	private static int counter = 0;


    /**
     * Method to create the UI
     * @param savedInstanceState the saved instance state from freezing the software
     */
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.slaveview);

        t = (TextView) findViewById(R.id.slavetest);
        
        mSlave = new Slave(mHandler);
    }
    
    /**
     * Ensures that this device is discoverable via Bluetooth Connection
     */
    private void ensureDiscoverable() 
    {
    	BluetoothAdapter mAdapter = BluetoothAdapter.getDefaultAdapter();
    	
        if (mAdapter.getScanMode() != BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) 
        {
            Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
            startActivity(discoverableIntent);
        }
    }
    
    public void onStart()
    {
    	super.onStart();
    	ensureDiscoverable();
    	mSlave.start();
    	
    	BluetoothAdapter mAdapter = BluetoothAdapter.getDefaultAdapter();
    	
    	Set<BluetoothDevice> pairedDevices = mAdapter.getBondedDevices();	//get paired Android phones
    	if (pairedDevices.size() > 0)										//if there are any paired phones
    	{
    		for (BluetoothDevice device : pairedDevices)
    		{
    			mSlave.connect(device);								//connect to that phone
    		}
    	}
    }
    
    /**
     * Inherited method to run when the activity pauses
     */
    public void onPause()
    {
    	super.onPause();
    }
    
    /**
     * Inherited method to run when the activity stops
     */
    public void onStop()
    {
    	super.onStop();
    }
    
    /**
     * Inherited method to run when the activity is destroyed
     * Stops the communication service
     */
    public void onDestroy()
    {
        super.onDestroy();

        if (mSlave != null) 
        	mSlave.stop();   
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
	            case MESSAGE_WRITE:
	                byte[] writeBuf = (byte[]) msg.obj;
	                String writeMessage = new String(writeBuf);
	                break;
	            case MESSAGE_READ:
	                byte[] readBuf = (byte[]) msg.obj;
	                //String readMessage = new String(readBuf, 0, msg.arg1);
	                t.setText("Hello, world: " + counter++);
	                Log.d("SLAVE", "message received and tried to set!");
	                break;
	            case MESSAGE_DEVICE_NAME: break;
            }
        }
    };
}