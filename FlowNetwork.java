/**
 * -------------------------------------------
 *              FlowNetwork.java
 * -------------------------------------------
 *
 * Year : 2022
 * Course : Graphs and networks
 * Authors : Juanfer MERCIER, Adrien PICHON
 **/


import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

// Class to store edges of the flow network
class Edge {
    // Edge destination, capacity and flow
    int dest, cap, flow;
    Edge(int dest, int cap, int flow){
        this.dest = dest;
        this.cap = cap;
        this.flow = flow;
    }
}

// Class to represent a flow network adapted to the baseball problem
class FlowNetwork {
    // Adjacency list
    ArrayList<ArrayList<Edge>> adj_list;
    private int n;

    private int source = 0, sink = -1; // Source and sink
    private int maxFlow = -1; // Maximum flow value
    private int[] visited; // Visited nodes
    private int G = 0; // Sum of capacities of the arcs out of the source
    // Games left to play for team i
    private ArrayList<Integer> gi = new ArrayList<Integer>();

    // False if maxFlow hasn't been solved for this instance. True
    // otherwise.
    private boolean solved = false;

    FlowNetwork(
            int t,
            int n,
            int[][] data,
            boolean[] eliminated){
        this.n = n;
        // Loop variables
        int matches = (n-1)*(n-2)/2;
        int i = 0, j = 0, k = 0, l = matches-1, m = 0;

        // Set size of adjacency list and initialize it
        this.sink = matches + n;
        this.adj_list = new ArrayList<ArrayList<Edge>>(this.sink+1);
        for(k = 0; k < sink+1; k++)
            this.adj_list.add(k, new ArrayList<Edge>());

        for(i = 0, k = 0; i < n-1; i++){
            l++; m = l;
            for(j = i+1; j < n; j++){
                if(i != t && j != t && !eliminated[i] && !eliminated[j]){
                    k++; m++;
                    // ---------------- FIRST TYPE OF EDGE ----------------
                    // The edges from the source s and the matches
                    // (by convention, s = 0)
                    // (matches are sorted in order of minimum index : 1-2, 1-3, 2-5, 2-6, ...)
                    this.adj_list.get(0).add(new Edge(k, data[i][j+2], 0));
                    this.G += data[i][j+2];

                    // ---------------- SECOND TYPE OF EDGE ----------------
                    // The edges between matches and team
                    this.adj_list.get(k).add(new Edge(l, Integer.MAX_VALUE, 0));
                    this.adj_list.get(k).add(new Edge(m, Integer.MAX_VALUE, 0));
                }

                if(i == 0) {
                    // ---------------- THIRD TYPE OF EDGE ----------------
                    // The edges between teams and the pit
                    // (by convention, p = ((n-1)*(n-2)/2) + n)
                    this.adj_list.get(matches+j).add(new Edge(sink, data[t][0]+data[t][1]-data[i][0], 0));
                }
            }

        }
    }

    /* Returns adjacency list */
    public ArrayList<ArrayList<Edge>> getAdjacencyList() {
        return this.adj_list;
    }

    /* Returns maximum flow with Ford-Fulkerson algorithm */
    public int FordFulkerson() {
        /* If the problem is already solved no need to solve it again */
        if(this.solved) return this.maxFlow;
        else {
            int flow = -1; // Flow returned by the DFS procedure
            int N = this.sink+1;
            this.maxFlow = 0; // Maximum flow

            // Find max flow using Depth First Search (DFS)
            do {
                // Since we'll be calculating the minimum between the flow
                // value passed to DFS and the residual flow of edges, we
                // ensure that the initial flow value passed to DFS is large
                // enough (that way, initially, the actual residual flow will
                // be picked)
                flow = this.DFS(this.source, Integer.MAX_VALUE);
                this.maxFlow += flow;
            } while(flow != 0); // Until there is no augmenting path


            // We mark the instance as solved so we don't have to repeat
            // the process and we return the maximum flow value
            this.solved = true;
            return this.maxFlow;
        }
    }

    /* Depth First Search procedure adapted to the baseball problem
     *
     * node : the node from which we start the procedure
     * flow : the current flow passing through that node
     */
    private int DFS(int node, int flow) {
        // When we reach the sink, return augmented path flow
        if(node == this.sink) return flow;
        int subflow = -1, limit = -1;

        // Get list of edges of the node
        List<Edge> edges = this.adj_list.get(node);

        for(Edge e : edges) {
            limit = (node <= (this.n-1)*(this.n-2)/2) ? 2 : 1;
            // If we found an augmenting path
            if(e.cap-e.flow > 0) {
                // Update flow and run DFS from the node
                flow = Math.min(e.cap-e.flow, flow);
                subflow = DFS(e.dest, flow);

                // Update edges flow if there is an augmenting path
                if(subflow > 0){
                    e.flow += subflow;

                    for(Edge se : this.adj_list.get(e.dest))
                        if(se.dest == node)
                            se.flow -= subflow;

                    return subflow;
                }

            }
        }

        // No augmenting path : we return 0
        return 0;
    }

    /* Computes max flow and checks if all the paths from the
     * source are saturated
     */
    public boolean flowExists(){
        // If already solved then check if maximum flow corresponds
        // to the sum of the capactities of the arcs out of the source
        if(this.solved) return this.maxFlow == this.G;
        else {
            this.FordFulkerson();
            return this.maxFlow == this.G;
        }
    }

    /*
     * Called when a team t is eliminated. Check if other teams are
     * eliminated with a lemma.
     *
     * t            : the index of the team eliminated
     * data         : matrix containing the number of games the teams have
     *                won, have to play and against which team they have to
     *                play them
     * names        : team names
     * eliminated   : vector of booleans at true whenever a team is eliminated
     */
    public void useEliminationLemma(
            int t,
            int[][] data,
            String[] names,
            boolean[] eliminated) {
        int i = 0;

        for(i = 0; i < this.n; i++){
            if(i != t && !eliminated[i] && eliminated[t]
            && data[i][0] + data[i][1] <= data[t][0] + data[t][1]) {
                eliminated[i] = true;
                System.out.println("D'après le lemme, les " + names[i] + " sont aussi éliminés.");
            }
        }

        System.out.println();
    }
}
