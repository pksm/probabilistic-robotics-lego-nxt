/*
Projeto 5 - Parte C - Mapa de Ocupação
Dividir para conquistar:
- método para busca em largura (parâmetros: init, final, grafo   saída: plano)
- fazer grid do mapa de linhas (discretização)
- método da busca em largura, frente de onda 
*talvez colocar os bounds do grid como atributos da classe occupGrid*
*/
//import lejos.geom.*;
//import lejos.robotics.mapping.LineMap;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.List;
import java.awt.Point;
import java.util.PriorityQueue; 
import java.util.ArrayList;

public class occupGrid{
  
  public static int sizeX;
  public static int sizeY;
  public static ArrayList<Point> actions; 

  public static int[][] mapToGrid(int w, int h, int cellSize){ //width and height
  		sizeX = (int) Math.ceil((float)w / cellSize);
  		sizeY =(int) Math.ceil((float)h / cellSize);
  		int [][] map = new int [sizeX][sizeY];
  		for (int row = 0; row < sizeX; row++ )
     		for (int column = 0; column < sizeY; column++)
     		{
        		map[row][column] = 999; //initialize with 999
    		}
    	return map;
  }

  public static void printGrid(int[][] map, int w, int h, int cellSize){
  	//int sizeX = (int) Math.ceil((float)w / cellSize);
  	//int sizeY =(int) Math.ceil((float)h / cellSize);
  	for (int row = 0; row < sizeX; row++ ){
		for (int column = 0; column < sizeY; column++)
		{
			System.out.print(" "+ map[row][column]);
		}
		System.out.println("");
	}
  }
  public static Set<Point> reachableCells(Point p){ // 4- neighboor  
  	Point cell = new Point(p);
  	Set<Point> neighboors = new HashSet<Point>();
  	for (int i = 0; i < actions.size(); i++) {
		if (((cell.x + actions.get(i).x) < sizeX) && ((cell.y + actions.get(i).y) < sizeY)){
			cell.translate(actions.get(i).x, actions.get(i).y);
			neighboors.add(cell);
			System.out.println("Actions: "+ cell); 
			cell = new Point(p);
		}
	}
	return neighboors;
  	//System.out.println("Actions: "+ Arrays.toString(actions.toArray())); print actions
  }

  public static void bfs(int[][] grid, int gx, int gy, int sx, int sy){ //gx, gy - goal coordinates --- sx, sy - start coordinates
  	Set<Point> explored = new HashSet<Point>(); //creates explored set of points
  	Set<Point> inQueue = new HashSet<Point>(); //create inQueue set of points that are in the queue but were not explored
  	Set<Point> nextCell;
  	PriorityQueue<Node> fringe = new PriorityQueue<Node>(20); // creates queue of nodes
  	Point goal = new Point(gx, gy);
  	Point init = new Point(sx,sy);
  	Node nodeParent = new Node(goal); //wavefront starting from goal
  	Node nodeSon;
  	boolean foundInit = false;
  	fringe.add(nodeParent);
  	inQueue.add(goal);
  	alterGrid(grid, goal, 0);
  	while (!foundInit && !fringe.isEmpty()){
  		nodeParent = fringe.poll();
  		inQueue.remove(nodeParent.getState());
  		explored.add(goal);
  		if (nodeParent.getState() == init){
  			foundInit = true;
  			alterGrid(grid,nodeParent.getState().x,nodeParent.getState().y,nodeParent.getPathCost());
  			break;
  		}
  		nextCell = reachableCells(nodeParent.getState());
  		for (Point p : nextCell) {
			if (!explored.contains(p) && !inQueue.contains(p)){
				nodeSon = new Node(p,nodeParent,1);
				fringe.add(nodeSon);
				inQueue.add(p);
				alterGrid(grid,p.x,p.y,nodeSon.getPathCost());
				//System.out.println(""+nodeSon.toString());
				// TENHO QUE CRIAR UM COMPARATOR PARA O PRIORITYQUEUE
			}
		}
		nextCell.clear();
	}

  }

 /*
	Any operations that alterGrid performs on mapRef are such that, for all practical purposes, they are performed on map itself 
	(except when mapRef is changed to point to a different int[][] instance like mapRef = new int [sizeX][sizeY]) 
 */
  public static void alterGrid(int [][] mapRef, int i, int j, int val){
  		mapRef[i][j] = val;
  }
  public static void alterGrid(int [][] mapRef, Point p, int val){
  		mapRef[p.x][p.y] = val;
  }


  public static void main(String[] args){
  	actions = new ArrayList<Point>(); //4 - neighboor
 	Point act = new Point(-1,0); // North
  	actions.add(act);
  	act = new Point(1,0); // South
  	actions.add(act);
  	act = new Point(0,1); // East
  	actions.add(act);
  	act = new Point(0,-1); // West
  	actions.add(act);
  	int [][] map = mapToGrid(10,16,2);
  	Point obs = new Point(); 
  	//setting obstacles
  	alterGrid(map,1,2,-1);
  	alterGrid(map,2,2,-1);
  	alterGrid(map,3,2,-1);
  	alterGrid(map,3,3,-1);
  	//exemplo de como usar a classe Point
  	obs.setLocation(2,5); 
  	alterGrid(map,obs.getLocation(),-1);
  	//
  	alterGrid(map,1,5,-1);
  	alterGrid(map,1,6,-1);
  	alterGrid(map,2,6,-1);
  	//
  	bfs(map,4,7,0,0);
  	printGrid(map,10,16,2);
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
