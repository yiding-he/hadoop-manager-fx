package com.hyd.hmf.project;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyd.fx.dialog.AlertDialog;
import com.hyd.fx.system.ZipFileCreator;
import com.hyd.fx.system.ZipFileReader;
import com.hyd.hmf.event.CurrentProjectChangedEvent;
import javafx.beans.property.SimpleObjectProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

@Component
public class ProjectRepository {

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private ApplicationEventPublisher eventPublisher;

  private final SimpleObjectProperty<Project> currentProject = new SimpleObjectProperty<>();

  public void setObjectMapper(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  public Project getCurrentProject() {
    return currentProject.get();
  }

  public SimpleObjectProperty<Project> currentProjectProperty() {
    return currentProject;
  }

  public void setCurrentProject(Project currentProject) {
    this.currentProject.set(currentProject);
  }

  @PostConstruct
  private void init() {
    this.currentProject.addListener(
      (any, _old, _new) -> {
        if (eventPublisher != null) {
          eventPublisher.publishEvent(new CurrentProjectChangedEvent());
        }
      }
    );
  }

  public String serializeProject() throws IOException {
    if (currentProject.get() != null) {
      return objectMapper.writeValueAsString(currentProject.get());
    } else {
      return null;
    }
  }

  public void saveProject(Path path) {
    try (ZipFileCreator zipFileCreator = new ZipFileCreator(path)) {
      zipFileCreator.putEntry("project.json", serializeProject(), StandardCharsets.UTF_8);
    } catch (IOException e) {
      AlertDialog.error("保存失败", e);
    }
  }

  public Project readProject(File projectFile) {
    try (ZipFileReader zipFileReader = new ZipFileReader(projectFile)) {
      String json = zipFileReader.readZipEntryString("project.json");
      return objectMapper.readValue(json, Project.class);
    } catch (IOException e) {
      AlertDialog.error("保存失败", e);
      return null;
    }
  }
}
