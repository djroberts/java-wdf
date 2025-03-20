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
    filter.computeWave(); // Ensure wave propagation happens

    double a = capacitor.getIncidentWave();
    double b = capacitor.getReflectedWave();
    double output = (a + b) * 0.5;

    // Debug output to trace wave propagation
    System.out.printf(
      "Input: %.3f, a_res: %.3f, b_res: %.3f, a_cap: %.3f, b_cap: %.3f, Output: %.3f%n",
      input,
      resistor.getIncidentWave(),
      resistor.getReflectedWave(),
      capacitor.getIncidentWave(),
      capacitor.getReflectedWave(),
      output
    );

    return output;
  }
}
