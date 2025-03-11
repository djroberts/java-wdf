package org.infernokitty.wdf;

public class SeriesAdapter extends BaseWDF {

  private BaseWDF port1;
  private BaseWDF port2;
  private double port1Reflect = 1.0;

  public SeriesAdapter(BaseWDF p1, BaseWDF p2) {
    port1 = p1;
    port2 = p2;
    port1.connectToParent(this);
    port2.connectToParent(this);
    calcImpedance();
  }

  @Override
  public void calcImpedance() {
    wdf.R = port1.wdf.R + port2.wdf.R;
    wdf.G = 1.0 / wdf.R;
    port1Reflect = port1.wdf.R / wdf.R;
  }

  @Override
  public void incident(double x) {
    double b1 = port1.wdf.b - port1Reflect * (x + port1.wdf.b + port2.wdf.b);
    port1.incident(b1);
    port2.incident(-(x + b1));
    wdf.a = x;
  }

  @Override
  public double reflected() {
    wdf.b = -(port1.reflected() + port2.reflected());

    return wdf.b;
  }

  public double getPort1Reflect() {
    return port1Reflect;
  }
}
