package org.infernokitty.afwdf;

public class Resistor implements WDFComponent {

  private double resistance;
  private double portResistance;
  private double incidentWave;
  private double reflectedWave;

  public Resistor(double resistance) {
    this.resistance = resistance > 0 ? resistance : 1000;
    this.portResistance = resistance;
  }

  public void setResistance(double resistance) {
    this.resistance = resistance > 0 ? resistance : 1000;
    this.portResistance = resistance;
    computeWave();
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
    reflectedWave = 0;
  }

  @Override
  public double getPortResistance() {
    return portResistance;
  }

  @Override
  public double getIncidentWave() {
    return incidentWave;
  }

  public double getResistance() {
    return resistance;
  }
}
