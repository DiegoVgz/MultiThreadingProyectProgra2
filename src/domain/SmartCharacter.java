package domain;

import gameInterface.MazeInterface;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class SmartCharacter extends Character implements Runnable {

    private int percentage;
    private int speed;
    private int posY;
    private int posX;
    private String type;
    private boolean arriba = false, abajo = false, adelante = false, atras = false;
    Logic logic;
    int indice = 0;
    JPanel jpanel;
    String name = "";
    Cronometro cronometro;
    LinkedList positionList;
    int[][] numMatriz;
    ArrayList<PositionCharacter> arrayTime;
    MazeInterface mazeInterface;

    public SmartCharacter(int percentage, int posX, int posY, int numImage,
            int[][] matrizObject, String name, String type, int speed, JPanel jpanel, MazeInterface maze) throws IOException {
        //constructor del hilo
        super(posX, posY, numImage, matrizObject, name, type, speed);
        setSprite();
        this.posX = posX;
        this.posY = posY;
        this.percentage = percentage;
        this.speed = speed;
        logic = new Logic();
        this.jpanel = jpanel;
        this.name = name;
        this.positionList = positionList;
        this.type = type;
        cronometro = new Cronometro();
        this.numMatriz = matrizObject;
        mazeInterface = maze;
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

//    public void setSprite() throws FileNotFoundException, IOException {
//        ArrayList<Image> sprite = super.getSprite();
//        for (int i = 1; i <= 6; i++) {
//
//            BufferedImage buffer1 = ImageIO.read(new FileInputStream("src/Img/p/Policia" + i + ".png"));
//            sprite.add(buffer1);
//        }
//        super.setSprite(sprite);
//    }
    //AQUI
    public ArrayList setSprite() throws FileNotFoundException, IOException {
        ArrayList<Image> sprite = super.getSprite();
        for (int i = 1; i <= 6; i++) {

            BufferedImage buffer1 = ImageIO.read(new FileInputStream("src/Img/p/Policia" + i + ".png"));
            sprite.add(buffer1);
        }
        // super.setSprite(setSprite2());
        return sprite;
    }

    public ArrayList setSprite2() throws FileNotFoundException, IOException {
        ArrayList<Image> sprite = new ArrayList<>();
        for (int i = 1; i <= 6; i++) {
            BufferedImage buffer1 = ImageIO.read(new FileInputStream("src/Img/p/" + i + ".png"));
            sprite.add(buffer1);
        }
        return sprite;
    }

    @Override
    public void run() {
        cronometro.start(); // hace que el cronometro inice cuando el personaje camine
        ArrayList<Image> sprite = super.getSprite();
        super.setPlayerImage(sprite.get(1));
        this.setPositionX(1);
        this.setPositionY(1);
        int coordenadaX = 0;
        int coordenadaY = 0;
        int[][] maze = super.getPath();
        int limite = maze.length - 1;
        boolean free[] = logic.freeSpace(maze, coordenadaX, coordenadaY);
        int random = 4;
        boolean tempD = free[0];
        boolean tempI = free[1];
        boolean tempA = free[2];
        boolean tempB = free[3];

        //recorre el hilo hasta que llegue a la meta
        while (coordenadaX != limite || coordenadaY != limite) {
            try {
                boolean back = false;
                boolean front = false;
                boolean down = false;
                boolean up = false;
                Thread.sleep(speed + 100);
                maze = super.getPath();
                free = logic.freeSpace(maze, coordenadaX, coordenadaY);

                if (free[0] != tempD || free[1] != tempI || free[2] != tempA || free[3] != tempB) {
                    random = logic.random();
                }

                // espacio libre adelante
                if (free[0] == true && random == 1 && front == false) {
                    // se ejecuta 65 veces por el tamanio de los cuadros
                    for (int j = 0; j < 65; j++) {
                        Thread.sleep(10);
                        // como es  hacia adelante, se suman las coordenadas del eje X
                        // y se mantienen las del Y
                        super.setPlayerImage(sprite.get(indice));
                        this.setPositionX(getPositionX() + 1);
                        this.setPositionY(this.getPositionY());

                        indice++; // hace el cambio de los distintos sprites
                        if (indice == 6) {
                            indice = 0;
                        }
                        // repinta el panel para que se vea reflejado el movimiento
                        jpanel.repaint();
                    }
                    back = true;
                    coordenadaY++;
                    // permite escribir un numero diferente de 0 en la matriz para que dos 
                    // personajes no puedan estar en la misma posicion
                    this.numMatriz[coordenadaX][coordenadaY - 1] = 0;
                    this.numMatriz[coordenadaX][coordenadaY] = 5;

                    if (this.numMatriz[coordenadaX][coordenadaY] == 9) {
                        this.numMatriz[coordenadaX][coordenadaY] = 0;
                        Thread.sleep(10);
                    }

                    tempD = free[0];
                    tempI = free[1];
                    tempA = free[2];
                    tempB = free[3];

                    //atras            
                } else if (free[1] == true && random == 2 && back == false) {
                    for (int j = 0; j < 65; j++) {
                        Thread.sleep(10);
                        super.setPlayerImage(sprite.get(indice));
                        this.setPositionX(this.getPositionX() - 1);
                        this.setPositionY(this.getPositionY());

                        indice++;
                        if (indice == 6) {
                            indice = 0;
                        }
                        jpanel.repaint();
                    }

                    front = true;
                    // como se va hacia atrás se coloca en la matriz de enteros una coordenada adelante como ocupada
                    coordenadaY--;
                    this.numMatriz[coordenadaX][coordenadaY + 1] = 0;
                    this.numMatriz[coordenadaX][coordenadaY] = 5;

                    if (this.numMatriz[coordenadaX][coordenadaY] == 6) {
                        this.numMatriz[coordenadaX][coordenadaY] = 0;
                        Thread.sleep(10);
                    }

                    tempD = free[0];
                    tempI = free[1];
                    tempA = free[2];
                    tempB = free[3];
                    // direccion hacia arriba libre 
                } else if (free[2] == true && random == 3 && down == false) {
                    for (int j = 0; j < 65; j++) {
                        Thread.sleep(10);
                        super.setPlayerImage(sprite.get(indice));
                        this.setPositionX(this.getPositionX());
                        this.setPositionY(this.getPositionY() - 1);

                        indice++;
                        if (indice == 6) {
                            indice = 0;
                        }
                        jpanel.repaint();
                    }

                    down = true;
                    coordenadaX--;
                    this.numMatriz[coordenadaX + 1][coordenadaY] = 0;
                    this.numMatriz[coordenadaX][coordenadaY] = 5;
                    if (this.numMatriz[coordenadaX][coordenadaY] == 9) {
                        this.numMatriz[coordenadaX][coordenadaY] = 0;
                        Thread.sleep(10);
                    }
                    tempD = free[0];
                    tempI = free[1];
                    tempA = free[2];
                    tempB = free[3];

                    // espacio hacia atrás libre
                } else if (free[3] == true && random == 4 && up == false) {
                    for (int j = 0; j < 65; j++) {
                        Thread.sleep(10);
                        super.setPlayerImage(sprite.get(indice));
                        this.setPositionX(this.getPositionX());
                        this.setPositionY(this.getPositionY() + 1);

                        indice++;
                        if (indice == 6) {
                            indice = 0;
                        }
                        jpanel.repaint();
                        up = true;
                    }

                    coordenadaX++;
                    this.numMatriz[coordenadaX - 1][coordenadaY] = 0;
                    this.numMatriz[coordenadaX][coordenadaY] = 9;

                    if (this.numMatriz[coordenadaX][coordenadaY] == 9) {
                        this.numMatriz[coordenadaX][coordenadaY] = 0;
                        Thread.sleep(10);
                    }
                    tempD = free[0];
                    tempI = free[1];
                    tempA = free[2];
                    tempB = free[3];
                }

            } catch (InterruptedException ex) {
                Logger.getLogger(FastCharacter.class.getName()).log(Level.SEVERE, null, ex);
            }
            // cuando las coordenadas coinciden con la ultima coordenada, que es donde se colocaron las salidas, 
            // significa que debe detenerse el movimiento del hilo, se cumple la condicion para que se salga

            if (coordenadaX == limite || coordenadaY == limite) {
                // se detiene el cronómetro
                cronometro.setFinish(true);
            }
        }

        try {
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
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SmartCharacter.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
