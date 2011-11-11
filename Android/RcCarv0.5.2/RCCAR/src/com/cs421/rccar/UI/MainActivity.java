package com.cs421.rccar.UI;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.cs421.rccar.R;

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
	private static final int NEW_MASTER_BUTTON_ACTIVITY = 1;
	private static final int NEW_SLAVE_ACTIVITY = 2;
	
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
        TextView t = (TextView) findViewById(R.id.textbox);
        
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
        		//sendMessage("Hello, world");
        	}
        });       
        
        ensureDiscoverable();
    }
    
    /**
     * Inherited method to run when the Application starts running
     * Enables Bluetooth if it is not already enabled
     */
    public void onStart()
    {
    	super.onStart();
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
    }
}