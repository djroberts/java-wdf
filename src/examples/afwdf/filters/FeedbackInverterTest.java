package examples.afwdf.filters;

import org.infernokitty.afwdf.*;

public class FeedbackInverterTest {

  public static void main(String[] args) {
    double sampleRate = 44100.0;
    Resistor r = new Resistor(1000.0);
    Capacitor c = new Capacitor(1e-6, sampleRate);
    SeriesAdapter filter = new SeriesAdapter(r, c);
    PolarityInverter inverter = new PolarityInverter(c.getPortResistance());
    double gain = 0.9;
    double previousOutput = 0.0;

    System.out.println("Testing Feedback with Inverter...");
    for (int i = 0; i < 100; i++) {
      double input = (i < 50) ? 1.0 : -1.0; // Step input
      inverter.setIncidentWave(previousOutput * gain);
      double feedback = inverter.getReflectedWave();
      filter.setIncidentWave(input + feedback);
      double output = (c.getIncidentWave() + c.getReflectedWave()) * 0.5;
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
