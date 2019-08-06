/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package patternRecognition.supervised.distance;

/**
 *
 * @author AndrésEspinalJiménez
 */
public interface Distance {
    
    public float calculate(float[] x1, float[] x2);
    
}
