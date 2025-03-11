package org.infernokitty.wdf;

public class IdealOpen extends BaseWDF {

  @Override
  public void calcImpedance() {
    wdf.R = Double.POSITIVE_INFINITY;
    wdf.G = 0.0;
  }

  @Override
  public void incident(double x) {
    wdf.a = x;
  }

  @Override
  public double reflected() {
    wdf.b = wdf.a;

    return wdf.b;
  }
}
