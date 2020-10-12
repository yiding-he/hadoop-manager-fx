package com.hyd.hmf;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.hyd.hmf.project.Project;
import lombok.Data;
import org.junit.jupiter.api.Test;

public class ObjectMapperTest {

  private JsonMapper jsonMapper = new JsonMapper();

  private ObjectMapper objectMapper = new ObjectMapper();

  @Data
  public static class Person {

    private String name;
  }

  @Test
  public void testSerialize() throws Exception {
    Person p = new Person();
    p.setName("123");
    System.out.println(objectMapper.writeValueAsString(p));
    System.out.println(jsonMapper.writeValueAsString(p));

    Project project = new Project();
    project.setName("123");
    System.out.println(objectMapper.writeValueAsString(project));
    System.out.println(jsonMapper.writeValueAsString(project));
  }
}
