package org.infernokitty.wdf;

public class ParallelAdapter extends BaseWDF {

  private BaseWDF port1;
  private BaseWDF port2;
  private double port1Reflect;

  public ParallelAdapter(BaseWDF port1, BaseWDF port2) {
    this.port1 = port1;
    this.port2 = port2;
    port1.connectToParent(this);
    port2.connectToParent(this);
    calcImpedance();
  }

  @Override
  public void calcImpedance() {
    port1.calcImpedance();
    port2.calcImpedance();
    wdf.G = port1.wdf.G + port2.wdf.G;
    wdf.R = 1.0 / wdf.G;
    port1Reflect = port1.wdf.G / wdf.G;
  }

  @Override
  public void incident(double x) {
    double b1 = port1.wdf.b;
    double b2 = port2.wdf.b;
    double bDiff = b1 - b2;
    port1.incident(x - port1Reflect * bDiff);
    port2.incident(x + (1 - port1Reflect) * bDiff);
    wdf.a = x;
  }

  @Override
  public double reflected() {
    wdf.b =
      port1Reflect * port1.reflected() + (1 - port1Reflect) * port2.reflected();
    return wdf.b;
  }

  public double getPort1Reflect() {
    return port1Reflect;
  }
}
