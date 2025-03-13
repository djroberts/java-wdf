package org.infernokitty.afwdf;

public interface WDFComponent {
  void setIncidentWave(double incidentWave);
  double getReflectedWave();
  void computeWave();
  double getPortResistance();
  double getIncidentWave(); // Add this
}
