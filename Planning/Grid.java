/*
Projeto 5 - Parte C - Mapa de Ocupação
Dado um lineMap transformar em um grid
*/

public class Grid {

    public int[][] matriz;
    private double size;
    private int x, y;

    /**
     * @brief Classe para desenhar segmentos em uma matriz
     * @details Vai criar na memoria um mapa de tamanho x por y onde
     * 
     * 
     * @param squareSize tamanho dos pontos
     * @param x comprimento vertical
     * @param y comprimento horizontal
     */
    public Grid (double squareSize, int x, int y) {
        matriz = new int[x][y];
        size = squareSize;
        this.x = x;
        this.y = y;
    }

    public void addLine (double ax, double ay, double bx, double by) {
        ax /= size;
        bx /= size;
        ay /= size;
        by /= size;
        double dx = bx - ax;
        double dy = by - ay;
        double tmp;
        if (Math.abs(dx) > Math.abs(dy)) {
            if (bx > ax) {
                tmp = bx;
                bx = ax;
                ax = tmp;
                tmp = by;
                by = ay;
                ay = tmp;
            }
            for (int cx = (int) (bx+0.5); cx < ax; cx++) {
                int cy = (int)(ay + dy * (cx - ax) / dx);
                if (cx >= 0 && cx < x && cy >= 0 && cy < y)
                    matriz[cx][cy] = 1;
            }
        } else {
            if (by > ay) {
                tmp = bx;
                bx = ax;
                ax = tmp;
                tmp = by;
                by = ay;
                ay = tmp;
            }
            for (int cy = (int) (by+0.5); cy < ay; cy++) {
                int cx = (int)(ax + dx * (cy - ay) / dy);
                if (cy >= 0 && cy < y && cx >= 0 && cx < x)
                    matriz[cx][cy] = 1;
            }
        }
    }

    public String toString() {
        String data = "";
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                data += matriz[i][j];
            }
            data += '\n';
        }
        return data;
    }

    public void alterGrid(double x, double y, int value) {
        x /= squareSize;
        y /= squareSize;
        matriz[(int) x][(int) y] = value;
    }

    public static void main(String[] args) {
        Grid grid =  new Grid(9.5, 120, 120);
        grid.addLine(170,437,60,680);
        grid.addLine(60,680,398,800);
        grid.addLine(398,800,450,677);
        grid.addLine(450,677,235,595);
        grid.addLine(235,595,281,472);
        grid.addLine(281,472,170,437);
        /* Triangle */
        grid.addLine(1070,815,770,602);
        grid.addLine(770,602,1060,516);
        grid.addLine(1070,815,1060,516);
        /* Pentagon */
        grid.addLine(335,345,502,155);
        grid.addLine(502,155,700,225);
        grid.addLine(700,225, 725,490);
        grid.addLine(725,490,480,525);
        grid.addLine(480,525,335,345);
        System.out.println(grid);
    }
}
