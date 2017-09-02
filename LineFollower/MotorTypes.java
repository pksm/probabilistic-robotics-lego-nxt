import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.MotorPort;
import lejos.nxt.NXTMotor;
import lejos.nxt.Button;
import lejos.util.Delay;
import lejos.nxt.Motor;

public class MotorTypes {

	
	public static void main(String args[])  
  	{
		NXTMotor mB = new NXTMotor(MotorPort.B);
		NXTMotor mC = new NXTMotor(MotorPort.C);
		NXTRegulatedMotor rb = new NXTRegulatedMotor(MotorPort.B);
		NXTRegulatedMotor rc = new NXTRegulatedMotor(MotorPort.C);
		
		boolean bola, bolc;		

		Button.waitForAnyPress();
    		mB.setPower(50);
		mC.setPower(50);
    		while (!Button.ESCAPE.isDown());
		mB.stop();
		mC.stop();


   		rb.rotate(3000,true);
		rc.rotate(-3000,true);
		Delay.msDelay(2000);
		


		rb.stop(true);
		rc.stop();
		

		
		bola = rb.suspendRegulation();
		bolc = rc.suspendRegulation();

		System.out.println(""+bola);	
		System.out.println(""+bolc);
		mB.setPower(-30);
		mC.setPower(-30);
		mB.forward();
		mC.forward();
		System.out.println(""+bola+" "+mB.getPower());	
		System.out.println(""+bolc+" "+mC.getPower());	
		Delay.msDelay(800);
		rb.stop(true);
		rc.stop();
		rb.rotate(300,true);
		rc.rotate(-300);
	}	
}
