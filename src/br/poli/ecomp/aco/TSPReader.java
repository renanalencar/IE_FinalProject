package br.poli.ecomp.aco;

import jdk.nashorn.internal.parser.Lexer;
import jdk.nashorn.internal.runtime.regexp.joni.Regex;

import java.io.*;
import java.util.Vector;

/**
 * Created by renanalencar on 27/05/17.
 */
public class TSPReader {
    private String fileName;

    public TSPReader(String fileName) {
        this.fileName = fileName;
    }

    public Vector<Integer> getPositions() throws IOException {
        FileInputStream fstream = new FileInputStream(this.fileName);
        DataInputStream in = new DataInputStream(fstream);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String line;
        Vector<Integer> output = new Vector<Integer>();

        while ((line = br.readLine()) != null) {
            line = trim(line);

            Vector<String> _line = split(line, " ");
            int lineSize = _line.size();
            if(lineSize == 3) {
                _line.remove(_line.firstElement());
                String test = _line.elementAt(0);
                if (is_number(test)) {
                    for (int i = 0; i < lineSize - 1; ++i) {
                        test = _line.elementAt(i);
                        int value = Integer.parseInt(test);
                        output.add(value);
                    }
                }
            }
        }
        return output;
    }

//    private String ltrim(String s) {
//
//    }
//
//    private String rtrim(String s) {
//
//    }

    private String trim(String s) {
//        return ltrim(rtrim(s));
        return s.trim();
    }

    private Vector<String> split (final String input, final String regex) {
        String[] words = input.split(regex);
        Vector<String> result = new Vector<>();
        for (String s : words) {
            result.add(s);
        }

        return result;
    }

    private boolean is_number(final String s) {
        return s != null && s.matches("[-+]?\\d*\\.?\\d+");
    }
}
