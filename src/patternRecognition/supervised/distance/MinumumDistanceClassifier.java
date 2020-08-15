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
            this.distances.add(new DistanceComparator(key, this.metric.calculate(x, this.centroids.get(key),3)));
        Collections.sort(this.distances);
        
        return this.distances.getFirst().getKeyClass();
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
                matrizc[i][exp.get(i)[j]-1]++;
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
