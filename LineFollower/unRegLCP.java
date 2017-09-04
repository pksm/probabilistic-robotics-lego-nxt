//import lejos.nxt.Motor;
import lejos.nxt.remote.RemoteMotor
import lejos.nxt.Button;
import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;

public class unRegLCP{

	public static void main(String args[]){
			int Kp = 240;
        	int Ki = 0;
        	int Kd = 25;
        	int white = 54;
        	int black = 32;
        	int offset = (white+black)/2;
        	int lastError = 0;
        	int derivative = 0;
        	int Tp = 25;
        	int integral = 0;
        	LightSensor light = new LightSensor(SensorPort.S4);
        	
        	RemoteMotor rB = Motor.B;
        	RemoteMotor rC = Motor.C;

        	rB.setRegulationMode(0);
        	rC.setRegulationMode(0);
        	//Motor.B.setRegulationMode(0);
        	//Motor.C.setRegulationMode(0);
        	while(true){
        		int Lval = light.getLightValue();
        		int error = Lval - offset;
        		derivative = error - lastError;
        		integral += error;
        		int turn = (Kp*error) + (Ki*integral) + (Kd*derivative);
        		turn /= 100;
        		int powB = Tp-turn;
        		int powC = Tp + turn;
        		//Motor.B.setPower(powB);
        		//Motor.C.setPower(powC);
        		rB.setPower(powB);
        		rC.setPower(powC);
        		lastError = error;
        	}
	}
}
