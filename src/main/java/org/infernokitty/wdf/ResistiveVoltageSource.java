package org.infernokitty.wdf;

public class ResistiveVoltageSource extends BaseWDF {

  private double R_value;

  public ResistiveVoltageSource(double resistance) {
    R_value = resistance;
    calcImpedance();
  }

  public void setResistance(double newR) {
    if (newR == R_value) return;
    R_value = newR;
    propagateImpedance();
  }

  @Override
  public void calcImpedance() {
    wdf.R = R_value;
    wdf.G = 1.0 / wdf.R;
  }

  public void setVoltage(double voltage) {
    wdf.b = voltage;
  }

  public double getVoltage() { // Added getter
    return wdf.b;
  }

  @Override
  public void incident(double x) {
    wdf.a = x;
  }

  @Override
  public double reflected() {
    return wdf.b;
  }

  @Override
  public void reset() {
    super.reset();
  }
}
