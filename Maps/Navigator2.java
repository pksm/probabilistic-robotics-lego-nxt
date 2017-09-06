
import java.io.IOException;

import lejos.nxt.Button;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.navigation.Navigator;
import lejos.robotics.navigation.Waypoint;
import lejos.nxt.Motor;
import lejos.robotics.localization.OdometryPoseProvider;
import lejos.robotics.navigation.Pose;


public class Navigator2
{
	Navigator nav;

	/*
	 * Quadrado de 20 cm
	 */
	public static void main(String[] args) throws IOException
	{

		System.out.println("Any button to start");
		Button.waitForAnyPress();
		//DifferentialPilot p = new DifferentialPilot(wheelDiameter, trackWidth, leftMotor, rightMotor);
		DifferentialPilot p = new DifferentialPilot(5.6f, 11.2f, Motor.C, Motor.B);
		OdometryPoseProvider position = new OdometryPoseProvider (p);
		Navigator nav = new Navigator(p,position);
		Pose init = new Pose(20f, 0f, 270f);
        position.setPose(init); //seta pose inicial para X:20 Y:0 THETA:270 graus (o sistema de coordenadas eh o convencional)

        nav.addWaypoint(20,0);
        nav.addWaypoint(20,20);
		nav.addWaypoint(0,20);
		nav.addWaypoint(0,0,0);
        //soh para ilustrar outra forma de passar parametros do addWaypoint
		// nav.addWaypoint(new Waypoint(20, 0));
		// nav.addWaypoint(new Waypoint(20,20));
		// nav.addWaypoint(new Waypoint(0,20));
		// nav.addWaypoint(new Waypoint(0,0,0)); //only accepts double or a Pose object
		nav.followPath();
		System.out.println("First point "+nav.getWaypoint());
		System.out.println("Any button to halt");
		Button.waitForAnyPress();
	}
}
