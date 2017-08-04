import lejos.nxt.*;
import lejos.nxt.comm.*;
import lejos.nxt.Motor;
import java.io.*;
import lejos.robotics.navigation.DifferentialPilot;

/**
 * Slave: Executes commands sent by PC Master application
 * @author Denis Maua'
 * @since 2016-06-05
 * Inspired on code by Lawrie Griffiths
 * Modified by @pksm
 */

public class Slave {
	private static final byte SQUARE = 0;
	private static final byte STOP = 1;

	public static void main(String[] args) throws Exception {
		//USBConnection btc = USB.waitForConnection(); /* USB communication */
		/* Uncomment next line for Bluetooth */
		BTConnection btc = Bluetooth.waitForConnection();
		DataInputStream dis = btc.openDataInputStream();
		DataOutputStream dos = btc.openDataOutputStream();
		LCD.drawString("READY", 0, 10);
		while (true) {
			try {
				byte cmd = dis.readByte();
				LCD.drawInt(cmd,1,0,0);
				float param = dis.readFloat();
				LCD.drawInt((int) (param + 0.5f),4,0,1);
				
				switch (cmd) {
				case SQUARE:
					Square(param);
					dos.writeFloat(0);
					break;				
				case STOP:
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
	public static void Square(float size){
		DifferentialPilot pilot = new DifferentialPilot(2.205f, 4.527f, Motor.B, Motor.C, false); //in inches
		pilot.setRotateSpeed(30);
		pilot.setTravelSpeed(15);
		for (int i = 0; i < 4; i++) {
			pilot.travel(size, false); 
			pilot.rotate(90, false); //90 degrees
		}
		pilot.stop();
	}

}
