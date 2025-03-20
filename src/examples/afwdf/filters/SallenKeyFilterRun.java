package examples.afwdf.filters;

import org.infernokitty.afwdf.filters.SallenKeyFilter;

public class SallenKeyFilterRun {

  public static void main(String[] args) {
    double sampleRate = 44100.0;
    SallenKeyFilter filter = new SallenKeyFilter(sampleRate);
    double[] cutoffs = { 0.1, 0.5, 0.9 };
    double[] frequencies = { 50.0, 159.0, 1000.0 };
    double[] resonances = { 0.5, 2.0, 4.0 };

    for (double cutoff : cutoffs) {
      for (double resonance : resonances) {
        System.out.printf("Cutoff: %.1f, Resonance: %.1f%n", cutoff, resonance);
        for (double freq : frequencies) {
          System.out.printf("Frequency: %.1f Hz%n", freq);
          for (int i = 0; i < 50; i++) {
            double input = Math.sin((2 * Math.PI * freq * i) / sampleRate);
            double output = filter.process(input, cutoff, resonance);
            if (i % 10 == 0) {
              System.out.printf(
                "Step %d: Input: %.3f, Output: %.3f%n",
                i,
                input,
                output
              );
            }
          }
        }
      }
    }
  }
}
