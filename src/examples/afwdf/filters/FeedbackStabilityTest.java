package examples.afwdf.filters;

import org.infernokitty.afwdf.*;

public class FeedbackStabilityTest {

  public static void main(String[] args) {
    double sampleRate = 44100.0;
    IdealVoltageSource source = new IdealVoltageSource(0.0);
    Resistor r1 = new Resistor(1000.0);
    Capacitor c1 = new Capacitor(1e-6, sampleRate);
    Resistor r2 = new Resistor(1000.0);
    Capacitor c2 = new Capacitor(1e-6, sampleRate);
    SeriesAdapter s2 = new SeriesAdapter(r2, c2);
    ParallelAdapter p1 = new ParallelAdapter(c1, s2);
    SeriesAdapter s1 = new SeriesAdapter(r1, p1);
    ThreePortSeriesAdapter feedbackAdapter = new ThreePortSeriesAdapter(
      source,
      s1,
      new Resistor(1e-6)
    );
    double feedbackGain = 0.5;

    double input = 1.0; // Static input
    double previousOutput = 0.0;
    System.out.println("Testing Feedback Stability...");
    for (int i = 0; i < 100; i++) {
      source.setVoltage(input);
      double feedback = previousOutput * feedbackGain * 0.5 * 0.1; // Test resonance = 0.5
      feedbackAdapter.setIncidentWave(0, source.getVoltage());
      feedbackAdapter.setIncidentWave(1, feedback);
      feedbackAdapter.setIncidentWave(2, 0.0);
      feedbackAdapter.computeWave();
      s1.setIncidentWave(feedbackAdapter.getReflectedWave(1));
      p1.setIncidentWave(s1.getReflectedWave());
      s2.setIncidentWave(p1.getReflectedWave());
      double output = (c2.getIncidentWave() + c2.getReflectedWave()) * 0.5;
      previousOutput = output;
      if (i % 10 == 0) {
        System.out.printf(
          "Step %d: Input: %.3f, Feedback: %.3f, Output: %.3f%n",
          i,
          input,
          feedback,
          output
        );
      }
    }
  }
}
