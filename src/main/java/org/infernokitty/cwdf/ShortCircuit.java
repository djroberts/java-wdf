package org.infernokitty.cwdf;

public class ShortCircuit extends BaseWDF {

  public ShortCircuit() {
    calcImpedance();
  }

  @Override
  public void calcImpedance() {
    wdf.R = 0.0;
    wdf.G = Double.POSITIVE_INFINITY; // Ground: infinite conductance
  }

  @Override
  public void incident(double x) {
    wdf.a = x;
  }

  @Override
  public double reflected() {
    wdf.b = -wdf.a; // Short circuit reflects opposite wave
    return wdf.b;
  }
}
