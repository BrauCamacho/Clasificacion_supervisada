/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package patternRecognition.supervised.distance;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import org.apache.commons.math3.random.RandomDataGenerator;
import patternRecognition.supervised.SupervisedClassifier;

/**
 *
 * @author brau
 */
public class RedNeuronal extends SupervisedClassifier{
int cuan;
float [][] V;
float [][] W;
     public RedNeuronal(HashMap<Integer, LinkedList<float[]>> training, int cuan) {
        super(training);
        this.cuan = cuan; 
        train();
    }

    @Override
    protected void train() {
        
        RandomDataGenerator r = new RandomDataGenerator();
       // System.out.println("----");
    this. V= llenar(r, this.cuan+1, 6);
       // System.out.println("---");
   this.W= llenar(r,training.size() , V[0].length+1);
       // System.out.println("---");
        
        for(int m = 0;m<16000;m++){
        for(int q = 0;q < training.size();q++){
               
            for(int b = 0;b < training.get(q+1).size();b++){   
          float[]imph = imph(V, training.get(q+1).get(b));
     //   System.out.println("---");
         float[]fimph = Sigmoide(imph);
       // System.out.println("---");
         float[]impo = impo(fimph, W);
       // System.out.println("---");
        float[] fimpo = Sigmoide(impo);
      //  System.out.println("---");
        float[] dk = DerivadaSig(fimpo, q);
        //System.out.println("---");
        float[] Dimphj = dimphj(dk, W);
        //System.out.println("---");
        float[] Dhj = dhi(Dimphj, fimph);
        //System.out.println("---");
        W = ActializarW(W, 0.15f, dk, fimph);
        //System.out.println("---");
        V = ActualizarV(V, 0.15f, Dhj, training.get(q+1).get(b));
        //System.out.println("...");
        }
    }
        }
       }

    @Override
    public int classify(float[] x) {
        float[]imph = imph(V, x);
     //   System.out.println("---");
        float[]fimph = Sigmoide(imph);
       // System.out.println("---");
         float[]impo = impo(fimph, W);
         
         float[] fimpo = Sigmoide(impo);
         int c =0;
        for(int i =0;i< fimpo.length;i++){
            if(fimpo[i] >= 0.5){
             c = i; 
             //   System.out.println(impo[i]);
            }
        }   
        return c+1;
     //   throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
   public static float[][] llenar (RandomDataGenerator r,int num, int var){
           float[][] f= new float[num][var];
           for(int i =0;i< f.length;i++){
               for(int g =0;g< f[i].length;g++){
                   f[i][g] =(float) r.nextUniform(-0.5, 0.5);
                 //  System.out.printf(" %f ",f[i][g]);
           }
               //System.out.println();
           }
           return f;
       }
       public static float[] imph(float[][] V,float[] imp){
           float[] f = new float[V[0].length];
           for(int i =0;i< V[0].length;i++){
               float d =0;
               for(int g =0;g< V.length;g++){
                   if(g != V.length-1){
                   d+= imp[g]*V[g][i];
                   }else{
                      // System.out.println("-");
                       d+=V[g][i];
                   }
               }
               f[i] = d;
              // System.out.println(f[i]);
           }
           return f;
       }
       public static float[] Sigmoide(float[] f){
            float[] n = new float[f.length];
            for(int g =0;g< f.length;g++){
                n[g] =(float)(1.0f/(1.0f+Math.exp(-f[g]))); 
              //  System.out.println(n[g]);
            }
            return n;
       }
       public static float[] hiperbolica(float[] f){
            float[] n = new float[f.length];
            for(int g =0;g< f.length;g++){
                n[g] =(float)((Math.exp(f[g])-Math.exp(-f[g]))/(Math.exp(f[g])+Math.exp(-f[g])));
                //System.out.println(n[g]);
            }
            return n;
       }
       public static float[] impo(float[] fmph, float[][] W){
           float[] n = new float[W.length];
           for(int i = 0;i <W.length ;i++){
              float d=0;
               for(int l =0;l < W[i].length;l++){
                   if(l!= W[i].length-1){
                   d+= fmph[l]*W[i][l];
                   }else{
                       d+= W[i][l];
                   }
                   
               }
               n[i] = d;
           //    System.out.println(n[i]);
           }
           return n;
       }
       public static float[] DerivadaSig(float[] fimpo,int clase){
           float[] f = new float[fimpo.length];
           for(int i = 0;i < fimpo.length;i++){
               if(i == clase){
                  // System.out.println("--  "+i);
                   f[i] =(float) ((1.0- fimpo[i])*(fimpo[i]*(1.0-fimpo[i])));
               }else{
                   //System.out.println(i);
                   f[i] = (float)((0.0- fimpo[i])*(fimpo[i]*(1.0-fimpo[i])));
               }
             //  System.out.println(f[i]);
           }
           return f;
       }
       public static float[] dimphj(float[] dk, float[][] W){
           float [] f = new float[W[0].length];
           for(int i = 0; i<W[0].length-1;i++){
               float d =0;
               for(int q =0;q< W.length;q++){
                   d+=dk[q]*W[q][i];
               }
               f[i] = d;
               //System.out.println(f[i]);
           }
           return f;
       }
       public static float[] dhi(float[] dimphj, float[] imphj){
           float [] f = new float[imphj.length];
           for(int i =0;i< imphj.length;i++){
               f[i] =(float) (dimphj[i]*(imphj[i]*(1.0-imphj[i])));
              // System.out.println(f[i]);
           }
           return f;
       }
       public static float[][] ActializarW(float[][] W, float taza,float[]dk,float[] fimphj){
           
           for(int i = 0;i< W.length;i++){
           for(int j = 0;j< W[i].length;j++){
                if(j != W[i].length-1){
                    W[i][j] =  W[i][j]+ (taza*dk[i]*fimphj[j]);
                }else{
                    W[i][j] = W[i][j]+ (taza*dk[i]);
                }
                //System.out.printf(" %f ",W[i][j]);
           }
             //  System.out.println();
           }
           return W;
       }
       public static float[][]ActualizarV(float[][] V,float taza,float[] dj, float[] X){
           //System.out.println(V.length);
//           System.out.println(V[0].length);
//           System.out.println(dj.length);
//           System.out.println(X.length);
           for(int i =0;i< V.length ;i++){
           for(int j =0;j< V[i].length ;j++){
               if(i != V.length-1){
                   V[i][j] = V[i][j] + (taza* dj[j]*X[i]);
               }else{
                   V[i][j] = V[i][j] + (taza* dj[j]); 
               }
           //    System.out.printf(" %f ", V[i][j]);
           }
             //  System.out.println();
           }
           return V;
       }
}
