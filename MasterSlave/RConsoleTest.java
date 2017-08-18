import lejos.nxt.Motor;
import lejos.nxt.comm.RConsole;
import lejos.util.Delay;

public class RConsoleTest {
	
	public static void main(String[] args) throws Exception {
		RConsole.openAny(0); //ver na documentação oq OpenAny significa
		
		Motor.A.rotate(1440,true);

		while (Motor.A.isMoving()){
			RConsole.println(""+Motor.A.getTachoCount());
			Delay.msDelay(200);
		} 

		RConsole.close();
	}

}