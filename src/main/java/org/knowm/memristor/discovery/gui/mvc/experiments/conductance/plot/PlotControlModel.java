/**
 * Memristor-Discovery is distributed under the GNU General Public License version 3
 * and is also available under alternative licenses negotiated directly
 * with Knowm, Inc.
 *
 * Copyright (c) 2016-2018 Knowm Inc. www.knowm.org
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
package org.knowm.memristor.discovery.gui.mvc.experiments.conductance.plot;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import org.knowm.memristor.discovery.gui.mvc.experiments.ExperimentControlModel;
import org.knowm.memristor.discovery.gui.mvc.experiments.ExperimentPreferences;
import org.knowm.memristor.discovery.gui.mvc.experiments.conductance.ConductancePreferences;

public class PlotControlModel extends ExperimentControlModel {

  /**
   * Min Max params
   */
  Double yMaxIV = null;
  Double yMinIV = null;

  Double yMaxGV = null;
  Double yMinGV = null;

  List<Number> gvData = new ArrayList<>();

  /**
   * Here is where the Controller registers itself as a listener to model changes.
   *
   * @param listener
   */
  public void addListener(PropertyChangeListener listener) {

    swingPropertyChangeSupport.addPropertyChangeListener(listener);
  }

  public Double getyMaxIV() {

    return yMaxIV;
  }

  public void setyMaxIV(Double yMaxIV) {

    this.yMaxIV = yMaxIV;
  }

  public Double getyMinIV() {

    return yMinIV;
  }

  public void setyMinIV(Double yMinIV) {

    this.yMinIV = yMinIV;
  }

  public Double getyMaxGV() {

    return yMaxGV;
  }

  public void setyMaxGV(Double yMaxGV) {

    this.yMaxGV = yMaxGV;
  }

  public Double getyMinGV() {

    return yMinGV;
  }

  public void setyMinGV(Double yMinGV) {

    this.yMinGV = yMinGV;
  }

  public List<Number> getGvData() {

    return gvData;
  }

  @Override
  public ExperimentPreferences initAppPreferences() {

    return new ConductancePreferences();
  }

  @Override
  public void loadModelFromPrefs() {

  }
}
