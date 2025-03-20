package org.infernokitty.cwdf;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class IdealVoltageSourceTest {

  @Test
  void testConstructorAndConnectToParent() {
    TestWDF load = new TestWDF();
    IdealVoltageSource source = new IdealVoltageSource(load);
    assertEquals(source, load.parent);
  }

  @Test
  void testSetAndGetVoltage() {
    TestWDF load = new TestWDF();
    IdealVoltageSource source = new IdealVoltageSource(load);
    double voltage = 5.0;
    source.setVoltage(voltage);
    assertEquals(voltage, source.getVoltage());
  }

  @Test
  void testCalcImpedance() {
    TestWDF load = new TestWDF();
    IdealVoltageSource source = new IdealVoltageSource(load);
    // calcImpedance() in IdealVoltageSource is empty, so we just test that it doesn't throw an exception
    source.calcImpedance();
    // Since calcImpedance() is empty, we cannot test any specific values.
  }

  @Test
  void testIncidentAndReflected() {
    TestWDF load = new TestWDF();
    IdealVoltageSource source = new IdealVoltageSource(load);
    double voltage = 10.0;
    source.setVoltage(voltage);
    double incidentValue = 3.0;
    source.incident(incidentValue);
    double expectedReflected = -incidentValue + 2 * voltage;
    assertEquals(expectedReflected, source.reflected());
    assertEquals(incidentValue, source.wdf.a);
    assertEquals(expectedReflected, source.wdf.b);
  }
}
