import lejos.geom.*;
import lejos.robotics.mapping.LineMap;

public class mapaCorredor{

  public static void main(String[] args){
    Line[] lines = {
      /* 1o Quadrante */	
      /* L-shape polygon */
      new Line(45,524,245,330),
      new Line(245,330,347,425),
      new Line(260,505,347,425),
      new Line(260,505,415,665),
      new Line(305,775,415,665),
      new Line(305,775,45,524),
      /* Open Pentagon */
      new Line(740,-135,515,0),
      new Line(610,247,515,0),
      new Line(610,247,880,284),
      new Line(940,0,880,284),
      /* Triangle */
      new Line(850,545,1130,510),
      new Line(1009,850,1130,510),
      new Line(1009,850,850,545),

	  /* 2o Quadrante */	
      /* Open Pentagon */
      new Line(1535,573,1715,375),
      new Line(1935,550,1715,375),
      new Line(1935,550,1900,795),
      new Line(1630,780,1900,795),
      /* Triangle */
      new Line(1390,-101,1550,190),
      new Line(1210,263,1550,190),
      new Line(1210,263,1390,-101),
 
 	  /* 3o Quadrante */	
      /* L-shape polygon */
      new Line(1940,20,2310,130),
      new Line(2210,400,2310,130),
      new Line(2210,400,2009,360),
      new Line(2125,260,2009,360),
      new Line(2125,260,1885,175),
      new Line(1940,20,1885,175),
      /* Open L-shape polygon */
      new Line(2200,830,2255,685),
      new Line(2370,730,2255,685),
      new Line(2370,730,2443,510),   
      new Line(2600,560,2443,510), 
      new Line(2600,560,2455,908),
      /* Open Pentagon */
      new Line(2960,260,2770,433),
      new Line(2580,253,2770,433),
      new Line(2580,253,2635,0),
      new Line(2895,0,2635,0)
    };
    //Rectangle(int x, int y, int width, int height)  -- always integer coordinates
    //Creates a rectangle with top left corner at (x,y) and with specified width and height.
    //Rectangle bounds = new Rectangle(0, -841, 1189, 841);  
    Rectangle bounds = new Rectangle(0, -140, 3020, 1200); 
    LineMap mymap = new LineMap(lines, bounds);

    try{
        mymap.createSVGFile("mapa.svg");
        mymap.flip().createSVGFile("mapaFlipY.svg"); //creates a fliped version in the Y-axis of the orginal image
    }catch (Exception e){
        System.out.print("Exception caught: ");
        System.out.println(e.getMessage());
    }
  }
}
