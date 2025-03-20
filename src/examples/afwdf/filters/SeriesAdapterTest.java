package examples.afwdf.filters;

import org.infernokitty.afwdf.*;

public class SeriesAdapterTest {

  public static void main(String[] args) {
    double sampleRate = 44100.0;
    IdealVoltageSource source = new IdealVoltageSource(0.0);
    Resistor r2 = new Resistor(1000.0);
    Capacitor c2 = new Capacitor(1e-6, sampleRate);
    SeriesAdapter s2 = new SeriesAdapter(r2, c2);

    double[] frequencies = { 10.0, 159.0, 1000.0 };
    for (double freq : frequencies) {
      System.out.printf("Testing frequency: %.1f Hz%n", freq);
      for (int i = 0; i < 100; i++) {
        double t = i / sampleRate;
        double input = Math.sin(2 * Math.PI * freq * t);
        source.setVoltage(input);
        s2.setIncidentWave(source.getVoltage());
        double output = (c2.getIncidentWave() + c2.getReflectedWave()) * 0.5;
        if (i % 10 == 0) {
          System.out.printf(
            "Step %d: Input: %.3f, a_c2: %.3f, b_c2: %.3f, Output: %.3f%n",
            i,
            input,
            c2.getIncidentWave(),
            c2.getReflectedWave(),
            output
          );
        }
      }
    }
  }
}
