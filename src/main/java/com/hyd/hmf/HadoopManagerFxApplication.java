package com.hyd.hmf;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyd.fx.app.AppPrimaryStage;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import static com.hyd.fx.Fxml.createFXMLLoader;

@SpringBootApplication
public class HadoopManagerFxApplication extends Application {

  private static ApplicationContext applicationContext;

  public static void main(String[] args) {
    applicationContext = SpringApplication.run(HadoopManagerFxApplication.class, args);
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    AppPrimaryStage.setPrimaryStage(primaryStage);

    FXMLLoader fxmlLoader = createFXMLLoader("/fxml/main.fxml", null, applicationContext::getBean);
    primaryStage.setScene(new Scene(fxmlLoader.load()));
    primaryStage.show();
  }

  @Bean
  public ObjectMapper objectMapper() {
    return new ObjectMapper();
  }
}
