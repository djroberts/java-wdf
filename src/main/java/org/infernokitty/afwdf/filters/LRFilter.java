package org.infernokitty.afwdf.filters;

import org.infernokitty.afwdf.*;

public class LRFilter {

  private IdealVoltageSource source;
  private Inductor inductor;
  private Resistor resistor;
  private SeriesAdapter filter;
  private static final double BASE_RESISTANCE = 1000;
  private static final double INDUCTANCE = 1e-3;

  public LRFilter(double sampleRate) {
    double validSampleRate = sampleRate > 0 ? sampleRate : 1000;
    this.source = new IdealVoltageSource(0.0);
    this.inductor = new Inductor(INDUCTANCE, validSampleRate);
    this.resistor = new Resistor(BASE_RESISTANCE);
    this.filter = new SeriesAdapter(inductor, resistor);
  }

  public double process(double input, double cutoff) {
    cutoff = Math.max(0.0, Math.min(1.0, cutoff));
    double minResistance = 1.0;
    double maxResistance = 1000.0;
    double scaledResistance =
      minResistance + (maxResistance - minResistance) * (1.0 - cutoff);

    resistor.setResistance(scaledResistance);
    source.setVoltage(input);
    filter.setIncidentWave(source.getVoltage());

    double a = resistor.getIncidentWave();
    double b = resistor.getReflectedWave();
    return (a + b) * 0.5;
  }
}
