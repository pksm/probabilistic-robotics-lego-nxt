import lejos.nxt.*;
import lejos.nxt.comm.*;
import lejos.nxt.Motor;
import java.io.*;

public class AulaSlave {
	private static final byte FORWARD = 0;
	private static final byte STOP = 1;
	private static final byte EXIT = 2;

	public static void main(String[] args) throws Exception {
		//USBConnection btc = USB.waitForConnection(); /* USB communication */
		/* Uncomment next line for Bluetooth */
		BTConnection btc = Bluetooth.waitForConnection();
		DataInputStream dis = btc.openDataInputStream();
		DataOutputStream dos = btc.openDataOutputStream();
		while (true) {
			try {
				byte cmd = dis.readByte();
				float param = dis.readFloat();
				switch (cmd) {
				case FORWARD: 
					if (!Motor.A.isMoving() && !Motor.B.isMoving()){
						Motor.A.setSpeed(param);
						Motor.B.setSpeed(param);
						Motor.A.forward();
						Motor.B.forward();}
					dos.writeFloat(0);
					break;				
				case STOP:
					Motor.A.stop(true);
					Motor.B.stop();
					dos.writeFloat(0);
					break;			
				case EXIT:
					System.exit(0);
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
}
