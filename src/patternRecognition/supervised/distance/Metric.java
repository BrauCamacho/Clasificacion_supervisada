/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package patternRecognition.supervised.distance;

/**
 *
 * @author Braulio
 */
public enum Metric implements Distance{
    
    EUCLIDIAN{
        @Override
        public float calculate(float[] x1, float[] x2, int q) {
            float d = 0f;
            
            for(int i = 0; i < x1.length; i++)
                d += Math.pow(x1[i] - x2[i], 2);
            d = (float) Math.sqrt(d);
            
            return d;
        }
        
    },
     MINKOWSKI{
        @Override
        public float calculate(float[] x1, float[] x2,int q) {
            float d = 0f;
            
            for(int i = 0; i < x1.length; i++){
                float tm = Math.abs(x1[i] - x2[i]);
                d += Math.pow(tm, q);
            }
            d = (float) Math.pow(d, 1.0/(float)q);
            
            return d;
        }
  
    },
    MANHATTAN{
        @Override
        public float calculate(float[] x1, float[] x2, int q) {
            float d = 0f;
            
            for(int i = 0; i < x1.length; i++)
                d += Math.abs(x1[i] - x2[i]);            
            
            return d;
        }
        
    };
    
}
