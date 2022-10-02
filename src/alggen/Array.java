/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package alggen;

import java.util.Random;

/**
 * @RA 201811020032
 * @author gaga1
 */
public class Array {
    
    public static int[] removeTheElementByIndex(int[] arr, int index){
        if (arr == null || index < 0
            || index >= arr.length) {
 
            return arr;
        }
 
        int[] anotherArray = new int[arr.length - 1];
      
        for (int i = 0, k = 0; i < arr.length; i++) {
            if (i == index) {
                continue;
            }
            anotherArray[k++] = arr[i];
        }
 
        return anotherArray;
    }
    
    public static int searchIndexByValue(int value, int[] arrOne){
        int index = -1;
        for(int i = 0; i < arrOne.length; i++) {
           if(arrOne[i] == value) {
               index = i;
               break;
           }
        }
                
        return index;
    }
    
    public static int[] randomizeArray(Random rnd, int[] initialArray) {
        for (int i = 0; i < initialArray.length; i++) {
            int randomIndexToSwap = rnd.nextInt(initialArray.length);
            int temp = initialArray[randomIndexToSwap];
            initialArray[randomIndexToSwap] = initialArray[i];
            initialArray[i] = temp;
        }
        return initialArray;
    }

}
