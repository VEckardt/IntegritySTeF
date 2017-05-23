/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package integrityfit.application;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * The small STeF Retriever application
 *
 * @author veckardt
 */
public class TestApp extends Application {

    public static Stage stage;

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("TestApp.fxml"));
        TestApp.stage = stage;
        Image applicationIcon = new Image(getClass().getResourceAsStream("STeF.png"));
        stage.getIcons().add(applicationIcon);

        Scene scene = new Scene(root);
        stage.setTitle("Generate FitNesse Test Case");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        // Config cfg = new Config();
        // cfg.Save();

        launch(args);
    }
}