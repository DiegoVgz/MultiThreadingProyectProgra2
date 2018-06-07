/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.util.Random;

/**
 *
 * @author Melissa Ramírez R
 */
public class Logic {
    Random random = new Random();
    int arriba=0;
    int abajo=0;
    int izquierda=0;
    int derecha=0;
   
    
    public boolean existeMedio(int posX, int posY, int limite) {

        if (posX < 0 || posY < 0) {
            return false;
        }
        if (posX > limite || posY > limite) {
            return false;
        }
        return true;
    }

    public boolean[] freeSpace(int[][] matriz, int posX, int posY) {
        boolean[] arrayFree = new boolean[4];
        arrayFree[0] = false;
        arrayFree[1] = false;
        arrayFree[2] = false;
        arrayFree[3] = false;
        
        int limiteX = matriz.length;
       
                
        //posicion hacia adelante
        if (posY<=limiteX-2) {
            if ( matriz[posX][posY+1] == 0|| matriz[posX][posY+1] == 4) {
                arrayFree[0] = true;
                 derecha=1;
            }
           
        }
        
        
        
        //posicion hacia atrás
        if (existeMedio(posX, posY-1, limiteX)) {
            if ((int) matriz[posX][posY-1] == 0 || (int) matriz[posX][posY-1] == 4) {
                arrayFree[1] = true;
                izquierda=2;
            }
             
        }
         
         
        //posicion hacia arriba
        if (existeMedio(posX-1 , posY, limiteX)) {
            if ((int) matriz[posX -1][posY] == 0 || (int) matriz[posX -1][posY] == 4) {
                arrayFree[2] = true;
                arriba=3;
            }
           
        }

        //posicion hacia abajo
        if (posX<=limiteX-2) {
            if ((int) matriz[posX+1 ][posY] == 0|| (int) matriz[posX+1 ][posY] == 4) {
                arrayFree[3] = true;
                abajo=4;
            }
             
        }

        return arrayFree;
    }
    
    public int random (){
        int result=0;
        
       int ll= (int) (Math.random() * 5) + 1;
       
       if(derecha==ll){
        result= derecha;
           
       }
       else if(izquierda==ll){
            result=  izquierda;
       }
       else  if(arriba==ll){
           result=  arriba;
       }
       else  if(abajo==ll){
        
        result=  abajo;
       }
       
       return result;
    }
    
   
}
