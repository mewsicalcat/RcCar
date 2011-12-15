package com.cs421.rccar.Util.Math;

/**
 * This class represents information in the Cartesian
 * coordinate system
 * 
 * @author David Widen
 * @author Jessie Young
 * @author Thomas Cheng
 *
 */
public class CartesianCoordinate implements Coordinate
{
	/**
	 * The x value
	 */
	private final double x;
	
	/**
	 * The y value
	 */
	private final double y;
	
	/**
	 * The z value
	 */
	private final double z;
	
	/**
	 * Empty Constructor
	 */
	public CartesianCoordinate()
	{
		x = 0.0;
		y = 0.0;
		z = 0.0;
	}
	
	/**
	 * Constructor for the CartesianCoordinate
	 * @param x the x coordinate
	 * @param y the y coordinate
	 * @param z the z coordinate
	 */
	public CartesianCoordinate(double x, double y, double z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	/**
	 * Retrieve the x value
	 * @return the x value
	 */
	public double getX()
	{
		return x;
	}
	
	/**
	 * Retrieve the y value
	 * @return the y value
	 */
	public double getY()
	{
		return y;
	}
	
	/**
	 * Retrieve the z value
	 * @return the z value
	 */
	public double getZ()
	{
		return z;
	}

	/**
	 * Get the 3 raw coordinate values in the following order
	 * x, y, and z
	 * @return the raw coordinate values
	 */
	public double[] getRawValues()
	{
		// TODO Auto-generated method stub
		double[] coord = new double[3];
		coord[0] = x;
		coord[1] = y;
		coord[2] = z;
		return coord;
	}
}
