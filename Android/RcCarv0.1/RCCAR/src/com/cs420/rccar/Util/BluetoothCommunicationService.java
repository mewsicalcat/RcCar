package com.cs420.rccar.Util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.Handler;

public class BluetoothCommunicationService
{
	private static UUID MY_UUID = UUID.fromString("437ee550-fde6-11e0-be50-0800200c9a66");
	private static String AppName = "RcCar";
	
	private final BluetoothAdapter mAdapter;
    private final Handler mHandler;
    private AcceptThread mAcceptThread;
    private ConnectThread mConnectThread;
    private ConnectedThread mConnectedThread;
    private int mState;
    
    public static final int STATE_NONE = 0;       // we're doing nothing
    public static final int STATE_LISTEN = 1;     // now listening for incoming connections
    public static final int STATE_CONNECTING = 2; // now initiating an outgoing connection
    public static final int STATE_CONNECTED = 3;  // now connected to a remote device
    
    public BluetoothCommunicationService(Context context, Handler handler)
    {
    	mAdapter = BluetoothAdapter.getDefaultAdapter();
    	mState = STATE_NONE;
    	mHandler = handler;
    }
    
    private synchronized void setState(int state)
    {
    	mState = state;
    }
    
    public synchronized int getState()
    {
    	return mState;
    }
    
    public synchronized void start()
    {
    	if (mConnectThread != null)
    	{
    		mConnectThread.cancel();
    		mConnectThread = null;
    	}
    	
    	if (mConnectedThread != null)
    	{
    		mConnectedThread.cancel();
    		mConnectedThread = null;
    	}
    	
    	setState(STATE_LISTEN);
    	
    	if (mAcceptThread == null)
    	{
    		mAcceptThread = new AcceptThread();
    		mAcceptThread.start();
    	}
    }
    
    public synchronized void connected(BluetoothSocket socket, BluetoothDevice device)
    {
    	if (mConnectThread != null)
    	{
    		mConnectThread.cancel();
    		mConnectThread = null;
    	}
    	
    	if (mConnectedThread != null)
    	{
    		mConnectedThread.cancel();
    		mConnectThread = null;
    	}
    	
    	if (mAcceptThread != null)
    	{
    		mAcceptThread.cancel();
    		mAcceptThread = null;
    	}
    	
    	mConnectedThread = new ConnectedThread(socket);
    	mConnectedThread.start();
    	
    	//Message handler stuff here
    	
    	setState(STATE_CONNECTED);
    }
    
    private void connectionFailed()
    {
    	//Message handler, send message back to activity
    	
    	//Start service over!
    	BluetoothCommunicationService.this.start();
    }
    
    private void connectionLost()
    {
    	//Message handler, send message back to activity
    	
    	//Start service over!
    	BluetoothCommunicationService.this.start();
    }
    
    private class AcceptThread extends Thread
    {
    	private final BluetoothServerSocket mServerSocket;
    	
    	public AcceptThread()
    	{
    		BluetoothServerSocket temp = null;
    		
    		try
    		{
    			temp = mAdapter.listenUsingRfcommWithServiceRecord(AppName, MY_UUID);
    		}
    		catch (IOException e)
    		{
    			//Add processing here for null value
    		}
    		
    		mServerSocket = temp;
    	}
    	
    	public void run()
    	{
    		BluetoothSocket socket = null;
    		
    		while (mState != STATE_CONNECTED)
    		{
    			try
    			{
    				socket = mServerSocket.accept();
    			}
    			catch (IOException e)
    			{
    				break;
    			}
    		}
    		
    		if (socket != null)
    		{
    			synchronized (BluetoothCommunicationService.this)
    			{
    				switch (mState)
    				{
    					case STATE_LISTEN:
    					case STATE_CONNECTING:
    						//connected stuff
    						break;
    					case STATE_NONE:
    					case STATE_CONNECTED:
    						try
    						{
    							socket.close();
    						}
    						catch (IOException e)
    						{
    							//add processing for not-closing
    						}
    						break;
    				}
    			}
    		}
    	}
    	
    	public void cancel()
    	{
    		try
    		{
    			mServerSocket.close();
    		}
    		catch (IOException e)
    		{
    			//add processing for not-closing
    		}
    	}
    }
    
    private class ConnectThread extends Thread
    {
    	private final BluetoothSocket mSocket;
    	private final BluetoothDevice mDevice;
    	
    	public ConnectThread(BluetoothDevice device)
    	{
    		mDevice = device;
    		BluetoothSocket temp = null;
    		
    		try
    		{
    			temp = device.createRfcommSocketToServiceRecord(MY_UUID);
    		}
    		catch (IOException e)
    		{
    			//add processing for null temp
    		}
    		
    		mSocket = temp;
    	}
    	
    	public void run()
    	{
    		mAdapter.cancelDiscovery();
    		
    		try
    		{
    			mSocket.connect();
    		}
    		catch (IOException e)
    		{
    			try
    			{
    				mSocket.close();
    			}
    			catch (IOException e1)
    			{
    				//add processing for failure to close
    			}
    			connectionFailed();
    			return;
    		}
    		
    		synchronized (BluetoothCommunicationService.this)
    		{
    			mConnectThread = null;
    		}
    		
    		connected(mSocket, mDevice);
    	}
    	
    	public void cancel()
    	{
    		try
    		{
    			mSocket.close();
    		}
    		catch (IOException e)
    		{
    			//add processing for failure to close
    		}
    	}
    }
    
    private class ConnectedThread extends Thread
    {
    	private final BluetoothSocket mSocket;
    	private final InputStream mInputStream;
    	private final OutputStream mOutputStream;
    	
    	public ConnectedThread(BluetoothSocket socket)
    	{
    		mSocket = socket;
    		InputStream tempIn = null;
    		OutputStream tempOut = null;
    		
    		try
    		{
    			tempIn = socket.getInputStream();
    			tempOut = socket.getOutputStream();
    		}
    		catch (IOException e)
    		{
    			//add processing for null streams
    		}
    		
    		mInputStream = tempIn;
    		mOutputStream = tempOut;
    	}
    	
    	public void run()
    	{
    		byte[] buffer = new byte[1024];
    		int bytes;
    		
    		while (true)
    		{
    			try
    			{
    				bytes = mInputStream.read(buffer);
    				//handler stuff?  send to activity?
    			}
    			catch (IOException e)
    			{
    				connectionLost();
    				BluetoothCommunicationService.this.start();
    				break;
    			}
    		}
    	}
    	
    	public void write(byte[] buffer)
    	{
    		try
    		{
    			mOutputStream.write(buffer);
    			//handler stuff?  send to activity?
    		}
    		catch (IOException e)
    		{
    			//handle failure to write data
    		}
    	}
    	
    	public void cancel()
    	{
    		try
    		{
    			mSocket.close();
    		}
    		catch (IOException e)
    		{
    			//handle failure to close socket
    		}
    	}
    }
}
