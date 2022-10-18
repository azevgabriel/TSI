/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backtracking;

import java.util.ArrayList;

/**
 *
 * @author Douglas
 */
public class AdjMatrix implements Graph {

    private int[][] matrix;

    public AdjMatrix(int nVertex) {
        this.matrix = new int[nVertex][nVertex];
    }

    @Override
    public ShortWay getAdjVertex(int node) {
        ShortWay sWay = new ShortWay();
        ArrayList<Integer> adj = new ArrayList<>();
        ArrayList<Integer> adjWeight = new ArrayList<>();
        for (int j = 0; j < this.matrix.length; j++) {
            if (node != j && this.matrix[node][j] != 0) {
                adjWeight.add(this.matrix[node][j]);
                adj.add(j);
            }
        }
        sWay.setShortWay(adj, adjWeight, node);
        return sWay;
    }

    @Override
    public void setEdge(int ori, int target, int weight) {
        this.matrix[ori][target] = weight;
    }

    @Override
    public void printGraph() {
        for (int i = 0; i < this.matrix.length; i++) {
            for (int j = 0; j < this.matrix.length; j++) {
                System.out.printf("%d\t", this.matrix[i][j]);
            }
            System.out.println("");
        }
    }
    
    public int returnWeightByPath (int pos, int[] path) {
        if (pos == this.getVertexSize() - 1) {
            if(matrix[path[pos]][path[0]] == 0){
                return -1;
            }
            
            return matrix[path[pos]][path[0]];
        } 
        return matrix[path[pos]][path[pos+1]];
    }
    
    public int getVertexSize () {
        return this.matrix.length;
    }

}
