package domain;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class MazeLevels extends JPanel implements MouseListener, Runnable {

    Maze mazeBuilder = new Maze();
    Rectangle2D[][] mazeStructure;
    int[][] num;
    int mazeLevel;
    FastCharacter fast;
    FuriousCharacter furious;
    SmartCharacter smart;
    Item1 item;

    //arrayList de threads
    ArrayList<FastCharacter> fastArray = new ArrayList<>();
    ArrayList< FuriousCharacter> furiusArray = new ArrayList<>();
    ArrayList<SmartCharacter> smartArray = new ArrayList<>();
    ArrayList<Item1> itemArray = new ArrayList<>();

    
    //llevan la cuenta de los array para  iniciar los threads
    ArrayList<Image> array;
    int countFast = 0;
    int countFurious = 0;
    int countSmart = 0;
      int countItem = 0;
      
    //activan el start
    int tempFastSize = 0;
    int tempFurious = 0;
    int tempSmart = 0;
    int tempItem = 0;


    public MazeLevels(Maze maze, int level, int width, int hight) throws IOException {
        addMouseListener(this);

        mazeLevel = level;
        this.setSize(width, hight);
        this.mazeBuilder = maze;
        this.mazeStructure = new Rectangle2D[mazeBuilder.getColums()][mazeBuilder.getRow()];
        num = mazeBuilder.fillMaze();
        this.setVisible(true);
        this.repaint();

        LinkedList positionList = new LinkedList();
        positionList.add(1);
        positionList.add(1);
        int counter = 1;

//        if (threadType.equalsIgnoreCase("fast")) {
//            fast[]
//            counter++;
//            fastCharacter = new FastCharacter(0, 25, 25, 0, num, "Kevin"+counter, "medium", 120, this, positionList);
//           
//        }
//
//        if (threadType.equalsIgnoreCase("furious")) {
//            counter++;
//            furious = new FuriousCharacter(0, 50, 50, 0, num, "Fer"+counter, "medium", 5000, this, positionList);
//        }
    }

    public int[][] getNum() {
        return num;
    }

    public void setNum(int[][] num) {
        this.num = num;
    }

    //pinta los componentes
    public void paintComponent(Graphics g) {

        Graphics2D g2 = (Graphics2D) g;
        try {
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.clearRect(0, 0, this.getWidth(), this.getHeight());
            this.draw(g2);
        } catch (IOException ex) {
            Logger.getLogger(MazeLevels.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    //crea los componentes
    public void draw(Graphics2D g2) throws IOException {

        g2.setColor(Color.black);
        int calculateY = mazeBuilder.getImageWall().getHeight(null) + mazeLevel;
        int calculateX = mazeBuilder.getImageWall().getHeight(null) + mazeLevel;
        int startY = 0;
        int startX = 0;

        for (int i = 0; i < mazeBuilder.fillMaze().length; i++) {

            for (int j = 0; j < mazeBuilder.fillMaze()[0].length; j++) {

                mazeStructure[i][j] = new Rectangle2D.Double(startX, startY, calculateX, calculateY);

                g2.draw(mazeStructure[i][j]);
                g2.setPaint(Color.black);
                g2.fill(mazeStructure[i][j]);

                if (num[i][j] == 1) {

                    g2.drawImage(mazeBuilder.getImageWall(), startX, startY, calculateX, calculateY, this);

                }

                if (num[i][j] == 3) {

                    g2.drawImage(mazeBuilder.getDoor(), startX, startY, calculateX, calculateY, this);
                }

                if (num[i][j] == 4) {

                    g2.drawImage(mazeBuilder.getExit(), startX, startY, calculateX, calculateY, this);
                }

                startX = startX + calculateX;
            }

            startY = startY + calculateY;
            startX = 0;
        }

        reDraw(g2);
    }

    public void reDraw(Graphics2D g2) {

        if (fastArray != null) {
            for (int i = 0; i < fastArray.size(); i++) {
                Font font = new Font(Font.DIALOG, Font.BOLD, 11);
                g2.setFont(font);
                g2.setColor(Color.WHITE);
                fast = (FastCharacter) fastArray.get(i);
                g2.drawImage(fast.getPlayerImage(), fast.getPositionX(), fast.getPositionY(), this);
                g2.drawString(fast.getTiredPercentage()+"% tired", fast.getPositionX(), fast.getPositionY()-5);
            }
            
        }
        if (furiusArray != null) {
            for (int i = 0; i < furiusArray.size(); i++) {

                furious = (FuriousCharacter) furiusArray.get(i);
                g2.drawImage(furious.getPlayerImage(), furious.getPositionX(), furious.getPositionY(), this);
            }
        }
        if (smartArray != null) {
            for (int i = 0; i < smartArray.size(); i++) {

                smart = (SmartCharacter) smartArray.get(i);
                g2.drawImage(smart.getPlayerImage(), smart.getPositionX(), smart.getPositionY(), this);
            }
        }
        
        if (itemArray != null){
            for (int i = 0; i < itemArray.size(); i++){
                
                item = (Item1) itemArray.get(i);
                g2.drawImage(item.getPlayerImage(), item.getPositionX(), item.getPositionY(), this);
            }
        }

    }

    public void addCharacter(Object character, String type) {

        if (type.equals("fast")) {

            //cast
            fast = (FastCharacter) character;

            //agregar a la lista
            fastArray.add(fast);

            //
            countFast++;
                   
         tempFastSize = 1;
         tempFurious = 0;
         tempSmart = 0;
        }
        if (type.equals("furious")) {
            //cast
            furious = (FuriousCharacter) character;

            //agregar a la lista
            furiusArray.add(furious);

            //
            countFurious++;
            tempFastSize = 0;
            tempFurious = 1;
            tempSmart = 0;
        }
        if (type.equals("smart")) {
            //cast
            smart = (SmartCharacter) character;

            //agregar a la lista
            smartArray.add(smart);

            //
            countSmart++;
            tempFastSize = 0;
            tempFurious = 0;
            tempSmart = 1;
        }
        
       if (type.equals("item")) {
            //cast
            item = (Item1) character;

            //agregar a la lista
            itemArray.add(item);

            //
            countItem++;
         
            tempFastSize = 0;
            tempFurious = 0;
            tempSmart = 0;
            tempItem = 1;
        }


    }

    @Override
    public void mouseClicked(MouseEvent e) {
       if (SwingUtilities.isLeftMouseButton(e)) {
            int cordenatesX = e.getX();
            int cordenatesY = e.getY();
            ;
            for (int i = 0; i < mazeStructure.length; i++) {
                for (int j = 0; j < mazeStructure.length; j++) {
                    
                    if (mazeStructure[i][j].contains(cordenatesX, cordenatesY)) {

                        if (num[i][j] == 1) {

                            num[i][j] = 0;
                        } else if (num[i][j] == 0) {
                            num[i][j] = 1;
                        }

                    }
                }
            }

            repaint();
        }
        if (SwingUtilities.isRightMouseButton(e)) {

            int cordenatesX2 = e.getX();
            int cordenatesY2 = e.getY();

            for (int i = 0; i < mazeStructure.length; i++) {
                for (int j = 0; j < mazeStructure.length; j++) {
                    if (mazeStructure[i][j].contains(cordenatesX2, cordenatesY2)&& num[i][j] != 1&&num[i][j] != 3&&num[i][j] != 4) {
                        try {
                            
                            //Item1 item = new Item1(0, (int)mazeStructure[i][j].getX(), (int)mazeStructure[i][j].getY(), 0, getNum(), "Kevin", "fast", 120, this);
                            Item1 item = new Item1((int)mazeStructure[i][j].getX(), (int)mazeStructure[i][j].getY(), 0, getNum(), "Kevin", "fast", 120, this,mazeStructure);
                            // Item1 i = new Item1(PROPERTIES, j, j, WIDTH, num, TOOL_TIP_TEXT_KEY, TOOL_TIP_TEXT_KEY, j, this);
                            addCharacter(item, "item");
                            repaint();
                            run();
                            
                        } catch (IOException ex) {
                            Logger.getLogger(MazeLevels.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }

                }
            }
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void run() {
        
        if (fastArray.size() != 0 && tempFastSize ==1) {
            //obtengo al kevin mas nuevo;
            fast = (FastCharacter) fastArray.get(countFast - 1);
            //start thread
            fast.start();
        }
        if (furiusArray.size() != 0 && tempFurious==1) {
            //obtengo al kevin mas nuevo;
            furious = (FuriousCharacter) furiusArray.get(countFurious - 1);

            //start thread
            furious.start();
        }
        if (smartArray.size() != 0 && tempSmart==1) {
            //obtengo al kevin mas nuevo;
            smart = (SmartCharacter) smartArray.get(countSmart - 1);

            //start thread
            smart.start();
        }
        if(itemArray.size() != 0 && tempItem ==1){
            System.out.println("ENTRE AL START DEL ITEM");
            item = (Item1) itemArray.get(countItem-1);
            try{
                item.start(); 
            }catch(IllegalThreadStateException i){
                System.out.println("ME CAI me cai ME CAI");
            }
           
        }
    }

    }


