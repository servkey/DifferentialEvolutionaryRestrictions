/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ed.util;

/**
 *
 * @author servkey
 */
public class Estadisticas {
        public static float media(float[] x){
            float media = 0;
            for (int i = 0; i < x.length;  i++){
                media += x[i];
            }
            media = media / x.length;
            return media;
        }
        
        public static float desviacionEstandar(float media,float[] x){
            float res = (1.0f/(x.length-1));
            float acumulador = 0;
            for (int i = 0; i < x.length;  i++){
                    acumulador += Math.pow(x[i]-media,2);
            }
            return (float)Math.sqrt(acumulador * res);
        }
}
