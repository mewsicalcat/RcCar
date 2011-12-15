package com.cs421.rccar.Util;

import com.cs421.rccar.Util.Math.Coordinate;
import com.cs421.rccar.Util.Math.SphericalCoordinate;

/**
 * This class maps and a position in Spherical coordinates
 * to a corresponding intended Command
 * 
 * @author David Widen
 * @author Jessie Young
 * @author Thomas Cheng
 *
 */
public class SphericalToCommand implements CoordinateToCommand
{
	/**
	 * Empty Constructor
	 */
	public SphericalToCommand()
	{
		
	}
	
	/**
	 * Maps the Spherical coordinate to a corresponding car Command
	 * @param Coordinate the Spherical representation of the coordinates
	 * @return the corresponding car Command
	 */
	public Command getCommand(Coordinate coordinate)
	{
		// TODO Auto-generated method stub
		if(coordinate instanceof SphericalCoordinate)
		{
			SphericalCoordinate sc = (SphericalCoordinate) coordinate;
			
			double theta = sc.getTheta();
			double psi = sc.getPsi();
			
			// Theta within the range [5*pi/6, pi]
			if (theta > (Math.PI * 5/6))
				return Command.STOP;
			
			// Theta within the range [pi/4, 4*pi/5]
			if (((theta > (Math.PI / 4)) && (theta < Math.PI * 4/5)))
			{
				// 8 equally spaced psi ranges placed all around the circle
				if((psi < (Math.PI/8 - Math.PI/144)) || (psi > 15*Math.PI/8 + Math.PI/144))
					return Command.BACKWARD;
				if((psi > (Math.PI/8 + Math.PI/144)) && (psi < 3*Math.PI/8 - Math.PI/144))
					return Command.BACKWARDRIGHT;
				if((psi > (3*Math.PI/8 + Math.PI/144)) && (psi < 5*Math.PI/8 - Math.PI/144))
					return Command.RIGHT;
				if((psi > (5*Math.PI/8 + Math.PI/144)) && (psi < 7*Math.PI/8 - Math.PI/144))
					return Command.FORWARDRIGHT;
				if((psi > (7*Math.PI/8 + Math.PI/144)) && (psi < 9*Math.PI/8 - Math.PI/144))
					return Command.FORWARD;
				if((psi > (9*Math.PI/8 + Math.PI/144)) && (psi < 11*Math.PI/8 - Math.PI/144))
					return Command.FORWARDLEFT;
				if((psi > (11*Math.PI/8 + Math.PI/144)) && (psi < 13*Math.PI/8 - Math.PI/144))
					return Command.LEFT;
				if((psi > (13*Math.PI/8 + Math.PI/144)) && (psi < 15*Math.PI/8 - Math.PI/144))
					return Command.BACKWARDLEFT;
			}
		}
		return Command.NONE;
	}

}
