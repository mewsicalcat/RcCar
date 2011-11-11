package org.microbridge.servocontrol;

import java.io.IOException;

import org.microbridge.server.Server;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

public class ServoControl extends Activity
{
	
	public static Context mContext;

	/**
	 * Called when the activity is first created.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		// Set full screen view, no title
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		Log.e("ServoControl", "onCreate has begun");
		
		// Create TCP server
		Server server = null;
		try
		{
			Log.e("ServoControl", "going to request a new Server (blocking call)");
			server = new Server(4567);
			Log.e("ServoControl", "acquired server, starting thread");
			server.start();
			Log.e("ServoControl", "thread started");


		} catch (IOException e)
		{
			Log.e("microbridge", "Unable to start TCP server", e);
			System.exit(-1);
		}
		
		JoystickView joystick = new JoystickView(this, server);
		setContentView(joystick);
		boolean requestFocus = joystick.requestFocus();
		Log.d("ServoControl", "requestFocus boolean is: " + requestFocus); 
		
		mContext = getApplicationContext();

	}

	public static void doSomething(byte mByte)
	{
		Toast.makeText(mContext, "I did something! mByte is: " + mByte, Toast.LENGTH_LONG);
	}
	
}