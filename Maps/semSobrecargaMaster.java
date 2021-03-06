import lejos.pc.comm.*;
import java.util.Scanner;
import java.io.*;
/*
 * Master: Sends commands to NXT Slave application
 * Ainda em atualização...
 */
public class MasterNav {
	private static final byte ADD_POINT = 0; //adds waypoint to path
	private static final byte TRAVEL_PATH = 1; // enables slave to execute the path
	private static final byte STATUS = 2; // enquires about slave's position 
	private static final byte STOP = 3; // closes communication
	
	private NXTComm nxtComm;
	private DataOutputStream dos;
	private DataInputStream dis;	
	
	private static final String NXT_ID = "PKSM"; // NXT BRICK ID

	private float sendCommand(byte command, float paramX, float paramY) {
		try {
			dos.writeByte(command);
			dos.writeFloat(paramX);
			dos.writeFloat(paramY);
			dos.flush();
			return dis.readFloat();
		} catch (IOException ioe) {
			System.err.println("IO Exception");
			System.exit(1);
			return -1f;
		}
	}
	
	/*
	 * Connect to the NXT
	 */
	private void connect() {
		try {
			NXTComm nxtComm = NXTCommFactory.createNXTComm(NXTCommFactory.USB);
			/* Uncomment next line for Bluetooth communication */
			//NXTComm nxtComm = NXTCommFactory.createNXTComm(NXTCommFactory.BLUETOOTH);			
			NXTInfo[] nxtInfo = nxtComm.search(Master.NXT_ID);
			
			if (nxtInfo.length == 0) {
				System.err.println("NO NXT found");
				System.exit(1);
			}
			
			if (!nxtComm.open(nxtInfo[0])) {
				System.err.println("Failed to open NXT");
				System.exit(1);
			}
			
			dis = new DataInputStream(nxtComm.getInputStream());
			dos = new DataOutputStream(nxtComm.getOutputStream());
			
		} catch (NXTCommException e) {
			System.err.println("NXTComm Exception: "  + e.getMessage());
			System.exit(1);
		}
	}		
	/**
	 * Terminate the program and send stop command to the robot
	 *
	 */
	private void close() {
		try {
			dos.writeByte(STOP);
			dos.writeFloat(0f);
			dos.writeFloat(0f);
			dos.flush();
			Thread.sleep(200);
			System.exit(0);
		} catch (Exception ioe) {
			System.err.println("IO Exception");
		}
	}	
	/*
	- Criar um objeto Coordenadas com dois atributos
	- Vetor param com duas pos

	*/
	public static void main(String[] args) {
		byte cmd = 0; float param = 0f; float ret=0f; float addX = 0f; float addY = 0f; boolean boolRet = false;
		MasterNav master = new MasterNav();
		master.connect();
	    Scanner scan = new Scanner( System.in );	    
	    while(true) {
	    	System.out.print("Enter command [0:ADD_POINT 1:TRAVEL_PATH 2:STATUS 3:STOP]: ");
	    	cmd = (byte) scan.nextFloat(); 
	    	if (cmd == 0){
	    		System.out.println("Enter coordinate X: ");
	    		addX = scan.nextFloat();
	    		System.out.println("Enter coordinate Y: ");
	    		addY = scan.nextFloat();
	    	} else {
	    		addX = -1;
	    		addY = -1;	    		
	    	}
	    	ret = master.sendCommand(cmd, addX, addY); // return 0 when Slave successfully recieved the dos
	    	System.out.println("cmd: " + addX + " X: " + "Y: " + addY +" return: " + ret);
	    }
	}

}
