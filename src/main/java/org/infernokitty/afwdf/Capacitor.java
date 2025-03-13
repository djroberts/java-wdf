package org.infernokitty.afwdf;

public class Capacitor implements WDFComponent {

  private double capacitance;
  private double sampleRate;
  private double portResistance;
  private double incidentWave;
  private double reflectedWave;
  private double previousIncidentWave;

  public Capacitor(double capacitance, double sampleRate) {
    this.capacitance = capacitance > 0 ? capacitance : 1e-6;
    this.sampleRate = sampleRate > 0 ? sampleRate : 1000;
    double samplingPeriod = 1.0 / this.sampleRate;
    this.portResistance = samplingPeriod / (2 * capacitance);
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
    reflectedWave = previousIncidentWave; // WDF: b(n) = a(n-1)
    previousIncidentWave = incidentWave;
  }

  @Override
  public double getPortResistance() {
    return portResistance;
  }

  public double getIncidentWave() {
    return incidentWave;
  }

  public double getReflectedWavePublic() {
    return reflectedWave;
  }
}
