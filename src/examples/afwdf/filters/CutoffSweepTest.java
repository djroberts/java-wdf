package examples.afwdf.filters;

import org.infernokitty.afwdf.filters.ResonantRCFilter;

public class CutoffSweepTest {

  public static void main(String[] args) {
    double sampleRate = 44100.0;
    ResonantRCFilter filter = new ResonantRCFilter(sampleRate);
    double[] cutoffs = { 0.1, 0.5, 0.9 };

    for (double cutoff : cutoffs) {
      System.out.printf("Cutoff: %.1f%n", cutoff);
      for (int i = 0; i < 20; i++) {
        double input = Math.sin((2 * Math.PI * 159.0 * i) / sampleRate); // 159 Hz
        double output = filter.process(input, cutoff, 0.0);
        if (i % 5 == 0) {
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
