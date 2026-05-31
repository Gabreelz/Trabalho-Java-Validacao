package app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.MainFrame;

/**
 * Ponto de entrada da aplicação JavaFX.
 * Estrutura: app/Main.java
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        MainFrame mainFrame = new MainFrame(primaryStage);
        Scene scene = new Scene(mainFrame.getRoot(), 900, 620);

        String css = getClass().getResource("/styles/estilo.css").toExternalForm();
        scene.getStylesheets().add(css);

        primaryStage.setTitle("ValidaSystem — Sistema de Validação");
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(700);
        primaryStage.setMinHeight(500);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
