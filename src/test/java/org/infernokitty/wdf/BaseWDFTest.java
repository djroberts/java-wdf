package org.infernokitty.cwdf;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class BaseWDFTest {

  @Test
  void testConnectToParent() {
    TestWDF parent = new TestWDF();
    TestWDF child = new TestWDF();
    child.connectToParent(parent);
    assertEquals(parent, child.parent);
  }

  @Test
  void testCalcImpedance() {
    TestWDF wdf = new TestWDF();
    wdf.calcImpedance();
    assertEquals(10.0, wdf.wdf.R);
    assertEquals(0.1, wdf.wdf.G);
  }

  @Test
  void testPropagateImpedance() {
    TestWDF parent = new TestWDF();
    TestWDF child = new TestWDF();
    child.connectToParent(parent);
    child.propagateImpedance();
    assertEquals(10.0, parent.wdf.R);
    assertEquals(0.1, parent.wdf.G);
  }

  @Test
  void testIncidentAndReflected() {
    TestWDF wdf = new TestWDF();
    double incidentValue = 5.0;
    wdf.incident(incidentValue);
    assertEquals(incidentValue, wdf.wdf.a);
    assertEquals(incidentValue, wdf.reflected());
    assertEquals(incidentValue, wdf.wdf.b);
  }

  @Test
  void testReset() {
    TestWDF wdf = new TestWDF();
    wdf.incident(10.0);
    wdf.calcImpedance();
    wdf.reset();
    assertEquals(0.0, wdf.wdf.a);
    assertEquals(0.0, wdf.wdf.b);
    //Check that impedance recalculation will occur.
    TestWDF wdf2 = new TestWDF();
    wdf2.calcImpedance();
    assertEquals(10.0, wdf2.wdf.R);
    wdf2.reset();
    wdf2.calcImpedance();
    assertEquals(10.0, wdf2.wdf.R);
  }
}
