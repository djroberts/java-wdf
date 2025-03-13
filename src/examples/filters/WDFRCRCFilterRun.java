package examples.filters;

import org.infernokitty.filters.*;

public class WDFRCRCFilterRun {

  public static void main(String[] args) {
    double sampleRate = 48000.0;
    int oversample = 1;
    WDFRCRC filter = new WDFRCRC(sampleRate, oversample);
    double[] freqs = { 100.0, 1000.0, 10000.0 };
    double dt = 1.0 / sampleRate;

    for (double freq : freqs) {
      double t = 0.0;
      System.out.println("\nFrequency: " + freq + " Hz");
      for (int i = 0; i < 200; i++) {
        double input = Math.sin(2 * Math.PI * freq * t);
        double resistanceKnob = 0.5;
        double output = filter.process(input, resistanceKnob);
        if (i % 50 == 0) {
          System.out.printf(
            "Sample %d: Input = %.6f, Output = %.6f%n",
            i,
            input,
            output
          );
        }
        t += dt;
      }
    }
  }
}
