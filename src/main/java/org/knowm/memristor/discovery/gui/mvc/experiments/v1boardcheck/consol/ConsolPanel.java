/**
 * Memristor-Discovery is distributed under the GNU General Public License version 3
 * and is also available under alternative licenses negotiated directly
 * with Knowm, Inc.
 *
 * Copyright (c) 2016-2017 Knowm Inc. www.knowm.org
 *
 * This package also includes various components that are not part of
 * Memristor-Discovery itself:
 *
 * * `Multibit`: Copyright 2011 multibit.org, MIT License
 * * `SteelCheckBox`: Copyright 2012 Gerrit, BSD license
 *
 * Knowm, Inc. holds copyright
 * and/or sufficient licenses to all components of the Memristor-Discovery
 * package, and therefore can grant, at its sole discretion, the ability
 * for companies, individuals, or organizations to create proprietary or
 * open source (even if not GPL) modules which may be dynamically linked at
 * runtime with the portions of Memristor-Discovery which fall under our
 * copyright/license umbrella, or are distributed under more flexible
 * licenses than GPL.
 *
 * The 'Knowm' name and logos are trademarks owned by Knowm, Inc.
 *
 * If you have any questions regarding our licensing policy, please
 * contact us at `contact@knowm.org`.
 */
package org.knowm.memristor.discovery.gui.mvc.experiments.v1boardcheck.consol;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.knowm.memristor.discovery.gui.mvc.experiments.ExperimentPlotPanel;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.style.Styler.LegendPosition;
import org.knowm.xchart.style.markers.SeriesMarkers;

public class ConsolPanel extends ExperimentPlotPanel {


  XYChart gChart;
  JTextArea consol;
 
//  JScrollPane scrollpane;
  
  private static final int consolSize=1024;//lines it will keep.
  private ArrayList<String> lines=new ArrayList<>();

  /**
   * Constructor
   */
  public ConsolPanel() {

    setLayout(new BorderLayout());
    setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));

    consol= new JTextArea();
    consol.setLineWrap(true);
    consol.setWrapStyleWord(true);
    consol.setAutoscrolls(true);
    
   // for (int i = 0; i < 100; i++) {
    Date now=new Date();
      lines.add("V1BoardDiagnostic");
      lines.add("Date: "+now.toString());
  //  }
   
    loadLinesToColsol();
    
    consol.setLineWrap(true);
    consol.setWrapStyleWord(true);
   
//    scrollpane = new JScrollPane(consol, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
//    scrollpane.add(consol);
    
//    consol.setColumns(10);
//    consolPanel = new JPanel();
//    consolPanel.add(consol);
   
    // ///////////////////////////////////////////////////////////
    // Check Box ////////////////////////////////////////////
    // ///////////////////////////////////////////////////////////

    JScrollPane scroll = new JScrollPane (consol, 
        JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    
    add(scroll);
  }

  public void loadLinesToColsol() {
    StringBuilder sb=new StringBuilder();
    for (int i = 0; i < lines.size(); i++) {
      sb.append(lines.get(i)+"\n");
    }
    
    consol.setText(sb.toString());
  }

  public void println(String newLine) {
    lines.add(newLine);
    loadLinesToColsol() ;
  }
  
  public ArrayList<String> getLines() {
  
    return lines;
  }

  public void setLines(ArrayList<String> lines) {
  
    this.lines = lines;
  }
  
  
 

}
