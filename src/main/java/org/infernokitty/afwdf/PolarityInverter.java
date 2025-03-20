package org.infernokitty.afwdf;

public class PolarityInverter implements WDFComponent {

  private double incidentWave;
  private double reflectedWave;
  private double portResistance;

  public PolarityInverter(double portResistance) {
    this.portResistance = portResistance; // Matches connected component’s resistance
    this.incidentWave = 0.0;
    this.reflectedWave = 0.0;
  }

  @Override
  public void setIncidentWave(double incidentWave) {
    this.incidentWave = incidentWave;
    computeWave();
  }

  @Override
  public double getReflectedWave() {
    return reflectedWave;
  }

  @Override
  public void computeWave() {
    // Inverts the incident wave
    reflectedWave = -incidentWave;
  }

  @Override
  public double getPortResistance() {
    return portResistance;
  }

  @Override
  public double getIncidentWave() {
    return incidentWave;
  }
}
