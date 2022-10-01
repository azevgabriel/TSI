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
    private final String patch;
    private final int parentWeight;
    private int numberOfRepeatability;

    public Part(String patch, int parentWeight) {
        this.patch = patch;
        this.parentWeight = parentWeight;
        this.numberOfRepeatability = 0;
    }
    
    public void incrementsNumberOfRepeatability(){
        this.numberOfRepeatability = this.numberOfRepeatability + 1;
    }

    public String getDNA() {
        return patch;
    }

    public int getNumberOfRepeatability() {
        return numberOfRepeatability;
    }

    public int getParentWeight() {
        return parentWeight;
    }
    
}
