/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package patternRecognition.supervised.distance;

import org.apache.commons.math3.distribution.NormalDistribution;

/**
 *
 * @author brau
 */
public class clase {
    
    public static void main(String[] args){
        NormalDistribution n = new NormalDistribution(4d, 4d);
        
        for(int i = 1; i <= 10; i++)
            System.out.println(n.sample());
    }
    
}
