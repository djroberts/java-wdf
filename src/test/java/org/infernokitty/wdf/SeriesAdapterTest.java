package org.infernokitty.cwdf;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class SeriesAdapterTest {

  @Test
  void testConstructorAndConnectToParent() {
    TestWDF port1 = new TestWDF();
    TestWDF port2 = new TestWDF();
    SeriesAdapter adapter = new SeriesAdapter(port1, port2);
    assertEquals(adapter, port1.parent);
    assertEquals(adapter, port2.parent);
  }

  @Test
  void testCalcImpedance() {
    Resistor port1 = new Resistor(10.0);
    Resistor port2 = new Resistor(20.0);
    SeriesAdapter adapter = new SeriesAdapter(port1, port2);

    double expectedR = port1.wdf.R + port2.wdf.R;
    double expectedG = 1.0 / expectedR;
    double expectedPort1Reflect = port1.wdf.R / expectedR;

    assertEquals(expectedR, adapter.wdf.R, 1e-9);
    assertEquals(expectedG, adapter.wdf.G, 1e-9);
    assertEquals(expectedPort1Reflect, adapter.getPort1Reflect(), 1e-9);
  }

  @Test
  void testIncidentAndReflected() {
    Resistor port1 = new Resistor(10.0);
    Resistor port2 = new Resistor(20.0);
    SeriesAdapter adapter = new SeriesAdapter(port1, port2);

    double incidentValue = 5.0;
    adapter.incident(incidentValue);

    double expectedReflected = -(port1.reflected() + port2.reflected());

    assertEquals(incidentValue, adapter.wdf.a);
    assertEquals(expectedReflected, adapter.reflected(), 1e-9);
    assertEquals(expectedReflected, adapter.wdf.b, 1e-9);
  }
}
