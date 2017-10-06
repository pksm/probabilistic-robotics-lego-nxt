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
	static DataOutputStream dos;

	public static void main(String[] args) throws Exception {
		USBConnection btc = USB.waitForConnection(); /* USB communication */
		/* Uncomment next line for Bluetooth */
		//BTConnection btc = Bluetooth.waitForConnection();
		DataInputStream dis = btc.openDataInputStream();
		DataOutputStream dos = btc.openDataOutputStream();
		// MOTOR A is being used to rotate the sonar
		DifferentialPilot pilot = new DifferentialPilot(5.6f, 11.2f, Motor.B, Motor.A); // (wheel diameter, dist between wheels, left motor, right motor )
		sonar = new UltrasonicSensor(SensorPort.S4);

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
					fullscan();
					dos.writeFloat(-20f);
					break;	
				case SINGLE_SCAN:
					int val = sonar.getDistance();
					System.out.println(""+ val);
					dos.writeFloat((float)val);
					break;
				case EXIT:
					System.exit(1);
				default:
					dos.writeFloat(-1);
				}
				dos.flush();
				
			} catch (Exception e) {
				System.err.println("FERROU");
				Thread.sleep(2000);
				System.exit(1);
			}
		}
	}

	public static void fullscan() throws IOException{
	   int val = 0;
	   Motor.C.setSpeed(100);
       //System.out.println("Inicial "+ Motor.C.getTachoCount() );
       val = sonar.getDistance();
       dos.writeFloat((float)val);
       Motor.C.rotate(450);
       //System.out.println("Metade da direita "+ Motor.C.getTachoCount() + " " + sonar.getDistance());
       val = sonar.getDistance();
       dos.writeFloat((float)val);
       Motor.C.rotate(-900);
       //System.out.println("Metade da esquerda "+ Motor.C.getTachoCount() + " " + sonar.getDistance() );
       val = sonar.getDistance();
       dos.writeFloat((float)val);
       Motor.C.rotate(450);
       //System.out.println("Meio "+ Motor.C.getTachoCount() + " " + sonar.getDistance());
       val = sonar.getDistance();
       dos.writeFloat((float)val);
       //Motor.C.resetTachoCount();
       //System.out.println("Meio depois do reset "+ Motor.C.getTachoCount()+ " " + sonar.getDistance() );
       Motor.C.rotate(450);
       val = sonar.getDistance();
       dos.writeFloat((float)val);
       //System.out.println("Metade da direita "+ Motor.C.getTachoCount() + " " + sonar.getDistance() );
       Motor.C.rotate(-900);
       val = sonar.getDistance();
       dos.writeFloat((float)val);
       //System.out.println("Metade da esquerda "+ Motor.C.getTachoCount()  + " " + sonar.getDistance());
       Motor.C.rotate(450);
       val = sonar.getDistance();
       dos.writeFloat((float)val);
       //System.out.println("Meio "+ Motor.C.getTachoCount() + " " + sonar.getDistance() );



		// //Motor A rotates to 0 degrees and rotates 30 degrees and read a measurement
		// int x = 450;
		// Motor.C.rotate(x);
		// double[] values = new double[90];
		// for (int i=0; i < 90; i++){
		// 	Delay.msDelay(100);
		// 	values[i] = sonar.getDistance();
		// 	x-=10; 
		// 	Motor.C.rotate(x);
		// }

		// for (int i=0; i < 90; i++){
		// 	System.out.println("Pos "+i + " "+ values[i]);
		// 	Button.waitForAnyPress();
		// }
	}
}
