/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package patternRecognition.supervised.distance;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import patternRecognition.supervised.SupervisedClassifier;

/**
 *
 * @author AndrésEspinalJiménez
 */
public class KNearestNeighbor extends SupervisedClassifier{
    private Integer K;
    private final Metric metric;
    private int nFeatures;    
    private LinkedList<DistanceComparator> distances;    
    
    public KNearestNeighbor(HashMap<Integer, LinkedList<float[]>> training, Integer K, Metric metric, int nFeatures) {
        
        super(training);
        this.K = K;
        this.metric = metric;
        this.nFeatures = nFeatures;        
        this.distances = new LinkedList<>();        
        train();        
        
    }

    @Override
    protected void train() {}

    @Override
    public int classify(float[] x) {
        int[] votes = new int[this.training.size()];
        Integer maxVotes = -1;
        Integer maxIndex = -1;
                
        this.distances.clear();
        for(Integer key : this.training.keySet()){
            LinkedList<float[]> patterns = this.training.get(key);
            
            for(float[] pattern : patterns)
                this.distances.add(new DistanceComparator(key, this.metric.calculate(x, pattern)));
        }
        Collections.sort(this.distances);
        for(Integer i = 0; i < this.K; i++)
            votes[this.distances.get(i).getKeyClass() - 1] ++;
        for(Integer i = 0; i < votes.length; i++)
            if(votes[i] > maxVotes){
                maxVotes = votes[i];
                maxIndex = i;
            }
        
        return maxIndex + 1;
    }
    
}
