package org.infernokitty.wdf;

public abstract class BaseWDF {

  protected BaseWDF parent = null;
  public WDFMembers wdf = new WDFMembers();

  public void connectToParent(BaseWDF p) {
    this.parent = p;
  }

  public abstract void calcImpedance();

  public void propagateImpedance() {
    calcImpedance();

    if (parent != null) {
      parent.propagateImpedance();
    }
  }

  public abstract void incident(double x);

  public abstract double reflected();

  public void reset() {
    wdf.reset();
  }
}
