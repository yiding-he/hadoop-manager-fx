package com.hyd.hmf.project;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

class ProjectRepositoryTest {

  @Test
  public void testSerialize() throws Exception {
    Project project = new Project();
    project.setName("123");

    ProjectRepository repository = new ProjectRepository();
    repository.setObjectMapper(new ObjectMapper());
    repository.setCurrentProject(project);

    System.out.println(repository.serializeProject());
  }
}
