package org.infernokitty.cwdf;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class CapacitorTest {

  @Test
  void testConstructorAndCalcImpedance() {
    double capacitance = 1e-6; // 1 uF
    double sampleRate = 44100.0;
    Capacitor capacitor = new Capacitor(capacitance, sampleRate);

    double expectedR = 1.0 / (2.0 * capacitance * sampleRate);
    assertEquals(expectedR, capacitor.wdf.R, 1e-9); // Tolerance for double comparison
    assertEquals(1.0 / expectedR, capacitor.wdf.G, 1e-9);
  }

  @Test
  void testSetCapacitance() {
    Capacitor capacitor = new Capacitor(1e-6, 44100.0);
    double newCapacitance = 2e-6;
    capacitor.setCapacitance(newCapacitance);

    double expectedR = 1.0 / (2.0 * newCapacitance * 44100.0);
    assertEquals(expectedR, capacitor.wdf.R, 1e-9);
    assertEquals(1.0 / expectedR, capacitor.wdf.G, 1e-9);
  }

  @Test
  void testIncidentAndReflected() {
    Capacitor capacitor = new Capacitor(1e-6, 44100.0);
    double incidentValue = 10.0;
    capacitor.incident(incidentValue);
    assertEquals(incidentValue, capacitor.wdf.a);
    assertEquals(incidentValue, capacitor.reflected());
    assertEquals(incidentValue, capacitor.wdf.b);
    assertEquals(incidentValue, capacitor.getZ());
  }

  @Test
  void testReset() {
    Capacitor capacitor = new Capacitor(1e-6, 44100.0);
    capacitor.incident(10.0);
    capacitor.reset();
    assertEquals(0.0, capacitor.wdf.a);
    assertEquals(0.0, capacitor.wdf.b);
    assertEquals(0.0, capacitor.getZ());
  }

  @Test
  void testSetCapacitanceNoChange() {
    double capacitance = 1e-6;
    double sampleRate = 44100.0;
    Capacitor capacitor = new Capacitor(capacitance, sampleRate);
    double originalR = capacitor.wdf.R;
    double originalG = capacitor.wdf.G;
    capacitor.setCapacitance(capacitance);
    assertEquals(originalR, capacitor.wdf.R);
    assertEquals(originalG, capacitor.wdf.G);
  }
}
