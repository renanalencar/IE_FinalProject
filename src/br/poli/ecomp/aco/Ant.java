package br.poli.ecomp.aco;

import java.util.Vector;

/**
 * Created by renanalencar on 27/05/17.
 */
public class Ant {
    private Vector<Integer> visited;
    private int distance;

    public Ant() {
        this.distance = 0;
        this.visited = new Vector<Integer>();
    }

    public void addCity(int c) {
        this.visited.add(c);
    }

    public Vector<Integer> getVisited() {
        return this.visited;
    }

    public void clearVisited() {
        this.visited.removeAllElements();
        this.distance = 0;
    }

    public void addDistance(final int d) {
        this.distance += d;
    }

    public int getDistance() {
        return this.distance;
    }

    public boolean hasVisited(int v) {
        boolean flag = false;
        for (int val : visited) {
            if (v == val) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    public int getLast() {
        return this.visited.elementAt(visited.size() - 1);

    }

    public void PopFirst() {
        this.visited.remove(this.visited.firstElement());
    }

}
