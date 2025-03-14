package org.infernokitty.cwdf;

public class PolarityInverter extends BaseWDF {

  private BaseWDF port1;

  public PolarityInverter(BaseWDF port1) {
    this.port1 = port1;
    port1.connectToParent(this);
    calcImpedance();
  }

  @Override
  public void calcImpedance() {
    // port1.calcImpedance();

    // todo: shoud we protect agains R = 0
    wdf.R = port1.wdf.R;
    wdf.G = 1.0 / wdf.R;
  }

  @Override
  public void incident(double x) {
    wdf.a = x;
    port1.incident(-x);
  }

  @Override
  public double reflected() {
    wdf.b = -port1.reflected();

    return wdf.b;
  }
}
