package com.hyd.hmf.controller;

import com.hyd.fx.app.AppPrimaryStage;
import com.hyd.fx.cells.ListCellFactory;
import com.hyd.fx.dialog.FileDialog;
import com.hyd.fx.enhancements.ListViewEnhancements;
import com.hyd.hmf.event.CurrentProjectChangedEvent;
import com.hyd.hmf.project.HadoopNode;
import com.hyd.hmf.project.Project;
import com.hyd.hmf.project.ProjectRepository;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Paths;

@Component
public class MainController {

  public TabPane tabContent;

  public TextField txtProjectName;

  public MenuItem mnuSaveProject;

  public ListView<HadoopNode> lstNodes;

  public Label lblNodeHost;

  public TextField txtNodeHost;

  public Button btnAddNode;

  public Button btnDelNode;

  @Autowired
  private ProjectRepository projectRepository;

  public void initialize() {
    this.mnuSaveProject.disableProperty().bind(this.projectRepository.currentProjectProperty().isNull());
    this.tabContent.disableProperty().bind(this.projectRepository.currentProjectProperty().isNull());
    this.btnDelNode.disableProperty().bind(lstNodes.getSelectionModel().selectedItemProperty().isNull());

    lstNodes.setCellFactory(new ListCellFactory<HadoopNode>().withTextProperty(HadoopNode::hostProperty));
    lstNodes.getSelectionModel().selectedItemProperty().addListener(this::onSelectedNodeChanged);
  }

  public void openNewProject() {
    this.projectRepository.setCurrentProject(new Project());
  }

  @EventListener(CurrentProjectChangedEvent.class)
  public void onCurrentProjectChanged() {
    this.txtProjectName.textProperty().unbind();
    Project currentProject = projectRepository.getCurrentProject();
    if (currentProject != null) {
      this.txtProjectName.textProperty().bindBidirectional(currentProject.nameProperty());
      this.lstNodes.setItems(currentProject.getNodes());
    }
  }

  private void onSelectedNodeChanged(Observable o, HadoopNode old, HadoopNode value) {
    loadNode(value);
  }

  private void loadNode(HadoopNode node) {
    txtNodeHost.textProperty().unbind();
    lblNodeHost.textProperty().unbind();
    txtNodeHost.setText(null);
    lblNodeHost.setText(null);

    if (node != null) {
      txtNodeHost.textProperty().bindBidirectional(node.hostProperty());
      lblNodeHost.textProperty().bind(node.hostProperty());
    }
  }

  public void saveProject() {
    this.projectRepository.saveProject(Paths.get("./sample.zip"));
  }

  public void openExistingProject() {
    File projectFile = FileDialog.showOpenFile(AppPrimaryStage.getPrimaryStage(), "打开项目", "*.zip", "项目文件");
    if (projectFile != null) {
      Project project = projectRepository.readProject(projectFile);
      if (project != null) {
        projectRepository.setCurrentProject(project);
      }
    }
  }

  public void closeCurrentProject() {
    this.projectRepository.setCurrentProject(null);
  }

  public void addNode() {
    HadoopNode hadoopNode = new HadoopNode("localhost");

    Project project = projectRepository.getCurrentProject();
    project.getNodes().add(hadoopNode);

    lstNodes.getSelectionModel().select(hadoopNode);
  }
}
