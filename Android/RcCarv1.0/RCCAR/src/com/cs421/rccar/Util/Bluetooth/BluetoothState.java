package com.cs421.rccar.Util.Bluetooth;

/**
 * Protects the different possible states of a Bluetooth connection
 * 
 * @author David Widen
 * @author Jessie Young
 * @author Thomas Cheng
 *
 */
public enum BluetoothState
{
	/**
	 * No Bluetooth state
	 */
    STATE_NONE,
    
    /**
     * Bluetooth radio is listening
     */
    STATE_LISTEN,
    
    /**
     * Bluetooth is connecting
     */
    STATE_CONNECTING,
    
    /**
     * Bluetooth is connected
     */
    STATE_CONNECTED
}
