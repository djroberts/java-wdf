package org.infernokitty.afwdf.filters;

import org.infernokitty.afwdf.*;

public class ResonantRCFilter {

  private IdealVoltageSource source;
  private Resistor r;
  private Capacitor[] caps;
  private SeriesAdapter[] stages;
  private PolarityInverter feedbackInverter;
  private double feedbackGain;
  private double previousOutput;
  private static final double BASE_RESISTANCE = 1000.0;
  private static final double CAPACITANCE = 1e-6;

  public ResonantRCFilter(double sampleRate) {
    double validSampleRate = sampleRate > 0 ? sampleRate : 1000.0;
    source = new IdealVoltageSource(0.0);
    r = new Resistor(BASE_RESISTANCE);
    caps = new Capacitor[4];
    stages = new SeriesAdapter[4];

    for (int i = 0; i < 4; i++) {
      caps[i] = new Capacitor(CAPACITANCE / 4.0, validSampleRate * 4); // 4x oversampling
      stages[i] = new SeriesAdapter(r, caps[i]);
    }
    feedbackInverter = new PolarityInverter(caps[3].getPortResistance());

    this.feedbackGain = 5.0; // Boosted for higher resonance
    this.previousOutput = 0.0;
  }

  public double process(double input, double cutoff, double resonance) {
    cutoff = Math.max(0.0, Math.min(1.0, cutoff));
    resonance = Math.max(0.0, Math.min(1.0, resonance));

    double minResistance = 0.008; // ~20 kHz
    double maxResistance = 8000.0; // ~20 Hz
    double scaledResistance =
      maxResistance - (maxResistance - minResistance) * cutoff;
    r.setResistance(scaledResistance);

    // Feedback with softer damping
    double feedbackRaw = previousOutput * feedbackGain * resonance;
    double feedback = feedbackRaw / (1.0 + Math.abs(feedbackRaw) * 0.05); // Lighter limit
    feedbackInverter.setIncidentWave(feedback);
    double invertedFeedback = feedbackInverter.getReflectedWave();

    source.setVoltage(input + invertedFeedback);
    double stageInput = source.getVoltage();
    for (int i = 0; i < 4; i++) {
      stages[i].setIncidentWave(stageInput);
      stageInput =
        (caps[i].getIncidentWave() + caps[i].getReflectedWave()) * 0.5;
    }
    double output = stageInput;
    previousOutput = output;
    output = Math.max(-10.0, Math.min(10.0, output));

    //         System.out.printf("Input: %.3f, Feedback: %.3f, a_c4: %.3f, b_c4: %.3f, Output: %.3f%n",
    //                 input, invertedFeedback, caps[3].getIncidentWave(), caps[3].getReflectedWave(), output);

    return output;
  }
}
