import lejos.nxt.Motor;
import lejos.nxt.comm.*;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.navigation.Navigator;
import lejos.robotics.navigation.Waypoint;
import lejos.robotics.localization.OdometryPoseProvider;
import lejos.robotics.navigation.Pose;

public class lcpNav
{
	public static void main(String[] args)
	{
		DifferentialPilot p = new DifferentialPilot(5.6f, 11.2f, Motor.C, Motor.B); // (wheel diameter, dist between wheels, left motor, right motor )
		OdometryPoseProvider position = new OdometryPoseProvider (p);
    	Navigator nav = new Navigator(p,position);
    	Pose init = new Pose(0f, 0f, 0f);

		nav.addWaypoint(20,0); // set waypoint
		nav.addWaypoint(20,20);
		nav.addWaypoint(0,20);
		nav.addWaypoint(0,0,0);

		nav.followPath();
		System.out.println("Any button to halt");
		Button.waitForAnyPress();
	}
}
