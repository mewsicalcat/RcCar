package com.cs420.rccar.UI;

import com.cs420.rccar.R;
import com.cs420.rccar.Slave.Slave;

import android.app.Activity;
import android.os.Bundle;

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

    /**
     * Method to create the UI
     * @param savedInstanceState the saved instance state from freezing the software
     */
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.slaveview);
    }
}
