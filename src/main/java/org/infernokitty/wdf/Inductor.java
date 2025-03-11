package org.infernokitty.wdf;

public class Inductor extends BaseWDF {

  private double L_value;
  private double z;
  private double fs;

  public Inductor(double inductance, double sampleRate) {
    L_value = inductance;
    fs = sampleRate;
    z = 0.0;
    calcImpedance();
  }

  public void prepare(double sampleRate) {
    fs = sampleRate;
    propagateImpedance();
    reset();
  }

  @Override
  public void reset() {
    super.reset();
    z = 0.0;
  }

  public void setInductanceValue(double newL) {
    if (newL == L_value) {
      return;
    }
    L_value = newL;
    propagateImpedance();
  }

  @Override
  public void calcImpedance() {
    wdf.R = 2.0 * L_value * fs;
    wdf.G = 1.0 / wdf.R;
  }

  @Override
  public void incident(double x) {
    wdf.a = x;
    z = wdf.a;
  }

  @Override
  public double reflected() {
    wdf.b = -z;
    return wdf.b;
  }

  public double getZ() {
    return z;
  }
}
