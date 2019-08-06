/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package patternRecognition.supervised;

import java.util.HashMap;
import java.util.LinkedList;

/**
 *
 * @author AndrésEspinalJiménez
 */
public abstract class SupervisedClassifier {
    protected HashMap<Integer,LinkedList<float[]>> training;
    
    public SupervisedClassifier(HashMap<Integer,LinkedList<float[]>> training){
        
        this.training = training;
        
        
    }
    
    protected abstract void train();
    
    public abstract int classify(float[] x);
    
}
