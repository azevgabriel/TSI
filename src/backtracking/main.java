/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backtracking;

import alggen.FileManager;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Douglas Castilho
 * @author Gabriel Azevedo
 */

public class main {

    public static int[] bestPath = new int[100];
    public static int bestWeight = 99999;
    
    public static void main(String[] args) {
        FileManager fileManager = new FileManager();
        ArrayList<String> text = fileManager.stringReader("./src/data/Teste.txt");
        
        Graph graph = null;

        int nVertex = 0;
        
        for (int i = 0; i < text.size(); i++) {
            String line = text.get(i);
            if (i == 0){
                nVertex = Integer.parseInt(line.trim());
                graph = new AdjMatrix(nVertex);
            }
            else {
                int oriVertex = Integer.parseInt(line.split(" ")[0]);
                String splits[] = line.substring(line.indexOf(" "), line.length()).split(";");
                for (String part : splits){
                    String edgeData[] = part.split("-");
                    int targetVertex = Integer.parseInt(edgeData[0].trim());
                    int weight = Integer.parseInt(edgeData[1]);
                    graph.setEdge(oriVertex, targetVertex, weight);
                    graph.setEdge(targetVertex, oriVertex, weight);
                }
            }
        }
        
        graph.printGraph();
        
        int [] path = new int[graph.getVertexSize()];
        boolean [] av = new boolean[graph.getVertexSize()];
        
        for (int i = 0; i < graph.getVertexSize(); i++) {
            av[i] = true;
        }
        
        backTracking(0, path, av, 0, graph, 0);
    }
    
    public static void backTracking (   
        int node,
        int path[],
        boolean av[],
        int pos,
        Graph graph,
        int weight
    ) {
        
        if (pos >= graph.getVertexSize() && graph.returnWeightByPath(graph.getVertexSize() - 1, path) != -1){
            weight = weight + graph.returnWeightByPath(graph.getVertexSize() - 1, path);
            if (weight < bestWeight) {
                bestWeight = weight;
                bestPath = path;
                System.out.println(Arrays.toString(path));
                System.out.println(weight);
            }
        } else if (pos >= graph.getVertexSize()) {
        } else {
            ShortWay sW = graph.getAdjVertex(node);
            
            if (sW.getLength() > 0)
                for (int j = 0; j < sW.getLength(); j++) {
                    int i = sW.getNodeByIndex(j);

                    if (av[i] == true) {
                        
                        int nextWeight = weight;
                        
                        if(pos > 0) 
                            nextWeight = nextWeight + sW.getWeightByIndex(j);

                           av[i] = false;
                            path[pos] = i;
                            pos++;
                       
                        if (nextWeight < bestWeight) 
                            backTracking(i, path, av, pos, graph, nextWeight);
                        
                        pos--;
                        av[i] = true;
                    }
                }
        }
    }
    
}
