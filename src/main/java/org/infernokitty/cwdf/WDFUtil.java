package org.infernokitty.cwdf;

public class WDFUtil {

  public static double voltage(BaseWDF wdf) {
    return (wdf.wdf.a + wdf.wdf.b) * 0.5;
  }
}
