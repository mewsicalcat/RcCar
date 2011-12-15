package com.cs421.rccar.Util.Math;


/**
 * This interface defines a single method which allows
 * Android devices to send a drive Command to some
 * implementation defined device, such as a USB port
 * or via Bluetooth
 * 
 * @author David Widen
 * @author Jessie Young
 * @author Thomas Cheng
 *
 */
public class SphericalCoordinate implements Coordinate
{
	/**
	 * The radius, r
	 */
	private final double r;
	
	/**
	 * The inclination, theta, in radians
	 */
	private final double theta;
	
	/**
	 * The azimuth, psi, in radians
	 */
	private final double psi;
	
	/**
	 * Empty Constructor
	 */
	public SphericalCoordinate()
	{
		r = 0.0;
		theta = 0.0;
		psi = 0.0;
	}
	
	/**
	 * Constructor for the SphericalCoordinate
	 * @param r the radius r
	 * @param theta the inclination, theta, in radians
	 * @param psi the azimuth, psi, in radians
	 */
	public SphericalCoordinate(double r, double theta, double psi)
	{
		this.r = r;
		this.theta = theta;
		this.psi = psi;
	}
	
	/**
	 * Retrieve the radius value
	 * @return the radius value
	 */
	public double getRadius()
	{
		return r;
	}
	
	/**
	 * Retrieve the inclination value, theta, in radians
	 * @return the inclination value, theta, in radians
	 */
	public double getTheta()
	{
		return theta;
	}
	
	/**
	 * Retrieve the azimuth value, psi, in radians
	 * @return the azimuth value, psi, in radians
	 */
	public double getPsi()
	{
		return psi;
	}

	/**
	 * Get the 3 raw coordinate values in the following order
	 * radius, theta, and psi
	 * @return the raw coordinate values
	 */
	public double[] getRawValues()
	{
		// TODO Auto-generated method stub
		double[] coord = new double[3];
		coord[0] = r;
		coord[1] = theta;
		coord[2] = psi;
		return coord;
	}
	
	
}
