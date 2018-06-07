
package domain;

import java.awt.Image;
import java.util.ArrayList;

public class Character extends Thread {

 private int positionX;
 private int positionY;        
 private Image playerImage;
 private int numImage;
 private ArrayList<Image> sprite;
 private int [][] path;
 private String playerName;
 private String type;
 private int speed;  

 // le quite el array del parametro ya que se le asigna dentro de cada clase del personaje
    public Character(int positionX, int positionY, int numImg,int[][] path, String name, String type, int speed) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.numImage = numImg;
        this.sprite = new ArrayList<Image>();
        this.path = path;
        this.playerName = name;
        this.type = type;
        this.speed = speed;
    }
    public Character() {
        this.positionX = 0;
        this.positionY = 0;
        this.playerImage = null;
        this.sprite = null;
        this.path = null;
        this.playerName = "";
        this.type = "";
        this.speed = 0;
    }

    public int getPositionX() {
        return positionX;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    public Image getPlayerImage() {
        return playerImage;
    }

    public void setPlayerImage(Image playerImage) {
        this.playerImage = playerImage;
    }

    public ArrayList<Image> getSprite() {
        return sprite;
    }

    public void setSprite(ArrayList<Image> sprite) {
        this.sprite = sprite;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int[][] getPath() {
        return path;
    }

    public void setPath(int[][] path) {
        this.path = path;
    }
    
    
    
}