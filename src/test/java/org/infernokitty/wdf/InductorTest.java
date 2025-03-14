package org.infernokitty.cwdf;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class InductorTest {

  @Test
  void testConstructorAndCalcImpedance() {
    double inductance = 1e-3; // 1 mH
    double sampleRate = 44100.0;
    Inductor inductor = new Inductor(inductance, sampleRate);

    double expectedR = 2.0 * inductance * sampleRate;
    assertEquals(expectedR, inductor.wdf.R, 1e-9); // Tolerance for double comparison
    assertEquals(1.0 / expectedR, inductor.wdf.G, 1e-9);
  }

  @Test
  void testPrepare() {
    Inductor inductor = new Inductor(1e-3, 44100.0);
    double newSampleRate = 48000.0;
    inductor.prepare(newSampleRate);

    double expectedR = 2.0 * 1e-3 * newSampleRate;
    assertEquals(expectedR, inductor.wdf.R, 1e-9);
    assertEquals(1.0 / expectedR, inductor.wdf.G, 1e-9);
    assertEquals(0.0, inductor.wdf.a);
    assertEquals(0.0, inductor.wdf.b);
    assertEquals(0.0, inductor.getZ());
  }

  @Test
  void testSetInductanceValue() {
    Inductor inductor = new Inductor(1e-3, 44100.0);
    double newInductance = 2e-3;
    inductor.setInductanceValue(newInductance);

    double expectedR = 2.0 * newInductance * 44100.0;
    assertEquals(expectedR, inductor.wdf.R, 1e-9);
    assertEquals(1.0 / expectedR, inductor.wdf.G, 1e-9);
  }

  //   @Test
  //   void testIncidentAndReflected() {
  //     Inductor inductor = new Inductor(1e-3, 44100.0);
  //     double incidentValue = 10.0;
  //     inductor.incident(incidentValue);
  //     assertEquals(incidentValue, inductor.wdf.a);
  //     assertEquals(-incidentValue, inductor.reflected());
  //     assertEquals(-incidentValue, inductor.wdf.b);
  //     assertEquals(incidentValue, inductor.getZ());
  //   }

  @Test
  void testReset() {
    Inductor inductor = new Inductor(1e-3, 44100.0);
    inductor.incident(10.0);
    inductor.reset();
    assertEquals(0.0, inductor.wdf.a);
    assertEquals(0.0, inductor.wdf.b);
    assertEquals(0.0, inductor.getZ());
  }

  @Test
  void testSetInductanceValueNoChange() {
    double inductance = 1e-3;
    double sampleRate = 44100.0;
    Inductor inductor = new Inductor(inductance, sampleRate);
    double originalR = inductor.wdf.R;
    double originalG = inductor.wdf.G;
    inductor.setInductanceValue(inductance);
    assertEquals(originalR, inductor.wdf.R);
    assertEquals(originalG, inductor.wdf.G);
  }
}
