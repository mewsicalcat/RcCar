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
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cs420.rccar.R;
import com.cs421.rccar.Util.BluetoothCommunicationService;
import com.cs421.rccar.Util.BluetoothState;

/**
 * This class displays to the user the GUI which allows
 * them to choose to run either the master or slave 
 * version of the app on the device.  
 * 
 * @author David Widen
 * @author Jessie Young
 * @author Thomas Cheng
 *
 */
public class MainActivity extends Activity 
{
	
    private static final int MESSAGE_STATE_CHANGE = 1;
    private static final int MESSAGE_READ = 2;
    private static final int MESSAGE_WRITE = 3;
    private static final int MESSAGE_DEVICE_NAME = 4;
	
	private static final int NEW_MASTER_BUTTON_ACTIVITY = 1;
	private static final int NEW_SLAVE_ACTIVITY = 2;
	private static final int REQUEST_ENABLE_BT = 3;
	private static TextView t;
	
	private BluetoothAdapter mAdapter;
	private BluetoothCommunicationService mCommService;
	private StringBuffer mOutputStringBuffer;
	
    /**
     * Method to create the UI
     * @param savedInstanceState the saved instance state from freezing the software
     */
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Button launchMaster = (Button) findViewById(R.id.launchMaster);
        Button launchSlave = (Button) findViewById(R.id.LaunchSlave);
        Button sendMessage = (Button) findViewById(R.id.send);
        t = (TextView) findViewById(R.id.textbox);
        
        Log.d("ARG!", "Value of sendMessage: " + sendMessage);
        
        launchMaster.setOnClickListener(new OnClickListener() 
        {
            public void onClick(View v) 
            {
               	launchMaster();
            }
        }); 
        
        launchSlave.setOnClickListener(new OnClickListener()
        {
        	public void onClick(View v)
        	{
        		launchSlave();
        	}
        });
        
        sendMessage.setOnClickListener(new OnClickListener()
        {
        	public void onClick(View v)
        	{
        		sendMessage("Hello, world");
        	}
        });
				
        
        mAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mAdapter == null)
        {
            Toast.makeText(this, "Bluetooth is not available!", Toast.LENGTH_LONG).show();
            finish();
            return;
        }
        
        /*Master m = new Master();
        t = (TextView) findViewById(R.id.textbox);
        m.setText(t);
        m.init();
        */
    }
    
    /**
     * Inherited method to run when the Application starts running
     * Enables Bluetooth if it is not already enabled
     */
    public void onStart()
    {
    	super.onStart();
    	
    	ensureDiscoverable();
    	
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
     * Launches the master application
     */
    public void launchMaster()
    {
       	Intent mIntent = new Intent(MainActivity.this, MasterButtonActivity.class);
    	startActivityForResult(mIntent, NEW_MASTER_BUTTON_ACTIVITY);
    }
    
    /**
     * Launches the slave application
     */
    public void launchSlave()
    {
    	Intent mIntent = new Intent(MainActivity.this, SlaveActivity.class);
    	startActivityForResult(mIntent, NEW_SLAVE_ACTIVITY);
    }
    
    private final Handler mHandler = new Handler() 
    {
        @Override
        public void handleMessage(Message msg) 
        {
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
     * Sends a message to the BluetoothDevice which this current device is connected to
     * via the BluetoothCommunicationService
     * @param message the message to send
     */
    private void sendMessage(String message)
    {
    	
    	Log.d("ARG", "start sendMessage");
        // Check that we're actually connected before trying anything
        if (mCommService.getState() != BluetoothState.STATE_CONNECTED) 
        {
            //Toast.makeText(this, R.string.not_connected, Toast.LENGTH_SHORT).show();
            return;
        }

        // Check that there's actually something to send
        if (message.length() > 0) 
        {
            // Get the message bytes and tell the BluetoothChatService to write
            byte[] send = message.getBytes();
            mCommService.write(send);

            Log.d("ARG", "wrote message: " + message);
            
            // Reset out string buffer to zero and clear the edit text field
            mOutputStringBuffer.setLength(0);
            t.setText(message);
        }
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
}