package org.infernokitty.afwdf.filters;

import org.infernokitty.afwdf.*;

public class SallenKeyFilterNoOpAmp {

  private IdealVoltageSource source;
  private Resistor r1, r2;
  private Capacitor c1, c2;
  private SeriesAdapter series1;
  private ParallelAdapter parallel;

  public SallenKeyFilterNoOpAmp(double sampleRate) {
    source = new IdealVoltageSource(0.0);
    r1 = new Resistor(1000.0);
    r2 = new Resistor(1000.0);
    c1 = new Capacitor(1e-6, sampleRate);
    c2 = new Capacitor(1e-6, sampleRate);
    series1 = new SeriesAdapter(r1, c1);
    parallel = new ParallelAdapter(r2, c2);
  }

  public double process(double input, double cutoff) {
    cutoff = Math.max(0.0, Math.min(1.0, cutoff));
    double minResistance = 0.008;
    double maxResistance = 8000.0;
    double scaledResistance =
      maxResistance - (maxResistance - minResistance) * cutoff;
    r1.setResistance(scaledResistance);
    r2.setResistance(scaledResistance);

    source.setVoltage(input);
    series1.setIncidentWave(source.getVoltage());
    double v1 = (c1.getIncidentWave() + c1.getReflectedWave()) * 0.5;

    parallel.setIncidentWave(v1); // No op-amp, direct from C1
    double output = (c2.getIncidentWave() + c2.getReflectedWave()) * 0.5;

    return output;
  }
}
