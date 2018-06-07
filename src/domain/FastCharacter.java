package domain;

import gameInterface.MazeInterface;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class FastCharacter extends Character implements Runnable, Serializable {

    private int tired = 0;
    private int percentage;
    private int speed;
    private int posY;
    private int posX;
    private String type;
    private long tiempo;
    private long tiempoFinalizado;
    private boolean arriba = false, abajo = false, adelante = false, atras = false;
    Logic logic;
    int indice = 0;
    JPanel jpanel;
    String name = "";
    LinkedList positionList;
    int[][] numMatriz;
    Cronometro cronometro;
    ArrayList<PositionCharacter> arrayTime;
    MazeInterface mazeInterface;

    public FastCharacter(int percentage, int posX, int posY, int numImage,
            int[][] matrizObject, String name, String type, int speed, JPanel jpanel, MazeInterface maze) throws IOException {
        //constructor del hilo
        super(posX, posY, numImage, matrizObject, name, type, speed);
        //setSprite();
        this.posX = posX;
        this.posY = posY;
        this.percentage = percentage;
        this.speed = speed;
        logic = new Logic(); // clase que contiene los metodos para validar las posiciones de la matriz
        this.jpanel = jpanel;
        this.name = name;
        this.positionList = positionList;
        this.type = type;
        this.numMatriz = matrizObject;
        cronometro = new Cronometro(); // hilo que ejecuta el cronometro
        arrayTime = new ArrayList<>(); // arreglo que contiene el tiempo en que finaliza un hilo
        mazeInterface = maze; // instancia de la interfaz para mostrar el cambio
    }

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;

    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;

    }

    public int getTiredPercentage() {
        int percentage = (100 * this.tired) / 10;
        return percentage;

    }

  

    public ArrayList setSprite() throws FileNotFoundException, IOException {
        ArrayList<Image> sprite = super.getSprite();
        for (int i = 1; i <= 11; i++) {

            BufferedImage buffer1 = ImageIO.read(new FileInputStream("src/Img/a/Santa" + i + ".png"));
            sprite.add(buffer1);
        }
       // super.setSprite(setSprite2());
       return sprite;
    }

    public ArrayList setSprite2() throws FileNotFoundException, IOException {
        ArrayList<Image> sprite = new ArrayList<>();
        for (int i = 1; i < 11; i++) {
            BufferedImage buffer1 = ImageIO.read(new FileInputStream("src/Img/a/" + i + ".png"));
            sprite.add(buffer1);
        }
        return sprite;
    }

    @Override
    public void run() {
        try {
            ArrayList<Image> sprite = setSprite();
            cronometro.start(); // hace que el cronometro inice cuando el personaje camine
            super.setPlayerImage(sprite.get(1));
            this.setPositionX(1);
            this.setPositionY(1);
            int coordenadaX = 0;
            int coordenadaY = 0;

            int[][] maze = super.getPath();
            int limite = numMatriz.length - 1;
            boolean free[] = logic.freeSpace(maze, coordenadaX, coordenadaY);
            int random = 4;
            boolean tempD = free[0];
            boolean tempI = free[1];
            boolean tempA = free[2];
            boolean tempB = free[3];
            boolean meta = true;

            //recorre el hilo hasta que llegue a la meta
            while (coordenadaX != limite || coordenadaY != limite) {
                try {
                    boolean back = false;
                    boolean front = false;
                    boolean down = false;
                    boolean up = false;
                    Thread.sleep(speed + 100);
                    maze = super.getPath();
                    
                    // devuelve un arrelgo en el cual se indica donde hay espacio libre, 
                    // adecuado para avanzar, generalizado para cualquiera de los laberintos
                    free = logic.freeSpace(maze, coordenadaX, coordenadaY);

                    if (free[0] != tempD || free[1] != tempI || free[2] != tempA || free[3] != tempB) {
                        random = logic.random();
                    }
                    // adelante

                    if (this.tired > 9) {

                        this.sleep(4000);
                        this.tired = 0;

                    }
                    //condiciones que hacen que el jugador no se devuelva
                    // espacio libre adelante
                    if (free[0] == true && random == 1 && front == false) {
                        sprite = setSprite();
                        for (int j = 0; j < 65; j++) {
                            // se ejecuta 65 veces por el tamanio de los cuadros
                            super.setPlayerImage(sprite.get(indice));
                            
                            // como es  hacia adelante, se suman las coordenadas del eje X
                            // y se mantienen las del Y
                            this.setPositionX(getPositionX() + 1);
                            this.setPositionY(this.getPositionY());
                            Thread.sleep(20);
                            // hace el cambio de los distintos sprites
                            indice++;
                            if (indice == 10) {
                                indice = 0;
                            }
                            // repinta el panel para que se vea reflejado el movimiento
                            jpanel.repaint();
                        }
                        back = true;
                        
                        coordenadaY++;
                        // permite escribir un numero diferente de 0 en la matriz para que dos 
                     // personajes no puedan estar en la misma posicion
                        numMatriz[coordenadaX][coordenadaY - 1] = 0; // 0 significa libre 
                        numMatriz[coordenadaX][coordenadaY] = 5; // distinto de 0 es ocupado
                        
                      //se ponen nuevamente en el valor inicial
                        tempD = free[0];
                        tempI = free[1];
                        tempA = free[2];
                        tempB = free[3];
                      // se aumenta el tired (porcentaje de progreso para "detener" el hilo)
                        tired++;

                        //atras
                    } else if (free[1] == true && random == 2 && back == false) {
                        sprite = setSprite2();
                        for (int j = 0; j < 65; j++) {

                            super.setPlayerImage(sprite.get(indice));
                            this.setPositionX(this.getPositionX() - 1);
                            this.setPositionY(this.getPositionY());
                            Thread.sleep(25);
                            indice++;
                            if (indice == 10) {
                                indice = 0;
                            }
                            jpanel.repaint();
                        }
                        front = true;
                        // como se va hacia atrás se coloca en la matriz de enteros una coordenada adelante como ocupada
                        coordenadaY--;
                        numMatriz[coordenadaX][coordenadaY + 1] = 0;
                        numMatriz[coordenadaX][coordenadaY] = 5;
                        tempD = free[0];
                        tempI = free[1];
                        tempA = free[2];
                        tempB = free[3];
                        tired++;

                        // arriba
                    } else if (free[2] == true && random == 3 && down == false) {
                        sprite = setSprite();
                        for (int j = 0; j < 65; j++) {

                            super.setPlayerImage(sprite.get(indice));
                            this.setPositionX(this.getPositionX());
                            this.setPositionY(this.getPositionY() - 1);
                            Thread.sleep(20);
                            indice++;
                            if (indice == 10) {
                                indice = 0;
                            }
                            jpanel.repaint();
                        }
                        down = true;
                        coordenadaX--;
                        numMatriz[coordenadaX + 1][coordenadaY] = 0;
                        numMatriz[coordenadaX][coordenadaY] = 5;
                        tempD = free[0];
                        tempI = free[1];
                        tempA = free[2];
                        tempB = free[3];
                        tired++;
                       // espacio hacia atrás libre
                    } else if (free[3] == true && random == 4 && up == false) {
                         sprite = setSprite();
                        for (int j = 0; j < 65; j++) {

                            super.setPlayerImage(sprite.get(indice));
                            this.setPositionX(this.getPositionX());
                            this.setPositionY(this.getPositionY() + 1);
                            Thread.sleep(20);
                            indice++;
                            if (indice == 10) {
                                indice = 0;
                            }
                            jpanel.repaint();
                            up = true;
                        }
                        coordenadaX++;
                        numMatriz[coordenadaX - 1][coordenadaY] = 0;
                        numMatriz[coordenadaX][coordenadaY] = 5;
                        tempD = free[0];
                        tempI = free[1];
                        tempA = free[2];
                        tempB = free[3];
                        tired++;
                    }

                  // cuando las coordenadas coinciden con la ultima coordenada, que es donde se colocaron las salidas, 
                 // significa que debe detenerse el movimiento del hilo, se cumple la condicion para que se salga
                    if (coordenadaX == limite || coordenadaY == limite) {
                        // se detiene el cronómetro
                        cronometro.setFinish(true);
                    }
                } catch (InterruptedException ex) {
                    Logger.getLogger(FastCharacter.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
            this.numMatriz[limite][limite] = 4;
            //se guardan los tiempos de todos los hilos que se han ejecutado en el tda ArrayList
        // y este a su vez se guarda en un archivo serializado 
            File file = new File("Time.obj");
            if (file.exists()) {
                arrayTime = new SerializableFile().cargar(new ArrayList<>(), "Time.obj");
                
            } else {
                arrayTime = new ArrayList<>();
            }
  
            arrayTime.add(new PositionCharacter(this.getName(), cronometro.getTime()));
            new SerializableFile().escribir(arrayTime, "Time.obj");
            // se muestra en la tabla del frontEnd
            mazeInterface.mostrar();
        } catch (ClassNotFoundException | IOException ex) {
            Logger.getLogger(FastCharacter.class.getName()).log(Level.SEVERE, null, ex);
        }
      

    }

}
