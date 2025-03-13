package org.infernokitty.wdf;

public class Inductor extends BaseWDF {

  private double L_value;
  private double z; // State variable (previous incident wave)
  private double fs;

  public Inductor(double inductance, double sampleRate) {
    L_value = inductance;
    fs = sampleRate;
    z = 0.0; // Initialize state to 0
    calcImpedance();
  }

  public void prepare(double sampleRate) {
    fs = sampleRate;
    propagateImpedance();
    reset();
  }

  @Override
  public void reset() {
    super.reset();
    z = 0.0; // Reset state
  }

  public void setInductanceValue(double newL) {
    if (newL == L_value) {
      return;
    }
    L_value = newL;
    propagateImpedance();
  }

  @Override
  public void calcImpedance() {
    wdf.R = 2.0 * L_value * fs; // WDF inductor impedance
    wdf.G = 1.0 / wdf.R;
  }

  @Override
  public void incident(double x) {
    wdf.a = x; // Store incident wave
    // z retains previous state until reflected() is called
  }

  @Override
  public double reflected() {
    wdf.b = -z; // Reflect previous state
    z = wdf.a; // Update state with current incident wave for next sample
    return wdf.b;
  }

  public double getZ() {
    return z;
  }
}
