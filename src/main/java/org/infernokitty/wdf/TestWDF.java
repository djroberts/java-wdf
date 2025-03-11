package org.infernokitty.wdf;

public class TestWDF extends BaseWDF {

  @Override
  public void calcImpedance() {
    wdf.R = 10.0; // Set a test resistance
    wdf.G = 1.0 / wdf.R;
  }

  @Override
  public void incident(double x) {
    wdf.a = x;
  }

  @Override
  public double reflected() {
    wdf.b = wdf.a; // Simple reflection for testing
    return wdf.b;
  }
}
