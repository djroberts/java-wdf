package examples.afwdf.filters;

import org.infernokitty.afwdf.*;

public class FeedbackTest {

  public static void main(String[] args) {
    double sampleRate = 44100.0;
    Resistor r = new Resistor(1000.0);
    Capacitor c = new Capacitor(1e-6, sampleRate);
    SeriesAdapter filter = new SeriesAdapter(r, c);
    double gain = 0.5;

    double input = 1.0; // Static input
    double previousOutput = 0.0;
    System.out.println("Testing Feedback...");
    for (int i = 0; i < 100; i++) {
      filter.setIncidentWave(input + previousOutput * gain);
      double output = (c.getIncidentWave() + c.getReflectedWave()) * 0.5;
      previousOutput = output;
      if (i % 10 == 0) {
        System.out.printf(
          "Step %d: Input: %.3f, Feedback: %.3f, Output: %.3f%n",
          i,
          input,
          previousOutput * gain,
          output
        );
      }
    }
  }
}
