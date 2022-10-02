/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package alggen;

import static alggen.Array.removeTheElementByIndex;
import static alggen.Array.searchIndexByValue;
import static alggen.Array.randomizeArray;
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
    private final int mutationRate;
    private final int[][] graph;
    
    public Individual(int numberOfNodes, double mutationRate, int[][] graph) {
        this.length = numberOfNodes;
        this.nodes = new int[numberOfNodes];
        this.positions = new int[numberOfNodes];
        this.mutationRate = (int)(mutationRate * 100);
        this.graph = graph;
        
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
        this.mutationRate = individual.getMutationRate();
        this.graph = individual.getGraph();
    };
    
    public void createTotalRandom(int cofOfMaxInterations) {
        
        Random rnd = new Random(System.nanoTime());
        int[] positionsRandomized = this.positions;
        int maxInterations = this.length * cofOfMaxInterations;
        
        positionsRandomized = randomizeArray(rnd, positionsRandomized);
        //System.out.println("Random Array: " + Arrays.toString(positionsRandomized));
        
        for (int i = 0; i < this.length; i++) {
            
            int interations = 0;

            int length = positionsRandomized.length;
            int indexRandomized = rnd.nextInt(length);
                       
            if(i == 0) {
                this.nodes[i] = positionsRandomized[indexRandomized];
            } else {
                int position = positionsRandomized[indexRandomized];
                int actualWeight = this.graph[this.nodes[i-1]][position];
                
                while (actualWeight == 0 && interations < maxInterations) {
                    indexRandomized = rnd.nextInt(length);
                    position = positionsRandomized[indexRandomized];
                    actualWeight = this.graph[this.nodes[i-1]][position];
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
            
            positionsRandomized = removeTheElementByIndex(positionsRandomized, indexRandomized);
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
                this.weight = this.weight + this.graph[lastPosition][firstPosition];
            else this.weight = this.weight + badNews;
        }
    }
    
    public void createRandomWithPossibilityMutation(int cofOfMaxInterations, Part[] parts, int numberOfParts){
        Random rnd = new Random(System.nanoTime());
        int[] positions = this.positions;
        int maxInterations = this.length * cofOfMaxInterations;     
        int mutationChance = Math.round(rnd.nextInt((100 * 1000) / 1000));
        
        if (mutationChance < mutationRate && numberOfParts > 0) {
            // Random pick of the part, but then we will implement more pick rate in the best
            int pickPart = Math.round(rnd.nextInt(numberOfParts * 1000)/1000);

            String[] partsStringify = parts[pickPart].getDNA().split(",");
            int lengthOfParts = partsStringify.length;
            int positionPickPart = parts[pickPart].getPosition();
            
            int[] positionsRandomized = positions;
            
            for(int i = 0; i < lengthOfParts; i++){
                int valueNode = Integer.parseInt(partsStringify[i]);
                int index = searchIndexByValue(valueNode, positions);
                positionsRandomized = removeTheElementByIndex(positions, index);
            }
                     
            positionsRandomized = randomizeArray(rnd, positionsRandomized);
            
            // Insight: disabled parts after used
            
            for (int i = 0; i < this.length - lengthOfParts + 1; i++) {
            
                int interations = 0;

                int length = positionsRandomized.length;
                int indexRandomized = rnd.nextInt(length);
                
                if(i == positionPickPart) {
                    for(int j = 0; j < lengthOfParts; j++){
                        this.nodes[i + j] = Integer.parseInt(partsStringify[j]);
                    }
                } else if(i == 0) {
                    if (this.nodes[i] == -1) this.nodes[i] = positionsRandomized[indexRandomized];
                    else this.nodes[i + lengthOfParts] = positionsRandomized[indexRandomized];
                } else {
                    int position = positionsRandomized[indexRandomized];
                    
                    if(this.nodes[i] == -1){                    
                        int actualWeight = this.graph[this.nodes[i-1]][position];

                        while (actualWeight == 0 && interations < maxInterations) {
                            indexRandomized = rnd.nextInt(length);
                            position = positionsRandomized[indexRandomized];
                            actualWeight = this.graph[this.nodes[i-1]][position];
                            interations = interations + 1;
                        }
                        
                        if(interations == maxInterations){
                            break;
                        } else {
                            this.nodes[i] = position;
                            this.weight = this.weight + actualWeight;
                        }
                    } else {
                        int actualWeight = this.graph[this.nodes[i + lengthOfParts - 2]][position];

                        while (actualWeight == 0 && interations < maxInterations) {
                            indexRandomized = rnd.nextInt(length);
                            position = positionsRandomized[indexRandomized];
                            actualWeight = this.graph[this.nodes[i + lengthOfParts - 2]][position];
                            interations = interations + 1;
                        }
                        
                        if(interations == maxInterations){
                            break;
                        } else {
                            this.nodes[i + lengthOfParts - 1] = position;
                            this.weight = this.weight + actualWeight;
                        }
                    }
                }

                positionsRandomized = removeTheElementByIndex(positionsRandomized, indexRandomized);
            }
            
        } else {
            int[] positionsRandomized = randomizeArray(rnd, positions);
            
            for (int i = 0; i < this.length; i++) {
            
                int interations = 0;

                int length = positionsRandomized.length;
                int indexRandomized = rnd.nextInt(length);

                if(i == 0) {
                    this.nodes[i] = positionsRandomized[indexRandomized];
                } else {
                    int position = positionsRandomized[indexRandomized];
                    int actualWeight = this.graph[this.nodes[i-1]][position];

                    while (actualWeight == 0 && interations < maxInterations) {
                        indexRandomized = rnd.nextInt(length);
                        position = positionsRandomized[indexRandomized];
                        actualWeight = this.graph[this.nodes[i-1]][position];
                        interations = interations + 1;
                    }

                    if(interations == maxInterations){
                        break;
                    } else {
                        this.nodes[i] = position;
                        this.weight = this.weight + actualWeight;
                    }
                }

                positionsRandomized = removeTheElementByIndex(positionsRandomized, indexRandomized);
            }
        }
        
        int lastPosition = this.nodes[this.length - 1];
        int firstPosition = this.nodes[0];
        int badNews = 99 * this.length;
        
        if(lastPosition == -1) {
            this.weight = this.weight + badNews;
        } else {
            if(graph[lastPosition][firstPosition] != 0) 
                this.weight = this.weight + this.graph[lastPosition][firstPosition];
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
    
    public int getMutationRate() {
        return this.mutationRate;
    }
    
    public int[][] getGraph() {
        return this.graph;
    }
    
    public void print() {
        System.out.println(Arrays.toString(this.nodes) + " Peso: " + this.weight);
    }
    
}
