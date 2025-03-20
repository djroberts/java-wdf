package org.infernokitty.afwdf.filters;

import org.infernokitty.afwdf.*;

public class SallenKeyFilter {

  private IdealVoltageSource source;
  private Resistor r1, r2, rFeedback;
  private Capacitor c1, c2;
  private OpAmp opAmp;
  private SeriesAdapter series1;
  private ParallelAdapter feedbackJunction;
  private PolarityInverter inverter;
  private final double capVal = 1.0e-8; // Base capacitance

  public SallenKeyFilter(double sampleRate) {
    double effectiveSampleRate = sampleRate * 4; // 4x oversampling
    source = new IdealVoltageSource(0.0);
    r1 = new Resistor(1000.0);
    r2 = new Resistor(1000.0);
    rFeedback = new Resistor(1000.0);
    c1 = new Capacitor(capVal, effectiveSampleRate);
    c2 = new Capacitor(capVal, effectiveSampleRate);
    opAmp = new OpAmp(effectiveSampleRate);
    inverter = new PolarityInverter(rFeedback.getPortResistance());
    series1 = new SeriesAdapter(r1, c1); // R1-C1 series
    feedbackJunction = new ParallelAdapter(c2, rFeedback); // C2 || R_feedback
  }

  public double process(double input, double cutoff, double resonance) {
    cutoff = Math.max(0.0, Math.min(1.0, cutoff));
    resonance = Math.max(0.0, Math.min(1.0, resonance)); // 0–1
    double minResistance = 0.008; // ~20 kHz
    double maxResistance = 8000.0; // ~20 Hz
    double scaledResistance =
      maxResistance - (maxResistance - minResistance) * cutoff;
    r1.setResistance(scaledResistance);
    r2.setResistance(scaledResistance);
    rFeedback.setResistance(scaledResistance / (resonance + 0.01)); // Q = R2 / R_feedback, avoid div by 0

    source.setVoltage(input);
    series1.setIncidentWave(source.getIncidentWave()); // Drive R1-C1
    double v1 = (c1.getIncidentWave() + c1.getReflectedWave()) * 0.5; // Voltage at C1

    r2.setIncidentWave(opAmp.getReflectedWave()); // R2 from op-amp output
    feedbackJunction.setIncidentWave(r2.getReflectedWave()); // C2 || R_feedback
    double v2 = (c2.getIncidentWave() + c2.getReflectedWave()) * 0.5; // Voltage at C2

    inverter.setIncidentWave(v2); // Invert feedback to V-
    opAmp.setIncidentWavePlus(v1);
    opAmp.setIncidentWaveMinus(inverter.getReflectedWave());

    double output = opAmp.getReflectedWave();
    return output;
  }
}
