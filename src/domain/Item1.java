
package domain;


import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;


public class Item1 extends Item {

    private int tired = 0;
    
    private int speed;
    private int posY;
    private int posX;
    private String type;
    private boolean arriba = false, abajo = false, adelante = false, atras = false;
    Logic logic;
    int indice = 0;
    JPanel jpanel;
    String name = "";
    LinkedList positionList;
    int[][] numMatriz;
    Rectangle2D[][] rec2;

    public Item1(int posX, int posY, int numImage,
            int[][] matrizObject, String name, String type, int speed, JPanel jpanel,Rectangle2D[][] rec2) throws IOException {
        //constructor del hilo
        super(posX, posY, numImage, matrizObject, name, type, speed);
        setSprite();
        this.posX = posX;
        this.posY = posY;
        
        this.speed = speed;
        logic = new Logic();
        this.jpanel = jpanel;
        this.name = name;
        this.positionList = positionList;
        this.type = type;
        this.numMatriz = matrizObject;
        this.rec2 = rec2;
    }

   

   

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;

    }

   

    public void setSprite() throws FileNotFoundException, IOException {
        ArrayList<Image> sprite = super.getSprite();
       

            BufferedImage buffer1 = ImageIO.read(new FileInputStream("src/Img/Item.png"));
            sprite.add(buffer1);
        
        super.setSprite(setSprite2());
    }

    public ArrayList setSprite2() throws FileNotFoundException, IOException {
        ArrayList<Image> sprite = super.getSprite();
        

            BufferedImage buffer1 = ImageIO.read(new FileInputStream("src/Img/Item.png"));
            sprite.add(buffer1);
        
        return sprite;
    }

    @Override
    public void run() {
        try {
            
            ArrayList<Image> sprite = super.getSprite();
            super.setPlayerImage(sprite.get(1));
            this.setPositionX(this.posX);
            this.setPositionY(this.posY);
            
            int coordenadaX =1;
            int coordenadaY = 1;
            for (int i = 0; i < rec2.length; i++) {
                for (int j = 0; j < rec2.length; j++) {
                    
                   if (rec2[i][j].contains(this.getPositionX(), this.getPositionY())&& numMatriz[i][j] != 1&&numMatriz[i][j] != 3&&numMatriz[i][j] != 4) { 
                      coordenadaX = i;
            coordenadaY = j; 
                }
            }
            }
//            for (int i = 0; i < mazeStructure.length; i++) {
//                for (int j = 0; j < mazeStructure.length; j++) {
//                    if (mazeStructure[i][j].contains(cordenatesX2, cordenatesY2)&& num[i][j] != 1&&num[i][j] != 3&&num[i][j] != 4) {
//                        try {
//                            
//                            //Item1 item = new Item1(0, (int)mazeStructure[i][j].getX(), (int)mazeStructure[i][j].getY(), 0, getNum(), "Kevin", "fast", 120, this);
//                            Item1 item = new Item1((int)mazeStructure[i][j].getX(), (int)mazeStructure[i][j].getY(), 0, getNum(), "Kevin", "fast", 120, this,mazeStructure);
//                            // Item1 i = new Item1(PROPERTIES, j, j, WIDTH, num, TOOL_TIP_TEXT_KEY, TOOL_TIP_TEXT_KEY, j, this);
//                            addCharacter(item, "item");
//                            repaint();
//                            run();
//                            
//                        } catch (IOException ex) {
//                            Logger.getLogger(MazeLevels.class.getName()).log(Level.SEVERE, null, ex);
//                        }
            
            

            int[][] maze = super.getPath();
            int limite = numMatriz.length - 1;
            boolean free[] = logic.freeSpace(maze, coordenadaX, coordenadaY);
            int random = 4;
            boolean tempD = free[0];
            boolean tempI = free[1];
            boolean tempA = free[2];
            boolean tempB = free[3];
            boolean meta = true;
            
            //recorre el hilo  siempre
            while (true) {

                try {
                    boolean back = false;
                    boolean front = false;
                    boolean down = false;
                    boolean up = false;
                    Thread.sleep(200);
                    maze = super.getPath();
                    free = logic.freeSpace(maze,coordenadaX, coordenadaY);

                    if (free[0] != tempD || free[1] != tempI || free[2] != tempA || free[3] != tempB) {
                        random = logic.random();
                    }
                    // adelante

                  // if(free[0])
                    

                    if (free[0] == true && random == 1 && front == false) {
                        for (int j = 0; j < 65; j++) {

                            super.setPlayerImage(sprite.get(0));
                            this.setPositionX(getPositionX() + 1);
                            this.setPositionY(this.getPositionY());

                            jpanel.repaint();
                        }
                      //  back = true;
                        coordenadaY++;
                        this.numMatriz[coordenadaX][coordenadaY - 1] = 0;
                        this.numMatriz[coordenadaX][coordenadaY] = 5;
                        tempD = free[0];
                        tempI = free[1];
                        tempA = free[2];
                        tempB = free[3];
                        
                        
                        //atras
                    } else if (free[1] == true && random == 2 && back == false) {
                        super.setSprite(setSprite2());
                        for (int j = 0; j < 65; j++) {

                            super.setPlayerImage(sprite.get(0));
                            this.setPositionX(this.getPositionX() - 1);
                            this.setPositionY(this.getPositionY());

                           
                            jpanel.repaint();
                        }
                       // front = true;
                        coordenadaY--;
                        this.numMatriz[coordenadaX][coordenadaY + 1] = 0;
                        this.numMatriz[coordenadaX][coordenadaY] = 6;
                        tempD = free[0];
                        tempI = free[1];
                        tempA = free[2];
                        tempB = free[3];
                        
                        
                        
                        // arriba
                    } else if (free[2] == true && random == 3 && down == false) {
                        for (int j = 0; j < 65; j++) {

                            super.setPlayerImage(sprite.get(0));
                            this.setPositionX(this.getPositionX());
                            this.setPositionY(this.getPositionY() - 1);

                            
                            jpanel.repaint();
                        }
                       // down = true;
                        coordenadaX--;
                        this.numMatriz[coordenadaX + 1][coordenadaY] = 0;
                        this.numMatriz[coordenadaX][coordenadaY] = 6;
                        tempD = free[0];
                        tempI = free[1];
                        tempA = free[2];
                        tempB = free[3];
                        
                        //abajo
                    } else if (free[3] == true && random == 4 && up == false) {
                        for (int j = 0; j < 65; j++) {

                            super.setPlayerImage(sprite.get(0));
                            this.setPositionX(this.getPositionX());
                            this.setPositionY(this.getPositionY() + 1);

                            
                            jpanel.repaint();
                           // up = true;
                        }
                        coordenadaX++;
                        this.numMatriz[coordenadaX - 1][coordenadaY] = 0;
                        this.numMatriz[coordenadaX][coordenadaY] = 6;
                        tempD = free[0];
                        tempI = free[1];
                        tempA = free[2];
                        tempB = free[3];
                        
                    }
                    
                } catch (InterruptedException ex) {
                    Logger.getLogger(FastCharacter.class.getName()).log(Level.SEVERE, null, ex);
                }
               
            }
           // this.numMatriz[limite][limite] = 4;
           
        } catch (IOException ex) {
            Logger.getLogger(FastCharacter.class.getName()).log(Level.SEVERE, null, ex);
        } 

    }
    
}
