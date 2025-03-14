package org.infernokitty.afwdf.filters;

import org.infernokitty.afwdf.*;

public class ResonantRCFilter {

  private IdealVoltageSource source;
  private Resistor r1;
  private Capacitor c1;
  private Resistor r2;
  private Capacitor c2;
  private SeriesAdapter s1;
  private SeriesAdapter s2;
  private ParallelAdapter p1;
  private double feedbackGain;
  private static final double BASE_RESISTANCE = 1000;
  private static final double CAPACITANCE = 1e-6;

  public ResonantRCFilter(double sampleRate) {
    double validSampleRate = sampleRate > 0 ? sampleRate : 1000;
    source = new IdealVoltageSource(0.0);
    r1 = new Resistor(BASE_RESISTANCE);
    c1 = new Capacitor(CAPACITANCE, validSampleRate);
    r2 = new Resistor(BASE_RESISTANCE);
    c2 = new Capacitor(CAPACITANCE, validSampleRate);

    s2 = new SeriesAdapter(r2, c2);
    p1 = new ParallelAdapter(c1, s2);
    s1 = new SeriesAdapter(r1, p1);

    this.feedbackGain = 0.5;
  }

  public double process(double input, double cutoff, double resonance) {
    cutoff = Math.max(0.0, Math.min(1.0, cutoff));
    resonance = Math.max(0.0, Math.min(1.0, resonance));
    double minResistance = 1.0;
    double maxResistance = 8000.0;
    double scaledResistance =
      minResistance + (maxResistance - minResistance) * (1.0 - cutoff);

    r1.setResistance(scaledResistance);
    r2.setResistance(scaledResistance);

    source.setVoltage(input);
    s1.setIncidentWave(source.getVoltage());
    s1.getReflectedWave();

    //double v_out = (c2.getIncidentWave() + c2.getReflectedWave()) * 0.5; // V_C2
    //double feedback = v_out * resonance; // Resonance scales Q
    //seriesR1C1.setIncidentWave(source.getVoltage() + feedback);

    double a = c2.getIncidentWave();
    double b = c2.getReflectedWave();
    return (a + b) * 0.5; // Output V_C2
  }
}
