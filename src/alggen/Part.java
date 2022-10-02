package alggen;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * @RA 201811020032
 * @author Gabriel Azevedo
 */
public class Part {
    private String id;
    private String patch;
    private int parentWeight;
    private int position;
    private int numberOfRepeatability;
    private int relevance;

    public Part(String patch, int parentWeight, int position) {
        this.id = patch + "-" + position + "-" + parentWeight;
        this.patch = patch;
        this.parentWeight = parentWeight;
        this.numberOfRepeatability = 0;
        this.position = position;
        this.relevance = parentWeight;
    }
    
    public Part(Part part) {
        this.id = part.getId();
        this.parentWeight = part.getParentWeight();
        this.patch = part.getDNA();
        this.position = part.getPosition();
        this.numberOfRepeatability = part.getNumberOfRepeatability();
        this.relevance = part.getRelevance();
    }
    
    public void repeat(){
        this.numberOfRepeatability = this.numberOfRepeatability + 1;
        this.relevance = this.relevance - 5;
    }
    
    public void punishment(int punishment){
        this.relevance = this.relevance + punishment;
    }
    
    public String getId() {
        return id;
    }
    
    public String getDNA() {
        return patch;
    }

    public int getParentWeight() {
        return parentWeight;
    }
    
    public int getNumberOfRepeatability(){
        return numberOfRepeatability;
    }
    
    public int getPosition() {
        return position;
    }
    
    public int getRelevance() {
        return relevance;
    }
}
