/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package patternRecognition.supervised;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;
import patternRecognition.supervised.distance.Bayes;
import patternRecognition.supervised.distance.KNearestNeighbor;
import patternRecognition.supervised.distance.Lineal;
import patternRecognition.supervised.distance.Metric;
import patternRecognition.supervised.distance.MinumumDistanceClassifier;
import patternRecognition.supervised.distance.RedNeuronal;
import patternRecognition.supervised.distance.UnivAprox;
import patternRecognition.supervised.distance.kfolds;

/**
 *
 * @author AndrésEspinalJiménez
 */
public class AppTest {

    public static void main(String[] args) throws FileNotFoundException, IOException {
        String cadena;
        Scanner sc = new Scanner(System.in);
        HashMap<Integer, LinkedList<float[]>> training = new HashMap<>();
        FileReader f = new FileReader("D:\\irisplant.txt");
      //  FileReader f2 = new FileReader("D:\\balance-scale.data");
       // FileReader f3 = new FileReader("D:\\blood.txt");
         BufferedReader b = new BufferedReader(f);
        System.out.println("Que atributo es la etiqueta?");
        int Etiqueta = sc.nextInt();
        while ((cadena = b.readLine()) != null) {
            String[] partes = cadena.split(",");
            float[] Ag = new float[partes.length-1];
            int ind =0;
            for(int i  =0;i < partes.length;i++){
                if(i != Etiqueta){
                    Ag[ind] = Float.valueOf(partes[i]);
                    ind++;
                }
            }
                if(training.containsKey(Integer.valueOf(partes[Etiqueta]))){
                    training.get(Integer.valueOf(partes[Etiqueta])).add(Ag);
                }else{
                    LinkedList<float[]> tra = new LinkedList<>();
                    tra.add(Ag);
                    training.put((Integer.valueOf(partes[Etiqueta])), tra);
                }
            
           }
        System.out.println(training.size());
        System.out.println("Cuantas clases se usaran? ");
        int clases = sc.nextInt(); 
        int [] select = new int[clases];
        System.out.println("seleccionar");
        for(int i = 0;i< select.length;i++){
            select[i] = sc.nextInt();
        }
        System.out.println("Cuantas caracteristicas se uaran? ");
        int Caracteristicas = sc.nextInt();
        int [] select2 = new int[Caracteristicas];
        System.out.println("seleccionar");
        for(int i = 0;i< select2.length;i++){
            select2[i] = sc.nextInt();
        }
        training = Limitar(training, select, select2);
        int k = 10;
        int [][] pos = new int[training.size()][k];
        for(int i = 1;i < training.size()+1;i++){
           
            int sumatoria =0;
            for(int l = 0;l<k;l++){
              sumatoria+=training.get(i).size()/k;
                pos[i-1][l] = sumatoria;
               // System.out.println(pos[i-1][l]);
            }
            while(pos[i-1][k-1] != training.get(i).size()){
               pos[i-1][k-1]+=1;
               
            }
            
        }
        
        
        for(int i =0;i<k;i++){
        kfolds kf = new kfolds(training, pos, i);
        LinkedList<SupervisedClassifier> s = new LinkedList<>();
        s.add(new Lineal(kf.Ent));
      //  s.add(new UnivAprox(training, select2.length));
     //   s.add(new RedNeuronal(kf.Ent, select2.length));
//       s.add(new Bayes(remuestreo(kf.Ent), select2.length));
//        s.add(new KNearestNeighbor(remuestreo(kf.Ent), 3, Metric.MANHATTAN, select2.length));
//        s.add(new KNearestNeighbor(remuestreo(kf.Ent), 3, Metric.EUCLIDIAN, select2.length));
//        s.add(new MinumumDistanceClassifier(remuestreo(kf.Ent), Metric.MANHATTAN, select2.length));
//        s.add(new MinumumDistanceClassifier(remuestreo(kf.Ent), Metric.EUCLIDIAN, select2.length));
       LinkedList<float[]> ent = new LinkedList<>();
        for(int l = 0;l< kf.getEntrenamiento().size();l++){
           // System.out.println("---");
            for(int q = 0;q< kf.getEntrenamiento().get(l+1).size();q++){
             ent.add(kf.getEntrenamiento().get(l+1).get(q));
            // System.out.println(Arrays.toString(kf.getEntrenamiento().get(l+1).get(q)));
        }
           
        }
            System.out.println("Corrida : "+i);
            Ver(leave(s, clases, ent, kf.getEntrenamiento()));
    }
    }
    public static HashMap Limitar(HashMap<Integer, LinkedList<float[]>> c, int[] selected, int[] selected2){
        HashMap<Integer, LinkedList<float[]>> b = new HashMap<>();
        for(int i  =0; i<selected.length;i++){
            b.put(i+1, c.get(selected[i]));
        }
        for(int i  =0; i<b.size();i++){
            LinkedList<float[]> tmp = new LinkedList<>();
            for(int j  =0;j<b.get(i+1).size();j++){
                float[] f = new float[selected2.length];
            for(int k  =0;k<selected2.length;k++){
            f[k] = b.get(i+1).get(j)[selected2[k]];
        }
            tmp.add(f);
        }
            b.replace(i+1, tmp);
        }
        return b;
    }
    public static int [][] leave(LinkedList<SupervisedClassifier> conjunto, int clases,LinkedList<float[]> r, HashMap<Integer, LinkedList<float[]>> Entr){
        int[][] c = new int[conjunto.size()][r.size()];
        
        
            for(int j=0;j<conjunto.size();j++){
                for(int i =0;i<r.size();i++){
                c[j][i] = conjunto.get(j).classify(r.get(i));
                }
            }
            int[] res = MatrizC(c, clases);
          int [][] b=  sa(res, Entr);
        return b;
    }
    public static int[] MatrizC(int[][] matriz, int clases){
        int[] tmp = new int[matriz[0].length];
        
        for(int i  = 0;i< matriz[0].length;i++){
            int cl =0;
            for(int q =0;q< matriz.length;q++){
            cl+=matriz[q][i];
            }
            tmp[i] = (cl/matriz.length);
        }
        return tmp;
    }
    public static int [][] sa(int [] s, HashMap<Integer, LinkedList<float[]>> Entr){
        int[][] MC = llenar(Entr.size());
        int[] puntos = new int[Entr.size()];
        int t =0;
        for(int i =0;i< Entr.size();i++){
            t+= Entr.get(i+1).size();
            puntos[i] = t;
            
        }
        for(int i =0;i< Entr.size();i++){
            if(i==0){
                for(int q = 0;q< puntos[i];q++){
                    MC[i][s[q]-1]++;
                }
            }else{
                for(int q = puntos[i-1];q< puntos[i];q++){
                    MC[i][s[q]-1]++;
                }
            }
        }
        return MC;
    }
    public static int[][] llenar(int entr){
        int[][] c = new int[entr][entr];
        for(int i =0;i<c.length;i++){
            for(int j =0;j<c[i].length;j++){
                c[i][j] =0;
        }
        }
        return c;
    }
    public static void Ver(int[][] c){
        
        for(int i =0;i<c.length;i++){
            for(int j =0;j<c[i].length;j++){
                System.out.printf(" %d ",c[i][j]);
        }
            System.out.println();
        }
        
    }
    public static HashMap<Integer, LinkedList<float[]>> remuestreo(HashMap<Integer, LinkedList<float[]>> muesestreo){
        HashMap<Integer, LinkedList<float[]>> muesestreo2 = new HashMap<>();
        Random r = new Random();
        for(int i = 0;i< muesestreo.size();i++){
            LinkedList<float[]> tmp = new LinkedList<>();
            for(int j=0;j< muesestreo.get(i+1).size();j++){
                tmp.add(muesestreo.get(i+1).get(r.nextInt(muesestreo.get(i+1).size())));
            }
            muesestreo2.put(i+1, tmp);
        }
        return muesestreo2;
    }
}
