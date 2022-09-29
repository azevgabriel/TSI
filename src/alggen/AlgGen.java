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
    static int numberOfPopulation = 1000;
    static int numberOfSelections = 5;
  
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
    
        
        Population population = new Population(nVertex, graph, numberOfPopulation);
        Individual[] bestPopulation = population.naturalSelection(numberOfSelections);
      
        for (int i = 0; i < numberOfSelections; i++) {
            bestPopulation[i].print();
        }     
        
    }
    
}
