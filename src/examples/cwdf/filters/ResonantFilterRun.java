package examples.cwdf.filters;

import org.infernokitty.cwdf.filters.*;

public class ResonantFilterRun {

  public static void main(String[] args) {
    double sampleRate = 48000.0;
    int oversample = 1;
    ResonantFilter filter = new ResonantFilter(sampleRate, oversample);
    double[] freqs = { 100.0, 1000.0, 5000.0 }; // Adjusted frequencies for resonance
    double dt = 1.0 / sampleRate;

    for (double freq : freqs) {
      double t = 0.0;
      System.out.println("\nFrequency: " + freq + " Hz");
      for (int i = 0; i < 200; i++) {
        double input = Math.sin(2 * Math.PI * freq * t);
        double resistance = 100.0; // Fixed resistance for resonant behavior
        double output = filter.process(input, resistance);
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
