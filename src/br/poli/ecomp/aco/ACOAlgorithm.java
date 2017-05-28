package br.poli.ecomp.aco;

import com.sun.xml.internal.bind.v2.TODO;

import java.io.IOException;
import java.util.Random;
import java.util.Vector;

/**
 * Created by renanalencar on 27/05/17.
 */
public class ACOAlgorithm {
    private Ground ground;
    private Vector<Ant> ants;
    private int pheromoneQ;
    private int pushNum;
    private double pheromoneRho;
    private double alpha;
    private double beta;
    private boolean useQ;

    public ACOAlgorithm(double alpha, double beta, int pheromoneQ, double pheromoneRho,
                        String fileOpen, boolean useOnlyQ, int pushNum) {

        this.alpha          = alpha;
        this.beta           = beta;
        this.pheromoneQ     = pheromoneQ;
        this.pheromoneRho   = pheromoneRho;
        this.pushNum        = pushNum;

        this.useQ           = useOnlyQ;
        TSPReader tspr      = new TSPReader(fileOpen);
        this.ground         = new Ground();

        try {
            ground.createCities(tspr.getPositions());
        } catch (IOException e) {
            System.err.println("Error reading graph.");
            e.printStackTrace();
        }

        this.createAnts(this.ground.vectorSize() * 2);
    }

    private void resetAnts() {
        for (Ant ant : this.ants) {
            ant.clearVisited();
            ant.addCity(0);
        }
    }
    private void putPheromone() {
        double sum = 0.0;
        int size = this.ground.vectorSize();

        for (Ant ant : this.ants) {
            if (useQ) {
                sum += pheromoneQ;
            } else {
                sum += pheromoneQ / (double) ant.getDistance();
            }
        }

        int last = 0;

        for (int i = 0; i < size; ++i) {
            Ant ant = this.ants.elementAt(i);

            for (int j = 0; j < ant.getVisited().size(); ++j) {
                int now = ant.getVisited().elementAt(j);
                Path p = this.ground.at(last, now);
                double pheromone = p.getPheromone();
                pheromone += sum;

                p.setPheromone(pheromone);

                this.ground.at(now, last).setPheromone(pheromone);
                last = now;
            }

            last = 0;
        }

        for (int i = 0; i < size; ++i) {

            for (int j = 0; j < size; j++) {
                Path p = this.ground.at(i, j);
                p.setPheromone(p.getPheromone() * (1 - pheromoneRho));
            }
        }
    }
    private int lowestPath() {
        int lowestDist = Integer.MAX_VALUE;

        for (Ant ant : this.ants) {
            if (ant.getDistance() < lowestDist) {
                lowestDist = ant.getDistance();
            }
        }
        return lowestDist;
    }

    private int roulette(Vector<Double> vec) {
        Random rand     = new Random();
        double random   = rand.nextDouble();
        int position    = -1;

        do {
            position ++;
            random -= vec.elementAt(position);
        } while (random > 0);

        return position;
    }

    public Vector<Integer> run(int epoch) {
        Vector<Integer> distances = new Vector<Integer>();

        for (int i = 0; i < epoch; ++i) {
            // ants walk
            int lowest = this.walk();
            if (i % pushNum == 0) {
                distances.add(lowest);
                System.out.println(i + ". Shortest path: " + lowest);
            }
        }
        return distances;
    }

    public void createAnts(int amount) {
        this.ants = new Vector<Ant>();

        for (int i = 0; i < amount; i++) {
            Ant ant = new Ant();
            this.ants.add(ant);
        }
    }

    public int walk() {
        // make ants forget they just have visited a city already
        this.resetAnts();
        // move all ants
        for (Ant ant: this.ants) {
            this.antStep(ant);
        }
        // all ants walked
        this.putPheromone();

        // return the shortest path
        return this.lowestPath();
    }

    public void antStep( Ant ant) {
        // for all cities
        int size = this.ground.vectorSize();

        for (int i = 0; i < size; ++i) {
            if (ant.getVisited().size() == size) {
                // thus it makes the round-trip to the first one
                ant.PopFirst();
            }

            Vector<Double> path = new Vector<Double>();
            int last = ant.getLast();

            double otherInfluence = 0;

            for (int k = 0; k < size; ++k) {
                if (ant.hasVisited(k)) {
                    continue;
                } else {
                    Path toGo = this.ground.at(last, k);
                    otherInfluence += toGo.getInfluence(alpha, beta);
                }
            }

            for (int j = 0; j < size; ++j) {
                if (ant.hasVisited(j)) {
                    path.add(0.0);
                    continue;
                }

                Path p = this.ground.at(last, j);
                double pInfluence = p.getInfluence(this.alpha, this.beta);

                double prob = pInfluence / otherInfluence;
                path.add(prob);
            }

            int pos = this.roulette(path);
            ant.addCity(pos);
            Path p = this.ground.at(last, pos);
            ant.addDistance(p.getDistance());
        }
    }

}
