import lejos.robotics.navigation.DifferentialPilot;
import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.Motor;

public class dPilot {

	public static void main(String[] args) {
		LCD.drawString("D.P. - SQUARE", 0, 0);
		Button.waitForAnyPress();
		DifferentialPilot pilot = new DifferentialPilot(2.205f, 4.527f, Motor.B, Motor.C, false);
		LCD.clear();
		pilot.setRotateSpeed(18);
		pilot.setTravelSpeed(4);
		for (int i = 0; i < 4; i++) {
			pilot.travel(15.75, false); //approx. 40cm
			pilot.rotate(90, false);
		}
		pilot.stop();

		Button.waitForAnyPress();
	}

}
