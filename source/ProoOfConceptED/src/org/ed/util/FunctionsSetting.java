/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ed.util;

/**
 *
 * @author servkey
 */
public class FunctionsSetting {
              //-P 20 -CR .5 -F 0.51 -s .443 -G 10000 -nF 1 -nH 0 -nG 2 -nV 10 -d 1 -r 0,10

    private static String[][] matrixFunctions = new String[][]{//h  g
            {"C01","20", ".5","0.51",".443","10000","1","0","2","10","0,10"},
            {"C02","20", ".5","0.51",".443","10000","1","1","2","10","-5.12,5.12"},
            {"C03","20", ".5","0.51",".443","10000","1","1","0","10","-100,100"},
            {"C04","20", ".5","0.51",".443","10000","1","4","0","10","-50,50"},
            {"C05","20", ".95","0.4",".23","10000","1","2","0","10","-600,600"},
            {"C06","20", ".76","0.41",".543","10000","1","2","0","10","-600,600"},
            {"C07","20", ".5","0.51",".443","10000","1","0","1","10","-140,140"},
            {"C08","20", ".5","0.51",".443","10000","1","0","1","10","-140,140"},
            {"C09","20", ".85","0.65",".443","10000","1","1","0","10","-500,500"},
            {"C10","20", ".5","0.51",".443","10000","1","1","0","10","-500,500"},
            {"C11","20", ".7","0.8",".443","10000","1","1","0","10","-100,100"},
            {"C12","20", ".5","0.51",".443","10000","1","1","1","10","-1000,1000"},
            {"C13","20", ".5","0.51",".443","10000","1","0","3","10","-500,500"},
            {"C14","20", ".5","0.51",".443","10000","1","0","3","10","-1000,1000"},
            {"C15","20", ".5","0.51",".443","10000","1","0","3","10","-1000,1000"},
            {"C16","20", ".5","0.51",".443","10000","1","2","2","10","-10,10"},
            {"C17","20", ".5","0.51",".443","10000","1","1","2","10","-10,10"},
            {"C18","20", ".5","0.51",".443","10000","1","1","1","10","-50,50"},
            {"g01","20", ".5","0.51",".443","10000","1","0","9","13","0,1,1,2,3,4,5,6,7,8,9:0,100,10,11,12:0,1,13"},
            {"g02","20", ".5","0.51",".443","10000","1","0","2","20","0,10"},
            {"g03","20", ".53","0.35",".243","10000","1","1","0","10","0,1"},
            {"g04","20", ".5","0.51",".443","10000","1","0","6","5","78,102,1:33,45,2:27,45,3,4,5"},
            {"g05","20", ".5","0.522",".243","10000","1","3","2","4","0,1200,1:0,1200,2:-0.55,0.55,3:-0.55,0.55,4"},
            {"g06","20", ".5","0.652",".243","10000","1","0","2","2","13,100,1:0,100,2"},
            {"g07","20", ".5","0.522",".243","10000","1","0","8","10","-10,10"},
            {"g08","20", ".5","0.522",".243","10000","1","0","2","2","0,10,1:0,10,2"},
            {"g09","20", ".5","0.522",".243","10000","1","0","4","7","-10,10"},
            {"g10","20", ".57","0.522",".243","10000","1","0","6","8","100,10000,1:1000,10000,2,3:10,1000,4,5,6,7,8"},
            {"g11","20", ".5","0.522",".243","10000","1","1","0","2","-1,1,1:-1,1,2"},
            {"g12","20", ".5","0.522",".243","10000","1","0","1","3","0,10,1,2,3"},
            {"g13","20", ".5","0.522",".243","10000","1","3","0","5","-2.3,2.3,1,2:-3.2,3.2,3,4,5"},
            {"g14","20", ".5","0.522",".243","10000","1","3","0","10","0,10"},
            {"g15","20", ".5","0.522",".243","10000","1","2","0","3","0,10"},
            {"g16","20", ".5","0.522",".243","10000","1","0","38","5","704.4148,906.3855,1:68.6,288.88,2:0,134.75,3:193,287.0966,4:25,84.1988,5"},
            {"g17","20", ".5","0.522",".243","10000","1","4","0","6","0,400,1:0,1000,2:340,420,3:340,420,4:-1000,1000,5:0,.5236,6"},
            {"g18","20", ".5","0.522",".243","10000","1","0","13","9","-10,10,1,2,3,4,5,6,7,8:0,20,9"},
            {"g19","20", ".5","0.522",".243","10000","1","0","5","15","0,10"},
            {"g20","20", ".5","0.522",".243","10000","1","14","6","24","0,10"},
            {"g21","20", ".5","0.522",".243","10000","1","5","1","7","0,1000,1:0,40,2,3:100,300,4:6.3,6.7,5:5.9,6.4,6:4.5,6.25,7"},
            ///
            {"g22","20", ".5","0.522",".243","1000000","1","19","1","22","0,20000,1:0,1000000,2,3,4:0,40000000,5,6,7:100,299.99,8:100,399.99,9:100.01,300,10:100,400,11:100,600,12:0,500,13,14,15:0.01,300,16:0.01,400,17:-4.7,6.25,18,19,20,21,22"},
            {"g23","20", ".64","0.642",".243","1000000","1","4","2","9","0,300,1,2,6:0,300,3,5,7:0,200,4,8:0.01,.03,9"},
            //{"g23","20", ".64","0.778",".963","10000","1","4","2","9","0,300,1,2,6:0,300,3,5,7:0,200,4,8:0.01,.03,9"},
            {"g24","20", ".5","0.522",".243","10000","1","0","2","2","0,3,1:0,4,2"},
            {"C19","20", ".5","0.522",".243","10000","1","1","0","8","1,8"}

            //0,1,1,2,3,4,5,6,7,8,9:0,10,10,11,12:0,1,13
    };

    public static String[] getFunction(String name){
        String [] result = null;
        for (int i = 0; i < matrixFunctions.length; i++){
            if (matrixFunctions[i][0].equals(name))
                return matrixFunctions[i];

        }
        return result;
    }
    public static String[][] getMATRIXFUNCTIONS(){
        return matrixFunctions;
    }
 }