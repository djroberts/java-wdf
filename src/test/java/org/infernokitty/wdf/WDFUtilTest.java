package org.infernokitty.cwdf;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class WDFUtilTest {

  @Test
  void testVoltage() {
    TestWDF wdf = new TestWDF();
    wdf.wdf.a = 10.0;
    wdf.wdf.b = 20.0;

    double expectedVoltage = (10.0 + 20.0) * 0.5;
    assertEquals(expectedVoltage, WDFUtil.voltage(wdf), 1e-9); // Tolerance for double comparison
  }

  @Test
  void testVoltageWithNegativeValues() {
    TestWDF wdf = new TestWDF();
    wdf.wdf.a = -5.0;
    wdf.wdf.b = 15.0;

    double expectedVoltage = (-5.0 + 15.0) * 0.5;
    assertEquals(expectedVoltage, WDFUtil.voltage(wdf), 1e-9);
  }

  @Test
  void testVoltageWithZeroValues() {
    TestWDF wdf = new TestWDF();
    wdf.wdf.a = 0.0;
    wdf.wdf.b = 0.0;

    double expectedVoltage = (0.0 + 0.0) * 0.5;
    assertEquals(expectedVoltage, WDFUtil.voltage(wdf), 1e-9);
  }
}
