package runtimetests;

import org.infernokitty.cwdf.*;

public class Feedback {

  public static void main(String[] args) {
    double sampleRate = 44100.0;
    double resistance = 100.0;
    double capacitance = 1e-8;
    double inductance = 1e-3;
    double voltage = 1.0;

    Resistor resistor = new Resistor(resistance);
    Capacitor capacitor = new Capacitor(capacitance, sampleRate);
    Inductor inductor = new Inductor(inductance, sampleRate);
    ResistiveVoltageSource voltageSource = new ResistiveVoltageSource(
      resistance
    );

    SeriesAdapter seriesAdapter1 = new SeriesAdapter(voltageSource, resistor);
    SeriesAdapter seriesAdapter2 = new SeriesAdapter(seriesAdapter1, capacitor);
    SeriesAdapter seriesAdapter3 = new SeriesAdapter(seriesAdapter2, inductor);

    voltageSource.connectToParent(inductor);
    voltageSource.setVoltage(voltage);
    voltageSource.propagateImpedance();

    // Frequency sweep variables (adjusted)
    double startFrequency = 4000.0; // Adjusted starting frequency
    double endFrequency = 6000.0; // Adjusted ending frequency
    int numFrequencies = 200; // Increased resolution
    int discardSamples = 100;

    for (int freqIndex = 0; freqIndex < numFrequencies; freqIndex++) {
      double frequency =
        startFrequency +
        ((endFrequency - startFrequency) * freqIndex) / (numFrequencies - 1);
      double angularFrequency = 2.0 * Math.PI * frequency;

      double maxOutputAmplitude = 0.0;

      for (int i = 0; i < 200; i++) {
        double time = i / sampleRate;
        double input = Math.sin(angularFrequency * time);
        seriesAdapter3.incident(input);
        double output = seriesAdapter3.reflected();

        if (i >= discardSamples) {
          maxOutputAmplitude = Math.max(maxOutputAmplitude, Math.abs(output));
        }
      }
      System.out.println(
        "Frequency: " +
        frequency +
        " Hz, Max Output Amplitude: " +
        maxOutputAmplitude
      );
    }
  }
}
