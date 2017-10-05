import lejos.pc.comm.*;
import java.util.Scanner;
import java.io.*;

public class MasterSonar {
	private static final byte TRAVEL = 0; 
	private static final byte ROTATE = 1;
	private static final byte FULL_SCAN = 2; 
	private static final byte SINGLE_SCAN = 3; 
	private static final byte EXIT = 4; 
	
	private NXTComm nxtComm;
	//private DataOutputStream dos;
	//private DataInputStream dis;	
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	
	private static final String NXT_ID = "NXT06"; // NXT BRICK ID

	private float sendCommand(byte command, int param) throws Exception{ // used by functions 0 and 1
		try {
			oos.writeByte(command);
			oos.writeInt(param);
			oos.flush();
			return ois.readFloat();
		} catch (IOException ioe) {
			System.err.println("IO Exception");
			System.exit(1);
			return -1f;
		}
	}
	private Object sendCommand(byte command) throws Exception { //used by functions 2 and 3
		try {
			oos.writeByte(command);
			oos.flush();
			return ois.readObject();
		} catch (IOException ioe) {
			System.err.println("IO Exception");
			System.exit(1);
			return false;
		}
	}

	private void connect() throws IOException {
		try {
			NXTComm nxtComm = NXTCommFactory.createNXTComm(NXTCommFactory.USB);
			/* Uncomment next line for Bluetooth communication */
			//NXTComm nxtComm = NXTCommFactory.createNXTComm(NXTCommFactory.BLUETOOTH);			
			NXTInfo[] nxtInfo = nxtComm.search(MasterSonar.NXT_ID);
			
			if (nxtInfo.length == 0) {
				System.err.println("NO NXT found");
				System.exit(1);
			}
			
			if (!nxtComm.open(nxtInfo[0])) {
				System.err.println("Failed to open NXT");
				System.exit(1);
			}
			
			ois = new ObjectInputStream(nxtComm.getInputStream());
			oos = new ObjectOutputStream(nxtComm.getOutputStream());
			
		} catch (NXTCommException e) {
			System.err.println("NXTComm Exception: "  + e.getMessage());
			System.exit(1);
		}
	}		

	private void close() throws Exception{
		try {
			oos.writeByte(EXIT);
			//oos.writeFloat(0f);
			//oos.writeFloat(0f);
			oos.flush();
			Thread.sleep(200);
			System.exit(0);
		} catch (Exception ioe) {
			System.err.println("IO Exception");
		}
	}	
	public static void main(String[] args) throws Exception{
		byte cmd = 0; int param = 0; float ret=0f; double[] readings; int singleRead = -1;
		MasterSonar master = new MasterSonar();
		master.connect();
	    Scanner scan = new Scanner( System.in );	    
	    while(true) {
	    	System.out.print("Enter command [0:TRAVEL 1:ROTATE 2:FULL_SCAN 3:SINGLE_SCAN 4:EXIT]: ");
	    	cmd = (byte) scan.nextFloat(); 
	    	if (cmd == 0 || cmd == 1){
	    		System.out.println("Enter integer parameter: ");
	    		param = scan.nextInt();
	    		ret = master.sendCommand(cmd, param); 
	    		System.out.println("cmd: " + cmd + " Parameter: " + param +" return: " + ret);
	    		param = 0;
	    	} 
	    	if (cmd == 2){
	    		readings = (double[]) master.sendCommand(cmd);
	    		System.out.println("cmd: " + cmd + "Sonar Values...");
	    		for (int i=0; i < readings.length; i++){
	    			System.out.println("N." + i + "Value: "+ readings[i]);
	    		}
	    	}
	    	else{
	    		singleRead = (int) master.sendCommand(cmd);
	    		System.out.println("Value: "+ singleRead);
	    	}
	    }
	}

}
