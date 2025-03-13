package org.infernokitty.afwdf;

public class IdealVoltageSource implements WDFComponent {

  private double voltage;
  private double reflectedWave;
  private final double portResistance = 0; // Ideal source

  public IdealVoltageSource(double voltage) {
    this.voltage = voltage;
  }

  @Override
  public void setIncidentWave(double incidentWave) {
    this.reflectedWave = incidentWave; // Captures feedback if used
  }

  @Override
  public double getReflectedWave() {
    return reflectedWave;
  }

  @Override
  public void computeWave() {
    // No computation needed; voltage drives directly
  }

  @Override
  public double getPortResistance() {
    return portResistance; // Always 0
  }

  public void setVoltage(double voltage) {
    this.voltage = voltage;
  }

  public double getVoltage() {
    return voltage; // Drives the filter
  }
}
