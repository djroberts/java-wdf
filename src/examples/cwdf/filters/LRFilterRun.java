package examples.cwdf.filters;

import org.infernokitty.cwdf.filters.*;

public class LRFilterRun {

  public static void main(String[] args) {
    double sampleRate = 44100;
    int oversampleFactor = 1;

    WDFLRFilter filter = new WDFLRFilter(sampleRate, oversampleFactor);

    double input = 1.0;
    for (int i = 0; i < 100; i++) {
      double output = filter.process(input, i / 100);
      System.out.println("Input: " + input + ", Output: " + output);
      System.out.println("Inductor a: " + filter.l.wdf.a);
      System.out.println("Inductor b: " + filter.l.wdf.b);
      System.out.println("Inductor R: " + filter.l.wdf.R);
      System.out.println("Inductor G: " + filter.l.wdf.G);
    }
  }
}
