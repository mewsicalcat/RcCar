package com.cs421.rccar.UI;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.cs421.rccar.R;
import com.cs421.rccar.Master.Master;
import com.cs421.rccar.Util.BluetoothCommunicationService;
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
    
	private Master mMaster;
    private static final int MESSAGE_STATE_CHANGE = 1;
    private static final int MESSAGE_READ = 2;
    private static final int MESSAGE_WRITE = 3;
    private static final int MESSAGE_DEVICE_NAME = 4;
	
    /**
     * Method to create the UI
     * @param savedInstanceState the saved instance state from freezing the software
     */
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buttonview);
        
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
				mMaster.sendCarCommand(Command.FORWARD);
			}
		});
        
        backwardButton.setOnClickListener(new OnClickListener()
		{
			
			public void onClick(View v)
			{
				mMaster.sendCarCommand(Command.BACKWARD);
			}
		});
        
        leftButton.setOnClickListener(new OnClickListener()
		{
			
			public void onClick(View v)
			{
				mMaster.sendCarCommand(Command.LEFT);
			}
		});
        
        rightButton.setOnClickListener(new OnClickListener()
		{
			
			public void onClick(View v)
			{
				mMaster.sendCarCommand(Command.RIGHT);
			}
		});
        
        forwardLeftButton.setOnClickListener(new OnClickListener()
		{
			
			public void onClick(View v)
			{
				mMaster.sendCarCommand(Command.FORWARDLEFT);
			}
		});
        
        backwardRightButton.setOnClickListener(new OnClickListener()
		{
			
			public void onClick(View v)
			{
				mMaster.sendCarCommand(Command.BACKWARDRIGHT);
			}
		});
        
        forwardRightButton.setOnClickListener(new OnClickListener()
		{
			
			public void onClick(View v)
			{
				mMaster.sendCarCommand(Command.FORWARDRIGHT);
			}
		});
        
        backwardLeftButton.setOnClickListener(new OnClickListener()
		{
			
			public void onClick(View v)
			{
				mMaster.sendCarCommand(Command.BACKWARDLEFT);
			}
		});
        
        stopButton.setOnClickListener(new OnClickListener()
		{
			
			public void onClick(View v)
			{
				mMaster.sendCarCommand(Command.STOP);
			}
		});
        
        
        
        mMaster = new Master(mHandler);
        mMaster.start();
    }
    
    
    /**
     * Inherited method to run when the Application starts running
     * Enables Bluetooth if it is not already enabled
     */
    public void onStart()
    {
    	super.onStart();
    }
    
    /**
     * Inherited method to run when the activity pauses
     */
    public void onPause()
    {
    	super.onPause();
    }
    
    /**
     * Inherited method to run when the activity stops
     */
    public void onStop()
    {
    	super.onStop();
    }
    
    /**
     * Inherited method to run when the activity is destroyed
     * Stops the communication service
     */
    public void onDestroy()
    {
        super.onDestroy();  
    }
    
    /**
     * Switch the screen so it uses the accelerometer to control the RC car
     */
    public void toggleMode()
    {
    	
    }
    
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
                case BluetoothCommunicationService.STATE_CONNECTED:
                    //setStatus(getString(R.string.title_connected_to, mConnectedDeviceName));
                    //mConversationArrayAdapter.clear();
                    break;
                case BluetoothCommunicationService.STATE_CONNECTING:
                    //setStatus(R.string.title_connecting);
                    break;
                case BluetoothCommunicationService.STATE_LISTEN:
                case BluetoothCommunicationService.STATE_NONE:
                    //setStatus(R.string.title_not_connected);
                    break;
                }
                break;
            case MESSAGE_WRITE:
                byte[] writeBuf = (byte[]) msg.obj;
                // construct a string from the buffer
                String writeMessage = new String(writeBuf);
                //mConversationArrayAdapter.add("Me:  " + writeMessage);
                break;
            case MESSAGE_READ:
                byte[] readBuf = (byte[]) msg.obj;
                // construct a string from the valid bytes in the buffer
                String readMessage = new String(readBuf, 0, msg.arg1);
                //mConversationArrayAdapter.add(mConnectedDeviceName+":  " + readMessage);
                break;
            case MESSAGE_DEVICE_NAME:
                // save the connected device's name
                //mConnectedDeviceName = msg.getData().getString(DEVICE_NAME);
                //Toast.makeText(getApplicationContext(), "Connected to "
                               //+ mConnectedDeviceName, Toast.LENGTH_SHORT).show();
                break;
            }
        }
    };
}
