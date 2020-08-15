/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package patternRecognition.supervised.distance;

import java.util.HashMap;
import java.util.LinkedList;
import patternRecognition.supervised.SupervisedClassifier;

/**
 *
 * @author brau
 */
public class Lineal extends SupervisedClassifier{
private float [] X;
private float [] W;
private float t;
    public Lineal(HashMap<Integer, LinkedList<float[]>> training) {
        super(training);
        train();
    }

    @Override
    protected void train() {
      float[][] centroide = centroides(training, training.size());
      this.X = X(centroide);
      this.W = W(centroide);
      this.t = T(X, W);
    }
    public static float[][] centroides(HashMap<Integer, LinkedList<float[]>> training, int total){
        float[][] f = new float[training.get(training.size()).get(0).length][total];
        for(int i =0;i< training.size();i++){
            for(int j = 0;j< training.get(i+1).get(0).length;j++){
                float tmp = 0;
                for(int l =0;l< training.get(i+1).size();l++){
                    tmp+=training.get(i+1).get(l)[j];
                }
                f[j][i] = tmp/(float) training.get(i+1).size();
               // System.out.printf(" %f ",f[j][i]);
            }
         //   System.out.println();
        }
        return f;
    }
public static float[] X (float[][] centroides){
    float[] tmp = new float[centroides.length];
    for(int i =0;i< centroides.length;i++){
            float tmp2 = 0;
            for(int j=0;j< centroides[i].length;j++){
              tmp2+= centroides[i][j];    
            }
            tmp[i] = tmp2/2.0f;
           // System.out.println(tmp[i]);
    }
    return tmp;
  }
 public static float[] W(float[][] centroides){
     float[] tmp = new float[centroides.length];
     for(int i =0;i< centroides.length;i++){
          float tmp2 = 0;
            tmp[i] = centroides[i][0] - centroides[i][1];
          //  System.out.println(tmp[i]);
    }
     return tmp;
 }
 public static float T(float[] X, float[] W){
     float f = 0;
     for(int i =0;i< X.length;i++){
         f+= (W[i]*X[i]); 
     }
    // System.out.println(" T "+f);
     return f;
 }
    @Override
    public int classify(float[] x) {
        float p = 0;
        for(int i =0;i< x.length;i++){
            p+= (this.W[i]*x[i]);
        }
        int r =0;
        if(p-this.t>0){
        r= 1;    
        }else if(p+this.t<0){
        r=2;    
        }
       return r;
    } 
    public int[][] desempenototal(){
        HashMap<Integer, int[]> exp = new HashMap<>();
        for(int i =0;i< training.size();i++){
            int[] c = new int[training.get(i+1).size()];
        for(int j =0;j< training.get(i+1).size();j++){
            c[j] = classify(training.get(i+1).get(i));
        }
        exp.put(i, c);
        }
        return generarmatriz(exp);
    }
    public static int[][] generarmatriz(HashMap<Integer, int[]> exp){
        int[][] matrizc = llenar(exp.size());
        for(int i =0;i< matrizc.length;i++){
            for(int j =0;j< exp.get(i).length;j++){
                matrizc[i][exp.get(i)[j]]++;
            }
        }
        return matrizc;
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
}
