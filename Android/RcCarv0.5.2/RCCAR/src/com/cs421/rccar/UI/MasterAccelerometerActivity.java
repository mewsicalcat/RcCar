package com.cs421.rccar.UI;

import com.cs421.rccar.R;
import com.cs421.rccar.Master.Master;

import android.app.Activity;
import android.os.Bundle;

/**
 * This class handles input from the user via the 
 * accelerometer.  This is part of our secondary features.
 * The activity handles input from the accelerometer and passes
 * this input to the Master activity via event listeners.  This
 * screen can be switched to the MasterButtonActivity.
 * 
 * @author David Widen
 * @author Jessie Young
 * @author Thomas Cheng
 *
 */
public class MasterAccelerometerActivity extends Activity {
    
	private Master mMaster;
	
    /**
     * Method to create the UI
     * @param savedInstanceState the saved instance state from freezing the software
     */
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
    
    /**
     * Switch the screen so it uses buttons to control the RC car
     */
    public void toggleMode()
    {
    	
    }
}
