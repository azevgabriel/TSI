/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package alggen;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @RA 201811020032
 * @author Gabriel Azevedo
 */

public class AlgGen {
    static int numberOfPopulation = 200000;
    static int numberOfSelectedIndividuals = 20;
    static int numberOfSelectedParts = 5;
    static int numberOfGenerations = 10;
    static double mutationRate = 0.4;
  
    public static void main(String[] args) {
        
        FileManager fileManager = new FileManager();
        ArrayList<String> text = fileManager.stringReader("./src/data/Teste_2.txt");
       
        int nVertex = 0;
        int graph[][] = null;
        
        for (int i = 0; i < text.size(); i++) {
            String line = text.get(i);
            if (i == 0){
                nVertex = Integer.parseInt(line.trim());
                graph = new int[nVertex][nVertex];
            }
            else {
                int oriVertex = Integer.parseInt(line.split(" ")[0]);
                String splits[] = line.substring(line.indexOf(" "), line.length()).split(";");
                for (String part : splits){
                    String edgeData[] = part.split("-");
                    int targetVertex = Integer.parseInt(edgeData[0].trim());
                    int weight = Integer.parseInt(edgeData[1]);
                    
                    graph[oriVertex][targetVertex] = weight;
                    graph[targetVertex][oriVertex] = weight;
                }
            }
        }
        
        int WorstWeightOfIndividual = 10000;
        
        Population population = new Population(nVertex, numberOfPopulation, graph, numberOfSelectedParts);
        Individual[] bestPopulation = population.naturalSelection(numberOfSelectedIndividuals, nVertex, WorstWeightOfIndividual);
        
        System.out.println("Geration | Weight (Best Individual) | Patch (Best Part) | Parent Weight (Best Part) | Relevance (Best Part)");
        System.out.println(0 + " | " + bestPopulation[0].getWeight() + " | NaN | NaN | NaN | ");
        
        Part[] bestParts = population.getBestPart();
        
        for(int i = 0; i < numberOfGenerations - 1; i++) {
            int lastWeight = bestPopulation[0].getWeight();
            population.rePopulate(nVertex, graph, mutationRate);
            bestPopulation = population.naturalSelection(numberOfSelectedIndividuals, nVertex, lastWeight);
            bestParts = population.getBestPart();
            System.out.println(i + 1 + " | " + bestPopulation[0].getWeight() + " | " + bestParts[0].getDNA() + " | " + bestParts[0].getParentWeight() + " | " + bestParts[0].getRelevance());
        }
    }
}
