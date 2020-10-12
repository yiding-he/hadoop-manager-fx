package com.hyd.hmf.project;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class HadoopNode {

  private final StringProperty host = new SimpleStringProperty();

  public HadoopNode() {
  }

  public HadoopNode(String host) {
    this.setHost(host);
  }

  public String getHost() {
    return host.get();
  }

  public StringProperty hostProperty() {
    return host;
  }

  public void setHost(String host) {
    this.host.set(host);
  }
}
