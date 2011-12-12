package com.cs421.rccar.Controller;

import com.cs421.rccar.Util.BluetoothState;
import com.cs421.rccar.Util.Command;

public interface MasterController
{
	/**
	 * Sends a Command enum to the slave device utilizing
	 * Bluetooth, or some other protocol used to communicate 
	 * between devices
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
