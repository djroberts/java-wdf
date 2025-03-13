package examples.afwdf.filters;

import org.infernokitty.afwdf.filters.*;

public class RCFilterRun {

  public static void main(String[] args) {
    RCFilter filter = new RCFilter(1000);
    double input = 5.0;
    for (double cutoff : new double[] { 0.1, 0.5, 0.9 }) {
      System.out.println("Cutoff: " + cutoff);
      for (int i = 0; i < 25; i++) {
        double output = filter.process(input, cutoff);
        System.out.println("Step " + i + ": " + output);
      }
    }
  }
}
