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

  //   @Override
  //   public void incident(double x) {
  //     System.out.println("Series adapter incident receiving x: " + x);
  //     double b1 = port1.wdf.b;
  //     double b2 = port2.wdf.b;
  //     double a1 = x - b2; // Incident to port1 reflects port2’s state
  //     double a2 = x - b1; // Incident to port2 reflects port1’s state
  //     port1.incident(a1);
  //     port2.incident(a2);
  //     wdf.a = x;
  //   }

  //   @Override
  //   public void incident(double x) {
  //     System.out.println("Series adapter incident receiving x: " + x);
  //
  //     double b1 = port1.wdf.b - port1Reflect * (x + port1.wdf.b + port2.wdf.b);
  //     port1.incident(b1);
  //     port2.incident(-(x + b1));
  //     wdf.a = x;
  //   }

  public void incident(double x) {
    double b1 = port1.wdf.b;
    double b2 = port2.wdf.b;
    double a1 = port1Reflect * (x - b2) + b2;
    double a2 = (1 - port1Reflect) * (x - b1) + b1;
    port1.incident(a1);
    port2.incident(a2);
    wdf.a = x;
  }

  @Override
  public double reflected() {
    wdf.b = -(port1.reflected() + port2.reflected());

    System.out.println("Series adapter reflected wfd.b has become: " + wdf.b);

    return wdf.b;
  }

  public double getPort1Reflect() {
    return port1Reflect;
  }
}
