package org.infernokitty.cwdf;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class ResistiveVoltageSourceTest {

  @Test
  void testConstructorAndCalcImpedance() {
    double resistance = 50.0;
    ResistiveVoltageSource source = new ResistiveVoltageSource(resistance);

    assertEquals(resistance, source.wdf.R, 1e-9);
    assertEquals(1.0 / resistance, source.wdf.G, 1e-9);
  }

  @Test
  void testSetResistance() {
    ResistiveVoltageSource source = new ResistiveVoltageSource(50.0);
    double newResistance = 100.0;
    source.setResistance(newResistance);

    assertEquals(newResistance, source.wdf.R, 1e-9);
    assertEquals(1.0 / newResistance, source.wdf.G, 1e-9);
  }

  @Test
  void testSetAndGetVoltage() {
    ResistiveVoltageSource source = new ResistiveVoltageSource(50.0);
    double voltage = 10.0;
    source.setVoltage(voltage);

    assertEquals(voltage, source.getVoltage(), 1e-9);
  }

  @Test
  void testIncidentAndReflected() {
    ResistiveVoltageSource source = new ResistiveVoltageSource(50.0);
    double voltage = 10.0;
    source.setVoltage(voltage);
    double incidentValue = 5.0;
    source.incident(incidentValue);

    assertEquals(incidentValue, source.wdf.a, 1e-9);
    assertEquals(voltage, source.reflected(), 1e-9);
    assertEquals(voltage, source.wdf.b, 1e-9);
  }

  @Test
  void testReset() {
    ResistiveVoltageSource source = new ResistiveVoltageSource(50.0);
    source.setVoltage(10.0);
    source.incident(5.0);
    source.reset();

    assertEquals(0.0, source.wdf.a, 1e-9);
    assertEquals(0.0, source.wdf.b, 1e-9);
  }

  @Test
  void testSetResistanceNoChange() {
    double resistance = 50.0;
    ResistiveVoltageSource source = new ResistiveVoltageSource(resistance);
    double originalR = source.wdf.R;
    double originalG = source.wdf.G;
    source.setResistance(resistance);
    assertEquals(originalR, source.wdf.R);
    assertEquals(originalG, source.wdf.G);
  }
}
