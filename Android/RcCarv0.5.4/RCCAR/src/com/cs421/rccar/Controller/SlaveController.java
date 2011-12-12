package com.cs421.rccar.Controller;

import android.bluetooth.BluetoothDevice;

import com.cs421.rccar.Util.BluetoothState;

public interface SlaveController
{
	/**
	 * Stop listening for communication with the Master, 
	 * and stop communication with the Arduino
	 * @return true if stopped, false otherwise
	 */
	public boolean stop();
	
	/**
	 * Performs all required initializations to start the 
	 * BluetoothCommunication Service and Bluetooth Receiver,
	 * as well as communication via the Serial port.  Should initialize
	 * any structures which foster communication between Master and Slave
	 * devices, as well as with the Arduino Microcontroller
	 */
	public void start();
	
	/**
	 * Send a single picture back to the Master device
	 */
	public void sendPicture(byte[] data);
	
	/**
	 * Send a single frame of the video source to the Master device
	 */
	public void sendVideo(byte[] data);
	
	/**
	 * Gets the current BluetoothState of the Master/Slave connection
	 * @return the current Bluetooth state
	 */
	public BluetoothState getState();

	/**
	 * Connects the Android device to the Master Bluetooth Device
	 * @param device the Android device to connect to
	 */
	public void connect(BluetoothDevice device);
}
