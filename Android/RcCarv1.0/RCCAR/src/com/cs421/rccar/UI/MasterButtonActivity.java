package com.cs421.rccar.UI;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.cs421.rccar.R;
import com.cs421.rccar.Controller.MasterController;
import com.cs421.rccar.Master.Master;
import com.cs421.rccar.Util.Command;

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
    
	private MasterController mMasterController;
	
    /**
     * Method to create the UI
     * @param savedInstanceState the saved instance state from freezing the software
     */
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buttonview);
        
        //Initialize all buttons
        Button forwardButton = (Button) findViewById(R.id.forward);
        Button backwardButton = (Button) findViewById(R.id.backward);
        Button leftButton = (Button) findViewById(R.id.left);
        Button rightButton = (Button) findViewById(R.id.right);
        Button stopButton = (Button) findViewById(R.id.stop);
        
        Button forwardLeftButton = (Button) findViewById(R.id.forwardLeft);
        Button backwardRightButton = (Button) findViewById(R.id.backwardRight);
        Button forwardRightButton = (Button) findViewById(R.id.forwardRight);
        Button backwardLeftButton = (Button) findViewById(R.id.backwardLeft);
        
        //Set onClick functionality for each button
        forwardButton.setOnClickListener(new OnClickListener()
		{
			
			public void onClick(View v)
			{
				mMasterController.sendCarCommand(Command.FORWARD);
			}
		});
        
        backwardButton.setOnClickListener(new OnClickListener()
		{
			
			public void onClick(View v)
			{
				mMasterController.sendCarCommand(Command.BACKWARD);
			}
		});
        
        leftButton.setOnClickListener(new OnClickListener()
		{
			
			public void onClick(View v)
			{
				mMasterController.sendCarCommand(Command.LEFT);
			}
		});
        
        rightButton.setOnClickListener(new OnClickListener()
		{
			
			public void onClick(View v)
			{
				mMasterController.sendCarCommand(Command.RIGHT);
			}
		});
        
        forwardLeftButton.setOnClickListener(new OnClickListener()
		{
			
			public void onClick(View v)
			{
				mMasterController.sendCarCommand(Command.FORWARDLEFT);
			}
		});
        
        backwardRightButton.setOnClickListener(new OnClickListener()
		{
			
			public void onClick(View v)
			{
				mMasterController.sendCarCommand(Command.BACKWARDRIGHT);
			}
		});
        
        forwardRightButton.setOnClickListener(new OnClickListener()
		{
			
			public void onClick(View v)
			{
				mMasterController.sendCarCommand(Command.FORWARDRIGHT);
			}
		});
        
        backwardLeftButton.setOnClickListener(new OnClickListener()
		{
			
			public void onClick(View v)
			{
				mMasterController.sendCarCommand(Command.BACKWARDLEFT);
			}
		});
        
        stopButton.setOnClickListener(new OnClickListener()
		{
			
			public void onClick(View v)
			{
				mMasterController.sendCarCommand(Command.STOP);
			}
		});
        
        //Make a new Master object, and start the communication
        mMasterController = new Master();
    }
    
    
    /**
     * Inherited method to run when the Application resumes from a paused or stopped state
     */
    public void onResume()
    {
    	super.onResume();
    }
    
    /**
     * Inherited method to run when the Application starts running
     * Enables Bluetooth if it is not already enabled
     */
    public void onStart()
    {
    	super.onStart();
    	mMasterController.start();
    }
    
    /**
     * Inherited method to run when the activity pauses
     */
    public void onPause()
    {
    	super.onPause();
    	mMasterController.disconnectFromSlave();
    }
    
    /**
     * Inherited method to run when the activity stops
     */
    public void onStop()
    {
    	super.onStop();
    	mMasterController.disconnectFromSlave();
    }
    
    /**
     * Inherited method to run when the activity is destroyed
     */
    public void onDestroy()
    {
        super.onDestroy();  
        mMasterController.disconnectFromSlave();
    }
    
    /**
     * Switch the screen so it uses the accelerometer to control the RC car
     */
    public void toggleMode()
    {
    	//TODO
    }
}