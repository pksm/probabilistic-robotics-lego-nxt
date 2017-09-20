/*
Projeto 5 - Parte C - Mapa de Ocupação
Dividir para conquistar:
- método para busca em largura (parâmetros: init, final, grafo   saída: plano)
- fazer grid do mapa de linhas (discretização)
- 

*/
import lejos.geom.*;
import lejos.robotics.mapping.LineMap;
import java.util.ArrayList;
import java.util.Arrays;

public class occupGrid{

  public void mapToGrid(int w, int h, int cellSize){ //width and height
  		int sizeX = (int) Math.ceil((float)w / cellSize);
  		int sizeY =(int) Math.ceil((float)h / cellSize);
  		int [][] map = new int [sizeX][sizeY];


  }


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
      /* Pentagon */
      new Line(335,345,502,155),
      new Line(502,155,700,225),
      new Line(700,225, 725,490),
      new Line(725,490,480,525),
      new Line(480,525,335,345)

    };
    Rectangle bounds = new Rectangle(0, 0, 1195, 920); 
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
