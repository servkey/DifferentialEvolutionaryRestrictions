/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ed.util;

import java.text.DecimalFormat;

/**
 *
 * @author servkey
 */
public class TruncarDecimal {
    public static float truncate(float number){
        /*float res = 0;
        if (number != 0){
            res = number * 1000000;
            res = (((int)(res)) / 1000000.0f);
        }
        return res;*/
        return number;
    }

    public static String truncateRnd(float number){
            DecimalFormat twoDForm = new DecimalFormat("#######.##############");
            //DecimalFormat twoDForm = new DecimalFormat("#####.######");
            return twoDForm.format(number);
    }


    public static String truncateRnd2Decimales(float number){
            DecimalFormat twoDForm = new DecimalFormat("#######.##############");
            return twoDForm.format(number);
    }
//		System.out.println(float.valueOf(twoDForm.format(4.2395)));
}
