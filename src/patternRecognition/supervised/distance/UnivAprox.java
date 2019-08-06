/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package patternRecognition.supervised.distance;
import java.util.HashMap;
import java.util.LinkedList;
import org.apache.commons.math3.random.RandomDataGenerator;
import patternRecognition.supervised.SupervisedClassifier;

/**
 *
 * @author brau
 */
public class UnivAprox extends SupervisedClassifier{
int cuan;
float[][] peso;
float [] f2;
    public UnivAprox(HashMap<Integer, LinkedList<float[]>> training, int cuan) {
        super(training);
        this.cuan = cuan;
        train();
    }

    @Override
    protected void train() {
    RandomDataGenerator r = new RandomDataGenerator();
    this.peso = llenar(r, this.cuan, training.size());
    this.f2 = new float[training.size()];
      for(int m = 0;m<16;m++){
        for(int q = 0;q < training.size();q++){ 
       for(int b = 0;b < training.get(q+1).size();b++){
       float[][] f = clas(peso, training.get(q+1).get(b));
       f2 = sp(f,f2);
       float [] target = target(q-1, training.size());
       peso = actualizarpeso(peso, f2, 0.5f, training.get(q+1).get(b), target);  
       }
        }
    }
    }
    public static float[] target(int clase,int clases){
        float[] f= new float[clases];
        for(int i =0;i< clases;i++){
       if(i == clase){
           f[i] = 1;
       }else{
           f[i] =0;
       }     
        }
        return f;
    } 
    @Override
    public int classify(float[] x) {
        float[][] f = clas(peso, x);
       
       f2 = sp(f,f2);
      
      
       return 1;
    }
    public static float[][] llenar (RandomDataGenerator r,int num, int n){
           float[][] f= new float[num][n];
           for(int i =0;i< f.length;i++){
               for(int g =0;g< f[i].length;g++){
                   f[i][g] =(float) r.nextUniform(-1.0, 1.0);
                  // System.out.printf(" %f ",f[i][g]);
           }
               //System.out.println();
           }
           return f;
       }
     public static float[][] clas(float[][] W, float[] input){
         float[][] res = new float[W.length][W[0].length];
          for(int i =0;i< res.length;i++){
               for(int g =0;g< res[i].length;g++){
                   float d = W[i][g]*input[i];
                   if(d >= 0){
                   res[i][g] =1;    
                   }else{
                       res[i][g]=-1;
                   }
                 //  System.out.printf(" %f ",res[i][g]);
               }
            //   System.out.println();
               }
         return res;
     }
     public static float[] sp(float[][] fz,float[] f2){
         float[] f = new float[fz[0].length];
         
         for(int i =0;i< fz[0].length;i++){
         for(int j =0;j< fz.length;j++){
             f[i] += fz[j][i];
         }
            // System.out.println(f[i]);
             if(f[i]<= -1){
                  f2[i] -=1;
             }else if(f[i]> -1 &&f[i] <1){
                 f2[i]= f[i]/2;
             }else if(f[i] >= 1){
                 f2[i] +=1;
             }
           // System.out.println(f2[i]);
             
         }
         return f2;
     }
     public static float[][] actualizarpeso(float[][] peso, float[] output,float exactitud, float[] input, float[] target){
         for(int i =0;i< peso.length;i++){
             for(int j =0;j< peso[i].length;j++){
                 if(output[j] > (target[j]+exactitud)&&peso[i][j]* input[i] >=0){
                     peso[i][j] = peso[i][j] + 0.15f*-input[i];
                    // System.out.println("--");
                 }else if(output[j] < (target[j]-exactitud)&&peso[i][j]* input[i] <0){
                     peso[i][j] = peso[i][j] + 0.15f*+input[i];
                    // System.out.println("-");
                 }else{
                     peso[i][j] =0;
                 }
              //   System.out.printf(" %f ",peso[i][j]);
                  }
           //  System.out.println();
         }
         return peso;
     }
}
