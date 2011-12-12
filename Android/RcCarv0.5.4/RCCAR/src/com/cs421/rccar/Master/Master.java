package com.cs421.rccar.Master;

import android.os.Handler;
import android.os.Message;

import com.cs421.rccar.Controller.Driveable;
import com.cs421.rccar.Controller.MasterController;
import com.cs421.rccar.Util.BluetoothCommunicationService;
import com.cs421.rccar.Util.BluetoothState;
import com.cs421.rccar.Util.Command;

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
	 * Disconnect this device from the slave device
	 * @return true if successfully disconnected, false otherwise
	 */
    public boolean disconnectFromSlave()
    {
    	return mDriveable.stop();
    }
    
	/**
	 * Sends a Command enum to the slave device utilizing
	 * Bluetooth, or some other protocol used to communicate 
	 * between devices
	 */
    public void sendCarCommand(Command mCommand)
    {
    	mDriveable.driveCommand(mCommand);
    }
    
	/**
	 * Performs all required initializations to start the 
	 * BluetoothCommunication Service and Bluetooth Receiver,
	 * or other structures which allow for communication with 
	 * the slave device
	 */
    public void start()
    {
    	mDriveable.start();
    }
    
	/**
	 * Gets the current BluetoothState of the Master/Slave connection
	 * @return the current Bluetooth state
	 */
    public synchronized BluetoothState getState()
    {
    	return mDriveable.getState();
    }
    
    //Message handler to be used in conjunction with the Bluetooth Communication Service
    private final Handler mHandler = new Handler() 
    {
        @Override
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