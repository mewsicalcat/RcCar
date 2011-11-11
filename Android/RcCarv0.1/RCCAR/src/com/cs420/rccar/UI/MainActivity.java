package com.cs420.rccar.UI;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cs420.rccar.R;
import com.cs420.rccar.Master.Master;

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
	
    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_WRITE = 3;
    public static final int MESSAGE_DEVICE_NAME = 4;
	
	private static final int NEW_MASTER_BUTTON_ACTIVITY = 1;
	private static final int NEW_SLAVE_ACTIVITY = 2;
	private static final int REQUEST_ENABLE_BT = 3;
	public static TextView t;
	public static String all;
	
	private BluetoothAdapter mAdapter;
	
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
        
        mAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mAdapter == null)
        {
            Toast.makeText(this, "Bluetooth is not available!", Toast.LENGTH_LONG).show();
            finish();
            return;
        }
        
        Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
        startActivity(discoverableIntent);
        
        Master m = new Master();
        t = (TextView) findViewById(R.id.textbox);
        m.setText(t);
        m.init();
        
        
        //t = (TextView) findViewById(R.id.textbox);
        
        
        /* Make a BluetoothAdapter */
		/*
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		if (mBluetoothAdapter != null) {
		    t.setText("SUCCESS");
		}
		*/
		/* See if Bluetooth is enabled */
		/*
        if (!mBluetoothAdapter.isEnabled()) {
		    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
		    startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
		    t.setText("SUCCESS + ENABLED BLUETOOTH");
		}
		else
		{
		    t.setText("SUCCESS + BLUETOOTH ALREADY ENABLED");
		}
		*/
		
        /*
		Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
		// If there are paired devices
		if (pairedDevices.size() > 0) 
		{
		    // Loop through paired devices
		    for (BluetoothDevice device : pairedDevices) 
		    {
		    	t.setText(device.getName() + ": " + device.getAddress());
		    }
		}
		*/
		
		/*                            
		// Create a BroadcastReceiver for ACTION_FOUND
		final BroadcastReceiver mReceiver = new BroadcastReceiver() {
		    public void onReceive(Context context, Intent intent) {
		        String action = intent.getAction();
		        // When discovery finds a device
		        if (BluetoothDevice.ACTION_FOUND.equals(action)) {
		            // Get the BluetoothDevice object from the Intent
		            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
		            // Add the name and address to an array adapter to show in a ListView
		            all += device.getName() + ": " + device.getAddress() + "\n";
		            t.setText(all);
		        }
		    }
		};
		// Register the BroadcastReceiver
		IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
		registerReceiver(mReceiver, filter); // Don't forget to unregister during onDestroy
		
		                             
		mBluetoothAdapter.startDiscovery();
		all = "";
		*/
    }
    
    
    public void onStart()
    {
    	super.onStart();
    	
        Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
        startActivity(discoverableIntent);
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
}