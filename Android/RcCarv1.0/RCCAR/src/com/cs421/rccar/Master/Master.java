package com.cs421.rccar.Master;

import android.os.Handler;
import android.os.Message;

import com.cs421.rccar.Controller.Driveable;
import com.cs421.rccar.Controller.MasterController;
import com.cs421.rccar.Util.Command;
import com.cs421.rccar.Util.Bluetooth.BluetoothCommunicationService;
import com.cs421.rccar.Util.Bluetooth.BluetoothState;

/**
 * This class serves as a communication interface between
 * the MasterButtonActivity/MasterAccelerometerActivity
 * and the Driveable object.  This class receives input
 * from its containing activity and passes that command
 * to a driveable object
 * 
 * @author David Widen
 * @author Jessie Young
 * @author Thomas Cheng
 *
 */
public class Master implements MasterController
{
	private Driveable mDriveable;
	
    private static final int MESSAGE_STATE_CHANGE = 1;
    private static final int MESSAGE_READ = 2;
    private static final int MESSAGE_WRITE = 3;
    private static final int MESSAGE_DEVICE_NAME = 4;
	
	/**
	 * Default constructor for the Master class
	 */
	public Master()
	{
		mDriveable = new BluetoothCar(mHandler);
	}
	
	/**
     * {@inheritDoc}
     */
    public boolean disconnectFromSlave()
    {
    	return mDriveable.stop();
    }

    /**
     * {@inheritDoc}
     */
    public void sendCarCommand(Command mCommand)
    {
    	mDriveable.driveCommand(mCommand);
    }
    
    /**
     * {@inheritDoc}
     */
    public void start()
    {
    	mDriveable.start();
    }
    
    /**
     * {@inheritDoc}
     */
    public synchronized BluetoothState getState()
    {
    	return mDriveable.getState();
    }
    
    //Message handler to be used in conjunction with the Bluetooth Communication Service
    private final Handler mHandler = new Handler() 
    {
    	/**
         * {@inheritDoc}
         */
        public void handleMessage(Message msg) 
        {
            switch (msg.what) 
            {
	            case MESSAGE_STATE_CHANGE:
	                switch (msg.arg1) 
	                {
	                	case BluetoothCommunicationService.STATE_CONNECTED: break;
	                	case BluetoothCommunicationService.STATE_CONNECTING: break;
	                	case BluetoothCommunicationService.STATE_LISTEN: break;
	                	case BluetoothCommunicationService.STATE_NONE: break;
	                }
	                break;
	            case MESSAGE_WRITE:
	                break;
	            case MESSAGE_READ:
	                break;
	            case MESSAGE_DEVICE_NAME:
	                break;
            }
        }
    };
   
}