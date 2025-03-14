package org.infernokitty.cwdf;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class OpenCircuitTest {

  @Test
  void testConstructorAndImpedance() {
    OpenCircuit openCircuit = new OpenCircuit();
    assertEquals(Double.POSITIVE_INFINITY, openCircuit.wdf.R);
    assertEquals(0.0, openCircuit.wdf.G);
  }

  @Test
  void testCalcImpedance() {
    OpenCircuit openCircuit = new OpenCircuit();
    // calcImpedance() in OpenCircuit is empty, so we just test that it doesn't throw an exception
    openCircuit.calcImpedance();
    // Since calcImpedance() is empty, we cannot test any specific values.
  }

  @Test
  void testIncidentAndReflected() {
    OpenCircuit openCircuit = new OpenCircuit();
    double incidentValue = 7.0;
    openCircuit.incident(incidentValue);
    assertEquals(incidentValue, openCircuit.wdf.a);
    assertEquals(incidentValue, openCircuit.reflected());
    assertEquals(incidentValue, openCircuit.wdf.b);
  }

  @Test
  void testReset() {
    OpenCircuit openCircuit = new OpenCircuit();
    openCircuit.incident(10.0);
    openCircuit.reset();
    assertEquals(0.0, openCircuit.wdf.a);
    assertEquals(0.0, openCircuit.wdf.b);
  }
}
