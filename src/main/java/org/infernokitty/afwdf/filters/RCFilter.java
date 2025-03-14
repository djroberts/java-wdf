package org.infernokitty.afwdf.filters;

import org.infernokitty.afwdf.*;

public class RCFilter {

  private IdealVoltageSource source;
  private Resistor resistor;
  private Capacitor capacitor;
  private SeriesAdapter filter;
  private static final double BASE_RESISTANCE = 1000;
  private static final double CAPACITANCE = 1e-6;

  public RCFilter(double sampleRate) {
    double validSampleRate = sampleRate > 0 ? sampleRate : 1000;
    this.source = new IdealVoltageSource(0.0);
    this.resistor = new Resistor(BASE_RESISTANCE);
    this.capacitor = new Capacitor(CAPACITANCE, validSampleRate);
    this.filter = new SeriesAdapter(resistor, capacitor);
  }

  public double process(double input, double cutoff) {
    cutoff = Math.max(0.0, Math.min(1.0, cutoff));
    double minResistance = 1.0;
    double maxResistance = 8000.0;
    double scaledResistance =
      minResistance + (maxResistance - minResistance) * (1.0 - cutoff);

    resistor.setResistance(scaledResistance);
    source.setVoltage(input);
    filter.setIncidentWave(source.getVoltage());

    double a = capacitor.getIncidentWave();
    double b = capacitor.getReflectedWave(); // Fix: was getReflectedWavePublic
    return (a + b) * 0.5;
  }
}
