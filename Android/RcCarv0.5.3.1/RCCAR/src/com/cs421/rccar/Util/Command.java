package com.cs421.rccar.Util;

/**
 * Contains all 9 possible
 * actions that an RC Car can perform
 * 
 * @author David Widen
 * @author Jessie Young
 * @author Thomas Cheng
 *
 */
public enum Command 
{
	FORWARD(0),
	BACKWARD(1),
	LEFT(2),
	RIGHT(3),
	FORWARDLEFT(4),
	FORWARDRIGHT(5),
	BACKWARDLEFT(6),
	BACKWARDRIGHT(7),
	STOP(8);
	
	private byte name;
	
	private Command(int i)
	{
		name = (byte) i;
	}
	
	public String toString()
	{
		switch (name)
		{
			case 0: return "FORWARD";
			case 1: return "BACKWARD";
			case 2: return "LEFT";
			case 3: return "RIGHT";
			case 4: return "FORWARDLEFT";
			case 5: return "FORWARDRIGHT";
			case 6: return "BACKWARDLEFT";
			case 7: return "BACKWARDRIGHT";
			case 8: return "STOP";
			default: return "STOP";
		}
	}
	
	public static Command commandInflater(byte b)
	{
		switch (b)
		{
			case 0: return FORWARD;
			case 1: return BACKWARD;
			case 2: return LEFT;
			case 3: return RIGHT;
			case 4: return FORWARDLEFT;
			case 5: return FORWARDRIGHT;
			case 6: return BACKWARDLEFT;
			case 7: return BACKWARDRIGHT;
			case 8: return STOP;
			default: return STOP;
		}
	}
}