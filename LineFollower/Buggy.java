import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.LightSensor;
import lejos.nxt.MotorPort;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;
import lejos.util.Delay;
import lejos.nxt.*;
import lejos.util.Stopwatch;
 
public class Buggy {
    public static double EIXO = 11.2f; //Eixo entre rodas
    public static double D_RODA = 5.6f; //diametro da roda
    public double[] pose = new double[3];
    public double[] destino = new double[2];
    public double[] origem = new double[2];
    public NXTMotor mB = new NXTMotor(MotorPort.B);
	public NXTMotor mC = new NXTMotor(MotorPort.C);
    
    public LightSensor light = new LightSensor(SensorPort.S4);
    
    public Buggy(double xo, double xf, double yo, double yf){
    	this.origem[0] = xo;
    	this.origem[1] = yo;
    	this.destino[0] = xf;
    	this.destino[1] = yf;
    }
    
    public static double rot_to_dist (double rotations) {
		return Math.toRadians(rotations) * (D_RODA/2.0f);
    } 

    public static double delta_mov (double rot_L, double rot_R) {
		return (rot_to_dist(rot_L) + rot_to_dist(rot_R))/2.0f;
    }

    private static double delta_theta (double rot_L, double rot_R) {
		return (rot_to_dist(rot_R) - rot_to_dist(rot_L)) / EIXO;
    }
    private static double delta_x (double I_Theta, double rot_L, double rot_R) {
		double Ds = delta_mov(rot_R, rot_L);
		double DTheta = delta_theta(rot_L, rot_R);
		return  Ds * Math.cos(I_Theta + DTheta/2.0f); 
    }

    private static double delta_y (double I_Theta, double rot_L, double rot_R) {
		double Ds = delta_mov(rot_R, rot_L);
		double DTheta = delta_theta(rot_L, rot_R);
		return  Ds * Math.sin(I_Theta + DTheta/2.0f); 
    }
    
    public double[] End_pose (double rot_R, double rot_L) {
    	//theta zero como zero...hardcoded
		double[] coord = new double[3];
		coord[0] = this.origem[0] + delta_x(0.0, rot_L, rot_R);
		coord[1] = this.origem[1] + delta_y(0.0, rot_L, rot_R);
		coord[2] = 0.0 + delta_theta(rot_L, rot_R);
		return coord;
    }
    
    private boolean line(double x, double y)
    {
    	double m = (this.destino[1] - this.origem[1]) / (this.destino[0] - this.origem[0]);
    	double y_calc = m * (x - this.origem[0] + this.origem[1]);
    	double erro = 0.25;
    	return ((y_calc - y) < erro);
    }
    
    private void control()
    {
//    	int pk;
//    	int tp;
//    	int pi;
//    	int pd;
    	while(true){
    	if (this.light.getLightValue() < 45){
    		System.out.println("teste");
    		pid(25,240,0,25);
    	}
    	else if(this.isGoal())
    	{
    		System.out.println("oi");
    		this.mC.stop();
    		this.mB.stop();		
    	}
    	else{ //pode virar else-if se fizermos variacoes do comando de saida do pid
    		System.out.println("oii3");
    		fw(25);
    	}
    	}
    }
    
    private boolean isGoal() {
    	double epsilon = 0.25;
    	return (((this.destino[0] - this.pose[0]) + (this.destino[1] - this.pose[1])) * ((this.destino[0] - this.pose[0]) + (this.destino[1] - this.pose[1])) < epsilon);
//    		return true;
//    	}
//		// TODO Auto-generated method stub
//		return false;
	}

	private void fw(int Tp)
    {
    	this.mB.setPower(Tp);
		this.mC.setPower(Tp);
		Delay.msDelay(200);
		this.pose = End_pose((double)this.mB.getTachoCount(),(double)this.mC.getTachoCount());
		return;
    }
    
    private void pid(int Tp, int Kp,int Ki, int Kd)
    {
    	//int Kp = 240;
    	//int Ki = 0;
    	//int Kd = 25;
    	//int Tp = 25;
    	int white = 54;
    	int black = 32;
    	int offset = (white+black)/2;
    	int lastError = 0;
    	int derivative = 0;
    	int integral = 0;
    	
    	//NXTMotor mB = new NXTMotor(MotorPort.B);
    	//NXTMotor mC = new NXTMotor(MotorPort.C);
    	//Stopwatch s1 = new Stopwatch();
    	while(true){ //chamar funcao teste
    		System.out.println(""+light.getLightValue());
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
    		this.pose = End_pose((double)mB.getTachoCount(),(double)mC.getTachoCount());
    		// Need to think if its better to compare Ks or pass as parameter
    		if (this.line(this.pose[0],this.pose[1]) && (Kp+Ki+Kd)>0){
    			return;
    		}
    	}
    }
    public static void main(String args[])  
    { 
    			//double or[2] = new or[0.0,0.0];
        		Buggy robo = new Buggy(0.0,0.0,30.0,20.0);
        		robo.control();
        		//Button.waitForAnyPress();
    }
}
