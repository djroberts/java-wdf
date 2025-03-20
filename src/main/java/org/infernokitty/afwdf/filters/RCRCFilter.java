package org.infernokitty.afwdf.filters;

import org.infernokitty.afwdf.*;

public class RCRCFilter {

  private IdealVoltageSource source;
  private Resistor resistor1;
  private Capacitor capacitor1;
  private Resistor resistor2;
  private Capacitor capacitor2;
  private SeriesAdapter seriesAdapter1;
  private SeriesAdapter seriesAdapter2;
  private ParallelAdapter parallelAdapter;
  private static final double BASE_RESISTANCE = 1000;
  private static final double CAPACITANCE = 1e-6;

  public RCRCFilter(double sampleRate) {
    double validSampleRate = sampleRate > 0 ? sampleRate : 1000;

    this.source = new IdealVoltageSource(0.0);
    this.resistor1 = new Resistor(BASE_RESISTANCE);
    this.capacitor1 = new Capacitor(CAPACITANCE, validSampleRate);
    this.resistor2 = new Resistor(BASE_RESISTANCE);
    this.capacitor2 = new Capacitor(CAPACITANCE, validSampleRate);

    seriesAdapter2 = new SeriesAdapter(resistor2, capacitor2);
    parallelAdapter = new ParallelAdapter(capacitor1, seriesAdapter2);

    seriesAdapter1 = new SeriesAdapter(resistor1, parallelAdapter);
  }

  public double process(double input, double cutoff) {
    cutoff = Math.max(0.0, Math.min(1.0, cutoff));
    double minResistance = 1.0;
    double maxResistance = 8000.0;
    double scaledResistance =
      minResistance + (maxResistance - minResistance) * (1.0 - cutoff);

    resistor1.setResistance(scaledResistance);
    resistor2.setResistance(scaledResistance);
    source.setVoltage(input);

    seriesAdapter1.setIncidentWave(source.getVoltage());
    seriesAdapter1.computeWave(); // Ensure wave propagation happens

    double a = capacitor2.getIncidentWave();
    double b = capacitor2.getReflectedWave();
    double output = (a + b) * 0.5;

    // Debug output to trace wave propagation
    //     System.out.printf(
    //       "Input: %.3f, a_res: %.3f, b_res: %.3f, a_cap: %.3f, b_cap: %.3f, Output: %.3f%n",
    //       input,
    //       resistor.getIncidentWave(),
    //       resistor.getReflectedWave(),
    //       capacitor.getIncidentWave(),
    //       capacitor.getReflectedWave(),
    //       output
    //     );

    return output;
  }
}
