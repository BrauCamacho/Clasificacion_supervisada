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
public class DistanceComparator implements Comparable<DistanceComparator> {

    private int keyClass;
    private Float distance;

    public DistanceComparator(int keyClass, float distance) {

        this.keyClass = keyClass;
        this.distance = distance;

    }

    public int getKeyClass() {

        return this.keyClass;

    }

    @Override
    public int compareTo(DistanceComparator o) {

        return this.distance.compareTo(o.distance);

    }
}
