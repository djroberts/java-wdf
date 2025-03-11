package org.infernokitty.wdf;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class PolarityInverterTest {

  @Test
  void testConstructorAndConnectToParent() {
    TestWDF port1 = new TestWDF();
    PolarityInverter inverter = new PolarityInverter(port1);
    assertEquals(inverter, port1.parent);
  }

  @Test
  void testCalcImpedance() {
    Resistor port1 = new Resistor(50.0);
    PolarityInverter inverter = new PolarityInverter(port1);

    assertEquals(port1.wdf.R, inverter.wdf.R, 1e-9);
    assertEquals(port1.wdf.G, inverter.wdf.G, 1e-9);
  }

  @Test
  void testIncidentAndReflected() {
    Resistor port1 = new Resistor(50.0);
    PolarityInverter inverter = new PolarityInverter(port1);

    double incidentValue = 10.0;
    inverter.incident(incidentValue);

    assertEquals(incidentValue, inverter.wdf.a);
    assertEquals(-port1.reflected(), inverter.reflected(), 1e-9);
    assertEquals(-port1.reflected(), inverter.wdf.b, 1e-9);
  }
}
