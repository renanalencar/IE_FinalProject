package br.poli.ecomp.aco;

import java.awt.*;
import java.util.Vector;

/**
 * Created by renanalencar on 27/05/17.
 */
public class Ground {
    private Vector<Vector<Path>> cities;

    public Ground() {

    }

    public void createCities (Vector<Integer> vec) {
        int size = vec.size() - 1;
        this.cities = new Vector<Vector<Path>>();
        Point[] arr = new Point[vec.size() / 2];

        for (int i = 0; i < size; i += 2) {
//           arr[i / 2].setLocation(vec.elementAt(i), vec.elementAt(i + 1));
            arr[i / 2] = new Point(vec.elementAt(i), vec.elementAt(i + 1));

            this.cities.add(new Vector<Path>());
        }

        size = vec.size() / 2;
        for (int i = 0; i < size; ++i) {
            Point from = arr[i];
            for (int j = 0; j < size; ++j) {
                Point to = arr[j];

                int distance = (int) Point.distance(from.getX(), from.getY(), to.getX(), to.getY());

                Path p = new Path();
                p.setDistance(distance);

                this.cities.elementAt(i).add(p);
            }
        }
    }

    public Path at(int i, int j) {
        int temp = j;
        j = i;
        i = temp;
        return this.cities.elementAt(i).elementAt(j);
    }

    public int vectorSize() {
        return this.cities.size();
    }
}
