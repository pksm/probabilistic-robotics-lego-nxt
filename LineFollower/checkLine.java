import lejos.nxt.Button;
import lejos.nxt.LightSensor;
import lejos.nxt.MotorPort;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.SensorPort;
import lejos.nxt.*;
 
public class checkLine {
        public static void main(String [] args){
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
        	NXTMotor mB = new NXTMotor(MotorPort.B);
        	NXTMotor mC = new NXTMotor(MotorPort.C);
        	while(true){
        		//LCD.drawInt(light.getLightValue(), 4, 0, 0);
        		int Lval = light.getLightValue();
        		int error = Lval - offset;
        		derivative = error - lastError;
        		integral += error;
        		int turn = (Kp*error) + (Ki*integral) + (Kd*derivative);
        		turn /= 100;
        		int powB = Tp-turn;
        		int powC = Tp + turn;
        		mB.setPower(powB);
        		mC.setPower(powC);
        		lastError = error;
        	}
        }
}

