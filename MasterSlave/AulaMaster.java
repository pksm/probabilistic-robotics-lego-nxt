import lejos.pc.comm.*;
import java.util.Scanner;
import java.io.*;

public class AulaMaster {
	private static final byte FORWARD = 0;
	private static final byte STOP = 1;
	private static final byte EXIT = 2;
	
	private NXTComm nxtComm; 
	private DataOutputStream dos; 
	private DataInputStream dis; 
	
	private static final String NXT_ID = "NXT12"; // NXT BRICK NAME

	private float sendCommand(byte command, float param) { 
		try {
			dos.writeByte(command); 
			dos.writeInt(param); 
			dos.flush();
			return dis.readFloat();
		} catch (IOException ioe) { 
			System.err.println("IO Exception");
			System.exit(1);
			return -1f;
		}
	}
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
	private void close() {
		try {
			dos.writeByte(EXIT);
			dos.writeFloat(0f);
			dos.flush();
			Thread.sleep(200);
			System.exit(0);
		} catch (Exception ioe) {
			System.err.println("IO Exception");
		}
	}	
	
	public static void main(String[] args) {
		byte cmd = 0; float param = 0; float ret=0f; 
		AulaMaster master = new AulaMaster();
		master.connect();
	    Scanner scan = new Scanner( System.in ); 	    
	    while(true) {
	    	System.out.print("Enter command [0:Forward 1:Stop 2:Exit]: ");
	    	cmd = (byte) scan.nextFloat(); // read float and converts it to byte
	    	if (cmd == 0) { 
	    	 System.out.print("Set speed: ");
	    	 param = scan.nextFloat();
	    	} 
	    	else param = 0f;
	    	ret = master.sendCommand(cmd, param);
	    	System.out.println("Command: " + cmd + " param: " + param + " return: " + ret); // print 
	    }
	}

}