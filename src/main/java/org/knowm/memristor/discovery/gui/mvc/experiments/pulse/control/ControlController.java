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
package org.knowm.memristor.discovery.gui.mvc.experiments.pulse.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComponent;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.knowm.memristor.discovery.DWFProxy;
import org.knowm.memristor.discovery.gui.mvc.experiments.ExperimentControlModel;
import org.knowm.memristor.discovery.gui.mvc.experiments.ExperimentPreferences.Waveform;

public class ControlController implements PropertyChangeListener {

  private final ControlPanel controlPanel;
  private final ControlModel controlModel;

  /**
   * Constructor
   *
   * @param controlPanel
   * @param controlModel
   * @param dwf
   */
  public ControlController(ControlPanel controlPanel, ControlModel controlModel, DWFProxy dwf) {

    this.controlPanel = controlPanel;
    this.controlModel = controlModel;
    dwf.addListener(this);

    initGUIComponents();
    setUpViewEvents();

    // register the controller as the listener of the controlModel
    controlModel.addListener(this);
  }

  private void initGUIComponents() {

    initGUIComponentsFromModel();
  }

  private void initGUIComponentsFromModel() {

    controlPanel.getWaveformComboBox().setSelectedItem(controlModel.getWaveform());
    controlPanel.getWaveformComboBox().setModel(new DefaultComboBoxModel<>(new Waveform[]{Waveform.QuarterSine, Waveform.SquareSmooth, Waveform.Square, Waveform.Triangle, Waveform.HalfSine}));

    controlPanel.getSeriesTextField().setText("" + controlModel.getSeriesResistance());
    controlPanel.getAmplitudeSlider().setValue((int) (controlModel.getAmplitude() * 100));
    controlPanel.getAmplitudeSlider().setBorder(BorderFactory.createTitledBorder("Amplitude [V] = " + controlModel.getAmplitude()));
    if (controlModel.getPulseWidth() >= 5000) {
      controlPanel.getPulseWidthSlider().setValue((int) (controlModel.getPulseWidth()));
      controlPanel.getPulseWidthSliderNs().setValue(0);
      controlPanel.getPulseWidthSlider().setBorder(BorderFactory.createTitledBorder("Pulse Width [µs] = " + controlModel.getPulseWidth() / 1000));
      controlPanel.getPulseWidthSliderNs().setBorder(BorderFactory.createTitledBorder("Pulse Width [µs]"));
    }
    else {
      controlPanel.getPulseWidthSlider().setValue(0);
      controlPanel.getPulseWidthSliderNs().setValue(controlModel.getPulseWidth());
      controlPanel.getPulseWidthSlider().setBorder(BorderFactory.createTitledBorder("Pulse Width [µs]"));
      controlPanel.getPulseWidthSliderNs().setBorder(BorderFactory.createTitledBorder("Pulse Width [µs] = " + controlModel.getPulseWidth() / 1000));
    }
    controlPanel.getPulseNumberSlider().setBorder(BorderFactory.createTitledBorder("Pulse Number = " + controlModel.getPulseNumber()));
    controlPanel.getPulseNumberSlider().setValue((int) (controlModel.getPulseNumber()));
  }

  /**
   * Here, all the action listeners are attached to the GUI components
   */
  private void setUpViewEvents() {

    controlPanel.getWaveformComboBox().addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {

        controlModel.setWaveform(controlPanel.getWaveformComboBox().getSelectedItem().toString());
      }
    });

    controlPanel.getAmplitudeSlider().addChangeListener(new ChangeListener() {

      @Override
      public void stateChanged(ChangeEvent e) {

        JSlider source = (JSlider) e.getSource();
        if (!(source.getValueIsAdjusting())) {
          controlModel.setAmplitude(source.getValue() / (float) 100);
          controlPanel.getAmplitudeSlider().setBorder(BorderFactory.createTitledBorder("Amplitude [V] = " + controlModel.getAmplitude()));
        }
      }
    });

    controlPanel.getPulseWidthSlider().addChangeListener(new ChangeListener() {

      @Override
      public void stateChanged(ChangeEvent e) {

        JSlider source = (JSlider) e.getSource();
        if (!(source.getValueIsAdjusting())) {
          controlModel.setPulseWidth(source.getValue());
          controlPanel.getPulseWidthSlider().setBorder(BorderFactory.createTitledBorder("Pulse Width [µs] = " + (double) controlModel.getPulseWidth() / 1000));
          controlPanel.getPulseWidthSliderNs().setBorder(BorderFactory.createTitledBorder("Pulse Width [µs]"));
        }
      }
    });

    controlPanel.getPulseWidthSliderNs().addChangeListener(new ChangeListener() {

      @Override
      public void stateChanged(ChangeEvent e) {

        JSlider source = (JSlider) e.getSource();
        if (!(source.getValueIsAdjusting())) {
          controlModel.setPulseWidth(source.getValue());
          controlPanel.getPulseWidthSlider().setBorder(BorderFactory.createTitledBorder("Pulse Width [µs]"));
          controlPanel.getPulseWidthSliderNs().setBorder(BorderFactory.createTitledBorder("Pulse Width [µs] = " + (double) controlModel.getPulseWidth() / 1000));
        }
      }
    });

    controlPanel.getPulseNumberSlider().addChangeListener(new ChangeListener() {

      @Override
      public void stateChanged(ChangeEvent e) {

        JSlider source = (JSlider) e.getSource();
        if (!(source.getValueIsAdjusting())) {
          controlModel.setPulseNumber(source.getValue());
          controlPanel.getPulseNumberSlider().setBorder(BorderFactory.createTitledBorder("Pulse Number = " + controlModel.getPulseNumber()));
        }
      }
    });

    controlPanel.getSeriesTextField().addKeyListener(new KeyAdapter() {

      @Override
      public void keyReleased(KeyEvent e) {

        JTextField textField = (JTextField) e.getSource();
        String text = textField.getText();

        try {
          int newShuntValue = Integer.parseInt(text);
          controlModel.setSeriesResistance(newShuntValue);
        } catch (Exception ex) {
          // parsing error, default back to previous value
          textField.setText(Integer.toString(controlModel.getSeriesResistance()));
        }
      }
    });
    controlPanel.getMemristorVoltageCheckBox().addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent actionEvent) {

        AbstractButton abstractButton = (AbstractButton) actionEvent.getSource();
        boolean selected = abstractButton.getModel().isSelected();
        // System.out.println("selected = " + selected);
        controlModel.setMemristorVoltageDropSelected(selected);
      }
    });

    controlPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("S"), "fire");
    controlPanel.getActionMap().put("fire", new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {

        controlPanel.getStartStopButton().doClick();
      }
    })
    ;
  }

  /**
   * These property change events are triggered in the controlModel in the case where the underlying controlModel is updated. Here, the controller can respond to those events and make sure the corresponding GUI
   * components get updated.
   */
  @Override
  public void propertyChange(PropertyChangeEvent evt) {

    switch (evt.getPropertyName()) {

      case DWFProxy.AD2_STARTUP_CHANGE:

        controlPanel.enableAllChildComponents((Boolean) evt.getNewValue());
        break;

      case ExperimentControlModel.EVENT_PREFERENCES_UPDATE:

        initGUIComponentsFromModel();
        break;

      case ExperimentControlModel.EVENT_WAVEFORM_UPDATE:

        controlModel.updateWaveformChartData();
        controlModel.updateEnergyData();
        controlPanel.updateEnergyGUI(controlModel.getAppliedAmplitude(), controlModel.getAppliedCurrent(), controlModel.getAppliedEnergy());
        break;

      default:
        break;
    }
  }
}