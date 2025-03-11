package org.infernokitty.wdf;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class ResistorTest {

  @Test
  void testConstructorAndGetResistance() {
    double resistance = 100.0;
    Resistor resistor = new Resistor(resistance);
    assertEquals(resistance, resistor.getResistance());
  }

  @Test
  void testSetResistance() {
    Resistor resistor = new Resistor(100.0);
    double newResistance = 200.0;
    resistor.setResistance(newResistance);
    assertEquals(newResistance, resistor.getResistance());
  }

  @Test
  void testCalcImpedance() {
    double resistance = 50.0;
    Resistor resistor = new Resistor(resistance);
    resistor.calcImpedance();
    assertEquals(resistance, resistor.wdf.R);
    assertEquals(1.0 / resistance, resistor.wdf.G);
  }

  @Test
  void testIncidentAndReflected() {
    Resistor resistor = new Resistor(100.0);
    double incidentValue = 10.0;
    resistor.incident(incidentValue);
    assertEquals(incidentValue, resistor.wdf.a);
    assertEquals(0.0, resistor.reflected());
    assertEquals(0.0, resistor.wdf.b);
  }
}
