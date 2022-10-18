/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package alggen;

import java.util.ArrayList;
import static alggen.Array.searchIndexByValue;

/**
 * @RA 201811020032
 * @author Gabriel Azevedo
 */
public class Population {

    private Individual[] individuals;
    private Part[] parts;
    private int numberOfParts;
    private final int sizeOfPopulation;
    private Individual[] bestPopulation;
    private Part[] bestParts;
    private int numberOfSelectedParts;


    public Population(int numberOfNodes, int sizeOfPopulation, int[][] graph, int numberOfSelectedParts) {
        this.sizeOfPopulation = sizeOfPopulation;
        this.individuals = new Individual[sizeOfPopulation];
        this.parts = new Part[sizeOfPopulation * 100];
        this.numberOfParts = 0;
        this.numberOfSelectedParts = numberOfSelectedParts;
        this.bestParts = new Part[numberOfSelectedParts];
        for (int i = 0; i < sizeOfPopulation; i++) {
            this.individuals[i] = new Individual(numberOfNodes, 0, 0, graph);
            this.individuals[i].createTotalRandom(1);
        }
    }
    
    public void rePopulate(int numberOfNodes, int[][] graph, double crossingRate, double crossingRandomPositionRate){
        this.individuals = new Individual[this.sizeOfPopulation];
        
        
        
        selectBestPart(this.numberOfSelectedParts);
        
        for(int i = 0; i < (this.sizeOfPopulation); i++){
            this.individuals[i] = new Individual(numberOfNodes, crossingRate, crossingRandomPositionRate, graph);
            this.individuals[i].createRandomWithPossibilityMutation(1, this.bestParts, this.numberOfSelectedParts);
        }
    }

    public Individual[] naturalSelection(int numberOfSelectedIndividuals, int individualLength, int lastWeight) {
        bestPopulation = new Individual[numberOfSelectedIndividuals];

        for (int i = 0; i < this.sizeOfPopulation; i++) {
            for (int j = 0; j < numberOfSelectedIndividuals; j++) {
                if (bestPopulation[j] == null) {
                    bestPopulation[j] = new Individual(individuals[i]);
                    break;
                } else if (individuals[i].getWeight() < bestPopulation[j].getWeight()) {
                    bestPopulation[j] = new Individual(individuals[i]);
                    break;
                }
            }
        }

        if(this.bestParts[0] != null || this.parts[0] != null) {
            for(int i = 0; i < this.numberOfSelectedParts; i++) {
                int index = -1;
                for(int j = 0; j < this.numberOfParts; j++) {
                   if(this.parts[j] == this.bestParts[i]) {
                       index = j;
                       break;
                   }
                }
                
                if(bestPopulation[0].getWeight() > lastWeight){
                    this.parts[index].punishment(bestPopulation[0].getWeight() - lastWeight);
                } else {
                    this.parts[index].reward(lastWeight - bestPopulation[0].getWeight());
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
        
        for (int i = 0; i < numberOfSelectedIndividuals; i++) {
            int[] compareOne = bestPopulation[i].getNodes();
            String compareOneStringify = "";
            
            for(int j = 0; j < individualLength; j++){
                if(j != (individualLength - 1)) compareOneStringify = compareOneStringify + compareOne[j] + ",";
                else compareOneStringify = compareOneStringify + compareOne[j];
            }
            
            for (int j = i + 1; j < numberOfSelectedIndividuals; j++) {
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
                            int valueNode = Integer.parseInt(compareTwoStringify.split(",")[0]);
                                    
                            int indexOne = searchIndexByValue(valueNode, compareOne);
                            int indexTwo = searchIndexByValue(valueNode, compareTwo);
                            String compareOneId = compareTwoStringify + "-" + indexOne + "-" + bestPopulation[i].getWeight();
                            String compareTwoId = compareTwoStringify + "-" + indexTwo + "-" + bestPopulation[j].getWeight();
                            
                            for (int c = 0; c < this.numberOfParts + 1; c++){
                                if(this.parts[c] == null){
                                    
                                    if(compareOneId.equals(compareTwoId)){
                                        this.parts[this.numberOfParts] = new Part(compareTwoStringify, bestPopulation[i].getWeight(), indexOne);
                                        this.parts[this.numberOfParts].repeat();
                                        this.numberOfParts = this.numberOfParts + 1;
                                    } else {
                                        this.parts[this.numberOfParts] = new Part(compareTwoStringify, bestPopulation[i].getWeight(), indexOne);
                                        this.parts[this.numberOfParts + 1] = new Part(compareTwoStringify, bestPopulation[j].getWeight(), indexTwo);
                                        this.numberOfParts = this.numberOfParts + 2;
                                    }
                                
                                    break;
                                } 
                                
                                if (this.parts[c].getDNA().equals(compareTwoStringify)) {
                                    
                                    if (compareOneId.equals(compareTwoId)) {
                                        if(this.parts[c].getId().equals(compareOneId)) {
                                            this.parts[c].repeat();
                                            this.parts[c].repeat();
                                        } else {
                                            this.parts[this.numberOfParts] = new Part(compareTwoStringify, bestPopulation[i].getWeight(), indexOne);
                                            this.parts[this.numberOfParts].repeat();
                                            this.numberOfParts = this.numberOfParts + 1;
                                        }
                                    } else {
                                        if(this.parts[c].getId().equals(compareOneId)) {
                                            this.parts[c].repeat();
                                        } else {
                                            this.parts[this.numberOfParts] = new Part(compareTwoStringify, bestPopulation[j].getWeight(), indexOne);
                                            this.numberOfParts = this.numberOfParts + 1;
                                        }

                                        if(this.parts[c].getId().equals(compareTwoId)) {
                                            this.parts[c].repeat();
                                        } else {
                                            this.parts[this.numberOfParts] = new Part(compareTwoStringify, bestPopulation[j].getWeight(), indexTwo);
                                            this.numberOfParts = this.numberOfParts + 1;
                                        }
                                    }
                                    
                                    break;
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
    
    public void selectBestPart(int numberOfSelectedParts) {
        if(numberOfSelectedParts > this.numberOfParts){
            this.numberOfSelectedParts = this.numberOfParts;
        } else {
            this.numberOfSelectedParts = numberOfSelectedParts;
        }
              
        for(int i = 0; i < this.numberOfParts; i++){
            for(int j = 0; j < this.numberOfSelectedParts; j++){
                if(bestParts[j] == null){
                    this.bestParts[j] = this.parts[i];
                    break;
                } else if (this.parts[i] == null) { 
                    break;
                } else if (this.parts[i].getRelevance() < this.bestParts[j].getRelevance()) {
                    this.bestParts[j] = this.parts[i];
                }
            }
        }
    }
    
    public int getNumberOfParts() {
        return numberOfParts;
    }
    
    public Part[] getBestPart() {
        return this.bestParts;
    }
    
}
