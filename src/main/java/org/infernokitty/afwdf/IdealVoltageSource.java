package org.infernokitty.afwdf;

public class IdealVoltageSource implements WDFComponent {

  private double voltage;
  private double reflectedWave;
  private final double portResistance = 0;

  public IdealVoltageSource(double voltage) {
    this.voltage = voltage;
  }

  @Override
  public void setIncidentWave(double incidentWave) {
    this.reflectedWave = incidentWave;
  }

  @Override
  public double getReflectedWave() {
    return reflectedWave;
  }

  @Override
  public void computeWave() {}

  @Override
  public double getPortResistance() {
    return portResistance;
  }

  @Override
  public double getIncidentWave() {
    return voltage; // Source outputs voltage as incident wave
  }

  public void setVoltage(double voltage) {
    this.voltage = voltage;
  }

  public double getVoltage() {
    return voltage;
  }
}
