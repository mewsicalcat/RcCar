package com.cs421.rccar.UI;

import java.io.IOException;
import java.util.Set;

import org.microbridge.server.Server;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cs421.rccar.R;
import com.cs421.rccar.R.id;
import com.cs421.rccar.Slave.Slave;
import com.cs421.rccar.Util.BluetoothCommunicationService;
import com.cs421.rccar.Util.Command;

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
    // Message types sent from the BluetoothChatService Handler
    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_WRITE = 3;
    public static final int MESSAGE_DEVICE_NAME = 4;
    public static final int MESSAGE_TOAST = 5;
    
	private static TextView t;
	private static TextView p; 
	private Command currState;

	private Server server;
	public CameraView camera; 
	public ImageView imv; 
	public Button b; 

    /**
     * Method to create the UI
     * @param savedInstanceState the saved instance state from freezing the software
     */
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.slaveview);
        
        t = (TextView) findViewById(R.id.slavetest);
        p = (TextView) findViewById(R.id.pictureTaken);
        b = (Button) findViewById(id.pictureButton); 

        
        mSlave = new Slave(mHandler);
                
//        Camera.Parameters parameters = camera.getParameters();
        
        
        b.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				takePicture(); 
			}
    }); 
        
 
        
//        parameters.setPictureFormat(PixelFormat.JPEG); 
//        camera.setParameters(parameters);
        
        
        //ServoControl 

		// Create TCP server
        //TODO: uncomment later 
//		server = null;
//		try
//		{
//			Log.e("ServoControl", "going to request a new Server (blocking call)");
//			
//			Log.e("ServoControl", "onCreate has begun");
//			server = new Server(1337);
//			Log.e("Server", server + ""); 
//			Log.e("ServoControl", "acquired server, starting thread");
//			server.start();
//			Log.e("ServoControl", "thread started");
//
//		} catch (IOException e)
//		{
//			Log.e("microbridge", "Unable to start TCP server", e);
//			System.exit(-1);
//		}		
    }
    
    private void takePicture() {
//    	  camera.takePicture(shutterCallback, rawCallback, jpegCallback); 
  	  	camera.takePicture(shutterCallback, rawCallback, jpegCallback); 
    	}
    	 
    	ShutterCallback shutterCallback = new ShutterCallback() {
    	  public void onShutter() {
    	    // TODO Do something when the shutter closes.
    	  }
    	};
    	 
    	PictureCallback rawCallback = new PictureCallback() {
    	  public void onPictureTaken(byte[] _data, Camera _camera) {
    	    // TODO Do something with the image RAW data.
//    	  	  Bitmap bmp = BitmapFactory.decodeByteArray(_data, 0, _data.length);
//    	  	  imv.setImageBitmap(bmp); 
              p.setText("Picture received at: " + System.currentTimeMillis()); 

    	  }
    	};
    	 
    	PictureCallback jpegCallback = new PictureCallback() {
    	  public void onPictureTaken(byte[] _data, Camera _camera) {
    	    // TODO Do something with the image JPEG data.
    	  }
    	};
    	
    	
    
    /**
     * Ensures that this device is discoverable via Bluetooth Connection
     */
    private void ensureDiscoverable() 
    {
    	BluetoothAdapter mAdapter = BluetoothAdapter.getDefaultAdapter();
    	
        if (mAdapter.getScanMode() != BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) 
        {
            Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
            startActivity(discoverableIntent);
        }
    }
    
    /**
     * Inherited method from Activity.  Ensures that this Android phone is discoverable via Bluetooth
     * and starts the Slave class.  Finally connects the slave to a device
     */
    public void onStart()
    {
//        camera = Camera.open(); 

    	super.onStart();
    	ensureDiscoverable();
    	mSlave.start();
    	
    	BluetoothAdapter mAdapter = BluetoothAdapter.getDefaultAdapter();
    	
    	Set<BluetoothDevice> pairedDevices = mAdapter.getBondedDevices();	//get paired Android phones
    	if (pairedDevices.size() > 0)										//if there are any paired phones
    	{
    		for (BluetoothDevice device : pairedDevices)
    		{
    			mSlave.connect(device);								//connect to that phone
    		}
    	}
    }
    
    /**
     * Inherited method to run when the Activity resumes from being paused or stopped
     */
    public void onResume()
    {
    	camera = Camera.open(); 
    	super.onResume();
    }
    
    /**
     * Inherited method to run when the activity pauses
     */
    public void onPause()
    {
        camera.release(); 
    	super.onPause();

    }
    
    /**
     * Inherited method to run when the activity stops
     */
    public void onStop()
    {
    	camera.a(); 
    	super.onStop();
    }
    
    /**
     * Inherited method to run when the activity is destroyed
     * Stops the communication service
     */
    public void onDestroy()
    {
    	camera.release(); 
    	super.onDestroy();

        if (mSlave != null) 
        	mSlave.stop();   
    }
    
    /**
     * For testing purposes only!  Passes a message to the handler
     * @param c the command to send
     */
    public void sendMessage(Command c)
    {
    	mHandler.obtainMessage(SlaveActivity.MESSAGE_READ, (int) c.getBytes(), -1, new byte[1024]).sendToTarget();
    }
    
    /**
     * Returns the state of the vehicle
     * @return the state of the vehicle
     */
    public Command getState()
    {
    	return currState;
    }
    
    //Message handler to receive messages from the BluetoothReceiver object
    private final Handler mHandler = new Handler() 
    {
        @Override
        public void handleMessage(Message msg) 
        {
        	Log.d("SLAVE", "handleMessage");
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
	            case MESSAGE_WRITE: break;
	            case MESSAGE_READ:
	                byte[] readBuf = (byte[]) msg.obj;
	                //String readMessage = new String(readBuf, 0, msg.arg1);
	                t.setText("Message received at: " + System.currentTimeMillis() + " with directive: " + Command.commandInflater(readBuf[0]).toString());
	                Log.d("SLAVE", "message received and tried to set!");
	                
	                
	                //send received byte to Arduino
	            	try
	        		{
	        		server.send(new byte[] { (byte)readBuf[0] });

	        		} catch (IOException e)
	        		{
	        			Log.e("microbridge", "problem sending TCP message", e);
	        		}		
	        		
	                
	                break;
	            case MESSAGE_DEVICE_NAME: break;
            }
        }
    };
}