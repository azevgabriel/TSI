/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package alggen;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @RA 201811020032
 * @author Gabriel Azevedo
 */
public class Population {

    private final Individual[] individuals;
    private final Part[] parts;
    private final int sizeOfPopulation;

    public Population(int numberOfNodes, int sizeOfPopulation) {
        this.sizeOfPopulation = sizeOfPopulation;
        this.individuals = new Individual[sizeOfPopulation];
        this.parts = new Part[sizeOfPopulation];
        for (int i = 0; i < sizeOfPopulation; i++) {
            this.individuals[i] = new Individual(numberOfNodes);
        }
    }
    
    public void Populate(Part[] parts, int[][] graph){
        if(parts[0] != null) {
            for (int i = 0; i < sizeOfPopulation - parts.length; i++) {
                this.individuals[i].create(graph, 1);
            }
        } else {
            for (int i = 0; i < sizeOfPopulation; i++) {
                this.individuals[i].create(graph, 1);
            }   

        }
    }

    public Individual[] naturalSelection(int numberOfSelections, int individualLength) {
        Individual[] bestPopulation = new Individual[numberOfSelections];

        for (int i = 0; i < this.sizeOfPopulation; i++) {
            for (int j = 0; j < numberOfSelections; j++) {
                if (bestPopulation[j] == null) {
                    bestPopulation[j] = new Individual(individuals[i]);
                    break;
                } else if (individuals[i].getWeight() < bestPopulation[j].getWeight()) {
                    bestPopulation[j] = new Individual(individuals[i]);
                    break;
                }
            }
        }
        
        ArrayList<Integer> equalsParts = new ArrayList<>();
        int minLengthPart = 2;
        
        for (int i = minLengthPart; i < individualLength; i++){
            if(individualLength % i == 0){
                equalsParts.add(i);
            }
        }
        
        for (int i = 0; i < numberOfSelections; i++) {
            int[] compareOne = bestPopulation[i].getNodes();
            String compareOneStringify = "";
            
            for(int j = 0; j < individualLength; j++){
                if(j != (individualLength - 1)) compareOneStringify = compareOneStringify + compareOne[j] + ",";
                else compareOneStringify = compareOneStringify + compareOne[j];
            }
            
            for (int j = i + 1; j < numberOfSelections; j++) {
                int[] compareTwo = bestPopulation[j].getNodes();
                String compareTwoStringify = "";
                        
                int auxLength = equalsParts.size();
                             
                while(auxLength > 0){
                    int lastIndex = auxLength - 1;
                    int lengthPart = equalsParts.get(lastIndex);
                    int numberOfParts = individualLength/lengthPart;
                    
                    for(int a = 0; a < numberOfParts; a++){
                        compareTwoStringify = ""; 
                        
                        // Building parts of second array
                        for(int b = 0 + (a * lengthPart); b < (lengthPart * (a + 1)); b++){
                            if(b != (lengthPart * (a + 1)) - 1) compareTwoStringify = compareTwoStringify + compareTwo[b] + ",";
                            else compareTwoStringify = compareTwoStringify + compareTwo[b];
                        }
                        
                        if(compareOneStringify.contains(compareTwoStringify)){
                            int meanWeight = Math.round((bestPopulation[i].getWeight() + bestPopulation[j].getWeight()) / 2);
                            
                            for (int c = 0; c < parts.length; c++){
                                if(this.parts[c] == null){
                                    this.parts[c] = new Part(compareTwoStringify, meanWeight);
                                    break;
                                } else if (this.parts[c].getDNA() == compareTwoStringify) {
                                    this.parts[c].incrementsNumberOfRepeatability();
                                }
                                
                            }
                            
                        }
                    }
                    
                    auxLength = auxLength - 1;
                }
            }    
        }    
        
        return bestPopulation;
    }
    
    public Part[] getParts() {
        return parts;
    }
    
}
