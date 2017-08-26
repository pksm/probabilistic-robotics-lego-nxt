
public class testeTacho {
    public static double EIXO = 11.2f; //Eixo entre rodas
    public static double D_RODA = 5.6f; //diametro da roda
    public double[] pose = new double[3];
    public double[] destino = new double[2];
    public double[] origem = new double[2];
    
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
    
 
    public static void main(String args[])  
    {
    	int resolucao = 10;
    	int c = 0;
    	double[] pose = {0, 0, 0};
    	double[] tmp = new double[3];
		
		testeTacho tt = new testeTacho();
    
    	while(c <= 3000) {
    		tmp = tt.End_pose(resolucao, 0);
    		for (int i = 0; i < 3; i++) {
    			pose[i] += tmp[i];
			}
    		c += resolucao;
    	}

    	c = 0;
    	while(c <= 3000) {
    		tmp = tt.End_pose(0, resolucao);
    		for (int i = 0; i < 3; i++) {
    			pose[i] += tmp[i];
			}
    		c += resolucao;
    	}

    	tt.pose = tt.End_pose(3000, 3000);
    	System.out.println("Seu valor "+ tt.pose[0] +" "+ tt.pose[1] +" "+  tt.pose[2]);

    	System.out.println("Meu valor "+ pose[0] +" "+ pose[1] +" "+  pose[2]);
        		//Button.waitForAnyPress();
    }
}
