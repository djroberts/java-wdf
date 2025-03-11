package org.infernokitty.wdf;

public class OpenCircuit extends BaseWDF {

  public OpenCircuit() {
    wdf.R = Double.POSITIVE_INFINITY;
    wdf.G = 0.0;
  }

  @Override
  public void calcImpedance() {}

  @Override
  public void incident(double x) {
    wdf.a = x;
  }

  @Override
  public double reflected() {
    wdf.b = wdf.a;

    return wdf.b;
  }

  @Override
  public void reset() {
    super.reset();
  }
}
