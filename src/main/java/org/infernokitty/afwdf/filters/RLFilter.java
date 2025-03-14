package org.infernokitty.afwdf.filters;

import org.infernokitty.afwdf.*;

public class RLFilter {

  private IdealVoltageSource source;
  private Resistor resistor;
  private Inductor inductor;
  private SeriesAdapter filter;
  private static final double BASE_RESISTANCE = 1000; // 1kΩ
  private static final double INDUCTANCE = 1e-3; // 1mH

  public RLFilter(double sampleRate) {
    double validSampleRate = sampleRate > 0 ? sampleRate : 1000;
    this.source = new IdealVoltageSource(0.0);
    this.resistor = new Resistor(BASE_RESISTANCE);
    this.inductor = new Inductor(INDUCTANCE, validSampleRate);
    this.filter = new SeriesAdapter(resistor, inductor);
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

    double a = inductor.getIncidentWave();
    double b = inductor.getReflectedWave();
    return (a + b) * 0.5;
  }
}
