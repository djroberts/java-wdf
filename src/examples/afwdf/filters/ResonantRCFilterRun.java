package examples.afwdf.filters;

import org.infernokitty.afwdf.filters.ResonantRCFilter;

public class ResonantRCFilterRun {

  public static void main(String[] args) {
    double sampleRate = 1000.0;
    ResonantRCFilter filter = new ResonantRCFilter(sampleRate);
    double amplitude = 5.0;
    double frequency = 159.0; // Near f_c = 1 / (2π √(1000 * 1e-6 * 1000 * 1e-6)) ≈ 159 Hz
    int steps = 100; // Short run to hear

    for (double cutoff = 0.1; cutoff <= 0.9; cutoff += 0.4) {
      for (double resonance = 0.1; resonance <= 0.9; resonance += 0.4) {
        System.out.println("Cutoff: " + cutoff + ", Resonance: " + resonance);
        for (int step = 0; step < steps; step++) {
          double t = step / sampleRate;
          double input = amplitude * Math.sin(2 * Math.PI * frequency * t);
          double output = filter.process(input, cutoff, resonance);
          // Log every 10 steps to avoid flood
          if (step % 10 == 0) {
            System.out.println("Step " + step + ": " + output);
          }
        }
      }
    }
  }
}
