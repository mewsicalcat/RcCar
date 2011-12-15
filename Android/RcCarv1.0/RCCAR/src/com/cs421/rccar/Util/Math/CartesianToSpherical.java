package com.cs421.rccar.Util.Math;

/**
 * This class transforms information in the Cartesian coordinate
 * system into the Spherical coordinate system
 * 
 * @author David Widen
 * @author Jessie Young
 * @author Thomas Cheng
 *
 */
public class CartesianToSpherical implements CoordinateTransform
{
	/**
	 * Default constructor for a CartesianToSpherical object
	 */
	public CartesianToSpherical()
	{
		
	}
	
	/**
	 * This method transforms information in the Cartesian coordinate
	 * system into the Spherical coordinate system
	 * @param Coordinate the Cartesian representation of the information
	 * @return the Spherical representation of the information
	 */
	public Coordinate transform(Coordinate coord)
	{
		// TODO Auto-generated method stub
		if (coord instanceof CartesianCoordinate)
		{	
			CartesianCoordinate cc = (CartesianCoordinate) coord;
			
			// Conversion from Cartesian to Spherical
			double r = Math.sqrt(Math.pow(cc.getX(), 2.0) + Math.pow(cc.getY(), 2.0) + Math.pow(cc.getZ(), 2.0));
			double theta = Math.acos(cc.getZ()/r);
			double psi = 0.0;
			if(cc.getX() >= 0)
			{
				psi = Math.atan(cc.getY()/cc.getX());
				// If the arctan result is negative, add 2*Pi to it
				if(psi < 0)
					psi += 2*Math.PI;
			}
			else
			{
				psi = Math.PI - Math.atan(-cc.getY()/cc.getX());
			}
			
			SphericalCoordinate spherical = new SphericalCoordinate(r, theta, psi);
			return spherical;
		}
		return null;
	}

}
