/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package patternRecognition.supervised.distance;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import patternRecognition.supervised.SupervisedClassifier;

/**
 *
 * @author brau
 */
public class Bayes extends SupervisedClassifier{
    float[][] medias;
    float[][] varianzas;
    int cuan;

    public int getCuan() {
        return cuan;
    }

    public void setCuan(int cuan) {
        this.cuan = cuan;
    }

    public float[][] getMedias() {
        return medias;
    }

    public void setMedias(float[][] medias) {
        this.medias = medias;
    }

    public float[][] getVarianzas() {
        return varianzas;
    }

    public void setVarianzas(float[][] varianzas) {
        this.varianzas = varianzas;
    }
    
     public static float formula(float media, float variansa,float numero){
        double res = (1.0/Math.sqrt(2.0f*Math.PI*variansa)*(Math.exp((-0.5f)*(Math.pow(numero-media, 2.0f)/variansa))));
        return (float)res;
    }
    public static float formula2(HashMap<Integer, LinkedList<float[]>> c, float[][] media,float[][] varianza, float[] Exp, int clase){
    float res = 1;
        for(int i = 0;i< Exp.length;i++){
        res*=formula(media[clase][i],varianza[clase][i],Exp[i]);
    }
       int  suma = 0;
     for(int i = 0;i< c.size();i++){
         suma+= c.get(i+1).size();
     }   
        return res *((float)c.get(clase+1).size()/(float)suma);
    }
    
    public static void promedio(LinkedList<float[]> uno,int clase,float[][] f){
    for(int i =0;i<uno.get(0).length;i++){
        float cont =0;
        float d =0;
        for(int p= 0;p < uno.size();p++){
          d+=uno.get(p)[i];
           cont++;
        }
        f[clase][i] = (d/cont);
       
    }
   
}

    public Bayes(HashMap<Integer, LinkedList<float[]>> training, int cuan) {
        super(training);
        this.cuan = cuan;
        train();
    }

    @Override
    protected void train() {
        this.medias = new float[training.size()][this.cuan];
        this.varianzas = new float[training.size()][this.cuan];
        for(int i =0;i< this.training.size();i++){
        promedio(this.training.get(i+1), i, medias);
        
        }
        for(int i =0;i< this.training.size();i++){
        
        varianza(this.medias, this.training.get(i+1), this.varianzas,i);
        }
    }

    @Override
    public int classify(float[] x) {
        float[] Resp = new float[this.training.size()];
        for(int i =0;i< this.training.size();i++){
            Resp[i] =formula2(training, this.getMedias(), this.getVarianzas(), x, i)/abajo(training,this.getMedias(), this.getVarianzas(), x, cuan, i);
        }
        int e = 0;
        for(int i = 0;i < Resp.length;i++){
            if(Resp[i] > Resp[e]){
                e = i;
            }
        }
        
        return e+1;
        }
    public static void varianza(float[][] media, LinkedList<float[]> Registros,float[][] f,int clase ){
        for(int i  =0;i<media[0].length;i++){
            float resp =0;
            float d = 0;
          for(int p = 0;p<Registros.size();p++){
              resp+= Math.pow(Registros.get(p)[i] - media[clase][i],2);
              d++;
          }
          f[clase][i] = (float)((1.0/d)*resp);
        }
       
    }
    public static float abajo(HashMap<Integer, LinkedList<float[]>> c, float[][] medias,float[][] varianzas, float[] Exp, int cuan,int clase){
       float res =0;
        for(int i = 0;i < c.size();i++){
              res+= formula2(c,medias,varianzas,Exp, i);
        }
        return res;
    }
    
}
