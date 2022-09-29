/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package alggen;

/**
 *
 * @author gaga1
 */
public class Population {

    private final Individual[] individuals;
    private final int sizeOfPopulation;
    private int generation = 0;

    public Population(int numberOfNodes, int[][] graph, int sizeOfPopulation) {
        this.sizeOfPopulation = sizeOfPopulation;
        this.individuals = new Individual[sizeOfPopulation];
        this.generation = 0;
        for (int i = 0; i < sizeOfPopulation; i++) {
            this.individuals[i] = new Individual(numberOfNodes);
            this.individuals[i].create(graph, 1);
        }
    }
    
    

    public Individual[] naturalSelection(int numberOfSelections) {
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

        return bestPopulation;
    }
    
}
