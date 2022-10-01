/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package alggen;

import static alggen.Array.removeTheElement;
import java.util.Arrays;
import java.util.Random;

/**
 * @RA 201811020032
 * @author Gabriel Azevedo
 */
public class Individual {
    private final int length;
    private final int[] nodes;
    private int weight; 
    private final int[] positions;
    
    public Individual(int numberOfNodes) {
        this.length = numberOfNodes;
        this.nodes = new int[numberOfNodes];
        this.positions = new int[numberOfNodes];
        
        for(int i = 0; i < numberOfNodes; i++){
            this.nodes[i] = -1;
            this.positions[i] = i;
        }
    }
        
    public Individual(Individual individual){
        this.nodes = individual.getNodes();
        this.weight = individual.getWeight();
        this.length = individual.getLength();
        this.positions = individual.getPositions();
    };
    
    public void create(int graph[][], int cofOfMaxInterations) {
        
        Random rnd = new Random(System.nanoTime());
        int[] positionsRandomized = this.positions;
        int maxInterations = this.length * cofOfMaxInterations;
        
        for (int i = 0; i < this.length; i++) {
            int randomIndexToSwap = rnd.nextInt(positionsRandomized.length);
            int temp = positionsRandomized[randomIndexToSwap];
            positionsRandomized[randomIndexToSwap] = positionsRandomized[i];
            positionsRandomized[i] = temp;
        }
        
        //System.out.println("Random Array: " + Arrays.toString(positionsRandomized));
        
        for (int i = 0; i < this.length; i++) {
            
            int interations = 0;

            int length = positionsRandomized.length;
            int indexRandomized = rnd.nextInt(length);
                       
            if(i == 0) {
                this.nodes[i] = positionsRandomized[indexRandomized];
            } else {
                int position = positionsRandomized[indexRandomized];
                int actualWeight = graph[this.nodes[i-1]][position];
                
                while (actualWeight == 0 && interations < maxInterations) {
                    indexRandomized = rnd.nextInt(length);
                    position = positionsRandomized[indexRandomized];
                    actualWeight = graph[this.nodes[i-1]][position];
                    interations = interations + 1;
                    //System.out.println(interations + "/" + maxInterations + " position: " + position + " actualWeight:" + actualWeight);
                }
                
                if(interations == maxInterations){
                    break;
                } else {
                    this.nodes[i] = position;
                    this.weight = this.weight + actualWeight;
                }
                 
                //System.out.println(interations + "/100 index:" + indexRandomized + "0 restOfLength:" + length);
            }
            
            positionsRandomized = removeTheElement(positionsRandomized, indexRandomized);
            //System.out.println(Arrays.toString(positionsRandomized));
        }
        
        //System.out.println("Way Array: " +Arrays.toString(this.nodes));
        
        int lastPosition = this.nodes[this.length - 1];
        int firstPosition = this.nodes[0];
        int badNews = 99 * this.length;
        
        if(lastPosition == -1) {
            this.weight = this.weight + badNews;
        } else {
            if(graph[lastPosition][firstPosition] != 0) 
                this.weight = this.weight + graph[lastPosition][firstPosition];
            else this.weight = this.weight + badNews;
        }
        
    }
    
    public int[] getNodes() {
        return this.nodes;
    }
    
    public int getWeight() {
        return this.weight;
    }
    
    public int getLength() {
        return this.length;
    }
    
    public int[] getPositions() {
        return this.positions;
    }
    
    public void print() {
        System.out.println(Arrays.toString(this.nodes) + " Peso: " + this.weight);
    }
    
}
