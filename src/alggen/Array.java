/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package alggen;

/**
 * @RA 201811020032
 * @author gaga1
 */
public class Array {
    
    public static int[] removeTheElement(int[] arr, int index){
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
}
