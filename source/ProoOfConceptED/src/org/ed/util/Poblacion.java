/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ed.util;

import org.ed.EvolucionDiferencial;

/**
 *
 * @author servkey
 */
public class Poblacion {
  
  public static void imprimirVector(float[] poblacion){
        for (int i = 0; i < poblacion.length; i++){
            //System.out.print((i == 0? "[":"") + TruncarDecimal.truncateRnd2Decimales(poblacion[i]) + (i == poblacion.length-1? "]":","));
            if (!EvolucionDiferencial.isVariablesUnicas()){
                if (!EvolucionDiferencial.isFormatoReporte())
                    EvolucionDiferencial.imprimir((i == 0? "[":"") + TruncarDecimal.truncateRnd2Decimales(poblacion[i]) + (i == poblacion.length-1? "]":","));
                else
                    EvolucionDiferencial.imprimir((i == 0? "":"") + TruncarDecimal.truncateRnd2Decimales(poblacion[i]) + (i == poblacion.length-1? "\t":"\t"));
            }else{
                if (!EvolucionDiferencial.isFormatoReporte())
                    EvolucionDiferencial.imprimir((i == 0? "[":"") + (int)(poblacion[i]) + (i == poblacion.length-1? "]":","));
                else
                    EvolucionDiferencial.imprimir((i == 0? "":"") + (int)(poblacion[i]) + (i == poblacion.length-1? "\t":"\t"));
            }
        }
  }

  public static boolean isIntoVector(int i, int[] vector){
        boolean result = false;
        for (int index = 0; index < vector.length; index++){
            if (vector[index] == i)
            {
                result = true;
                break;
            }
        }
        return result;
    }

    public int generateAleatorioDiferenteOf(int minimo, int maximo, int val){
        int result = 0;
        //while(){

        
        return result;
    }
    public static boolean isIntoMatrix(int i, int[][] matrix){
        boolean result = false;
        for (int index = 0; index < matrix.length; index++){
            for (int column = 0; column < matrix[0].length; column++){
                if (matrix[index][column] == i)
                {
                    result = true;
                    break;
                }
            }
        }
        return result;
    }


    public static void initialize(int matrix[][], int val){
        for (int i = 0; i < matrix.length; i++)
        {
            for (int j = 0; j < matrix[0].length; j++)
                matrix[i][j] = val;
        }
    }

    public static float[][] clone(float[][] matrix){
        float matrixClone[][] = new float[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++)
            System.arraycopy(matrix[i], 0, matrixClone[i], 0, matrix[0].length);

        return matrixClone;
    }

     public static double[] clone(double[] vector){
        double vectorClone[] = new double[vector.length];
        System.arraycopy(vector, 0, vectorClone, 0, vector.length);
        return vectorClone;
    }

     public static double[] clone(float[] vector){
        double vectorClone[] = new double[vector.length];
        for (int i = 0; i < vector.length; i++)
            vectorClone[i] = vector[i];
        
        return vectorClone;
    }
     public static float[] cloneToFloat(float[] vector){
        float vectorClone[] = new float[vector.length];
        for (int i = 0; i < vector.length; i++)
            vectorClone[i] = vector[i];

        return vectorClone;
    }

}
