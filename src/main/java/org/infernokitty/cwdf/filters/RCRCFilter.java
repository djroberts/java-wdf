package org.infernokitty.cwdf.filters;

import org.infernokitty.cwdf.*;

public class RCRCFilter {

  private IdealVoltageSource vs;
  private Resistor r1, r2;
  private Capacitor c1, c2;
  private SeriesAdapter sa1, sa2;
  private ParallelAdapter pa;
  private double sampleRate;
  private double feedbackBuffer;
  private SeriesAdapter feedbackSeriesAdapter;

  public RCRCFilter(double baseSampleRate, int oversampleFactor) {
    sampleRate = (double) oversampleFactor * baseSampleRate;
    c1 = new Capacitor(0.000001, sampleRate);
    c2 = new Capacitor(0.000001, sampleRate);
    r1 = new Resistor(5000.0);
    r2 = new Resistor(5000.0);
    sa1 = new SeriesAdapter(r1, c1);
    sa2 = new SeriesAdapter(r2, c2);
    pa = new ParallelAdapter(sa1, sa2);
    vs = new IdealVoltageSource(pa);
    feedbackBuffer = 0.0;
  }

  public double process(double input, double cutoff) {
    double resistanceValue = 7958.0 - 7950.0 * cutoff;
    r1.setResistance(resistanceValue);
    r2.setResistance(resistanceValue);

    vs.setVoltage(input);
    vs.incident(0.0);
    pa.incident(vs.reflected());
    pa.reflected();

    double output = WDFUtil.voltage(c2);

    return output;
  }
}
