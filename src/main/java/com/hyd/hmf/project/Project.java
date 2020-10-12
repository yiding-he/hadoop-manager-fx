package com.hyd.hmf.project;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class Project {

  private final StringProperty name = new SimpleStringProperty();

  private final ObservableList<HadoopNode> nodes = FXCollections.observableArrayList();

  public String getName() {
    return name.get();
  }

  public StringProperty nameProperty() {
    return name;
  }

  public void setName(String name) {
    this.name.set(name);
  }

  public ObservableList<HadoopNode> getNodes() {
    return nodes;
  }

  public void setNodes(List<HadoopNode> nodes) {
    this.nodes.setAll(nodes);
  }
}
