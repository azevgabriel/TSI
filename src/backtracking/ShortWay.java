/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backtracking;

import java.util.ArrayList;

/**
 *
 * @author Gabriel Azevedo
 */
public class ShortWay {
    public ArrayList<Integer> adj;
    public ArrayList<Integer> adjWeight;
    public int node;

    public ShortWay() {
        this.adj = new ArrayList<>();
        this.adjWeight = new ArrayList<>();
        this.node = -1;
    }
    
    public void setShortWay(
        ArrayList<Integer> adj,
        ArrayList<Integer> adjWeight,
        int node
    ) {
        this.adj = adj;
        this.adjWeight = adjWeight;
        this.node = node;
    }
    
    public int getNodeByIndex(int index) {
        return adj.get(index);
    }
    
    public int getWeightByIndex(int index) {
        return adjWeight.get(index);
    }
    
    public int getRelNode() {
        return this.node;
    }
    
    public int getLength() {
        return this.adj.size();
    }
}
