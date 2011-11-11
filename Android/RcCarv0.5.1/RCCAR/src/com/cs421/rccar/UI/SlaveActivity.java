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

import com.cs420.rccar.R;
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
    
	private BluetoothAdapter mAdapter;
	private BluetoothCommunicationService mCommService;
	private StringBuffer mOutputStringBuffer;
	private static TextView t;

    /**
     * Method to create the UI
     * @param savedInstanceState the saved instance state from freezing the software
     */
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.slaveview);
        
        // Enable discoverability
        Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
        startActivity(discoverableIntent);
        
        mAdapter = BluetoothAdapter.getDefaultAdapter();
        t = (TextView) findViewById(R.id.slavetest);

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
    
    /**
     * Inherited method to run when the Application starts running
     * Enables Bluetooth if it is not already enabled
     */
    public void onStart()
    {
    	super.onStart();
    	
        if (!mAdapter.isEnabled()) 
        {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
            setupCommService();
        } 
        else 
        {
            if (mCommService == null) 
            	setupCommService();
        }
    }
    
    /**
     * Setsup the BluetoothCommunicationService 
     */
    public void setupCommService()
    {
    	mCommService = new BluetoothCommunicationService(this, mHandler);

        // Initialize the buffer for outgoing messages
        mOutputStringBuffer = new StringBuffer("");
        connectDevice();
    }
    
    /**
     * Ensures that this device is discoverable via Bluetooth Connection
     */
    private void ensureDiscoverable() 
    {
        if (mAdapter.getScanMode() != BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) 
        {
            Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
            startActivity(discoverableIntent);
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

        if (mCommService != null) 
        	mCommService.stop();   
    }
    
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
                case BluetoothCommunicationService.STATE_CONNECTED:
                    //setStatus(getString(R.string.title_connected_to, mConnectedDeviceName));
                    //mConversationArrayAdapter.clear();
                    break;
                case BluetoothCommunicationService.STATE_CONNECTING:
                    //setStatus(R.string.title_connecting);
                    break;
                case BluetoothCommunicationService.STATE_LISTEN:
                case BluetoothCommunicationService.STATE_NONE:
                    //setStatus(R.string.title_not_connected);
                    break;
                }
                break;
            case MESSAGE_WRITE:
                byte[] writeBuf = (byte[]) msg.obj;
                // construct a string from the buffer
                String writeMessage = new String(writeBuf);
                //mConversationArrayAdapter.add("Me:  " + writeMessage);
                break;
            case MESSAGE_READ:
                byte[] readBuf = (byte[]) msg.obj;
                // construct a string from the valid bytes in the buffer
                String readMessage = new String(readBuf, 0, msg.arg1);
                t.setText(readMessage);
                Log.d("SLAVE", "message received and tried to set!");
                //mConversationArrayAdapter.add(mConnectedDeviceName+":  " + readMessage);
                break;
            case MESSAGE_DEVICE_NAME:
                // save the connected device's name
                //mConnectedDeviceName = msg.getData().getString(DEVICE_NAME);
                //Toast.makeText(getApplicationContext(), "Connected to "
                               //+ mConnectedDeviceName, Toast.LENGTH_SHORT).show();
                break;
            }
        }
    };
}
