import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.MotorPort;
import lejos.nxt.NXTMotor;
import lejos.nxt.Button;

public class unregulatedMotor {

	static NXTMotor mB;
	static NXTMotor mC;
	
	public static void main(String args[])  
  {
		mB = new NXTMotor(MotorPort.B);
		mC = new NXTMotor(MotorPort.C);
		Button.waitForAnyPress();
    mB.setPower(50);
		mC.setPower(50);
    while (!Button.ENTER.isPressed());
		mB.stop();
		mC.stop();
   }	
}
