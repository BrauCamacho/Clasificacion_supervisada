/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package patternRecognition.supervised.distance;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import patternRecognition.supervised.SupervisedClassifier;

/**
 *
 * @author AndrésEspinalJiménez
 */
public class MinumumDistanceClassifier extends SupervisedClassifier{
    private final Metric metric;
    private int nFeatures;
    private HashMap<Integer,float[]> centroids;
    private LinkedList<DistanceComparator> distances;
    
    public MinumumDistanceClassifier(HashMap<Integer, LinkedList<float[]>> training, Metric metric, int nFeatures) {
        
        super(training);
        this.metric = metric;
        this.nFeatures = nFeatures;
        this.centroids = new HashMap<>();
        this.distances = new LinkedList<>();
        train();
    }

    @Override
    protected void train() {
        
        for(int keyClass : this.training.keySet()){
            float[] c =  new float[this.nFeatures];
            LinkedList<float[]> patterns = this.training.get(keyClass);
            
            for(int i = 0; i < this.nFeatures; i++){
                for(float[] p : patterns)
                    c[i] += p[i];
                c[i] /= patterns.size();
            }
            this.centroids.put(keyClass, c);
        }
        
    }

    @Override
    public int classify(float[] x) {
        
        this.distances.clear();
        for(Integer key : this.centroids.keySet())
            this.distances.add(new DistanceComparator(key, this.metric.calculate(x, this.centroids.get(key))));
        Collections.sort(this.distances);
        
        return this.distances.getFirst().getKeyClass();
    }               
    
}
