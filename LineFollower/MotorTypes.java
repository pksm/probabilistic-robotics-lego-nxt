/*
Example of both regulated motor and unregulated motor operating in the same code.

This program is intended to run as a NXT Application (compile and run for NXT brick)
*/
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.MotorPort;
import lejos.nxt.NXTMotor;
import lejos.nxt.Button;
import lejos.util.Delay;
import lejos.nxt.Motor;

public class MotorTypes {

	public static void main(String args[])  
  	{
		NXTMotor mB = new NXTMotor(MotorPort.B); //create instance of an unregulated motor in motor port B 
		NXTMotor mC = new NXTMotor(MotorPort.C); //create instance of an unregulated motor in motor port C 
		NXTRegulatedMotor rb = new NXTRegulatedMotor(MotorPort.B); //create instance of a regulated motor in motor port B 
		NXTRegulatedMotor rc = new NXTRegulatedMotor(MotorPort.C); //create instance of a regulated motor in motor port C
		
		boolean bola, bolc; //probably not necessary ...NEED TO REFACTOR		
		//UNREGULATED
		Button.waitForAnyPress();
    		mB.setPower(50); 
		mC.setPower(50);
    		while (!Button.ESCAPE.isDown());
		mB.stop();
		mC.stop();

		//REGULATED
		//one can go from uregulated to regulated automatically using a NXTRegulataedMotor method
   		rb.rotate(3000,true);
		rc.rotate(-3000,true);
		Delay.msDelay(2000);

		rb.stop(true);
		rc.stop();
		//regulated to unregulated
		bola = rb.suspendRegulation(); 
		bolc = rc.suspendRegulation();
		
		//UNREGULATED
		System.out.println(""+bola);	
		System.out.println(""+bolc);
		mB.setPower(-30);
		mC.setPower(-30);
		mB.forward();
		mC.forward();
		System.out.println(""+bola+" "+mB.getPower());	
		System.out.println(""+bolc+" "+mC.getPower());	
		Delay.msDelay(800);
		
		//REGULATED
		rb.stop(true);
		rc.stop();
		rb.rotate(300,true);
		rc.rotate(-300);
	}	
}
