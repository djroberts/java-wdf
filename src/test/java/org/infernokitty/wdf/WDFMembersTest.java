package org.infernokitty.wdf;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class WDFMembersTest {

  @Test
  void testInitialValues() {
    WDFMembers members = new WDFMembers();
    assertEquals(1.0e-9, members.R, 1e-15); // Tolerance for double comparison
    assertEquals(1.0 / 1.0e-9, members.G, 1e-9);
    assertEquals(0.0, members.a, 1e-15);
    assertEquals(0.0, members.b, 1e-15);
  }

  @Test
  void testReset() {
    WDFMembers members = new WDFMembers();
    members.a = 5.0;
    members.b = 10.0;
    members.reset();
    assertEquals(0.0, members.a, 1e-15);
    assertEquals(0.0, members.b, 1e-15);
  }
}
