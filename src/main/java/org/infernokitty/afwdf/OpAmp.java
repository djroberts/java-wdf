package org.infernokitty.afwdf;

public class OpAmp implements WDFComponent {

  private double incidentWavePlus;
  private double reflectedWavePlus;
  private double incidentWaveMinus;
  private double reflectedWaveMinus;
  private double outputWave;
  private double previousOutput;
  private double portResistanceIn;
  private double portResistanceOut;

  public OpAmp(double sampleRate) {
    this.portResistanceIn = 1.0e12; // High input impedance
    this.portResistanceOut = 1.0 / (2.0 * sampleRate); // Low output impedance
    this.incidentWavePlus = 0.0;
    this.incidentWaveMinus = 0.0;
    this.outputWave = 0.0;
    this.previousOutput = 0.0;
  }

  public void setIncidentWavePlus(double incidentWave) {
    this.incidentWavePlus = incidentWave;
    computeWave();
  }

  public void setIncidentWaveMinus(double incidentWave) {
    this.incidentWaveMinus = incidentWave;
    computeWave();
  }

  @Override
  public void setIncidentWave(double incidentWave) {
    setIncidentWavePlus(incidentWave);
  }

  @Override
  public double getReflectedWave() {
    return previousOutput;
  }

  @Override
  public void computeWave() {
    double vPlus = (incidentWavePlus + reflectedWavePlus) * 0.5;
    double vMinus = (incidentWaveMinus + reflectedWaveMinus) * 0.5;
    outputWave = vPlus - vMinus; // Unity gain, feedback drives resonance
    outputWave = Math.max(-10.0, Math.min(10.0, outputWave));
    reflectedWavePlus = incidentWavePlus;
    reflectedWaveMinus = incidentWaveMinus;
    previousOutput = outputWave;
  }

  @Override
  public double getPortResistance() {
    return portResistanceOut;
  }

  public double getPortResistanceInPlus() {
    return portResistanceIn;
  }

  public double getPortResistanceInMinus() {
    return portResistanceIn;
  }

  @Override
  public double getIncidentWave() {
    return outputWave;
  }
}
