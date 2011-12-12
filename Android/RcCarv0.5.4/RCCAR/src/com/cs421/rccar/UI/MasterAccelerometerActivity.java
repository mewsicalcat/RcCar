package com.cs421.rccar.UI;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Surface;
import android.view.WindowManager;
import android.widget.TextView;

import com.cs421.rccar.R;
import com.cs421.rccar.Master.Master;
import com.cs421.rccar.Util.Command;

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
	private SensorManager mSensorManager;
	private Sensor mAccelerometer;
	private Display mDisplay;
	private WindowManager mWindowManager;
	
	private TextView mTextView;
	
	private float mSensorX;
	private float mSensorY;
	private float mSensorZ;
	//private PowerManager mPowerManager;
	//private WakeLock mWakeLock;
	private AccelerometerListener mAccelerometerListener;
	
    /**
     * Method to create the UI
     * @param savedInstanceState the saved instance state from freezing the software
     */
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accelerometerview);
        
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        
        // Code just to learn more about the accelerometer library
        
        //mPowerManager = (PowerManager) getSystemService(POWER_SERVICE);
        
        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        mDisplay = mWindowManager.getDefaultDisplay();
        
        //mWakeLock = mPowerManager.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK, getClass().getName());
        
        mTextView = (TextView) findViewById(R.id.accel_status);
        
        mMaster = new Master();
    }
    
    /**
     * Switch the screen so it uses buttons to control the RC car
     */
    public void toggleMode()
    {
    	//TO DO
    }
    
    public class AccelerometerListener implements SensorEventListener
    {
    
    	public AccelerometerListener()
    	{
    		mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    		mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_UI);
    		
    		Log.d("MasterAccelerometerActivity", "constructor run");
    	}
    	
    	public void stop()
    	{
    		mSensorManager.unregisterListener(this);
    	}
    	
	    public void onSensorChanged(SensorEvent event)
	    {
	    	if (MainActivity.DEBUG)
	    	{
	    		Log.d("MasterAccelerometerActivity", "onSensorChanged");
	    	}
	    	
	    	if (event.sensor.getType() != Sensor.TYPE_ACCELEROMETER)
	    		return;
	    	
	    	if (System.currentTimeMillis() % 50 == 0)
	    		return; 
	    	
	    	String rotation = "";
	    	
	    	switch (mDisplay.getRotation())
	    	{
	    		case Surface.ROTATION_0:
	    			mSensorX = event.values[0];
	    			mSensorY = event.values[1];
	    			mSensorZ = event.values[2];
	    			rotation = "0";
	    			break;
	    		case Surface.ROTATION_90:
	    			mSensorX = -event.values[1];
	    			mSensorY = event.values[0];
	    			mSensorZ = event.values[2];
	    			rotation = "1";
	    			break;
	    		case Surface.ROTATION_180:
	    			mSensorX = -event.values[0];
	    			mSensorY = -event.values[1];
	    			mSensorZ = event.values[2];
	    			rotation = "2";
	    			break;
	    		case Surface.ROTATION_270:
	    			mSensorX = event.values[1];
	    			mSensorY = -event.values[0];
	    			mSensorZ = event.values[2];
	    			rotation = "3";
	    			break;
	    		default:
	    			rotation = "default";
	    	}
	    	
	    	String directive = "";
	    	
	    	int x = (int) event.values[1];
	    	int y = (int) event.values[0];
	    	int z = (int) event.values[2];
	    	
	    	/*
	    	if (y > 8 && (-x > -2 && -x < -2) && (z < 2 && z > -2))
	    		directive = "STOP";
	    	
	    	if (y > 0 && y < 8 && (-x > 0 && -x < 1) && (z > 5))
	    		directive = "FORWARD";
	    	*/
	    	
	    	Command mCommand = Command.NONE;
	    	
	    	if ((x > -3 && x < 3) && (y > 9 && y < 15) && (z > -3 && z < 3))
	    	{
	    		directive = "STOP";
	    		mCommand = Command.STOP;
	    	}
	    	
	    	if ((x > -3 && x < 3) && (y > 0 && y < 7) && (z > 5 && z < 15))
	    	{
	    		directive = "FORWARD";
	    		mCommand = Command.FORWARD;
	    	}
	    	
	    	if ((x > -3 && x < 3) && (y > 0 && y < 9) && (z > -15 && z < -3))
	    	{
	    		directive = "BACKWARD";
	    		mCommand = Command.BACKWARD;
	    	}

	    	if ((x > 7 && x < 15) && (y > 0 && y < 7) && (z > -3 && z < 3))
	    	{
	    		directive = "RIGHT";
	    		mCommand = Command.RIGHT;
	    	}

	    	if ((x > -15 && x < -7) && (y > 0 && y < 7) && (z > -3 && z < 3))
	    	{
	    		directive = "LEFT";
	    		mCommand = Command.LEFT;
	    	}

	    	if ((x > 3 && x < 15) && (y > 0 && y < 7) && (z > 5 && z < 15))
	    	{
	    		directive = "FORWARDRIGHT";
	    		mCommand = Command.FORWARDRIGHT;
	    	}

	    	if ((x > -15 && x < -3) && (y > 0 && y < 7) && (z > 5 && z < 15))
	    	{
	    		directive = "FORWARDLEFT";
	    		mCommand = Command.FORWARDLEFT;
	    	}

	    	if ((x > 3 && x < 15) && (y > 0 && y < 9) && (z > -15 && z < -3))
	    	{
	    		directive = "BACKWARDRIGHT";
	    		mCommand = Command.BACKWARDRIGHT;
	    	}

	    	if ((x > -15 && x < -3) && (y > 0 && y < 9) && (z > -15 && z < -3))
	    	{
	    		directive = "BACKWARDLEFT";
	    		mCommand = Command.BACKWARDLEFT;
	    	}
	    	
	    	if (mCommand == Command.NONE)	
	    		return;
	    	
	    	mMaster.sendCarCommand(mCommand);
	    	mTextView.setText("Rotation: " + rotation + "\nX: " + mSensorX + "\nY: " + mSensorY + "\nZ: " + mSensorZ + "\ntime: " + System.nanoTime() + "\nDirective: " + directive);
	    }
	    
	    public void onAccuracyChanged(Sensor arg0, int arg1)
		{
			// TODO Auto-generated method stub
			
		}
    }
    
    @Override
    public void onStart()
    {
    	super.onStart();
    	mMaster.start();
    	mAccelerometerListener = new AccelerometerListener();
    }
    
    @Override
    public void onResume()
    {
    	super.onResume();
    }
    
    @Override
    public void onPause()
    {
    	super.onPause();
    	mMaster.disconnectFromSlave();
    }
    
    @Override
    public void onStop()
    {
    	super.onStop();
    	mMaster.disconnectFromSlave();
    	mAccelerometerListener.stop();
    }
    
    @Override
    public void onDestroy()
    {
    	super.onDestroy();
    	mMaster.disconnectFromSlave();
    }
}