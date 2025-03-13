package org.infernokitty.afwdf;

public class Inductor implements WDFComponent {

  private double inductance;
  private double sampleRate;
  private double portResistance;
  private double incidentWave;
  private double reflectedWave;
  private double previousIncidentWave;

  public Inductor(double inductance, double sampleRate) {
    this.inductance = inductance > 0 ? inductance : 1e-3;
    this.sampleRate = sampleRate > 0 ? sampleRate : 1000;
    double samplingPeriod = 1.0 / this.sampleRate;
    this.portResistance = (2 * inductance) / samplingPeriod;
    this.previousIncidentWave = 0;
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
    reflectedWave = previousIncidentWave;
    previousIncidentWave = incidentWave;
  }

  @Override
  public double getPortResistance() {
    return portResistance;
  }

  @Override
  public double getIncidentWave() { // Implement this
    return incidentWave;
  }

  public double getInductance() {
    return inductance;
  }
}
