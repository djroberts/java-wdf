package org.infernokitty.cwdf;

public class Resistor extends BaseWDF {

  private double R;

  public Resistor(double resistance) {
    R = resistance;
    calcImpedance();
  }

  public void setResistance(double resistance) {
    R = resistance;
    calcImpedance();
    propagateImpedance();
  }

  public double getResistance() {
    return R;
  }

  @Override
  public void calcImpedance() {
    wdf.R = R;
    wdf.G = 1.0 / wdf.R;
  }

  @Override
  public void incident(double x) {
    wdf.a = x;
  }

  @Override
  public double reflected() {
    wdf.b = 0.0;

    return wdf.b;
  }
}
