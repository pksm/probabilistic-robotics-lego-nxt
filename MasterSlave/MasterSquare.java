import lejos.pc.comm.*;
import java.util.Scanner;
import java.io.*;
/**
 * Master: Sends commands to NXT Slave application
 * @author Denis Maua'
 * @since 2016-06-05
 * Modified by @pksm
 */
public class Master {
	private static final byte SQUARE = 0;
	private static final byte BATTERY = 1;
	private static final byte STOP = 2;
	
	private NXTComm nxtComm; //NXTComm - PC initiator to connect to NXT
	private DataOutputStream dos; // outout data
	private DataInputStream dis; // input data
	
	private static final String NXT_ID = "NXT8"; // NXT BRICK NAME
	
	/**
	 * Send command to the robot
	 * @param command specifies command
	 * @param param argument
	 * @return value returned by the robot (float)
	 */
	private float sendCommand(byte command, float param) { // try sending byte commands to brick and returns a float ouputted by Slave
		try {
			dos.writeByte(command); // send byte with desired command
			dos.writeFloat(param); // send float with required parameter
			dos.flush(); // flush the output stream to be sure the data is actually transmitted
			return dis.readFloat(); // return float sent by Slave
		} catch (IOException ioe) { // exception handler
			System.err.println("IO Exception");
			System.exit(1);
			return -1f;
		}
	}
	
	/**
	 * Connect to the NXT
	 */
	private void connect() {
		try {
			NXTComm nxtComm = NXTCommFactory.createNXTComm(NXTCommFactory.USB); //USB initiator
			/* Uncomment next line for Bluetooth communication */
			//NXTComm nxtComm = NXTCommFactory.createNXTComm(NXTCommFactory.BLUETOOTH); // Bluetooth initiator		
			NXTInfo[] nxtInfo = nxtComm.search(Master.NXT_ID); //find the available bricks with this NXT_ID by doing a Bluetooth inquiry
			
			if (nxtInfo.length == 0) {
				System.err.println("NO NXT found");
				System.exit(1);
			}
			
			if (!nxtComm.open(nxtInfo[0])) {
				System.err.println("Failed to open NXT");
				System.exit(1);
			}
			
			dis = new DataInputStream(nxtComm.getInputStream()); // open data input stream 
			dos = new DataOutputStream(nxtComm.getOutputStream()); // open data output stream
			
		} catch (NXTCommException e) { // exception handler
			System.err.println("NXTComm Exception: "  + e.getMessage());
			System.exit(1);
		}
	}		
	/**
	 * Terminate the program and send stop command to the robot
	 */
	private void close() {
		try {
			dos.writeByte(STOP);
			dos.writeFloat(0f);
			dos.flush();
			Thread.sleep(200);
			System.exit(0);
		} catch (Exception ioe) {
			System.err.println("IO Exception");
		}
	}	
	
	public static void main(String[] args) {
		byte cmd = 0; float param = 0f; float ret=0f; 
		Master master = new Master();
		master.connect();
	    Scanner scan = new Scanner( System.in ); // create object to read from keyboard	    
	    while(true) {
	    	System.out.print("Enter command [0:Square 1:Battery Level 2:Stop]: ");
	    	cmd = (byte) scan.nextFloat(); // read float and converts it to byte
	    	if (cmd == 0) { 
	    	 System.out.print("Enter square size in cm [float]: ");
	    	 param = scan.nextFloat();
	    	} else {
	    		param = 0;	    		
	    	}
	    	ret = master.sendCommand(cmd, param);
	    	System.out.println("Command: " + cmd + " param: " + param + " return: " + ret); // print 
	    }
	}

}
