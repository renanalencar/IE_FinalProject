package br.poli.ecomp.aco;

/**
 * Created by renanalencar on 27/05/17.
 */
public class Path {
    private int distance;
    private double pheromone;

    public Path() {
        this.distance = Integer.MAX_VALUE;
        this.pheromone = 1.0 / 48.0;
    }

    public void setDistance(int d) {
        this.distance = d;
    }

    public int getDistance() {
        return this.distance;
    }

    public void setPheromone (double p) {
        this.pheromone = p;
    }

    public double getPheromone() {
        return this.pheromone;
    }

    public double getInfluence(double alpha, double beta) {
        double tao = Math.pow(this.pheromone, alpha);
        double theta = Math.pow(1.0 / this.distance, beta);

        return tao * theta;
    }
}
