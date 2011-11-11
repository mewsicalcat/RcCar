package com.cs420.rccar.UI;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.cs420.rccar.R;
import com.cs420.rccar.Slave.Slave;

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
        
        // Enable discoverability
        Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
        startActivity(discoverableIntent);
        
        TextView t = (TextView) findViewById(R.id.slavetest);
        
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        Slave s = new Slave();
        s.setText(t);
        s.setAdapter(mBluetoothAdapter);
        s.init();
    }
}
