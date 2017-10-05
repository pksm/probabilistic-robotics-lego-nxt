import lejos.nxt.*;
import lejos.nxt.comm.*;
import java.io.*;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.util.Delay;
//import lejos.nxt.UltrasonicSensor;

public class SlaveSonar {
	private static final byte TRAVEL = 0; 
	private static final byte ROTATE = 1;
	private static final byte FULL_SCAN = 2; 
	private static final byte SINGLE_SCAN = 3; 
	private static final byte EXIT = 4; 

	static UltrasonicSensor sonar;

	public static void main(String[] args) throws Exception {
		USBConnection btc = USB.waitForConnection(); /* USB communication */
		/* Uncomment next line for Bluetooth */
		//BTConnection btc = Bluetooth.waitForConnection();
		DataInputStream dis = btc.openDataInputStream();
		DataOutputStream dos = btc.openDataOutputStream();
		// MOTOR A is being used to rotate the sonar
		DifferentialPilot pilot = new DifferentialPilot(5.6f, 11.2f, Motor.C, Motor.B); // (wheel diameter, dist between wheels, left motor, right motor )
		sonar = new UltrasonicSensor(SensorPort.S1);

		LCD.drawString("READY", 0, 10);
		while (true) {
			try {
				byte cmd = dis.readByte();
				LCD.drawInt(cmd,1,0,0);
				int param = dis.readInt();

				switch (cmd) {
				case TRAVEL: 
					pilot.travel(param);
					dos.writeFloat(0);
					break;
				case ROTATE: 
					pilot.rotate(param);
					dos.writeFloat(0);
					break;
				case FULL_SCAN:
					dos.writeSomething(fullscan());
					break;	
				case SINGLE_SCAN:
					dos.writeInt(sonar.getDistance());
					break;
				case EXIT:
					System.exit(1);
				default:
					dos.writeFloat(-1);
				}
				dos.flush();
				
			} catch (IOException ioe) {
				System.err.println("IO Exception");
				Thread.sleep(2000);
				System.exit(1);
			}
		}
	}

	public static double[] fullscan(){
		//Motor A rotates to 0 degrees and rotates 30 degrees and read a measurement
		int x = 0;
		Motor.A.rotateTo(x);
		double[] values = new double[90];
		for (int i=0; i < 90; i++){
			Delay.msDelay(100);
			values[i] = sonar.getDistance();
			x+=2; 
			Motor.A.rotateTo(x);
		}
		return values;
	}
}
