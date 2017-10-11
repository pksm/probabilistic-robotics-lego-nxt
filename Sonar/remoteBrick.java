import lejos.robotics.navigation.DifferentialPilot;
import lejos.nxt.Motor;
import lejos.util.Delay;

public class remoteBrick {


    public static void main(String[] args){
        Delay.msDelay(2000);
        DifferentialPilot pilot = new DifferentialPilot(5.6f, 11.2f, Motor.B, Motor.A);
        pilot.travel(50);         // cm
        pilot.rotate(-90);        // degree clockwise
        pilot.travel(-50,true);  //  move backward for 50 cm
    }
}
