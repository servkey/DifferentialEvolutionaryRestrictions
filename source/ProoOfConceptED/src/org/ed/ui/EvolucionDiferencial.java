/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * EvolucionDiferencial.java
 *
 * Created on 17/08/2010, 01:00:27 AM
 */

package org.ed.ui;

import java.awt.Color;
import java.awt.Font;
import java.lang.Float;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import org.ed.util.Estadisticas;
import org.ed.util.FunctionsSetting;
import org.ed.util.Poblacion;
import prooofconcepted.Main;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

/**
 *
 * @author servkey
 */
public class EvolucionDiferencial extends javax.swing.JFrame {
    private IniciarEvolucion evolucionThread;
    private String funcion;
    private int poblacion;
    private int tamano;
    private int genIndexFactible;
    private boolean[] factibles;
    private int[] genFactibles;
    private boolean isFactible = false;
    private int corrida;
    private float cr;
    private float f;
    private float semilla;
    private int MAXGEN;
    private int nF;
    private int nG;
    private int nH;
    private int nV;
    private String restricc;
    private DefaultCategoryDataset  dataset;
    private DefaultCategoryDataset  dataset2;
    private JFreeChart jfc;
    private JFreeChart jfc2;
    private ArrayList<String> semillas;
    private ArrayList<String> listaFx;
    private float[][] listaMejoresIndividuosPorCadaCorrida;
    private int numF = 1;
    private boolean iniciado = false;

    private int numeroCorridasFactibles = 0;
    private int totalCorridas = 1;
    
    public javax.swing.JTextArea getTxtResultados(){
        return this.txtResultados;
    }

    
    /** Creates new form EvolucionDiferencial */
    public EvolucionDiferencial() {
        initComponents();
        
        dataset = new DefaultCategoryDataset();
        dataset2 = new DefaultCategoryDataset();
        //crearGrafico();
        initGrafica1();
        initGrafica2();
        iniciado = true;
//        this.txtSemillas.setText(".1,.123,.134,.23,.223, .32, .551,0.436,0.542,.55,.589,.632,0.6,.651,.662f,.672f,0.689, .723,.754, 0.788, 0.794, .8, .82.84,.833,.861,.873,.891,0.918,.978,.98");
        this.parseSemillas();
    }

    private void initGrafica1(){
          //dataset.clear();
           /*for (int i = 0; i < listaFxG.size(); i++){
                float xy = listaFxG.get(i);
                dataset.addValue(xy,"", String.valueOf(i+1));
            }*/
            //pnlGrafica.removeAll();
            TextTitle source = new TextTitle("Gráfico de convergencia");
            //extTitle gen = new TextTitle("Generación");

             jfc = ChartFactory.createLineChart("", "Generación", "f(x)", dataset, PlotOrientation.VERTICAL, false, false, false);
             source.setFont(new Font("SansSerif", Font.PLAIN, 12));
             //gen.setFont(new Font("SansSerif", Font.PLAIN, 10));
             jfc.addSubtitle(source);

             //jfc.a.addSubtitle(source);

            //source.setPosition(RectangleEdge.BOTTOM);
            CategoryPlot plot = (CategoryPlot) jfc.getPlot();
            plot.setBackgroundPaint(Color.WHITE);
            plot.setDomainGridlinePaint(Color.gray);
            plot.setRangeGridlinePaint (Color.gray);
            ///plot.setRangeMinorGridlinesVisible(true);
           // plot.setDomainGridlinesVisible(true);
           // plot.setRangeGridlinesVisible(true);
            //.setDomainGridlinesVisible(true);

            //plot.setRangeGridlinesVisible(true);
           // jfc.setBackgroundPaint(Color.white);
            ChartPanel cp = new ChartPanel(jfc);
            cp.setSize(410, 235);
            //pnlGrafica.removeAll();
            pnlGrafica.add(cp);
        }

      private void initGrafica2(){

            TextTitle source = new TextTitle("Gráfico de convergencia en soluciones factibles");
            //extTitle gen = new TextTitle("Generación");

             jfc2 = ChartFactory.createLineChart("", "Generación", "f(x)", dataset2, PlotOrientation.VERTICAL, false, false, false);
             source.setFont(new Font("SansSerif", Font.PLAIN, 12));
             //gen.setFont(new Font("SansSerif", Font.PLAIN, 10));
             jfc2.addSubtitle(source);

             //jfc.a.addSubtitle(source);

            //source.setPosition(RectangleEdge.BOTTOM);
            CategoryPlot plot = (CategoryPlot) jfc2.getPlot();
            plot.setBackgroundPaint(Color.WHITE);
            plot.setDomainGridlinePaint(Color.gray);
            plot.setRangeGridlinePaint (Color.gray);
            ///plot.setRangeMinorGridlinesVisible(true);
           // plot.setDomainGridlinesVisible(true);
           // plot.setRangeGridlinesVisible(true);
            //.setDomainGridlinesVisible(true);

            //plot.setRangeGridlinesVisible(true);
           // jfc.setBackgroundPaint(Color.white);
            ChartPanel cp = new ChartPanel(jfc2);
            cp.setSize(410, 235);
            //pnlGrafica.removeAll();
            this.pnlGrafico2.add(cp);
        }
    
    public void crearGrafico(){

        /*dataset.addValue(numDouble,title, "1");
        dataset.addValue(++numDouble,title, "2");
        dataset.addValue(++numDouble,title, "3");
        dataset.addValue(++numDouble,title, "4");
        dataset.addValue(++numDouble,title, "5");
        dataset.addValue(++numDouble,title, "6");

*/
         TextTitle source = new TextTitle("Gráfico de convergencia");

	 jfc = ChartFactory.createLineChart("", "Generación", "f(x)", dataset, PlotOrientation.VERTICAL, false, false, false);
         source.setFont(new Font("SansSerif", Font.PLAIN, 12));
         jfc.addSubtitle(source);



        //source.setPosition(RectangleEdge.BOTTOM);
        CategoryPlot plot = (CategoryPlot) jfc.getPlot();
        
        plot.setBackgroundPaint(Color.WHITE);
        plot.setDomainGridlinePaint(Color.gray);
        plot.setRangeGridlinePaint (Color.gray);
        plot.setDomainGridlinesVisible(true);

        //plot.setRangeGridlinesVisible(true);
       // jfc.setBackgroundPaint(Color.white);
        ChartPanel cp = new ChartPanel(jfc);
        cp.setSize(410, 235);
        this.pnlGrafica.add(cp);
       // this.add(cp);
    }
  
  

   
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        jLabel2 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txtPoblacion = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtCR = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtF = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtSemilla = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtGen = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        lblNH = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        lblNG = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtRango = new javax.swing.JTextArea();
        txtnVariables = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jList2 = new javax.swing.JList();
        jPanel5 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        txtPoblacion1 = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txtCR1 = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        txtF1 = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        txtSemilla1 = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        txtGen1 = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        lblNH1 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        lblNG1 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        txtRango1 = new javax.swing.JTextArea();
        txtnVariables1 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        txtMejoresPorCorrida = new javax.swing.JTextArea();
        txtTodas = new javax.swing.JTextField();
        txtFP = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        txtSemillaMediana = new javax.swing.JTextField();
        jLabel33 = new javax.swing.JLabel();
        txtCorridaObtenida = new javax.swing.JTextField();
        jPanel8 = new javax.swing.JPanel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        txtMedia = new javax.swing.JTextField();
        txtMediana = new javax.swing.JTextField();
        txtDesviacionEstandar = new javax.swing.JTextField();
        txtMejor = new javax.swing.JTextField();
        txtPeor = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        txtNumCorridas = new javax.swing.JTextField();
        txtSemillas = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        chkCorridas = new javax.swing.JCheckBox();
        jLabel26 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        txtResultados = new javax.swing.JTextArea();
        jLabel13 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        chkFormatoReporte = new javax.swing.JCheckBox();
        jLabel23 = new javax.swing.JLabel();
        chkBestCorrida1 = new javax.swing.JCheckBox();
        lblGeneracion = new javax.swing.JLabel();
        chkMejorPorGeneracion = new javax.swing.JCheckBox();
        chkRestricciones = new javax.swing.JCheckBox();
        prgBar = new javax.swing.JProgressBar();
        lblCorrida = new javax.swing.JLabel();
        prgBarCorrida = new javax.swing.JProgressBar();
        chkFactibles = new javax.swing.JCheckBox();
        jTabbedPane3 = new javax.swing.JTabbedPane();
        pnlGrafica = new javax.swing.JPanel();
        pnlGrafico2 = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        lblNumFactibles = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(null);

        jList1.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jList1ValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(jList1);

        jLabel2.setText("Parametros configurables");

        jLabel4.setText("Poblacion:");

        jLabel5.setText("CR:");

        jLabel6.setText("F:");

        jLabel7.setText("Semilla:");

        jLabel8.setText("Max. Gen.:");

        jLabel9.setText("Núm. H:");

        jLabel10.setText("Núm. G:");

        jLabel11.setText("Núm. Variables:");

        jLabel12.setText("Rango");

        txtRango.setColumns(20);
        txtRango.setEditable(false);
        txtRango.setRows(5);
        jScrollPane3.setViewportView(txtRango);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6)
                    .addComponent(jLabel5)
                    .addComponent(jLabel4)
                    .addComponent(jLabel11)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblNG, javax.swing.GroupLayout.DEFAULT_SIZE, 59, Short.MAX_VALUE)
                    .addComponent(lblNH, javax.swing.GroupLayout.DEFAULT_SIZE, 59, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(txtF, javax.swing.GroupLayout.Alignment.LEADING, 0, 0, Short.MAX_VALUE)
                        .addComponent(txtCR, javax.swing.GroupLayout.Alignment.LEADING, 0, 0, Short.MAX_VALUE)
                        .addComponent(txtPoblacion, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE))
                    .addComponent(txtnVariables, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addComponent(jLabel12))
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jScrollPane3, 0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addGap(14, 14, 14)
                                    .addComponent(jLabel7))
                                .addComponent(jLabel8))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtSemilla)
                                .addComponent(txtGen, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(29, 29, 29))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtSemilla, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(txtGen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 66, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtPoblacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(txtCR, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txtF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblNH, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblNG, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(txtnVariables, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(74, 74, 74)
                        .addComponent(jLabel2)
                        .addGap(24, 24, 24))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPane1.addTab("G", jPanel2);

        jList2.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jList2ValueChanged(evt);
            }
        });
        jScrollPane2.setViewportView(jList2);

        jLabel14.setText("Poblacion:");

        jLabel15.setText("CR:");

        jLabel16.setText("F:");

        jLabel17.setText("Semilla:");

        jLabel18.setText("Max. Gen.:");

        jLabel19.setText("Núm. H:");

        jLabel20.setText("Núm. G:");

        jLabel21.setText("Núm. Variables:");

        jLabel22.setText("Rango");

        txtRango1.setColumns(20);
        txtRango1.setEditable(false);
        txtRango1.setRows(5);
        jScrollPane5.setViewportView(txtRango1);

        txtnVariables1.setText("10");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel16)
                    .addComponent(jLabel15)
                    .addComponent(jLabel14)
                    .addComponent(jLabel21)
                    .addComponent(jLabel19)
                    .addComponent(jLabel20))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblNG1, javax.swing.GroupLayout.DEFAULT_SIZE, 59, Short.MAX_VALUE)
                    .addComponent(lblNH1, javax.swing.GroupLayout.DEFAULT_SIZE, 59, Short.MAX_VALUE)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(txtF1, javax.swing.GroupLayout.Alignment.LEADING, 0, 0, Short.MAX_VALUE)
                        .addComponent(txtCR1, javax.swing.GroupLayout.Alignment.LEADING, 0, 0, Short.MAX_VALUE)
                        .addComponent(txtPoblacion1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE))
                    .addComponent(txtnVariables1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addComponent(jLabel22))
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jScrollPane5, 0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel5Layout.createSequentialGroup()
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel5Layout.createSequentialGroup()
                                    .addGap(14, 14, 14)
                                    .addComponent(jLabel17))
                                .addComponent(jLabel18))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtSemilla1)
                                .addComponent(txtGen1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(29, 29, 29))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtSemilla1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel17))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel18)
                            .addComponent(txtGen1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel22)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 66, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14)
                            .addComponent(txtPoblacion1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel15)
                            .addComponent(txtCR1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel16)
                            .addComponent(txtF1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblNH1, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel19))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblNG1, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel20))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel21)
                            .addComponent(txtnVariables1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );

        jLabel3.setText("Parametros configurables");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(64, 64, 64)
                        .addComponent(jLabel3)
                        .addGap(24, 24, 24))
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPane1.addTab("C", jPanel1);

        jLabel29.setText("Mejor valor f(x) =");

        jLabel30.setText("FP=");

        txtMejoresPorCorrida.setColumns(20);
        txtMejoresPorCorrida.setRows(5);
        jScrollPane6.setViewportView(txtMejoresPorCorrida);

        jLabel31.setText("Mejores valores por cada corrida");

        jLabel32.setText("Semilla de la mediana = ");

        jLabel33.setText("obtenido en la corrida = ");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel7Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 372, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel7Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(jLabel29)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTodas, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel33)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCorridaObtenida, javax.swing.GroupLayout.DEFAULT_SIZE, 69, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel7Layout.createSequentialGroup()
                        .addGap(143, 143, 143)
                        .addComponent(jLabel32)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtSemillaMediana, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel7Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel31))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(238, 238, 238)
                        .addComponent(jLabel30)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtFP, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel29)
                    .addComponent(txtTodas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel33)
                    .addComponent(txtCorridaObtenida, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtFP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel30))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel32)
                    .addComponent(txtSemillaMediana, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel31)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Resultados", jPanel7);

        jLabel34.setText("Media:");

        jLabel35.setText("Mediana:");

        jLabel36.setText("Desviación Estándar:");

        jLabel37.setText("Mejor:");

        jLabel38.setText("Peor:");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel38)
                    .addComponent(jLabel37)
                    .addComponent(jLabel36)
                    .addComponent(jLabel35)
                    .addComponent(jLabel34))
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtPeor)
                    .addComponent(txtMejor)
                    .addComponent(txtDesviacionEstandar)
                    .addComponent(txtMediana, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtMedia, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(87, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel34)
                    .addComponent(txtMedia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel35)
                    .addComponent(txtMediana, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel36)
                    .addComponent(txtDesviacionEstandar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel37)
                    .addComponent(txtMejor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel38)
                    .addComponent(txtPeor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Estadísticas", jPanel8);

        jLabel24.setText("N. Corridas a realizar:");

        txtNumCorridas.setText("1");
        txtNumCorridas.setEnabled(false);

        txtSemillas.setText("0.3");
        txtSemillas.setEnabled(false);
        txtSemillas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSemillasKeyPressed(evt);
            }
        });

        jLabel25.setText("Semillas:");

        chkCorridas.setText("Realizar varias corridas");
        chkCorridas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkCorridasActionPerformed(evt);
            }
        });
        chkCorridas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                chkCorridasKeyPressed(evt);
            }
        });

        jLabel26.setText("Ejemplo: 0.1,0.2,0.3");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel24)
                    .addComponent(jLabel25))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(chkCorridas)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(txtNumCorridas)
                        .addComponent(txtSemillas, javax.swing.GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE)
                        .addComponent(jLabel26)))
                .addContainerGap(59, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(42, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(txtNumCorridas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSemillas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25))
                .addGap(4, 4, 4)
                .addComponent(jLabel26)
                .addGap(18, 18, 18)
                .addComponent(chkCorridas)
                .addGap(35, 35, 35))
        );

        jTabbedPane1.addTab("Configuración", jPanel6);

        getContentPane().add(jTabbedPane1);
        jTabbedPane1.setBounds(10, 60, 397, 230);
        jTabbedPane1.getAccessibleContext().setAccessibleName("C");

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12));
        jLabel1.setText("Resultados");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(380, 390, 170, 30);

        txtResultados.setBackground(new java.awt.Color(0, 0, 0));
        txtResultados.setColumns(20);
        txtResultados.setEditable(false);
        txtResultados.setFont(new java.awt.Font("Courier New", 0, 11));
        txtResultados.setForeground(new java.awt.Color(51, 255, 0));
        txtResultados.setRows(5);
        jScrollPane4.setViewportView(txtResultados);

        getContentPane().add(jScrollPane4);
        jScrollPane4.setBounds(10, 420, 840, 180);

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 18));
        jLabel13.setText("Evolución diferencial");
        getContentPane().add(jLabel13);
        jLabel13.setBounds(10, 10, 170, 30);

        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jButton1.setText("Iniciar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton3.setText("Detener");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton2.setText("Limpiar resultados");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addComponent(jButton2)
                .addGap(36, 36, 36)
                .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(38, 38, 38))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel4);
        jPanel4.setBounds(10, 290, 390, 30);

        chkFormatoReporte.setFont(new java.awt.Font("Tahoma", 0, 10));
        chkFormatoReporte.setText("Formato Reporte");
        getContentPane().add(chkFormatoReporte);
        chkFormatoReporte.setBounds(10, 390, 210, 21);

        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel23.setText("Generación:");
        getContentPane().add(jLabel23);
        jLabel23.setBounds(550, 350, 80, 14);

        chkBestCorrida1.setFont(new java.awt.Font("Tahoma", 0, 10));
        chkBestCorrida1.setSelected(true);
        chkBestCorrida1.setText("Mostrar la mejor de toda la corrida");
        getContentPane().add(chkBestCorrida1);
        chkBestCorrida1.setBounds(10, 330, 210, 20);

        lblGeneracion.setFont(new java.awt.Font("Tahoma", 1, 12));
        lblGeneracion.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblGeneracion.setText("0");
        getContentPane().add(lblGeneracion);
        lblGeneracion.setBounds(750, 350, 90, 14);

        chkMejorPorGeneracion.setFont(new java.awt.Font("Tahoma", 0, 10));
        chkMejorPorGeneracion.setText("Mostrar la mejor por cada generación");
        getContentPane().add(chkMejorPorGeneracion);
        chkMejorPorGeneracion.setBounds(10, 350, 210, 21);

        chkRestricciones.setFont(new java.awt.Font("Tahoma", 0, 10));
        chkRestricciones.setText("Mostrar restricciones");
        getContentPane().add(chkRestricciones);
        chkRestricciones.setBounds(10, 370, 210, 21);
        getContentPane().add(prgBar);
        prgBar.setBounds(640, 350, 100, 14);

        lblCorrida.setFont(new java.awt.Font("Tahoma", 1, 12));
        lblCorrida.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblCorrida.setText("0");
        getContentPane().add(lblCorrida);
        lblCorrida.setBounds(750, 370, 90, 14);
        getContentPane().add(prgBarCorrida);
        prgBarCorrida.setBounds(640, 370, 100, 14);

        chkFactibles.setFont(new java.awt.Font("Tahoma", 0, 10));
        chkFactibles.setText("Mostrar sólo las factibles");
        getContentPane().add(chkFactibles);
        chkFactibles.setBounds(220, 330, 200, 20);

        javax.swing.GroupLayout pnlGraficaLayout = new javax.swing.GroupLayout(pnlGrafica);
        pnlGrafica.setLayout(pnlGraficaLayout);
        pnlGraficaLayout.setHorizontalGroup(
            pnlGraficaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 415, Short.MAX_VALUE)
        );
        pnlGraficaLayout.setVerticalGroup(
            pnlGraficaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 232, Short.MAX_VALUE)
        );

        jTabbedPane3.addTab("Gráfica 1", pnlGrafica);

        javax.swing.GroupLayout pnlGrafico2Layout = new javax.swing.GroupLayout(pnlGrafico2);
        pnlGrafico2.setLayout(pnlGrafico2Layout);
        pnlGrafico2Layout.setHorizontalGroup(
            pnlGrafico2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 415, Short.MAX_VALUE)
        );
        pnlGrafico2Layout.setVerticalGroup(
            pnlGrafico2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 232, Short.MAX_VALUE)
        );

        jTabbedPane3.addTab("Gráfico 2", pnlGrafico2);

        getContentPane().add(jTabbedPane3);
        jTabbedPane3.setBounds(420, 60, 420, 260);

        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel27.setText("Corridas factibles:");
        getContentPane().add(jLabel27);
        jLabel27.setBounds(510, 390, 120, 14);

        jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel28.setText("Corridas:");
        getContentPane().add(jLabel28);
        jLabel28.setBounds(570, 370, 60, 14);

        lblNumFactibles.setFont(new java.awt.Font("Tahoma", 1, 12));
        lblNumFactibles.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblNumFactibles.setText("0");
        getContentPane().add(lblNumFactibles);
        lblNumFactibles.setBounds(640, 390, 90, 14);

        jMenu1.setText("Opciones");

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.SHIFT_MASK));
        jMenuItem1.setText("Salir");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Ayuda");

        jMenuItem2.setText("Acerca de...");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem2);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jList1ValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jList1ValueChanged
        // TODO add your handling code here:
       // System.out.println(this.jList1.getSelectedValue().toString());
        String funcion[] = FunctionsSetting.getFunction(this.jList1.getSelectedValue().toString());
        this.funcion = funcion[0];
        this.poblacion = Integer.valueOf(funcion[1]);
        this.cr = Float.valueOf(funcion[2]);
        this.f = Float.valueOf(funcion[3]);
        this.semilla = Float.valueOf(funcion[4]);
        this.MAXGEN = Integer.valueOf(funcion[5]);
        this.nF = Integer.valueOf(funcion[6]);
        this.nH = Integer.valueOf(funcion[7]);
        this.nG = Integer.valueOf(funcion[8]);
        this.nV = Integer.valueOf(funcion[9]);
        restricc = funcion[10];

        this.txtCR.setText(funcion[2]);
        this.txtF.setText(funcion[3]);
        this.txtGen.setText(funcion[5]);
        this.txtPoblacion.setText(funcion[1]);
        this.txtRango.setText(funcion[10]);
        this.txtSemilla.setText(funcion[4]);
        this.lblNG.setText(funcion[8]);
        this.lblNH.setText(funcion[7]);
        this.txtnVariables.setText(funcion[9]);


    }//GEN-LAST:event_jList1ValueChanged

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try{
            if (iniciado){
             if (this.jTabbedPane1.getSelectedIndex() == 0){
                    //this.funcion = funcion[0];
                    this.poblacion = Integer.valueOf(this.txtPoblacion.getText());
                    this.cr = Float.valueOf(txtCR.getText());
                    this.f = Float.valueOf(this.txtF.getText());
                    this.semilla = Float.valueOf(this.txtSemilla.getText());
                    this.MAXGEN = Integer.valueOf(this.txtGen.getText());
                    this.nV = Integer.valueOf(txtnVariables.getText());
               }else if(this.jTabbedPane1.getSelectedIndex() == 1)
               {
                    //this.funcion = funcion[0];
                    this.poblacion = Integer.valueOf(this.txtPoblacion1.getText());
                    this.cr = Float.valueOf(txtCR1.getText());
                    this.f = Float.valueOf(this.txtF1.getText());
                    this.semilla = Float.valueOf(this.txtSemilla1.getText());
                    this.MAXGEN = Integer.valueOf(this.txtGen1.getText());
                    this.nV = Integer.valueOf(txtnVariables1.getText());
                 }else
                {

                }
                    //this.nV = Integer.valueOf(funcion[9]);

            /*this.txtRango.setText(funcion[10]);
            this.txtSemilla.setText(funcion[4]);
            this.lblNG.setText(funcion[8]);
            this.lblNH.setText(funcion[7]);
            this.lblNV.setText(funcion[9]);-*/
            /**this.funcion = funcion[0];
            this.poblacion = Integer.valueOf(funcion[1]);
            this.cr = Float.valueOf(funcion[2]);
            this.f = Float.valueOf(funcion[3]);
            this.semilla = Float.valueOf(funcion[4]);
            this.MAXGEN = Integer.valueOf(funcion[5]);
            this.nF = Integer.valueOf(funcion[6]);
            this.nH = Integer.valueOf(funcion[7]);
            this.nG = Integer.valueOf(funcion[8]);
            this.nV = Integer.valueOf(funcion[9]);
            restricc = funcion[10];
            */
             if (this.jTabbedPane1.getSelectedIndex() == 0 || this.jTabbedPane1.getSelectedIndex() == 1){
                numeroCorridasFactibles = 0;
                this.corrida = 0;
                isFactible = false;
                dataset.clear();
                dataset2.clear();
                txtMejoresPorCorrida.setText("");
                this.lblCorrida.setText("0");
             //iniciarCorrida();
                new IniciarCorridas(this).start();
             }else
                 JOptionPane.showMessageDialog(this, "Seleccione la pestaña de la función a resolver.");
          }
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, "Seleccione una función.");
        }
    }//GEN-LAST:event_jButton1ActionPerformed


class IniciarCorridas extends Thread{
        private EvolucionDiferencial frame;

        public IniciarCorridas(EvolucionDiferencial frame){
            this.frame = frame;
        }

        public void run(){
            int numCorrida = frame.chkCorridas.isSelected()?corrida:1;


            if (chkCorridas.isSelected()){
                parseSemillas();
                numCorrida = Integer.valueOf(txtNumCorridas.getText());
            }

            listaMejoresIndividuosPorCadaCorrida = new float[numCorrida][nV+1];
            factibles = new boolean[numCorrida];
            genFactibles = new int[numCorrida];
            txtMejoresPorCorrida.append("********************************************************************************************\n");
            txtMejoresPorCorrida.append("\tValores de todas las corridas \n");
            txtMejoresPorCorrida.append("\tNúmero de corridas: " + numCorrida + "\n");
            txtMejoresPorCorrida.append("********************************************************************************************\n");
            while(corrida < numCorrida){
                jButton1.setEnabled(false);
                dataset.clear();
                dataset2.clear();
        //this.semilla = Float.valueOf(this.txtSemilla.getText());
        //this.semilla = Float.valueOf(this.txtSemilla.getText());
                if (chkCorridas.isSelected()){
                    try{
                    if (Float.valueOf(semillas.get(corrida)) != 0)
                        semilla = Float.valueOf(semillas.get(corrida));
                    }catch(Exception e){

                    }
                }
                txtResultados.append("###################################################\n");
                txtResultados.append("Corrida número: " + (++corrida) + "\n");
                txtResultados.append("Fecha: " + new Date() + "\n");
                evolucionThread = new IniciarEvolucion(frame,funcion, poblacion, cr, f, semilla, MAXGEN, nF, nG, nH, nV, restricc);
                //BarraProgreso bp = new BarraProgreso(evolucionThread);
                lblGeneracion.setText("0");
                prgBar.setValue(0);
                prgBarCorrida.setValue(0);
                evolucionThread.start();
                isFactible = false;

                while(!evolucionThread.getEd().isFinish());


            }

            //Terminaron las corridas
            //Ordenas resultados
            for (int indexTmp0 = 0 ; indexTmp0 < listaMejoresIndividuosPorCadaCorrida.length; indexTmp0++){
                    txtMejoresPorCorrida.append((indexTmp0 + 1) + "\t" + semillas.get(indexTmp0) +"\t");
                    for (int columna = 0; columna < listaMejoresIndividuosPorCadaCorrida[0].length; columna++)
                        txtMejoresPorCorrida.append(listaMejoresIndividuosPorCadaCorrida[indexTmp0][columna]+ "\t");

                    txtMejoresPorCorrida.append("\t" + factibles[indexTmp0]);
                    txtMejoresPorCorrida.append("\t" + genFactibles[indexTmp0]);
                    txtMejoresPorCorrida.append("\n");
            }

        /*    System.out.println("Poblacion sin ordenar");
            for (int indexTmp = 0 ; indexTmp < listaMejoresIndividuosPorCadaCorrida.length; indexTmp++){
                Poblacion.imprimirVector(listaMejoresIndividuosPorCadaCorrida[indexTmp]);
                 System.out.println("\n");
            }*/

            //}
            //Ingresarlo en los resoltados de las corridas
            /*txtMejoresPorCorrida.append(corrida + "\t" + semillas.get(corrida - 1) +"\t");
            for (int columna = 0; columna < listaMejoresIndividuosPorCadaCorrida[0].length; columna++)
                txtMejoresPorCorrida.append(listaMejoresIndividuosPorCadaCorrida[corrida-1][columna]+ "\t");

            txtMejoresPorCorrida.append("\n");
            */

           for (int indexTmp01 = 0 ; indexTmp01 < listaMejoresIndividuosPorCadaCorrida.length; indexTmp01++)
                listaMejoresIndividuosPorCadaCorrida[indexTmp01][0] = indexTmp01 + 1;

           
           //int i = listaMejoresIndividuosPorCadaCorrida.length - 1;
           
           //int t = listaMejoresIndividuosPorCadaCorrida.length - 1;
           for (int i = 0;i < listaMejoresIndividuosPorCadaCorrida.length; i++)
           {

               for (int j = 0; j < listaMejoresIndividuosPorCadaCorrida.length - 1; j++){
                   if (listaMejoresIndividuosPorCadaCorrida[j][listaMejoresIndividuosPorCadaCorrida[0].length - 1] > listaMejoresIndividuosPorCadaCorrida[j + 1][listaMejoresIndividuosPorCadaCorrida[0].length - 1] ){
                    //swap(List[j], List[j+1]);
                    //intercambio
                     float tmpMayor = listaMejoresIndividuosPorCadaCorrida[j][listaMejoresIndividuosPorCadaCorrida[0].length - 1];
                     float tmpMenor = listaMejoresIndividuosPorCadaCorrida[j + 1][listaMejoresIndividuosPorCadaCorrida[0].length - 1];

                     float corridaTmp = listaMejoresIndividuosPorCadaCorrida[j][0];
                     float corridaTmp1 = listaMejoresIndividuosPorCadaCorrida[j + 1][0];

                     //Intercambiar x

                     listaMejoresIndividuosPorCadaCorrida[j][0] = corridaTmp1;
                     listaMejoresIndividuosPorCadaCorrida[j + 1][0] = corridaTmp;
                     listaMejoresIndividuosPorCadaCorrida[j][listaMejoresIndividuosPorCadaCorrida[0].length - 1] = tmpMenor;
                     listaMejoresIndividuosPorCadaCorrida[j + 1][listaMejoresIndividuosPorCadaCorrida[0].length - 1] = tmpMayor;
                  }
               }
            }




           txtTodas.setText(String.valueOf(listaMejoresIndividuosPorCadaCorrida[0][listaMejoresIndividuosPorCadaCorrida[0].length - 1]));
           txtCorridaObtenida.setText(String.valueOf((int)(listaMejoresIndividuosPorCadaCorrida[0][0])));
           int mediana = numCorrida/2;
           float mitad = numCorrida / 2.0f;

           if (mitad == 0)
               txtMediana.setText(String.valueOf(listaMejoresIndividuosPorCadaCorrida[mediana-1][listaMejoresIndividuosPorCadaCorrida[0].length - 1]));
           else
           {
            try{
                float m1 = listaMejoresIndividuosPorCadaCorrida[mediana-1][listaMejoresIndividuosPorCadaCorrida[0].length - 1];

               float m2 = listaMejoresIndividuosPorCadaCorrida[mediana-2][listaMejoresIndividuosPorCadaCorrida[0].length - 1];
               float p = (m2 + m1)/2;
               txtMediana.setText(String.valueOf(p));
               }catch(Exception e){}

           }
           try{
           float sum = 0;
           float[] x = new float[listaMejoresIndividuosPorCadaCorrida.length];
           for (int indexM = 0; indexM <  listaMejoresIndividuosPorCadaCorrida.length; indexM++){
               sum += listaMejoresIndividuosPorCadaCorrida[indexM][listaMejoresIndividuosPorCadaCorrida[0].length - 1];
               x[indexM] = listaMejoresIndividuosPorCadaCorrida[indexM][listaMejoresIndividuosPorCadaCorrida[0].length - 1];
           }
           float media = sum/listaMejoresIndividuosPorCadaCorrida.length;
           txtMedia.setText(String.valueOf(media));
           txtMejor.setText(String.valueOf(listaMejoresIndividuosPorCadaCorrida[0][listaMejoresIndividuosPorCadaCorrida[0].length - 1]));
           txtPeor.setText(String.valueOf(listaMejoresIndividuosPorCadaCorrida[listaMejoresIndividuosPorCadaCorrida.length-1][listaMejoresIndividuosPorCadaCorrida[0].length - 1]));
           
           txtDesviacionEstandar.setText(String.valueOf(Estadisticas.desviacionEstandar(media, x)));
            }catch(Exception e){}
           try{
           float semilla = Float.valueOf(semillas.get(((int)(listaMejoresIndividuosPorCadaCorrida[mediana-1][0])) - 1));

           float p = numeroCorridasFactibles /(numCorrida * 1.0f);
           txtFP.setText(String.valueOf(p));
           txtSemillaMediana.setText(String.valueOf(semilla));

            }catch(Exception e){}
      //         System.out.println("Poblacion ordenada");
            //for (int indexTmp00 = 0 ; indexTmp00 < listaMejoresIndividuosPorCadaCorrida.length; indexTmp00++){
//                Poblacion.imprimirVector(listaMejoresIndividuosPorCadaCorrida[indexTmp00]);
  //              System.out.println("");

    //        }
         //  System.exit(0);
           //float fx = listaMejoresIndividuosPorCadaCorrida[0][listaMejoresIndividuosPorCadaCorrida.length - 1];
    }

  }

    
    private void jList2ValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jList2ValueChanged
        //System.out.println(this.jList2.getSelectedValue().toString());
        String funcion[] = FunctionsSetting.getFunction(this.jList2.getSelectedValue().toString());
        this.funcion = funcion[0];
        this.poblacion = Integer.valueOf(funcion[1]);
        this.cr = Float.valueOf(funcion[2]);
        this.f = Float.valueOf(funcion[3]);
        this.semilla = Float.valueOf(funcion[4]);
        this.MAXGEN = Integer.valueOf(funcion[5]);
        this.nF = Integer.valueOf(funcion[6]);
        this.nH = Integer.valueOf(funcion[7]);
        this.nG = Integer.valueOf(funcion[8]);
        this.nV = Integer.valueOf(funcion[9]);
        restricc = funcion[10];

        this.txtCR1.setText(funcion[2]);
        this.txtF1.setText(funcion[3]);
        this.txtGen1.setText(funcion[5]);
        this.txtPoblacion1.setText(funcion[1]);
        this.txtRango1.setText(funcion[10]);
        this.txtSemilla1.setText(funcion[4]);
        this.lblNG1.setText(funcion[8]);
        this.lblNH1.setText(funcion[7]);
        this.txtnVariables1.setText(funcion[9]);
    }//GEN-LAST:event_jList2ValueChanged

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        this.txtResultados.setText("");
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void chkCorridasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkCorridasActionPerformed
        // TODO add your handling code here:
        if (this.chkCorridas.isSelected())
            this.txtSemillas.setEnabled(true);
        else
            this.txtSemillas.setEnabled(false);

    }//GEN-LAST:event_chkCorridasActionPerformed

    private void chkCorridasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_chkCorridasKeyPressed
        // TODO add your handling code here:
       

    }//GEN-LAST:event_chkCorridasKeyPressed

    private void txtSemillasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSemillasKeyPressed
        // TODO add your handling code here:
        parseSemillas();
    }//GEN-LAST:event_txtSemillasKeyPressed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
            JOptionPane.showMessageDialog(this, "EDkv1. \nAlgoritmo que emplea la estrategia de búsqueda de Evolución Diferencial con restricciones.\nFecha: Agosto 2010.\nAutor: Luis G. Montané Jiménez. \nEmail: luis.montane@hotmail.com");        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
            System.exit(0);

    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void parseSemillas(){
           try{
            String semillasStr = this.txtSemillas.getText();
            StringTokenizer str = new StringTokenizer(semillasStr,",");
            int numSemillas = 0;
            semillas = new ArrayList<String>();
            while (str.hasMoreElements()){
                semillas.add(str.nextElement().toString());
                numSemillas++;
            }
            txtNumCorridas.setText(String.valueOf(numSemillas));
            if (this.chkCorridas.isSelected())
                this.totalCorridas = Integer.valueOf(txtNumCorridas.getText());
            else
                totalCorridas = 1;
        }catch(Exception e){
        }
    }

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                EvolucionDiferencial fED = new EvolucionDiferencial();
                DefaultListModel model = new DefaultListModel();
                DefaultListModel modelC = new DefaultListModel();
                for (int i = 0; i < FunctionsSetting.getMATRIXFUNCTIONS().length;i++)
                {
                   if (FunctionsSetting.getMATRIXFUNCTIONS()[i][0].toUpperCase().toCharArray()[0] == 'C')
                        modelC.addElement(FunctionsSetting.getMATRIXFUNCTIONS()[i][0]);
                   if (FunctionsSetting.getMATRIXFUNCTIONS()[i][0].toUpperCase().toCharArray()[0] == 'G')
                       model.addElement(FunctionsSetting.getMATRIXFUNCTIONS()[i][0]);
                }
                /*for(int i=0; i < lista.size(); i++){
                    model.addElement(lista.get(i));
                }*/
                fED.jList1.setModel(model);fED.jList2.setModel(modelC);
                fED.setSize(870,650);
                fED.setTitle("Evolución Diferencial - Restricciones");
                fED.setVisible(true);
               // fED.getTxtResultados().setVisible(false);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox chkBestCorrida1;
    private javax.swing.JCheckBox chkCorridas;
    private javax.swing.JCheckBox chkFactibles;
    private javax.swing.JCheckBox chkFormatoReporte;
    private javax.swing.JCheckBox chkMejorPorGeneracion;
    private javax.swing.JCheckBox chkRestricciones;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JList jList1;
    private javax.swing.JList jList2;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane3;
    private javax.swing.JLabel lblCorrida;
    private javax.swing.JLabel lblGeneracion;
    private javax.swing.JLabel lblNG;
    private javax.swing.JLabel lblNG1;
    private javax.swing.JLabel lblNH;
    private javax.swing.JLabel lblNH1;
    private javax.swing.JLabel lblNumFactibles;
    private javax.swing.JPanel pnlGrafica;
    private javax.swing.JPanel pnlGrafico2;
    private javax.swing.JProgressBar prgBar;
    private javax.swing.JProgressBar prgBarCorrida;
    private javax.swing.JTextField txtCR;
    private javax.swing.JTextField txtCR1;
    private javax.swing.JTextField txtCorridaObtenida;
    private javax.swing.JTextField txtDesviacionEstandar;
    private javax.swing.JTextField txtF;
    private javax.swing.JTextField txtF1;
    private javax.swing.JTextField txtFP;
    private javax.swing.JTextField txtGen;
    private javax.swing.JTextField txtGen1;
    private javax.swing.JTextField txtMedia;
    private javax.swing.JTextField txtMediana;
    private javax.swing.JTextField txtMejor;
    private javax.swing.JTextArea txtMejoresPorCorrida;
    private javax.swing.JTextField txtNumCorridas;
    private javax.swing.JTextField txtPeor;
    private javax.swing.JTextField txtPoblacion;
    private javax.swing.JTextField txtPoblacion1;
    private javax.swing.JTextArea txtRango;
    private javax.swing.JTextArea txtRango1;
    private javax.swing.JTextArea txtResultados;
    private javax.swing.JTextField txtSemilla;
    private javax.swing.JTextField txtSemilla1;
    private javax.swing.JTextField txtSemillaMediana;
    private javax.swing.JTextField txtSemillas;
    private javax.swing.JTextField txtTodas;
    private javax.swing.JTextField txtnVariables;
    private javax.swing.JTextField txtnVariables1;
    // End of variables declaration//GEN-END:variables

    /**
     * @return the chkBestCorrida1
     */
    public javax.swing.JCheckBox getChkBestCorrida1() {
        return chkBestCorrida1;
    }

    /**
     * @param chkBestCorrida1 the chkBestCorrida1 to set
     */
    public void setChkBestCorrida1(javax.swing.JCheckBox chkBestCorrida1) {
        this.chkBestCorrida1 = chkBestCorrida1;
    }

    /**
     * @return the chkMejorPorGeneracion
     */
    public javax.swing.JCheckBox getChkMejorPorGeneracion() {
        return this.chkMejorPorGeneracion;
    }

    /**
     * @param chkMejorPorGeneracion the chkMejorPorGeneracion to set
     */
    public void setChkMejorPorGeneracion(javax.swing.JCheckBox chkMejorPorGeneracion) {
        this.chkMejorPorGeneracion = chkMejorPorGeneracion;
    }

    /**
     * @return the chkRestricciones
     */
    public javax.swing.JCheckBox getChkRestricciones() {
        return chkRestricciones;
    }

    /**
     * @param chkRestricciones the chkRestricciones to set
     */
    public void setChkRestricciones(javax.swing.JCheckBox chkRestricciones) {
        this.chkRestricciones = chkRestricciones;
    }


    class BarraProgreso extends Thread{
        private org.ed.EvolucionDiferencial evolucionDiferencial;

        public BarraProgreso(org.ed.EvolucionDiferencial ed){
            this.evolucionDiferencial = ed;
        }

        
       
        public void run(){
            while(evolucionDiferencial.getGeneracionActual() <= MAXGEN){
               
               if (evolucionDiferencial.isSolucionFactible())   {
                   isFactible = true;
                   genIndexFactible = evolucionDiferencial.getGeneracionActual();
               //if (evolucionDiferencial.i)
               }
               lblGeneracion.setText(String.valueOf(evolucionDiferencial.getGeneracionActual()) + "/" + MAXGEN);
               lblCorrida.setText(String.valueOf(corrida) + "/" + (chkCorridas.isSelected() ?Integer.valueOf(txtNumCorridas.getText()):1));
               //100 -> 10000
               //x -> 10
               int porcentaje = (evolucionDiferencial.getGeneracionActual() * 100) / MAXGEN;
               int porcentaje2 = (corrida * 100) / (chkCorridas.isSelected() ?Integer.valueOf(txtNumCorridas.getText()):1);
               prgBar.setValue(porcentaje);
               prgBarCorrida.setValue(porcentaje2);
               //createGrafico();

               float xy = evolucionDiferencial.getFxActual();
               try{
                    dataset.addValue(xy,"", String.valueOf(evolucionDiferencial.getGeneracionActual()+1));
                }catch(Exception e){}

               if (evolucionDiferencial.isSolucionFactible())
                   try{
                    dataset2.addValue(xy,"", String.valueOf(evolucionDiferencial.getGeneracionActual()+1));
                   }catch(Exception e){}

               if (evolucionDiferencial.getGeneracionActual() == MAXGEN){
                 lblGeneracion.setText(String.valueOf(evolucionDiferencial.getGeneracionActual()) + "/" + MAXGEN);
                 prgBar.setValue(100);
                 prgBarCorrida.setValue(100);
                 break;                
               }
            }
        }
    }


    class IniciarEvolucion extends Thread{
        private org.ed.EvolucionDiferencial ed = new org.ed.EvolucionDiferencial();
        private String funcion;
        private int poblacion;

        private float cr;
        private float f;
        private float semilla;
        private int MAXGEN;
        private int nF;
        private int nG;
        private int nH;
        private int nV;
        private String restricc;
        private EvolucionDiferencial frame;

        public IniciarEvolucion(EvolucionDiferencial frame, String funcion, int poblacion,float cr, float f, float semilla, int MAXGEN, int nF, int nG, int nH, int nV, String restricc){
            this.funcion = funcion;
            this.poblacion = poblacion;
            this.f = f;
            this.cr = cr;
            this.semilla = semilla;
            this.MAXGEN = MAXGEN;
            this.nF = nF;
            this.nG = nG;
            this.nH = nH;
            this.nV = nV;
            this.frame = frame;
            this.restricc = restricc;
        }

        public org.ed.EvolucionDiferencial getEvolucionDiferencial(){
            return this.getEd();
        }

        public void run(){
            //frame.getTxtResultados().setText("");
            getEd().setFuncion(funcion);
            getEd().setPoblacionTamano(poblacion);
            getEd().setCR(cr);
            getEd().setF(f);
            getEd().setSemilla(semilla);
            getEd().setMAXGEN(MAXGEN);
            getEd().setnF(nF);
            getEd().setnH(nH);
            getEd().setnG(nG);
            getEd().setnVariables(nV);
            //Restricciones
            Main.settingRestricciones(restricc, getEd());
            getEd().setAreaTexto(frame.getTxtResultados());

            //-d 1 = impresion de la mejor solucion encontrada
            //-d 0 = impresion de la mejor solucion por cada generación
            //-df 1
            if (frame.chkFormatoReporte.isSelected())
                getEd().setFormatoReporte(true);

            if (frame.getChkRestricciones().isSelected())
                getEd().setdGH(1);
            if (frame.getChkMejorPorGeneracion().isSelected())
                getEd().setD(0);
            else if(frame.getChkBestCorrida1().isSelected())
                getEd().setD(1);
            if (frame.chkFactibles.isSelected())
                getEd().setdF(1);
            new BarraProgreso(getEd()).start();

            getEd().setMejorVector(listaMejoresIndividuosPorCadaCorrida[corrida-1]);
            getEd().setFactibles(factibles);
            getEd().setGenFactibles(genFactibles);
            getEd().setCorrida(corrida);
            getEd().start();


           /* float[] individuo = Poblacion.cloneToFloat(getEd().getMejorIndividuo());
            for (int i = 0;  i < individuo.length; i++)
                listaMejoresIndividuosPorCadaCorrida[corrida-1][i] = individuo[i];

            listaMejoresIndividuosPorCadaCorrida[corrida-1][listaMejoresIndividuosPorCadaCorrida[0].length - 1] = getEd().getFxActual();
*/
            //Ingresarlo en los resoltados de las corridas
            /*txtMejoresPorCorrida.append(corrida + "\t" + semillas.get(corrida - 1) +"\t");
            for (int columna = 0; columna < listaMejoresIndividuosPorCadaCorrida[0].length; columna++)
                txtMejoresPorCorrida.append(listaMejoresIndividuosPorCadaCorrida[corrida-1][columna]+ "\t");
            
            txtMejoresPorCorrida.append("\n");
            */
            if (getEd().isSolucionFactible())
                numeroCorridasFactibles++;
            jButton1.setEnabled(true);
            lblNumFactibles.setText(String.valueOf(numeroCorridasFactibles));
            txtResultados.append("###################################################");
            //.setText(ed.getBitacora());
        }

        /**
         * @return the ed
         */
        public org.ed.EvolucionDiferencial getEd() {
            return ed;
        }

        /**
         * @param ed the ed to set
         */
        public void setEd(org.ed.EvolucionDiferencial ed) {
            this.ed = ed;
        }

    }

}