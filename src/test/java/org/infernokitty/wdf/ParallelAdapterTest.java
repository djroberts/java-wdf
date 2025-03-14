package org.infernokitty.cwdf;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class ParallelAdapterTest {

  @Test
  void testConstructorAndConnectToParent() {
    TestWDF port1 = new TestWDF();
    TestWDF port2 = new TestWDF();
    ParallelAdapter adapter = new ParallelAdapter(port1, port2);
    assertEquals(adapter, port1.parent);
    assertEquals(adapter, port2.parent);
  }

  @Test
  void testCalcImpedance() {
    Resistor port1 = new Resistor(10.0);
    Resistor port2 = new Resistor(20.0);
    ParallelAdapter adapter = new ParallelAdapter(port1, port2);

    double expectedG = port1.wdf.G + port2.wdf.G;
    double expectedR = 1.0 / expectedG;
    double expectedPort1Reflect = port1.wdf.G / expectedG;

    assertEquals(expectedR, adapter.wdf.R, 1e-9);
    assertEquals(expectedG, adapter.wdf.G, 1e-9);
    assertEquals(expectedPort1Reflect, adapter.getPort1Reflect(), 1e-9);
  }

  @Test
  void testIncidentAndReflected() {
    Resistor port1 = new Resistor(10.0);
    Resistor port2 = new Resistor(20.0);
    ParallelAdapter adapter = new ParallelAdapter(port1, port2);

    double incidentValue = 5.0;
    adapter.incident(incidentValue);

    double expectedReflected =
      adapter.getPort1Reflect() * port1.reflected() +
      (1 - adapter.getPort1Reflect()) * port2.reflected();

    assertEquals(incidentValue, adapter.wdf.a);
    assertEquals(expectedReflected, adapter.reflected(), 1e-9);
    assertEquals(expectedReflected, adapter.wdf.b, 1e-9);
  }
}
