package com.cs421.rccar.UI;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.cs421.rccar.R;
import com.cs421.rccar.Controller.SlaveController;
import com.cs421.rccar.Slave.Slave;

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
	private SlaveController mSlave;
       
	private static TextView t;


    /**
     * Method to create the UI
     * @param savedInstanceState the saved instance state from freezing the software
     */
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.slaveview);

        t = (TextView) findViewById(R.id.slavetest);
        mSlave = new Slave();
    }
    
    
    /**
     * Inherited method from Activity.  Ensures that this Android phone is discoverable via Bluetooth
     * and starts the Slave class.  Finally connects the slave to a device
     */
    public void onStart()
    {
    	super.onStart();
    	//ensureDiscoverable();
    	mSlave.start();
    }
    
    /**
     * Inherited method to run when the Activity resumes from being paused or stopped
     */
    public void onResume()
    {
    	super.onResume();
    }
    
    /**
     * Inherited method to run when the activity pauses
     */
    public void onPause()
    {
    	super.onPause();
    	//mSlave.stop();
    }
    
    /**
     * Inherited method to run when the activity stops
     */
    public void onStop()
    {
    	super.onStop();
    	mSlave.stop();
    }
    
    /**
     * Inherited method to run when the activity is destroyed
     * Stops the communication service
     */
    public void onDestroy()
    {
        super.onDestroy();
        mSlave.stop();  
    }
           
    public static void setText(String text)
    {
    	t.setText(text);
    }
}