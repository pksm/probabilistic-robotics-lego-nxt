import lejos.nxt.Motor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.localization.OdometryPoseProvider;
import lejos.nxt.comm.RConsole;
import lejos.robotics.navigation.Pose;

public class RConsoleTest {
	static OdometryPoseProvider position;

	public static void main(String[] args) throws Exception {
		RConsole.openAny(0);
		Square(30);
		RConsole.close();
	}
	public static void Square(float size){
		// wheel diameter: 2.205f
		// wheel distance: 4.527f
		DifferentialPilot pilot = new DifferentialPilot(5.6f, 11.2f, Motor.B, Motor.C, false); //in cm
		position = new OdometryPoseProvider (pilot);
		Pose tmp = new Pose(0.0f, 0.0f, 0.0f);
        position.setPose(tmp);
		pilot.setRotateSpeed(50);
		pilot.setTravelSpeed(25);
		for (int i = 0; i < 4; i++) {
			pilot.travel(size, true); 
			while (pilot.isMoving()) {
	            //Get pose
	            tmp = position.getPose();
	        }
			pilot.rotate(-90, false);
			RConsole.println("Side: " + i);
			RConsole.println("Final Pose: " + tmp);
			RConsole.println("Current Pose: " + position.getPose());
		}
		pilot.stop();
	}

}
