import lejos.geom.*;
import lejos.robotics.mapping.LineMap;
import lejos.nxt.Button;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.navigation.Navigator;
import lejos.robotics.navigation.Waypoint;
import lejos.nxt.Motor;
import lejos.robotics.localization.OdometryPoseProvider;
import lejos.robotics.navigation.Pose;
import lejos.nxt.comm.RConsole;

public class waypointRConsole{

  public static void main(String[] args){
    Line[] lines = {
      /* L-shape polygon */
      new Line(170,437,60,680),
      new Line(60,680,398,800),
      new Line(398,800,450,677),
      new Line(450,677,235,595),
      new Line(235,595,281,472),
      new Line(281,472,170,437),
      /* Triangle */
      new Line(1070,815,770,602),
      new Line(770,602,1060,516),
      new Line(1070,815,1060,516),

      // My trajectory
      new Line(263,350,450,593), //P8 to P7
      new Line(450,593,690,571)  //P7 to P6
    };
    Rectangle bounds = new Rectangle(0, 0, 1195, 920); 
    LineMap mymap = new LineMap(lines, bounds);

    try{
        mymap.createSVGFile("mapa.svg");
        //mymap.flip().createSVGFile("mapaFlipY.svg"); //creates a fliped version in the Y-axis of the orginal image
    }catch (Exception e){
        System.out.print("Exception caught: ");
        System.out.println(e.getMessage());
    }

    System.out.println("Any button to start");
    RConsole.openAny(0);
    //Button.waitForAnyPress();
    //DifferentialPilot p = new DifferentialPilot(wheelDiameter, trackWidth, leftMotor, rightMotor);
    DifferentialPilot p = new DifferentialPilot(5.6f, 11.2f, Motor.C, Motor.B);
    OdometryPoseProvider position = new OdometryPoseProvider (p);
    Navigator nav = new Navigator(p,position);
    Pose init = new Pose(26.3f,35f, 0f);
    position.setPose(init); //seta pose inicial para X:26.3 cm Y:35 cm THETA:0 graus 
    nav.addWaypoint(45f,59.3f);
    nav.addWaypoint(69f,57.1f);
    nav.followPath();
    System.out.println("First point "+nav.getWaypoint());
    //System.out.println("Any button to halt");
    while(!nav.pathCompleted());
    RConsole.close();
    //Button.waitForAnyPress();
  }
}
