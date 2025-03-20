package org.infernokitty.cwdf.filters;

import org.infernokitty.cwdf.*;

public class RCFilter {

  private IdealVoltageSource vs;
  private Resistor r;
  private Capacitor c;
  private SeriesAdapter sa;
  private double sampleRate;

  public RCFilter(double baseSampleRate, int oversampleFactor) {
    sampleRate = (double) oversampleFactor * baseSampleRate;
    c = new Capacitor(0.000001, sampleRate); // 1 µF
    r = new Resistor(7958.0); // Initial 7958 Ω (20 Hz)
    sa = new SeriesAdapter(r, c);
    vs = new IdealVoltageSource(sa);
  }

  public double process(double input, double resistance) {
    r.setResistance(7958.0 - 7950.0 * resistance); // 7958 Ω (20 Hz) to 8 Ω (20 kHz)
    vs.setVoltage(input);
    vs.incident(0.0);
    sa.incident(vs.reflected());
    sa.reflected();
    return WDFUtil.voltage(c);
  }
}
