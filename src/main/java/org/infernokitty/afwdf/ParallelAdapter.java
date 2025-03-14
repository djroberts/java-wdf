package org.infernokitty.afwdf;

public class ParallelAdapter implements WDFComponent {

  private WDFComponent component1;
  private WDFComponent component2;
  private double incidentWave;
  private double reflectedWave;
  private double portResistance;

  public ParallelAdapter(WDFComponent component1, WDFComponent component2) {
    this.component1 = component1;
    this.component2 = component2;
    double R1 = component1.getPortResistance();
    double R2 = component2.getPortResistance();
    this.portResistance = (R1 * R2) / (R1 + R2);
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
    double R1 = component1.getPortResistance();
    double R2 = component2.getPortResistance();
    double totalR = R1 + R2;
    double sigma1 = R2 / totalR;
    double sigma2 = R1 / totalR;

    double a_s = incidentWave;
    double b1 = component1.getReflectedWave();
    double b2 = component2.getReflectedWave();
    double a1 = a_s - b2;
    double a2 = a_s - b1;
    component1.setIncidentWave(a1);
    component2.setIncidentWave(a2);
    reflectedWave = sigma1 * b1 + sigma2 * b2;
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
