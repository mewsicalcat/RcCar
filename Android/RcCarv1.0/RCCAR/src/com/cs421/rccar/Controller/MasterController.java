package com.cs421.rccar.Controller;

import com.cs421.rccar.Util.Command;
import com.cs421.rccar.Util.Bluetooth.BluetoothState;


/**
 * Controls the interactions between the Slave device GUI
 * and the underlying implementation of all of the message
 * passing that happens to allow the Android device to 
 * send data to the slave Android device
 * 
 * @author David Widen
 * @author Jessie Young
 * @author Thomas Cheng
 *
 */
public interface MasterController
{
	/**
	 * Sends a Command enumeration to the slave device utilizing
	 * Bluetooth, or some other protocol used to communicate 
	 * between devices
	 * @param mCommand the command to send through Bluetooth
	 */
	public void sendCarCommand(Command mCommand);
	
	/**
	 * Performs all required initializations to start the 
	 * BluetoothCommunication Service and Bluetooth Receiver,
	 * or other structures which allow for communication with 
	 * the slave device
	 */
	public void start();
	
	/**
	 * Disconnect this device from the slave device
	 * @return true if successfully disconnected, false otherwise
	 */
	public boolean disconnectFromSlave();
	
	/**
	 * Gets the current BluetoothState of the Master/Slave connection
	 * @return the current Bluetooth state
	 */
	public BluetoothState getState();
}
