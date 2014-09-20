/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ed;

import java.lang.Integer;
import java.util.HashMap;
import java.util.HashSet;
import javax.swing.JTextArea;
import org.ed.util.Poblacion;
import org.ed.util.Random;
import org.ed.util.TruncarDecimal;
import org.ed.util.FcnSuite;
/**
 *
 * @author servkey
 */
public class EvolucionDiferencial {

    private static String bitacora = "";
    private float[][] poblacion;
    private float[][] triales;
    private int poblacionTamano = 20;
    private float CR = 0.5f;
    private float F = 0.51f;
    private float[] mejorVector;
    private boolean[] factibles;
    private int[] genFactibles;
    private int corrida;

    private boolean finish = false;
    //private float F = 0.49f;
    //Tolerancia de error
    private float e = 0.0001f;    
    private float semilla = 0.443f;
    ///private float semilla = 0.913f,943;
    private float l = 0f;
    private float u = 10f;
    private static boolean variablesUnicas = false;
    /*
     * private float l = -600;
    private float u = 600;
     */
    //private int MAXGEN = 10000;
    private String funcion = "C05";
    private int MAXGEN = 10000;
    private boolean solucionFactible = false;
    //private int MAXGEN = 5150;

    //Nivel de impresion d
    private int d = 0;
    private int dF =0;
    private int dS =1;
    private int dGH =0 ;
    private static boolean formatoReporte = false;
    private float fxActual = 0f;
    //Valores configurables
    private int nF = 1;
    private int nH = 0;
    private int nG = 2;
    private int nVariables = 20;
    private int nViolaciones;
    private int iteracionFactible = -1;
    private float restricciones[][] = new float[nVariables][2];
    private static JTextArea texto;
    private int generaciones = 0;
    private Random random;

    public EvolucionDiferencial(){
        random = new Random();
        random.setRseed(this.semilla);
    }

    public void setAreaTexto(JTextArea txt){
        texto = txt;
    }
    
    public static void imprimirLinea(String str){
        System.out.println(str);
        if (texto != null){
            texto.append(str + "\n" );
          //  texto.setCaretPosition(texto.getText().length() - 1);
        }
        
        // bitacora += str + "\n";
    }

    public static void imprimir(String str){
        System.out.print(str);
        if (texto != null){
            texto.append(str);
            //texto.setCaretPosition(texto.getText().length() - 1);
        }
        
        //   bitacora += str;
    }

    public void imprimirSetting(){

        imprimirLinea("**********************************");
        imprimirLinea("Función: " + this.funcion);
        imprimirLinea("Semilla: " + this.semilla);
        imprimirLinea("CR: " + this.CR);
        imprimirLinea("F: " + this.F);
        imprimirLinea("Variables: " + this.nVariables);
        imprimirLinea("Poblacion: " + this.poblacion.length);
        if (getD() == 1)
            imprimirLinea("Modo: La mejor solución encontrada");
        else
            imprimirLinea("Modo: " + (this.getD() == 0 && this.getdF() == 1? "Factibles":"Todas"));
        imprimirLinea("***********************************");

    }

    public void start(){

        random.randomize();
        if(funcion.equals("C19"))
            EvolucionDiferencial.setVariablesUnicas(true);
        poblacion = new float[getPoblacionTamano()][getnVariables()];
        generaciones = 0;
        generarPoblacion();
        double h [] = new double[this.getnH()];
        double g[] = new double[this.getnG()];

        if (this.dS == 1)
            this.imprimirSetting();
        while(getGeneracionActual() < getMAXGEN() ){
            fxActual = TruncarDecimal.truncate(this.aptitud(poblacion[this.elegirMejorIndividuo(poblacion)],g, h));
            seleccionWithOperadores();
            reemplazo();

            if (getD() == 0)
                if (this.getdF() == 1){
                     if (this.isSolucionFactible()){
                        if (!EvolucionDiferencial.isFormatoReporte())
                            imprimirLinea("Gen " + (1 + getGeneracionActual() ));
                        else
                            imprimir(String.valueOf(1 + getGeneracionActual()));
                        imprimirDatos(g,h);

                         if (this.dGH == 1)
                            imprimirGH(g,h);

                    }
                }else{
                       if (!EvolucionDiferencial.isFormatoReporte())
                            imprimirLinea("Gen " + (1 + getGeneracionActual() ) + "\t");
                       else
                            imprimir(String.valueOf(1 + getGeneracionActual()) + "\t");
                       imprimirDatos(g,h);

                       if (this.dGH == 1)
                            imprimirGH(g,h);
            }

            setIteracionFactible(getIteracionFactible() == -1 && this.isSolucionFactible() ? getGeneracionActual() : getIteracionFactible());
            generaciones = getGeneracionActual() + 1;
          
        }
        if (getD() == 1)
        {         
            Poblacion.imprimirVector(poblacion[this.elegirMejorIndividuo(poblacion)]);
            imprimirLinea(" f(x) = "+ (EvolucionDiferencial.isFormatoReporte()?"\t":"") + TruncarDecimal.truncate(this.aptitud(poblacion[this.elegirMejorIndividuo(poblacion)],g, h)));

            if (this.dGH == 1)
                imprimirGH(g,h);
        }

        try{
            this.factibles[corrida-1] = this.isSolucionFactible();
            this.genFactibles[corrida-1] = this.isSolucionFactible()?this.getIteracionFactible() + 1:-1;
            for (int i = 0;  i < poblacion[this.elegirMejorIndividuo(poblacion)].length; i++)
                mejorVector[i] = poblacion[this.elegirMejorIndividuo(poblacion)][i];

            mejorVector[mejorVector.length-1] =  TruncarDecimal.truncate(this.aptitud(poblacion[this.elegirMejorIndividuo(poblacion)],g,h));
        }catch(Exception e){

        }
        //Poblacion.imprimirPoblacion(poblacion);
        imprimirLinea("\nSe encontró solución factible  = " + this.isSolucionFactible());
        imprimirLinea("Generación de primer solución factible  = " + (this.isSolucionFactible()?this.getIteracionFactible() + 1:-1));
        imprimirLinea("Número de violaciones = " + this.nViolaciones);
        imprimirLinea("Número de evaluaciones = " + (this.getMAXGEN() * this.getPoblacionTamano()));
        imprimirLinea("Número de generaciones = " + getGeneracionActual());
        imprimirLinea("************************************************************");
        setFinish(true);
    }


    public void imprimirGH(double g[], double h[]){
        for (int indexTmp = 0; indexTmp < this.getnG(); indexTmp++)
                imprimirLinea("G" + indexTmp + " = " + g[indexTmp]);

            for (int indexTmp = 0; indexTmp < this.getnH(); indexTmp++)
                imprimirLinea("H" + indexTmp + " = " + h[indexTmp]);
    }

    public float[] getMejorIndividuo(){
        return poblacion[this.elegirMejorIndividuo(poblacion)];

    }
    public void imprimirDatos(double[] g, double[] h){
            Poblacion.imprimirVector(poblacion[this.elegirMejorIndividuo(poblacion)]);
            //imprimirLinea(" f(x) = " + TruncarDecimal.truncate(this.aptitud(poblacion[this.elegirMejorIndividuo(poblacion)],g, h)));
            imprimirLinea(" f(x) = "+ (EvolucionDiferencial.isFormatoReporte()?"\t":"") + TruncarDecimal.truncate(this.aptitud(poblacion[this.elegirMejorIndividuo(poblacion)],g, h)));
            fxActual = TruncarDecimal.truncate(this.aptitud(poblacion[this.elegirMejorIndividuo(poblacion)],g, h));
    }

    public void generarPoblacion(){
        int i = 0;        
        while (i < getPoblacionTamano()){

            
            for (int j = 0; j < poblacion[0].length; j++){

                 boolean valida = false;
                 while(!valida){
                    
                    //poblacion[i][j] = TruncarDecimal.truncate(Random.rndreal(this.restricciones[j][0, u));
                     if (!isVariablesUnicas())
                         poblacion[i][j] = TruncarDecimal.truncate(random.rndreal(this.getRestricciones()[j][0], this.getRestricciones()[j][1]));
                     else
                         poblacion[i][j] = random.rnd((int)this.getRestricciones()[j][0], (int)this.getRestricciones()[j][1]);

                    valida = this.validarUnicas(poblacion[i]);
                //poblacion[i][j] = Float.valueOf(Random.rnd((int)l, (int) u));
                }
            }
            i++;
        }   
    }

    public void setting(){
        for (int i = 0; i < getnVariables(); i++){
            getRestricciones()[i][0] = getL();
            getRestricciones()[i][1] = getU();
        }
    }
     public void seleccionWithOperadores(){
        //Clonar poblacion
        //  float[][] poblacion = Poblacion.clone(this.poblacion);
        triales = new float[poblacion.length][poblacion[0].length];
        float[][] ruidos = new float[poblacion.length][poblacion[0].length];

        
        for (int i = 0 ; i < poblacion.length; i++){

            //validateVariablesOfVector(triales[i]);
            //this.validateVariablesOfVector(vector);
//            this.validarUnicas(triales);
            boolean valida = false;
            
                
                //poblacion, por cada target
                HashMap<Integer,Integer> numerosSeleccionados = new HashMap<Integer,Integer>();
                //Seleccionar los 3 aleatorios distintos
                while (numerosSeleccionados.size() < 3){
                    int indexTmp = random.rnd(0, poblacion.length - 1);
                    //Ciclar hasta que encuentre uno que no este repetido
                    while (numerosSeleccionados.containsValue(indexTmp))
                        indexTmp = random.rnd(0, poblacion.length - 1);
                    numerosSeleccionados.put(numerosSeleccionados.size() + 1, indexTmp);
                }

                //Calcular diferencias entre los dos primeros vectores
                //Mutando

                //while(!valida){
                for (int indexR = 0; indexR < ruidos[0].length;indexR++){
                    float rnd = random.rndreal(0, 1);
                    //int jRnd = random.rnd(0, poblacion.length - 1);
                    int jRnd = random.rnd(0, poblacion[0].length - 1);
                    float diff = 0;

                    float x2Tmp = Math.abs(poblacion[((int)numerosSeleccionados.get(2))][indexR]);
                    float x3Tmp = Math.abs(poblacion[((int)numerosSeleccionados.get(3))][indexR]);
                    diff = x2Tmp - x3Tmp;

                    ruidos[i][indexR] = (poblacion[((int)numerosSeleccionados.get(1))][indexR]) + (this.getF() * diff);
                   // if (rnd  < getCR() || i == jRnd){
                    if (rnd  < getCR() || indexR == jRnd){
                        //Aplicar Cruza
                        if (!isVariablesUnicas())
                            triales[i][indexR] = TruncarDecimal.truncate(ruidos[i][indexR]);
                        else{
                            int res = (int)(ruidos[i][indexR]);
                            if (res >= this.l && res <= this.u)
                                triales[i][indexR] = res;
                            else
                                triales[i][indexR] = poblacion[i][indexR];
                        }
                    }
                    else
                        triales[i][indexR] = poblacion[i][indexR];


                }
                valida = this.validarUnicas(triales[i]);
            //}
        }     
    }

     private boolean validarUnicas(float x[]){
         boolean result = true;
         if (variablesUnicas == true){
            HashMap<Integer,Integer> hm = new HashMap<Integer,Integer>();
            for (int i = 0; i < x.length; i++){
                 int xTmp = (int) x[i];
                 if (xTmp != 0){
                    if (!hm.containsKey(xTmp))
                        hm.put(xTmp, xTmp);
                     else
                     {
                         result = false;
                         break;
                     }
                }
             }
         }
         return result;
     }

     public void reemplazo(){
         //Reemplazo
        for (int i = 0 ; i < poblacion.length; i++){
            double[] g = new double[getnG()];
            double[] h = new double[getnH()];

            double[] gTrial = new double[getnG()];
            double[] hTrial = new double[getnH()];

            float fTrial = aptitud(triales[i],gTrial,hTrial);
            float fPadre = aptitud(poblacion[i],g,h);

            boolean violadoTrial = false;
            boolean violadoPadre = false;
            //Calcular para las H
            float suma[] = new float[1];

            //Calcular para las G
            float sumaVTrial = 0;
            float sumaVPadre = 0;

            //Para el trial
            if (this.sumaVG(gTrial, this.getnG(), suma)){
                violadoTrial = true;
                sumaVTrial += suma[0];
                this.nViolaciones++;
            }

            //Para el padre
            if (this.sumaVG(g, this.getnG(), suma)){
                violadoPadre = true;
                sumaVPadre += suma[0];
                this.nViolaciones++;
            }
            //H
            //Trial
            if (this.sumaVH(hTrial, this.getnH(), suma)){
                violadoTrial = true;
                sumaVTrial += suma[0];
                this.nViolaciones++;
            }                                 
            //Padre
            if (this.sumaVH(h, this.getnH(), suma)){
                violadoPadre = true;
                sumaVPadre += suma[0];
                this.nViolaciones++;
            }          
            //Si los dos son factibles
            if (!violadoTrial && !violadoPadre){
                //Si los dos son factibles (ninguno viola restricciones)
                //se elige el que tenga mejor valor en su funcion objetivo
                if (fTrial < fPadre)
                {
                   if (validateVariablesOfVector(triales[i])){
                       if (!isVariablesUnicas()){
                            poblacion[i] = triales[i];
                            setSolucionFactible(true);
                       }else if (this.validarUnicas(triales[i])){
                            poblacion[i] = triales[i];
                            setSolucionFactible(true);
                        }else if (!this.validarUnicas(triales[i])){
                            poblacion[i] = poblacion[i];
                            setSolucionFactible(true);
                        }
                    }
                }
                else
                    poblacion[i] = poblacion[i];
            }else if (!violadoTrial){    
                if (validateVariablesOfVector(triales[i])){
                    if (!isVariablesUnicas()){
                        poblacion[i] = triales[i];
                        setSolucionFactible(true);
                    }else if (this.validarUnicas(triales[i])){
                        poblacion[i] = triales[i];
                        setSolucionFactible(true);
                    }else if (!this.validarUnicas(triales[i])){
                        poblacion[i] = poblacion[i];
                        setSolucionFactible(true);
                    }
                }

            }else if (!violadoPadre){     
                setSolucionFactible(true);
            }
            else{
                
                //Si Ninguno de los dos es factible entonces se elige el que tenga menor suma de violacion de restricciones
                if (Math.abs(sumaVTrial) < Math.abs(sumaVPadre)){
                    if (validateVariablesOfVector(triales[i])){
                        if (!isVariablesUnicas()){
                            poblacion[i] = triales[i];
                        }else if (this.validarUnicas(triales[i])){
                            poblacion[i] = triales[i];                
                            setSolucionFactible(true);
                        }else if (!this.validarUnicas(triales[i])){
                            poblacion[i] = poblacion[i];     
                            setSolucionFactible(true);
                        }
                    }
                }
                else
                    poblacion[i] = poblacion[i];
            }
        }
     }

     private boolean validateVariablesOfVector(float[] vector){
         boolean result = true;
         for (int i = 0; i < vector.length; i++){
             //if (vector[i] < this.l || vector[i] > u){
             if (vector[i] < this.getRestricciones()[i][0] || vector[i] > this.getRestricciones()[i][1]){
                 result = false;
                 break;
             }
         }
         return result;
     }

     public boolean sumaVH(double[] vector, int n, float suma[]){
        //Calcular para las H, G
        suma[0] = 0;
        boolean violado = false;
        for (int i = 0; i < n; i++)
            if ((Math.abs(vector[i]) - getE() ) > 0){
                suma[0] += Math.abs(vector[i]);
                violado = true;
            }
        
        return violado;
    }

     public boolean sumaVG(double[] vector, int n, float suma[]){
        //Calcular para las H, G
        suma[0] = 0;
        boolean violado = false;
        for (int i = 0; i < n; i++)
            if (vector[i] > 0){
                suma[0] += vector[i];
                violado = true;
            }
        return violado;
    }

    public float aptitud(float[] vector, double[] g, double[] h){       
        double[] x = Poblacion.clone(vector);
        double[] f = new double[getnF()];
        //FcnSuite.g02(x,f,g,h, getnVariables(), getnF(), getnG(), getnH());
        if(funcion.equals("C01"))
            FcnSuite.C01(x,f,g,h, getnVariables(), getnF(), getnG(), getnH());
        else if(funcion.equals("C02"))
            FcnSuite.C02(x,f,g,h, getnVariables(), getnF(), getnG(), getnH());
        else if(funcion.equals("C03"))
            FcnSuite.C03(x,f,g,h, getnVariables(), getnF(), getnG(), getnH());
        else if(funcion.equals("C04"))
            FcnSuite.C04(x,f,g,h, getnVariables(), getnF(), getnG(), getnH());
        else if(funcion.equals("C05"))
            FcnSuite.C05(x,f,g,h, getnVariables(), getnF(), getnG(), getnH());
        else if(funcion.equals("C06"))
            FcnSuite.C06(x,f,g,h, getnVariables(), getnF(), getnG(), getnH());
        else if(funcion.equals("C07"))
            FcnSuite.C07(x,f,g,h, getnVariables(), getnF(), getnG(), getnH());
        else if(funcion.equals("C08"))
            FcnSuite.C08(x,f,g,h, getnVariables(), getnF(), getnG(), getnH());
        else if(funcion.equals("C09"))
            FcnSuite.C09(x,f,g,h, getnVariables(), getnF(), getnG(), getnH());
        else if(funcion.equals("C10"))
            FcnSuite.C10(x,f,g,h, getnVariables(), getnF(), getnG(), getnH());
        else if(funcion.equals("C11"))
            FcnSuite.C11(x,f,g,h, getnVariables(), getnF(), getnG(), getnH());
        else if(funcion.equals("C12"))
            FcnSuite.C12(x,f,g,h, getnVariables(), getnF(), getnG(), getnH());
        else if(funcion.equals("C13"))
            FcnSuite.C13(x,f,g,h, getnVariables(), getnF(), getnG(), getnH());
        else if(funcion.equals("C14"))
            FcnSuite.C14(x,f,g,h, getnVariables(), getnF(), getnG(), getnH());
        else if(funcion.equals("C15"))
            FcnSuite.C15(x,f,g,h, getnVariables(), getnF(), getnG(), getnH());
        else if(funcion.equals("C16"))
            FcnSuite.C16(x,f,g,h, getnVariables(), getnF(), getnG(), getnH());
        else if(funcion.equals("C17"))
            FcnSuite.C17(x,f,g,h, getnVariables(), getnF(), getnG(), getnH());
        else if(funcion.equals("C18"))
            FcnSuite.C18(x,f,g,h, getnVariables(), getnF(), getnG(), getnH());
        else if(funcion.equals("C19")){
            FcnSuite.R01(x,f,g,h, getnVariables(), getnF(), getnG(), getnH());
            EvolucionDiferencial.setVariablesUnicas(true);
        }
        else if(funcion.equals("g01"))
            FcnSuite.g01(x,f,g,h, getnVariables(), getnF(), getnG(), getnH());
        else if(funcion.equals("g02"))
            FcnSuite.g02(x,f,g,h, getnVariables(), getnF(), getnG(), getnH());
        else if(funcion.equals("g03"))
            FcnSuite.g03(x,f,g,h, getnVariables(), getnF(), getnG(), getnH());
        else if(funcion.equals("g04"))
            FcnSuite.g04(x,f,g,h, getnVariables(), getnF(), getnG(), getnH());
        else if(funcion.equals("g05"))
            FcnSuite.g05(x,f,g,h, getnVariables(), getnF(), getnG(), getnH());
        else if(funcion.equals("g06"))
            FcnSuite.g06(x,f,g,h, getnVariables(), getnF(), getnG(), getnH());
        else if(funcion.equals("g07"))
            FcnSuite.g07(x,f,g,h, getnVariables(), getnF(), getnG(), getnH());
        else if(funcion.equals("g08"))
            FcnSuite.g08(x,f,g,h, getnVariables(), getnF(), getnG(), getnH());
        else if(funcion.equals("g09"))
            FcnSuite.g09(x,f,g,h, getnVariables(), getnF(), getnG(), getnH());
        else if(funcion.equals("g10"))
            FcnSuite.g10(x,f,g,h, getnVariables(), getnF(), getnG(), getnH());
        else if(funcion.equals("g11"))
            FcnSuite.g11(x,f,g,h, getnVariables(), getnF(), getnG(), getnH());
        else if(funcion.equals("g12"))
            FcnSuite.g12(x,f,g,h, getnVariables(), getnF(), getnG(), getnH());
        else if(funcion.equals("g13"))
            FcnSuite.g13(x,f,g,h, getnVariables(), getnF(), getnG(), getnH());
        else if(funcion.equals("g14"))
            FcnSuite.g14(x,f,g,h, getnVariables(), getnF(), getnG(), getnH());
        else if(funcion.equals("g15"))
            FcnSuite.g15(x,f,g,h, getnVariables(), getnF(), getnG(), getnH());
        else if(funcion.equals("g16"))
            FcnSuite.g16(x,f,g,h, getnVariables(), getnF(), getnG(), getnH());
        else if(funcion.equals("g17"))
            FcnSuite.g17(x,f,g,h, getnVariables(), getnF(), getnG(), getnH());
        else if(funcion.equals("g18"))
            FcnSuite.g18(x,f,g,h, getnVariables(), getnF(), getnG(), getnH());
        else if(funcion.equals("g19"))
            FcnSuite.g19(x,f,g,h, getnVariables(), getnF(), getnG(), getnH());
        else if(funcion.equals("g20"))
            FcnSuite.g20(x,f,g,h, getnVariables(), getnF(), getnG(), getnH());
        else if(funcion.equals("g21"))
            FcnSuite.g21(x,f,g,h, getnVariables(), getnF(), getnG(), getnH());
        else if(funcion.equals("g22"))
            FcnSuite.g22(x,f,g,h, getnVariables(), getnF(), getnG(), getnH());
        else if(funcion.equals("g23"))
            FcnSuite.g23(x,f,g,h, getnVariables(), getnF(), getnG(), getnH());
        else if(funcion.equals("g24"))
            FcnSuite.g24(x,f,g,h, getnVariables(), getnF(), getnG(), getnH());

        return (float)f[getnF()-1];
    }

    /**
     * @return the poblacionTamano
     */
    public int getPoblacionTamano() {
        return poblacionTamano;
    }

    /**
     * @param poblacionTamano the poblacionTamano to set
     */
    public void setPoblacionTamano(int poblacionTamano) {
        this.poblacionTamano = poblacionTamano;
    }

    private boolean isSolucionFactible(double sumaVG, double sumaVH){
        boolean result = false;
        if (sumaVG == 0 && sumaVH == 0)
            result = true;
        return result;
    }

    public int elegirMejorIndividuo(float[][] vector){
        int index = 0;
        float aptitudes[] = new float[this.getPoblacionTamano()];
        double h[][] = new double[this.getPoblacionTamano()][this.getnH() + 1];
        double g[][] = new double[this.getPoblacionTamano()][this.getnG() + 1];

        boolean factible = false;
        //Para el mejor individuo se toma en cuanta h y g
        for (int i = 0; i < vector.length; i++){
            aptitudes[i] = aptitud(vector[i], g[i], h[i]);
            float[] suma = new float[1];
            this.sumaVG(g[i], this.getnG(), suma);
            g[i][this.getnG()] = suma[0];
            this.sumaVH(h[i], this.getnH(), suma);
            h[i][this.getnH()] = suma[0];
        }

        if (this.isSolucionFactible(g[index][getnG()], h[index][getnH()]))
            factible = true;
        for (int i = 1; i < aptitudes.length; i++){
            if (this.isSolucionFactible(g[i][getnG()], h[i][getnH()]) && this.isSolucionFactible(g[index][getnG()], h[index][getnH()]) )
            {            
                if ((aptitudes[i] < aptitudes[index])){
                    factible = true;
                    index = i;
                }else
                {
                    //Se queda igual
                }             
            }
            else if ((this.isSolucionFactible(g[i][getnG()], h[i][getnH()])) && (!this.isSolucionFactible(g[index][nG], h[index][nH])))
            {
                factible = true;
                index = i;            
            }
            else if ((!this.isSolucionFactible(g[i][nG], h[i][nH])) && (!this.isSolucionFactible(g[index][nG], h[index][nH])))
            //sin ninguno es factible
            {                
                double val = Math.abs(h[i][this.getnH()]);// - e;
                double val1 = Math.abs(h[index][this.getnH()]);// - e;
                if (val < val1)
                    index = i;
            }else            
                factible = true;
        }
        //System.out.println("Factible   " + factible );
        return index;
    }

    /**
     * @return the CR
     */
    public float getCR() {
        return CR;
    }

    /**
     * @param CR the CR to set
     */
    public void setCR(float CR) {
        this.CR = CR;
    }

    /**
     * @return the F
     */
    public float getF() {
        return F;
    }

    /**
     * @param F the F to set
     */
    public void setF(float F) {
        this.F = F;
    }

    /**
     * @return the e
     */
    public float getE() {
        return e;
    }

    /**
     * @param e the e to set
     */
    public void setE(float e) {
        this.e = e;
    }

    /**
     * @return the semilla
     */
    public float getSemilla() {
        return semilla;
    }

    /**
     * @param semilla the semilla to set
     */
    public void setSemilla(float semilla) {
        this.semilla = semilla;
    }

    /**
     * @return the l
     */
    public float getL() {
        return l;
    }

    /**
     * @param l the l to set
     */
    public void setL(float l) {
        this.l = l;
    }

    /**
     * @return the u
     */
    public float getU() {
        return u;
    }

    /**
     * @param u the u to set
     */
    public void setU(float u) {
        this.u = u;
    }

    /**
     * @return the MAXGEN
     */
    public int getMAXGEN() {
        return MAXGEN;
    }

    /**
     * @param MAXGEN the MAXGEN to set
     */
    public void setMAXGEN(int MAXGEN) {
        this.MAXGEN = MAXGEN;
    }

    /**
     * @return the nF
     */
    public int getnF() {
        return nF;
    }

    /**
     * @param nF the nF to set
     */
    public void setnF(int nF) {
        this.nF = nF;
    }

    /**
     * @return the nH
     */
    public int getnH() {
        return nH;
    }

    /**
     * @param nH the nH to set
     */
    public void setnH(int nH) {
        this.nH = nH;
    }

    /**
     * @return the nG
     */
    public int getnG() {
        return nG;
    }

    /**
     * @param nG the nG to set
     */
    public void setnG(int nG) {
        this.nG = nG;
    }

    /**
     * @return the nVariables
     */
    public int getnVariables() {
        return nVariables;
    }

    /**
     * @param nVariables the nVariables to set
     */
    public void setnVariables(int nVariables) {
        this.nVariables = nVariables;
    }

    /**
     * @return the restricciones
     */
    public float[][] getRestricciones() {
        return restricciones;
    }

    /**
     * @param restricciones the restricciones to set
     */
    public void setRestricciones(float[][] restricciones) {
        this.restricciones = restricciones;
    }

    /**
     * @return the d
     */
    public int getD() {
        return d;
    }

    /**
     * @param d the d to set
     */
    public void setD(int d) {
        this.d = d;
    }

    /**
     * @return the funcion
     */
    public String getFuncion() {
        return funcion;
    }

    /**
     * @param funcion the funcion to set
     */
    public void setFuncion(String funcion) {
        this.funcion = funcion;
    }

    /**
     * @return the dF
     */
    public int getdF() {
        return dF;
    }

    /**
     * @param dF the dF to set
     */
    public void setdF(int dF) {
        this.dF = dF;
    }

    /**
     * @return the dS
     */
    public int getdS() {
        return dS;
    }

    /**
     * @param dS the dS to set
     */
    public void setdS(int dS) {
        this.dS = dS;
    }

    /**
     * @return the dHG
     */
    public int getdGH() {
        return dGH;
    }

    /**
     * @param dHG the dHG to set
     */
    public void setdGH(int dGH) {
        this.dGH = dGH;
    }

    /**
     * @return the bitacora
     */
    public String getBitacora() {
        return bitacora;
    }

    /**
     * @param bitacora the bitacora to set
     */
    public void setBitacora(String bitacora) {
        this.bitacora = bitacora;
    }

    /**
     * @return the formatoReporte
     */
    public static boolean isFormatoReporte() {
        return formatoReporte;
    }

    /**
     * @param formatoReporte the formatoReporte to set
     */
    public static void setFormatoReporte(boolean formatoReporte) {
       EvolucionDiferencial.formatoReporte = formatoReporte;
    }

    /**
     * @return the generaciones
     */
    public int getGeneracionActual() {
        return generaciones;
    }

    /**
     * @return the finish
     */
    public boolean isFinish() {
        return finish;
    }

    /**
     * @param finish the finish to set
     */
    public void setFinish(boolean finish) {
        this.finish = finish;
    }

    /**
     * @return the fxActual
     */
    public float getFxActual() {
        return fxActual;
    }

    /**
     * @param fxActual the fxActual to set
     */
    public void setFxActual(float fxActual) {
        this.fxActual = fxActual;
    }

    /**
     * @return the iteracionFactible
     */
    public int getIteracionFactible() {
        return iteracionFactible;
    }

    /**
     * @param iteracionFactible the iteracionFactible to set
     */
    public void setIteracionFactible(int iteracionFactible) {
        this.iteracionFactible = iteracionFactible;
    }

    /**
     * @return the solucionFactible
     */
    public boolean isSolucionFactible() {
        return solucionFactible;
    }

    /**
     * @param solucionFactible the solucionFactible to set
     */
    public void setSolucionFactible(boolean solucionFactible) {
        this.solucionFactible = solucionFactible;
    }

    /**
     * @return the variablesUnicas
     */
    public static boolean isVariablesUnicas() {
        return variablesUnicas;
    }

    /**
     * @param variablesUnicas the variablesUnicas to set
     */
    public static void setVariablesUnicas(boolean variablesUnicas) {
        EvolucionDiferencial.variablesUnicas = variablesUnicas;
    }

    /**
     * @return the mejorVector
     */
    public float[] getMejorVector() {
        return mejorVector;
    }

    /**
     * @param mejorVector the mejorVector to set
     */
    public void setMejorVector(float[] mejorVector) {
        this.mejorVector = mejorVector;
    }

    /**
     * @return the factibles
     */
    public boolean[] getFactibles() {
        return factibles;
    }

    /**
     * @param factibles the factibles to set
     */
    public void setFactibles(boolean[] factibles) {
        this.factibles = factibles;
    }

    /**
     * @return the corrida
     */
    public int getCorrida() {
        return corrida;
    }

    /**
     * @param corrida the corrida to set
     */
    public void setCorrida(int corrida) {
        this.corrida = corrida;
    }

    /**
     * @return the genFactibles
     */
    public int[] getGenFactibles() {
        return genFactibles;
    }

    /**
     * @param genFactibles the genFactibles to set
     */
    public void setGenFactibles(int[] genFactibles) {
        this.genFactibles = genFactibles;
    }
}
