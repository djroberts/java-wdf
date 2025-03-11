package org.infernokitty.filters;

import org.infernokitty.wdf.*;

public class WDFResonantFilter {

  private IdealVoltageSource vs;
  private Resistor r;
  private Capacitor c;
  private Inductor l;
  private SeriesAdapter sa1;
  private SeriesAdapter sa2;
  private double sampleRate;

  public WDFResonantFilter(double baseSampleRate, int oversampleFactor) {
    sampleRate = (double) oversampleFactor * baseSampleRate;
    c = new Capacitor(1e-6, sampleRate); // 1 uF
    l = new Inductor(1e-3, sampleRate); // 1 mH
    r = new Resistor(100.0); // Initial resistance
    sa1 = new SeriesAdapter(r, c);
    sa2 = new SeriesAdapter(sa1, l);
    vs = new IdealVoltageSource(sa2);
    vs.connectToParent(l); // Feedback loop
    // Removed vs.propagateImpedance();
  }

  public double process(double input, double resistance) {
    r.setResistance(resistance); // Adjust resistance directly
    vs.setVoltage(input);
    vs.incident(0.0);
    sa2.incident(vs.reflected());
    double output = sa2.reflected();
    return WDFUtil.voltage(c); // Output voltage across capacitor
  }
}
