/*
Projeto 5 - Parte C - Mapa de Ocupação
Dado um lineMap transformar em um grid
*/

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.List;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
//
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.File;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Image;

public class lineToGrid{
  
  public static int sizeX;
  public static int sizeY;
  public int width;
  public int height;
  public int cellSize;
  public int[][] map; 

  public lineToGrid(int w, int h, int cs)
	{
		this.width = w;
		this.height = h;
		this.cellSize = cs;
		this.sizeX = (int)Math.ceil((float)w/cs);
		this.sizeY = (int)Math.ceil((float)h/cs);
  		this.map = new int[this.sizeX][this.sizeY];
	}

  private void buildGrid(){ 
  		for (int row = 0; row < this.sizeX; row++ )
     		for (int column = 0; column < this.sizeY; column++)
     		{
        		this.map[row][column] = 0; //initialize with 999
    		}
  }

  private void printGrid(){
  	for (int row = 0; row < this.sizeX; row++ ){
		for (int column = 0; column < this.sizeY; column++)
		{
			System.out.print(" "+ this.map[row][column]);
		}
		System.out.println("");
	  }
  }
  private void alterGrid(int i, int j, int val){
  		this.map[i][j] = val;
  }
  private void alterGrid(Point p, int val){
  		this.map[p.x][p.y] = val;
  }
  private void setMap() throws IOException{
    BufferedImage im = new BufferedImage(this.sizeY, this.sizeX, BufferedImage.TYPE_BYTE_BINARY);

    int white = (255 << 16) | (255 << 8) | 255;
    int black = 0;

    for (int y = 0; y < this.sizeY; y++)
      for (int x = 0; x < this.sizeX; x++)
          im.setRGB(y, x, ((map[x][y]) == 1) ? black : white);

    Image scaledImage = im.getScaledInstance(this.sizeY * 100, this.sizeX * 100, Image.SCALE_SMOOTH);
    BufferedImage newImage = new BufferedImage(
        scaledImage.getWidth(null), scaledImage.getHeight(null),
        BufferedImage.TYPE_BYTE_BINARY);
    Graphics2D g = newImage.createGraphics();
    g.drawImage(scaledImage, 0, 0, null);
    g.dispose();
    File outputfile = new File("checker.png");
    ImageIO.write(newImage, "png", outputfile);
  }


  public static void main(String[] args) throws IOException {
  	lineToGrid oc = new lineToGrid(10,16,2);
  	oc.buildGrid();
    oc.alterGrid(1,2,1);
    oc.alterGrid(2,2,1);
    oc.alterGrid(3,2,1);
    oc.alterGrid(3,3,1);

    oc.setMap();
    oc.printGrid();
    
  	
    // Line[] lines = {
    //   /* L-shape polygon */
    //   new Line(170,437,60,680),
    //   new Line(60,680,398,800),
    //   new Line(398,800,450,677),
    //   new Line(450,677,235,595),
    //   new Line(235,595,281,472),
    //   new Line(281,472,170,437),
    //   /* Triangle */
    //   new Line(1070,815,770,602),
    //   new Line(770,602,1060,516),
    //   new Line(1070,815,1060,516),
    //   /* Pentagon */
    //   new Line(335,345,502,155),
    //   new Line(502,155,700,225),
    //   new Line(700,225, 725,490),
    //   new Line(725,490,480,525),
    //   new Line(480,525,335,345)

    // };
    // Rectangle bounds = new Rectangle(0, 0, 1195, 920); 
    // LineMap mymap = new LineMap(lines, bounds);
    // mapToGrid(1195,920,5); //testar com (20,20,5) -- esperado uma discretizacao de 4x4 celulas
    // try{
    //     mymap.createSVGFile("mapa.svg");
    //     mymap.flip().createSVGFile("mapaFlipY.svg"); //creates a fliped version in the Y-axis of the orginal image
    // }catch (Exception e){
    //     System.out.print("Exception caught: ");
    //     System.out.println(e.getMessage());
    // }
  }
}
