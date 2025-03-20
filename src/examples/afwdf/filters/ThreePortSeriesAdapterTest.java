package examples.afwdf.filters;

import org.infernokitty.afwdf.*;

public class ThreePortSeriesAdapterTest {

  public static void main(String[] args) {
    double sampleRate = 44100.0;

    IdealVoltageSource source = new IdealVoltageSource(0.0);
    Resistor r1 = new Resistor(1000.0);
    Capacitor c1 = new Capacitor(1e-6, sampleRate);
    SeriesAdapter s1 = new SeriesAdapter(r1, c1);
    Resistor feedbackResistor = new Resistor(1e-6);

    ThreePortSeriesAdapter adapter = new ThreePortSeriesAdapter(
      source,
      s1,
      feedbackResistor
    );

    double input = 5.0;
    double feedback = 2.0;
    int steps = 10;

    System.out.println("Testing ThreePortSeriesAdapter...");
    for (int i = 0; i < steps; i++) {
      source.setVoltage(input);
      adapter.setIncidentWave(0, source.getVoltage());
      adapter.setIncidentWave(1, 0.0);
      adapter.setIncidentWave(2, feedback);
      adapter.computeWave();

      System.out.printf("Step %d:\n", i);
      System.out.printf("  Input: %.3f, Feedback: %.3f\n", input, feedback);
      System.out.printf(
        "  Port 0 - a: %.3f, b: %.3f\n",
        adapter.getIncidentWave(),
        adapter.getReflectedWave(0)
      );
      System.out.printf(
        "  Port 1 - a: %.3f, b: %.3f\n",
        s1.getIncidentWave(),
        s1.getReflectedWave()
      );
      System.out.printf(
        "  Port 2 - a: %.3f, b: %.3f\n",
        feedbackResistor.getIncidentWave(),
        feedbackResistor.getReflectedWave()
      );
      System.out.printf(
        "  s1 Components - R1 a: %.3f, b: %.3f, C1 a: %.3f, b: %.3f\n",
        r1.getIncidentWave(),
        r1.getReflectedWave(),
        c1.getIncidentWave(),
        c1.getReflectedWave()
      );
      System.out.println();
    }
  }
}
