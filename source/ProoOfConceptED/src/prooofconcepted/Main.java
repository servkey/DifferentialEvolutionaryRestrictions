/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package prooofconcepted;

import java.util.StringTokenizer;
import org.ed.EvolucionDiferencial;
import org.ed.util.FunctionsSetting;

/**
 * -d 1 -dF 1 -dS 1 -dGH 0 -func g01
 *
 * @author servkey
 */
public class Main { 

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // -r 0,10
        //Parametros
        //-P 20 -CR .5 -F 0.51 -s .443 -G 10000 -nF 1 -nH 0 -nG 2 -nV 20 -d 1  -df 0 -func g01
        //-d 1 = impresion de la mejor solucion encontrada
        //-d 0 = impresion de la mejor solucion por cada generación
        //-df 1 = impresion de la mejor solucion apartir de la región factible

        //Parametros para correr esta prueba
        //-P 20 -CR .5 -F 0.51 -s .443 -G 10000 -nF 1 -nH 0 -nG 2 -nV 20
        EvolucionDiferencial ed = new EvolucionDiferencial();

        //-P 20 -CR .5 -F 0.51 -s .443 -G 10000 -nF 1 -nH 0 -nG 2 -nV 10 -d 1 -r 0,10

        String restriccP = "";

        int r = 0;
        //Buscar funcion
        for (int i = 0; i < args.length; i++){
            if(args[i].equals("-func")){
                ed.setFuncion(args[i+1]);
                break;
            }
        }
        
        String[] funcion = FunctionsSetting.getFunction(ed.getFuncion());
        String restricc = "";

        if (funcion != null){
                ed.setFuncion(funcion[0]);
                ed.setPoblacionTamano(Integer.valueOf(funcion[1]));
                ed.setCR(Float.valueOf(funcion[2]));
                ed.setF(Float.valueOf(funcion[3]));
                ed.setSemilla(Float.valueOf(funcion[4]));
                ed.setMAXGEN(Integer.valueOf(funcion[5]));
                ed.setnF(Integer.valueOf(funcion[6]));
                ed.setnH(Integer.valueOf(funcion[7]));
                ed.setnG(Integer.valueOf(funcion[8]));
                ed.setnVariables(Integer.valueOf(funcion[9]));
                restricc = funcion[10];
                //ed.setnVariables(Integer.valueOf(funcion[6]));
        }


        for (int i = 0; i < args.length; i++){
            //-P 20 -CR .5 -F 0.51 -s .443 -G 10000 -nF 1 -nH 0 -nG 2 -nV 20 -d 1 -r 0,10
            if (args[i].equals("-P"))
                ed.setPoblacionTamano(Integer.valueOf(args[i+1]));
            else if(args[i].equals("-CR"))            
                ed.setCR(Float.valueOf(args[i+1]));            
            else if(args[i].equals("-F"))            
                ed.setF(Float.valueOf(args[i+1]));            
            else if(args[i].equals("-s"))            
                ed.setSemilla(Float.valueOf(args[i+1]));            
            else if(args[i].equals("-G"))            
                ed.setMAXGEN(Integer.valueOf(args[i+1])); 
            else if(args[i].equals("-nF"))  
                ed.setnF(Integer.valueOf(args[i+1])); 
            else if(args[i].equals("-nH"))  
                ed.setnH(Integer.valueOf(args[i+1])); 
            else if(args[i].equals("-nG"))  
                ed.setnG(Integer.valueOf(args[i+1])); 
            else if(args[i].equals("-nV"))  
                ed.setnVariables(Integer.valueOf(args[i+1]));
            else if(args[i].equals("-d"))
                ed.setD(Integer.valueOf(args[i+1]));
            else if(args[i].equals("-dF"))
                ed.setdF(Integer.valueOf(args[i+1]));
            else if(args[i].equals("-dS"))
                ed.setdS(Integer.valueOf(args[i+1]));
            else if(args[i].equals("-dGH"))
                ed.setdGH(Integer.valueOf(args[i+1]));
            else if(args[i].equals("-r"))
                restriccP = args[i + 1];            
        }

        float[][] restricciones = null;
        //Resetear
        if (!restriccP.equals("")){
            restricciones = settingRestricciones(restriccP,ed);
            
        }else
            restricciones = settingRestricciones(restricc,ed);
            
        ed.start();
    }

    public static float[][] settingRestricciones(String r, EvolucionDiferencial ed){
        int n = ed.getnVariables();
        float[][] restricciones = new float[n][2];
        
        float l = 0;
        float u = 0;
        //-r 0,1,1,2,3,4,5,6,7,8,9:0,10,10,11,12:0,1,13
        boolean first = true;
        StringTokenizer str = new StringTokenizer(r,":");
        while (str.hasMoreElements()){
            String cad1 = (String)str.nextElement();
            StringTokenizer element = new StringTokenizer(cad1,",");
            int index = 1;
            while(element.hasMoreElements()){
                String e = (String)element.nextElement();
                if (index == 1){
                    l = Float.valueOf(e);
                    index++;
                }
                else if (index == 2){
                    u = Float.valueOf(e);
                    if (first){
                        for (int i = 0; i < n; i++){
                            ed.setL(l);
                            ed.setU(u);
                            restricciones[i][0] = l;
                            restricciones[i][1] = u;
                        }
                    }
                    first = false;
                    index++;
                }else{
                    restricciones[Integer.valueOf(e)-1][0] = l;
                    restricciones[Integer.valueOf(e)-1][1] = u;
                }
            }
        }
        ed.setRestricciones(restricciones);
        return restricciones;
    }
}
