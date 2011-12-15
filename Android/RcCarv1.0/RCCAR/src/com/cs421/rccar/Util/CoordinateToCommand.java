package com.cs421.rccar.Util;

import com.cs421.rccar.Util.Math.Coordinate;

/**
 * This interface defines a single method to
 * translate a coordinate, Coordinate, into an Android
 * recognizable Command
 * 
 * @author David Widen
 * @author Jessie Young
 * @author Thomas Cheng
 *
 */
public interface CoordinateToCommand
{
	/**
	 * Method to translate a Coordinate into a Command
	 * @param coordinate the Coordinate to be translated
	 * @return the corresponding 
	 */
	public Command getCommand(Coordinate coordinate);
}
