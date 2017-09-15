import lejos.nxt.Motor;
import lejos.nxt.Button;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.navigation.Navigator;
import lejos.robotics.navigation.Waypoint;
import lejos.robotics.localization.OdometryPoseProvider;
import lejos.robotics.navigation.Pose;

public class waypointNavLcp
{
	public static void main(String[] args)
	{
		DifferentialPilot p = new DifferentialPilot(5.6f, 11.2f, Motor.C, Motor.B); // (wheel diameter, dist between wheels, left motor, right motor )
		OdometryPoseProvider position = new OdometryPoseProvider (p);
    	Navigator nav = new Navigator(p,position);
    	Pose init = new Pose(111.7f,43.2f, 180); //P4 with heading to 180 degrees
    	position.setPose(init);
    	
		nav.addWaypoint(98.6f,16.6f); //P10
		nav.addWaypoint(83.0f,50.7f); //P5
		nav.addWaypoint(69.0f,57.1f); //P6
		nav.addWaypoint(45.0f,59.3f); //P7
		nav.addWaypoint(26.3f,35.0f); //P8
		nav.addWaypoint(49.0f,10.0f); //P11
		nav.addWaypoint(98.6f,16.6f); //P10
		nav.addWaypoint(111.7f,43.2f); //P4

		nav.followPath();
		System.out.println("Any button to halt");
		Button.waitForAnyPress();
	}
}
