/*
Projeto 5 - Parte C - Mapa de Ocupação
Dado um lineMap transformar em um grid
*/
        
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.File;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Image;

public class Grid {

    public boolean[][] matriz;
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
        matriz = new boolean[x][y];
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
                    matriz[cx][cy] = true;
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
                    matriz[cx][cy] = true;
            }
        }
    }

    public String toString() {
        String data = "";
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                data += matriz[i][j] ? 1: 0;
            }
            data += '\n';
        }
        return data;
    }

    public void alterGrid(double x, double y, boolean value) {
        x /= size;
        y /= size;
        matriz[(int) x][(int) y] = value;
    }

    public void savePNG (String name, int sacale) throws IOException {
        int type = BufferedImage.TYPE_BYTE_BINARY;
        BufferedImage im = new BufferedImage(x, y, type);

        int white = (255 << 16) | (255 << 8) | 255;
        int black = 0;     

        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                int color = matriz[i][j] ? black : white;
                im.setRGB(i, j, color);
            }
        }

        Image scaledImage = im.getScaledInstance(x * sacale, y * sacale, Image.SCALE_SMOOTH);
        BufferedImage newImage = new BufferedImage(
            scaledImage.getWidth(null),
            scaledImage.getHeight(null),
            BufferedImage.TYPE_BYTE_BINARY
        ); 
        Graphics2D graph = newImage.createGraphics();
        graph.drawImage(scaledImage, 0, 0, null);
        graph.dispose(); 
        File outputfile = new File(name+".png");
        ImageIO.write(newImage, "png", outputfile);
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
        try {
            grid.savePNG ("checker", 2);
        } catch (IOException e) {
            System.out.println("Error to save image.");
        }
    }
}
