package domain;

import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Random;
import javax.swing.JPanel;

public class Maze {

    private int row;
    private int colums;
    private BufferedImage imageWall;
    private int[][] mazeItems;
    private String MazeLevel;
    private BufferedImage door;
    private BufferedImage exit;

    public Maze(int row, int colums, BufferedImage imageWall, String hardMode, BufferedImage door, BufferedImage exit) {
        this.row = row;
        this.colums = colums;
        this.imageWall = imageWall;
        this.mazeItems = new int[row][colums];
        this.MazeLevel = hardMode;
        this.door= door;
        this.exit= exit;
    }

    public Maze() {
        this.row = 0;
        this.colums = 0;
        this.imageWall = null;
        this.mazeItems = null;
        this.MazeLevel = "";
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColums() {
        return colums;
    }

    public void setColums(int colums) {
        this.colums = colums;
    }

    public BufferedImage getImageWall() {
        return imageWall;
    }

    public void setImageWall(BufferedImage imageWall) {
        this.imageWall = imageWall;
    }

    public int[][] getMazeItems() {
        return mazeItems;
    }

    public void setMazeItems(int[][] mazeItems) {
        this.mazeItems = mazeItems;
    }

    public String getMazeLevel() {
        return MazeLevel;
    }

    public void setMazeLevel(String MazeLevel) {
        this.MazeLevel = MazeLevel;
    }

    public BufferedImage getDoor() {
        return door;
    }

    public void setDoor(BufferedImage door) {
        this.door = door;
    }

    public BufferedImage getExit() {
        return exit;
    }

    public void setExit(BufferedImage exit) {
        this.exit = exit;
    }
    
    
    

    public int[][] fillMaze() {

        if (MazeLevel.equalsIgnoreCase("easy")) {
            int[][] mazeFilled = {
            {3, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {1, 1, 1, 1, 1, 0, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 0, 1, 1, 1, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {1, 0, 1, 1, 1, 1, 1, 1, 1, 0},
            {1, 0, 1, 1, 1, 1, 1, 1, 1, 0},
            {1, 0, 1, 0, 0, 0, 0, 1, 1, 0},
            {1, 0, 0, 0, 1, 1, 0, 0, 0, 0},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 4}
            };

            return mazeFilled;
        }
        if (MazeLevel.equalsIgnoreCase("normal")) {
            int[][] mazeFilled = {
            {3, 1, 0, 0, 0, 0, 0, 0, 1, 0, 1},
            {0, 0, 1, 1, 1, 1, 1, 0, 1, 0, 0},
            {1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1},
            {1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 1, 1, 0, 1, 1, 0, 1, 0, 0},
            {1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0},
            {1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0},
            {1, 0, 0, 1, 1, 1, 0, 0, 1, 0, 1},
            {1, 1, 0, 0, 0, 0, 0, 1, 1, 0, 0},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 4}
            };
            return mazeFilled;
        }

        if (MazeLevel.equalsIgnoreCase("hard")) {

            int[][] mazeFilled = {
            {3, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 3},
            {0, 0, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1},
            {1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0},
            {1, 0, 0, 1, 1, 1, 0, 1, 1, 0, 1, 0},
            {1, 0, 1, 1, 1, 1, 0, 1, 0, 0, 0, 0},
            {1, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 1},
            {1, 1, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0},
            {1, 0, 0, 1, 1, 1, 1, 0, 1, 1, 0, 1},
            {1, 0, 1, 1, 1, 0, 0, 0, 0, 1, 0, 0},
            {1, 0, 1, 1, 0, 0, 1, 1, 1, 1, 0, 1},
            {0, 0, 1, 1, 0, 1, 1, 0, 0, 0, 0, 1},
            {1, 1, 1, 4, 0, 1, 1, 4, 1, 1, 0, 0},
            
            };
            return mazeFilled;
        }
        return null;
    }

}
