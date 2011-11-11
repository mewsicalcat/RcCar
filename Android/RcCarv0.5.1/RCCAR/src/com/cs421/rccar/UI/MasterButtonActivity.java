package com.cs421.rccar.UI;

import com.cs420.rccar.R;
import com.cs421.rccar.Master.Master;

import android.app.Activity;
import android.os.Bundle;

/**
 * This class handles inupt from the user via
 * control buttons on the screen.  The activity
 * passes control commands from the user to the 
 * Master.java class.  This activity can change
 * the UI to MasterAccelerometerActivity.
 * 
 * @author David Widen
 * @author Jessie Young
 * @author Thomas Cheng
 *
 */
public class MasterButtonActivity extends Activity {
    
	private Master mMaster;
	
    /**
     * Method to create the UI
     * @param savedInstanceState the saved instance state from freezing the software
     */
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buttonview);
    }
    
    /**
     * Switch the screen so it uses the accelerometer to control the RC car
     */
    public void toggleMode()
    {
    	
    }
}
