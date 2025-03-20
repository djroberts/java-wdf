package org.infernokitty.afwdf;

public class SeriesAdapter implements WDFComponent {

  private WDFComponent component1; // Resistor
  private WDFComponent component2; // Capacitor
  private double incidentWave;
  private double reflectedWave;
  private double portResistance;

  public SeriesAdapter(WDFComponent component1, WDFComponent component2) {
    this.component1 = component1;
    this.component2 = component2;
    updatePortResistance();
  }

  private void updatePortResistance() {
    this.portResistance =
      component1.getPortResistance() + component2.getPortResistance();
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
    updatePortResistance();
    double R1 = component1.getPortResistance(); // Resistor
    double R2 = component2.getPortResistance(); // Capacitor
    double totalR = R1 + R2;

    double b2 = component2.getReflectedWave();
    double a2 = incidentWave * (R2 / totalR) + b2 * (R1 / totalR); // Capacitor voltage
    component2.setIncidentWave(a2);
    reflectedWave = component2.getReflectedWave();
    component1.setIncidentWave(incidentWave - reflectedWave);
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
