package com.cs421.rccar.Util.Math;

/**
 * This interface abstracts coordinate
 * to coordinate transformations
 * 
 * @author David Widen
 * @author Jessie Young
 * @author Thomas Cheng
 *
 */
public interface CoordinateTransform
{
	/**
	 * Transforms the information from one coordinate system
	 * to another
	 * @param coord the starting coordinate system
	 * @return the transformed coordinate in the new coordinate system
	 */
	public Coordinate transform(Coordinate coord);
}
