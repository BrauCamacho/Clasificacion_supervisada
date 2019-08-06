/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package patternRecognition.supervised.distance;

import java.util.HashMap;
import java.util.LinkedList;
/**
 *
 * @author brau
 */
public class kfolds {
    public HashMap <Integer, LinkedList<float[]>>  Registros;
    public HashMap <Integer, LinkedList<float[]>>  Entrenamiento;
    public HashMap <Integer, LinkedList<float[]>>  Ent;

    public HashMap<Integer, LinkedList<float[]>> getEnt() {
        return Ent;
    }

    public void setEnt(HashMap<Integer, LinkedList<float[]>> Ent) {
        this.Ent = Ent;
    }
    
    public int [][] pos;
    public int iteracion;

    public HashMap<Integer, LinkedList<float[]>> getRegistros() {
        return Registros;
    }

    public void setRegistros(HashMap<Integer, LinkedList<float[]>> Registros) {
        this.Registros = Registros;
    }

    public kfolds(HashMap<Integer, LinkedList<float[]>> Registros, int[][] pos, int iter) {
        this.Registros = Registros;
        this.pos = pos;
        this.iteracion = iter;
        HashMap <Integer, LinkedList<float[]>>  Entrenamiento = new HashMap<>();
        HashMap <Integer, LinkedList<float[]>>  Ent = new HashMap<>();
        for(int i =0;i< Registros.size();i++){
            LinkedList <float[]> f = new LinkedList<>();
            LinkedList <float[]> f2 = new LinkedList<>();
            for(int j =0;j< Registros.get(i+1).size();j++){
                if(iter ==0){
                    if(j>=0&&j<pos[i][iter]){
                        f.add(Registros.get(i+1).get(j));
                        
                    }else{
                        f2.add(Registros.get(i+1).get(j));
                    }
                }else{
                    if(j>=pos[i][iter-1]&&j<pos[i][iter]){
                        f.add(Registros.get(i+1).get(j));
                        
                        
                    }else{
                        f2.add(Registros.get(i+1).get(j));
                    }
                }
        }
            Entrenamiento.put(i+1, f);
            Ent.put(i+1, f2);
        }
        this.Entrenamiento = Entrenamiento;
        this.Ent = Ent;
    }
    public int[][] getPos() {
        return pos;
    }

    public void setPos(int[][] pos) {
        this.pos = pos;
    }

    public HashMap<Integer,LinkedList<float[]>> getEntrenamiento() {
        return Entrenamiento;
    }

    public void setEntrenamiento(HashMap<Integer,LinkedList<float[]>> Entrenamiento) {
        this.Entrenamiento = Entrenamiento;
    }

    public int getIteracion() {
        return iteracion;
    }

    public void setIteracion(int iteracion) {
        this.iteracion = iteracion;
    }
    
}

