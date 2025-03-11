package org.infernokitty.wdf;

public class IdealVoltageSource extends BaseWDF {

  private double voltage = 0.0;
  private BaseWDF load;

  public IdealVoltageSource(BaseWDF next) {
    this.load = next;
    load.connectToParent(this);
    calcImpedance();
  }

  public void setVoltage(double v) {
    this.voltage = v;
  }

  public double getVoltage() {
    return this.voltage;
  }

  @Override
  public void calcImpedance() {}

  @Override
  public void incident(double x) {
    wdf.a = x;
  }

  @Override
  public double reflected() {
    wdf.b = -wdf.a + 2 * voltage;

    return wdf.b;
  }
}
