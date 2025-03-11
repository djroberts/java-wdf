package org.infernokitty.wdf;

public class Capacitor extends BaseWDF {

  private double C;
  private double fs;
  private double z;

  public Capacitor(double capacitance, double sampleRate) {
    C = capacitance;
    fs = sampleRate;
    z = 0.0;
    calcImpedance();
  }

  public void setCapacitance(double capacitance) {
    if (C == capacitance) {
      return;
    }

    C = capacitance;
    calcImpedance();
    propagateImpedance();
  }

  @Override
  public void calcImpedance() {
    wdf.R = 1.0 / (2.0 * C * fs);
    wdf.G = 1.0 / wdf.R;
  }

  @Override
  public void incident(double x) {
    wdf.a = x;
    z = wdf.a;
  }

  @Override
  public double reflected() {
    wdf.b = z;

    return wdf.b;
  }

  @Override
  public void reset() {
    super.reset();
    z = 0.0;
  }

  public double getZ() {
    return z;
  }
}
