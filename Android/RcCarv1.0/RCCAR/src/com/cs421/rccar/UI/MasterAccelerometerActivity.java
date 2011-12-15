package com.cs421.rccar.UI;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.cs421.rccar.R;
import com.cs421.rccar.Controller.MasterController;
import com.cs421.rccar.Master.Master;
import com.cs421.rccar.Util.Command;
import com.cs421.rccar.Util.SphericalToCommand;
import com.cs421.rccar.Util.Math.CartesianCoordinate;
import com.cs421.rccar.Util.Math.CartesianToSpherical;
import com.cs421.rccar.Util.Math.SphericalCoordinate;

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
    
	private MasterController mMasterController;
	private SensorManager mSensorManager;
	private Sensor mAccelerometer;

	
	private TextView mTextView;
	
	private float mSensorX;
	private float mSensorY;
	private float mSensorZ;
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
        mTextView = (TextView) findViewById(R.id.accel_status);
        mMasterController = new Master();
    }
    
    /**
     * Switch the screen so it uses buttons to control the RC car
     */
    public void toggleMode()
    {
    	//TODO
    }
    
    /**
     * Listens for updates to the Accelerometer
     * 
     * @author David Widen
     * @author Jessie Young
     * @author Thomas Cheng
     *
     */
    public class AccelerometerListener implements SensorEventListener
    {
    	/**
    	 * Default constructor
    	 */
    	public AccelerometerListener()
    	{
    		mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    		mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_UI);
    		
    		Log.d("MasterAccelerometerActivity", "constructor run");
    	}
    	
    	/**
    	 * Stops the listener
    	 */
    	public void stop()
    	{
    		mSensorManager.unregisterListener(this);
    	}
    	
    	/**
    	 * {@inheritDoc}
    	 */
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
	    	
	    	// Get the accelerometers x, y, and z readings
	    	mSensorX = -event.values[2];
	    	mSensorY = event.values[1];
	    	mSensorZ = -event.values[0];
	    	
	    	Command mCommand = Command.NONE;
	    	
	    	// Create a Cartesian representation
	    	CartesianCoordinate mCC = new CartesianCoordinate((double) mSensorX, (double) mSensorY, (double) mSensorZ);
	    	
	    	// Transform it into a Spherical representation
	    	CartesianToSpherical mTransformer = new CartesianToSpherical();
	    	SphericalCoordinate mSC = (SphericalCoordinate) mTransformer.transform(mCC);
	    	
	    	// Map it to a corresponding car Command
	    	SphericalToCommand mMapper = new SphericalToCommand();
	    	mCommand = mMapper.getCommand(mSC);
	    	
	    	if (mCommand == Command.NONE)	
	    		return;
	    	
	    	mMasterController.sendCarCommand(mCommand);
	    	mTextView.setText("X: " + mSensorX + "\nY: " + mSensorY + "\nZ: " + mSensorZ + "\ntime: " + System.nanoTime() + "\nDirective: " + mCommand);
	    }
	    
	    /**
	     * {@inheritDoc}
	     */
	    public void onAccuracyChanged(Sensor arg0, int arg1)
		{
			// TODO Auto-generated method stub
			
		}
    }
    
    /**
     * {@inheritDoc}
     */
    public void onStart()
    {
    	super.onStart();
    	mMasterController.start();
    	mAccelerometerListener = new AccelerometerListener();
    }
    
    /**
     * {@inheritDoc}
     */
    public void onResume()
    {
    	super.onResume();
    }
    
    /**
     * {@inheritDoc}
     */
    public void onPause()
    {
    	super.onPause();
    	mMasterController.disconnectFromSlave();
    }
    
    /**
     * {@inheritDoc}
     */
    public void onStop()
    {
    	super.onStop();
    	mMasterController.disconnectFromSlave();
    	mAccelerometerListener.stop();
    }
    
    /**
     * {@inheritDoc}
     */
    public void onDestroy()
    {
    	super.onDestroy();
    	mMasterController.disconnectFromSlave();
    }
}