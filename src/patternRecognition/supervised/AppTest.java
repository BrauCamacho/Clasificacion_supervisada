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
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;
import patternRecognition.supervised.distance.Bayes;
import patternRecognition.supervised.distance.KNearestNeighbor;
import patternRecognition.supervised.distance.Metric;
import patternRecognition.supervised.distance.MinumumDistanceClassifier;
import patternRecognition.supervised.distance.RedNeuronal;
import patternRecognition.supervised.distance.kfolds;

/**
 *
 * @author brau
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
        System.out.println("Cuantas clases se usarán? ");
        int clases = sc.nextInt(); 
        int [] select = new int[clases];
        System.out.println("seleccionar");
        for(int i = 0;i< select.length;i++){
            select[i] = sc.nextInt();
        }
        System.out.println("Cuantas caracteristicas se usarán? ");
        int Caracteristicas = sc.nextInt();
        int [] select2 = new int[Caracteristicas];
        System.out.println("seleccionar");
        for(int i = 0;i< select2.length;i++){
            select2[i] = sc.nextInt();
        }
        training = Limitar(training, select, select2);
        training = remuestreo(training);
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
        
        double promediogen = 0;
        double promediogengen = 0;
        int[][] desgengen = llenar(clases);
        int[][] desgengen2 = llenar(clases);
        for(int i =0;i<k;i++){
        kfolds kf = new kfolds(training, pos, i);
        LinkedList<SupervisedClassifier> s = new LinkedList<>();
       // s.add(new Lineal(kf.Ent));
       // s.add(new RedNeuronal(kf.Ent, select2.length));
       s.add(new Bayes(kf.Ent, select2.length));
        s.add(new KNearestNeighbor(kf.Ent, 3, Metric.MANHATTAN, select2.length));
        s.add(new KNearestNeighbor(kf.Ent, 3, Metric.EUCLIDIAN, select2.length));
      //  s.add(new MinumumDistanceClassifier(kf.Ent, Metric.MINKOWSKI, select2.length));
       // s.add(new MinumumDistanceClassifier(kf.Ent, Metric.EUCLIDIAN, select2.length));
       LinkedList<float[]> ent = new LinkedList<>();
        for(int l = 0;l< kf.getEntrenamiento().size();l++){
           // System.out.println("---");
            for(int q = 0;q< kf.getEntrenamiento().get(l+1).size();q++){
             ent.add(kf.getEntrenamiento().get(l+1).get(q));
            // System.out.println(Arrays.toString(kf.getEntrenamiento().get(l+1).get(q)));
        }
           
        }
            System.out.println("Corrida : "+(i+1));
            int[][] des = leave(s, clases, ent, kf.getEntrenamiento());
            Ver(des);
            promediogengen+= desempenocomp(s);
            System.out.println("desempeño de experimentacion es : "+desempeno(des));
           promediogen+=desempeno(leave(s, clases, ent, kf.getEntrenamiento()));
            int[][] entrenamiento = desempenocomp2(s, clases);
           for(int desv = 0;desv< desgengen.length;desv++){
               for(int desv2 = 0;desv2< desgengen[desv].length;desv2++){
                   desgengen[desv][desv2]+= des[desv][desv];
                   desgengen2[desv][desv2]+= entrenamiento[desv][desv];
           }
           }
    }
        for(int desv = 0;desv< desgengen.length;desv++){
               for(int desv2 = 0;desv2< desgengen[desv].length;desv2++){
                   desgengen[desv][desv2]/=k;
                   desgengen2[desv][desv2]/=k;
           }
           }
        System.out.println("promedio general de experimentacion = "+(promediogen/(double)k));
        System.out.println("promedio general de entrenamiento = "+(promediogengen/(double)k));
        Estatisticos(desgengen, (promediogen/(double)k));
        Estatisticos(desgengen2, (promediogengen/(double)k));
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
            LinkedList<Integer> indices = new LinkedList<>();
            LinkedList<float[]> tmp = new LinkedList<>();
            indices.add(0);
            while(indices.size()< muesestreo.get(i+1).size()){
                int aleatorio = r.nextInt(muesestreo.get(i+1).size());
                int agreg = 1;
                for(int chec =0;chec< indices.size();chec++){
                    if(aleatorio == indices.get(chec)){
                     agreg = -1;   
                    }
                }
                if(agreg != -1){
                    indices.add(aleatorio);
                    //System.out.println(aleatorio);
                }
               // System.out.println(indices.size());
            }
            for(int ij = 0;ij< indices.size();ij++){
                tmp.add(muesestreo.get(i+1).get(indices.get(ij)));
            }
            muesestreo2.put(i+1, tmp);
        }
        return muesestreo2;
    }
    public static double desempeno(int[][] matrix){
        int cont = 0;
        for(int i =0;i<matrix.length;i++){
            cont+= matrix[i][i];
        }
        //System.out.println(cont);
        int cont2 =0;
        for(int i =0;i<matrix.length;i++){
            for(int j =0;j<matrix[i].length;j++){
            cont2+= matrix[i][j];
        }
        }
        double d = (double)cont/(double)cont2;
        Estatisticos(matrix, d);
        return d;
    }
    public static double desempenocomp(LinkedList<SupervisedClassifier> conjunto){
        double desempenocomp =0;
        System.out.println("matriz de confución de entrenamiento del clasificador");
        for(int i =0;i< conjunto.size();i++){
            Ver(conjunto.get(i).desempenototal());
            System.out.println("El desempeño de entrenamiento del clasificador es:  "+desempeno(conjunto.get(i).desempenototal()));
            desempenocomp+= desempeno(conjunto.get(i).desempenototal());
        }
        return desempenocomp/(double)conjunto.size();
    }
    public static double Estatisticos(int[][] d, double media){
    double suma2 =0;
    for(int i = 0;i< d.length;i++){
        
        suma2+= Math.pow(((double)d[i][i]-media), 2.0);
        
    }
   double resp2 = Math.pow(((1.0/(double)d.length)*suma2),1.0/2.0);
    System.out.println("Desviación estantar : "+resp2);
    return resp2;
}
    public static int[][] desempenocomp2(LinkedList<SupervisedClassifier> conjunto,int clases){
        int[][] tmp = llenar(clases);
        for(int i =0;i< conjunto.size();i++){
         int [][] des =  conjunto.get(i).desempenototal();
         for(int desv = 0;desv< tmp.length;desv++){
               for(int desv2 = 0;desv2< tmp[desv].length;desv2++){
                   tmp[desv][desv2]+= des[desv][desv];
           }
           }
        }
        for(int desv = 0;desv< tmp.length;desv++){
               for(int desv2 = 0;desv2< tmp[desv].length;desv2++){
                   tmp[desv][desv2]/=conjunto.size();
           }
           }
        return tmp;
    }
}
