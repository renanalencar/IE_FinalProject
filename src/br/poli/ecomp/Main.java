package br.poli.ecomp;

import br.poli.ecomp.aco.ACOAlgorithm;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Vector;

public class Main {

    public static Vector<Integer> simulation(double alpha, double beta, int q, double rho, boolean useQ,
                                             String loadFrom, int epoch, int times) {
        Vector<Vector<Integer>> out = new Vector<Vector<Integer>>();
        for (int i = 0; i < times; ++i) {
            System.out.println("Iteraction: " + i );
            ACOAlgorithm aco = new ACOAlgorithm(alpha, beta, q, rho, loadFrom, useQ, (int) ( epoch * 0.04));
            Vector<Integer> r = aco.run(epoch);
            out.add(r);
        }

        Vector<Integer> mean = new Vector<>(25);

        for (int i = 0; i < times; ++i) {
            Vector<Integer> atI = out.elementAt(i);
            int aux = 0;

            for (int j = 0; j < atI.size(); ++j) {
                aux = mean.elementAt(j);
                aux += atI.elementAt(j);
                mean.add(aux);
            }
        }

        int temp = 0;

        for (int b = 0; b < mean.size(); ++b) {
            temp = mean.elementAt(b);
            temp /= times;
        }

        return mean;
    }

    public static void write(Vector<Integer> input, String outputFile) {

        try{
            // Create file
            FileWriter fstream = new FileWriter(outputFile + ".csv");
            BufferedWriter out = new BufferedWriter(fstream);
            for (int i = 0; i < input.size(); ++i) {
                out.write(input.elementAt(i) + ",");
            }
            //out.write("Hello Java");
            //Close the output stream
            out.close();
        }catch (Exception e){//Catch exception if any
            System.err.println("Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {

        //3.1
        //alfa = 1, beta 0.5
        write(simulation(1.0, 0.5, 100, 0.3, false, "dataset/att48.tsp", 500, 1), "results/01 1 0.5");
        //alfa 1, beta 1
//        write(simulation(1.0, 1.0, 100, 0.3), "02 1 1");
        //alfa 1, beta 3
//        write(simulation(1.0, 3.0, 100, 0.3), "03 1 3");
        //alfa 3, beta 0.5
//        write(simulation(3.0, 0.5, 100, 0.3), "04 3 0.5");
        //alfa 3, beta 1
//        write(simulation(3.0, 1.0, 100, 0.3), "05 3 1");
        //alfa 3, beta 3
//        write(simulation(3.0, 3.0, 100, 0.3), "06 3 3");

        //3.2
        //rho 0.3 q 1
//        write(simulation(1, 1, 1, 0.3, true), "07 0.3 1");
        //rho 0.3 q 100
//        write(simulation(1, 1, 100, 0.3, true), "08 0.3 100");
        //rho 0.3 q 10000
//        write(simulation(1, 1, 10000, 0.3, true), "09 0.3 10000");
        //rho 0.5 q 1
//        write(simulation(1, 1, 1, 0.5, true), "10 0.5 1");
        //rho 0.5 q 100
//        write(simulation(1, 1, 100, 0.5, true), "11 0.5 100");
        //rho 0.5 q 10000
//        write(simulation(1, 1, 10000, 0.5, true), "12 0.5 10000");
        //rho 0.9 q 1
//        write(simulation(1, 1, 1, 0.9, true), "13 0.9 1");
        //rho 0.9 q 100
//        write(simulation(1, 1, 100, 0.9, true), "14 0.9 100");
        //rho 0.9 q 10000
//        write(simulation(1, 1, 10000, 0.9, true), "15 0.9 10000");
    }
}
