import lejos.nxt.Button;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.LCD;
import lejos.nxt.SensorPort;
import lejos.util.Delay;
import lejos.nxt.comm.RConsole;
 
public class checkLine {
        public static void main(String [] args){
        	LightSensor light = new LightSensor(SensorPort.S4);
         LCD.drawString("Teste sensor optico", 0, 0);
         LCD.drawString("Posicione o robo na linha e pressione uma tecla para prosseguir",2,0);
         LCD.clear();
         Button.waitForAnyPress();
         LCD.drawString("Sensor sobre a linha preta", 0, 0); //pos inicial - sem rotacionar
         LCD.drawInt(light.getLightValue(), 4, 0, 0); //pos inicial - sem rotacionar
         Button.waitForAnyPress();
         LCD.clear();
         LCD.drawString("Sensor sobre o branco", 0, 0); //pos inicial - sem rotacionar
         Motor.A.rotate(+, true);
         Motor.B.rotate(-);
         LCD.drawInt(light.getLightValue(), 4, 0, 0); // rotacionando em 20 graus para a direita
         Button.waitForAnyPress();
         LCD.clear();
         LCD.drawString("Leitura continua...", 0, 0);
        	while(true){
          Motor.A.rotate(+, true);
          Motor.B.rotate(-,true);
          while(Motor.A.isMoving() || Motor.B.isMoving()) {
             Delay.msDelay(80);
             LCD.drawInt(light.getLightValue(),4,0,0);
           }
          Delay.msDelay(200);
          Motor.A.rotateTo(+, true);
          Motor.B.rotateTo(-,true);
          while(Motor.A.isMoving() || Motor.B.isMoving()) {
             Delay.msDelay(80);
             LCD.drawInt(light.getLightValue(),4,0,0);
           }
          Delay.msDelay(200);
        	}
}

