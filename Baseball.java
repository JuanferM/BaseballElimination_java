/**
 * -------------------------------------------
 *                  Baseball.java
 * -------------------------------------------
 *
 * Year : 2022
 * Course : Graphs and networks
 * Authors : Juanfer MERCIER, Adrien PICHON
 **/

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.System;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

class Baseball {
    /* ANSI escape codes to print text with a certain color */
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\033[1;31m";
    public static final String ANSI_GREEN = "\033[1;32m";
    public static final String ANSI_CYAN = "\u001B[36m";

    /* Displays the program's manual */
    private static void usage() {
        System.err.println("Usage: java Baseball file.txt");
        System.err.println("Solve the baseball elimination problem on instance file.txt");
    }

    /* Return next int from file when possible, -1 otherwise */
    private static int getInt(Scanner file) {
        if(file.hasNextInt())
            return file.nextInt();
        return -1;
    }

    /* Construct flow network using the data provided
     *
     * t            : index of team considered
     * n            : number of rows
     * data         : matrix containing the number of games the teams have won,
     *                have to play and against who they gotta play them with
     * elimiated    : vector of booleans at true whenever a team is eliminated
     */
    public static FlowNetwork ConstructionReseau(
            int t,
            int n,
            int[][] data,
            boolean[] eliminated) {
        return new FlowNetwork(t, n, data, eliminated);
    }

    /* Do test for team t
     *
     * t            : team index
     * R            : the flow network for team t
     * eliminated   : each team status
     */
    public static void TestEliminationEquipe(
        int t,
        int[][] data,
        String[] names,
        FlowNetwork R,
        boolean[] eliminated) {
        if(!R.flowExists()) {
            eliminated[t] = true;
            System.out.println("Les " + names[t] + " sont éliminés.");
        }

        if(eliminated[t])
            R.useEliminationLemma(t, data, names, eliminated);
    }


    /* Solve the baseball elimination problem on the data provided
     *
     * n            : number of teams
     * names        : team names
     * data         : matrix containing the number of games the teams have won,
     *                have to play and against who they gotta play them with
     * eliminated   : each team status
     */
    public static void TestEliminationToutes(
        int n,
        int[][] data,
        String[] names,
        boolean[] eliminated) {
        // Loop variables
        int i;

        for(i = 0; i < n; i++) {
            // Construct the network from data
            if(!eliminated[i]) {
                FlowNetwork R = ConstructionReseau(i, n, data, eliminated);
                TestEliminationEquipe(i, data, names, R, eliminated);
            }
        }

    }

    public static void main(String args[]) {
        // Check if length of args array is
        // greater than 0
        if(args.length == 1) {
            // CHECKS PASSED. Now we read the file...
            try {
                Scanner file = new Scanner(new File(args[0]));
                // Loop and useful variables
                int i = 0, j = 0, idx = 0;

                // Number of rows
                int n = getInt(file);
                // Team names
                String[] names = new String[n];
                // Team relevant data
                int[][] data = new int[n][n+2];
                // Team status
                boolean[] eliminated = new boolean[n];


                for(i = 0; i < n; i++) {
                    idx = getInt(file)-1;
                    names[idx] = file.next().replace('-', ' ').replace('_', ' ');
                    for(j = 0; j < n+2; j++) data[idx][j] = getInt(file);
                    eliminated[i] = false;
                }

                System.out.println(ANSI_CYAN + "Noms des équipes :");
                for(i = 0; i < n; i++) {
                    System.out.println("\t" + names[i]);
                }

                System.out.println(ANSI_RESET + "\nDébut de la méthode...\n" + ANSI_RED);

                TestEliminationToutes(n, data, names, eliminated);

                System.out.println(ANSI_RESET + "Fin de la méthode.\n");

                System.out.println(ANSI_GREEN + "Équipes restantes : ");
                for(i = 0; i < n; i++) {
                    if(!eliminated[i]) System.out.println("\t" + names[i]);
                }
                System.out.println(ANSI_RESET);

                // Close input stream
                file.close();
            } catch(FileNotFoundException e) { // File not found
                System.err.println(ANSI_RED + "File \"" + args[0] + "\" not found\n" + ANSI_RESET);
                usage();
            }
        } else { // No file specified
            System.err.println(ANSI_RED + "Filename not specified\n" + ANSI_RESET);
            usage();
        }
    }
}
