/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package alggen;

import java.util.ArrayList;

/**
 * @RA 201811020032
 * @author Gabriel Azevedo
 */

public class AlgGen {
    static int numberOfPopulation = 250000;
    static int numberOfSelectedIndividuals = 25;
    static int numberOfSelectedParts = 5;
    static int numberOfGenerations = 100;
    static double crossingRate = 0.4;
    static double crossingRandomPositionRate = 0.05;
  
    public static void main(String[] args) {
        
        FileManager fileManager = new FileManager();
        ArrayList<String> text = fileManager.stringReader("./src/data/Teste.txt");
       
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
        
        
        System.out.println("Geration | Weight (Best Individual) | Individial Use Any Patch? | Patch (Best Part) | Parent Weight (Best Part) | Relevance (Best Part)");
        System.out.println(0 + " | " + bestPopulation[0].getWeight() + " | NaN | NaN | NaN | NaN");
        
        Part[] bestParts = population.getBestPart();
        
        Individual bestIndividual = bestPopulation[0];
        
        for(int i = 0; i < numberOfGenerations - 1; i++) {
            int lastWeight = bestPopulation[0].getWeight();
            population.rePopulate(nVertex, graph, crossingRate, crossingRandomPositionRate);
            bestPopulation = population.naturalSelection(numberOfSelectedIndividuals, nVertex, lastWeight);
            bestParts = population.getBestPart();
            String patch = "NaN";
            if (bestPopulation[0].getPartSelected() != null) patch = bestPopulation[0].getPartSelected().getDNA();
            if (bestIndividual.getWeight() > bestPopulation[0].getWeight()) bestIndividual = bestPopulation[0];
            System.out.println((i + 1) + " | " + bestPopulation[0].getWeight() + " | " + patch + " | "  + bestParts[0].getDNA() + " | " + bestParts[0].getParentWeight() + " | " + bestParts[0].getRelevance());
        }
        
        bestIndividual.print();
    }
}
