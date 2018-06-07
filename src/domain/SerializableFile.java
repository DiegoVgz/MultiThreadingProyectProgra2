/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Melissa Ramírez R
 */
public class SerializableFile implements Serializable {

    private String ruta;
    private String nombre;

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    //permite escribir en un archivo
    //reemplaza el contenido del archivo
    public String escribir(ArrayList<PositionCharacter> lista, String nombre) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(nombre));
            oos.writeObject(lista);
            oos.close();
            return "Ha sido registrado";
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            return "No se pudo registrar";
        }

    }

    //cargar en un tda el contenido de un archivo existente
    public ArrayList<PositionCharacter> cargar(ArrayList<PositionCharacter> tda, String nombre) throws ClassNotFoundException {
        ArrayList<PositionCharacter> aux = null;
        try {
            //se crea un objeto input stream
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(nombre));
            aux = (ArrayList) ois.readObject();
            ois.close();
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
        return tda = aux;
    }
}
