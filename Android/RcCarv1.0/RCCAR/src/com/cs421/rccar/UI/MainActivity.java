package com.cs421.rccar.UI;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

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
	private final int NEW_MASTER_BUTTON_ACTIVITY = 1;
	private final int NEW_SLAVE_ACTIVITY = 2;
	private final int NEW_MASTER_ACCEL_ACTIVITY = 3;
	
	/**
	 * Debug variable, set to TRUE for debug mode, FALSE otherwise
	 */
	public static final boolean DEBUG = true;
	
    /**
     * Method to create the UI
     * @param savedInstanceState the saved instance state from freezing the software
     */
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        //Initialize Screen Buttons and Text Views
        Button launchMaster = (Button) findViewById(R.id.launchMaster);
        Button launchSlave = (Button) findViewById(R.id.LaunchSlave);
        Button launchAccel = (Button) findViewById(R.id.send);
        
        //Set button onClick functionality
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
        
        
        launchAccel.setOnClickListener(new OnClickListener()
        {
        	public void onClick(View v)
        	{
        		launchAccel();
        	}
        });
    }

    /**
     * {@inheritDoc}
     */
    public void onStart()
    {
    	super.onStart();
    	ensureDiscoverable();	//Make this device discoverable
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
     * Launches the accelerometer application
     */
    public void launchAccel()
    {
       	Intent mIntent = new Intent(MainActivity.this, MasterAccelerometerActivity.class);
    	startActivityForResult(mIntent, NEW_MASTER_ACCEL_ACTIVITY);
    }
    
    /**
     * Ensures that this device is discoverable via Bluetooth Connection
     */
    private void ensureDiscoverable() 
    {
    	BluetoothAdapter mAdapter = BluetoothAdapter.getDefaultAdapter();	//Request system Bluetooth Adapter
    	
        if (mAdapter.getScanMode() != BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) 	//If it isn't discoverable
        {
            Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
            startActivity(discoverableIntent);	//make it discoverable
        }
    }
    
    /**
     * {@inheritDoc}
     */
    public void onPause()
    {
    	super.onPause();
    }
    
    /**
     * {@inheritDoc}
     */
    public void onStop()
    {
    	super.onStop();
    }
    
    /**
     * {@inheritDoc}
     */
    public void onDestroy()
    {
        super.onDestroy();  
    }
}