package Kerhorekisteri;

import Luokat.Rekisteri;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;


/**
 * @author Tapio Saarnio
 * @version 9.2.2019
 *
 */
public class RekisteriMain extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader ldr = new FXMLLoader(getClass().getResource("Avaus.fxml"));
            final Pane root = ldr.load();
            final RekisteriGUIController rekisteriCtrl = (RekisteriGUIController) ldr.getController();
            
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("rekisteri.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("rekisteri");
            primaryStage.show();
            
            Rekisteri rekisteri=new Rekisteri();
            rekisteriCtrl.setRekisteri(rekisteri);
            //rekisteriCtrl.avaa();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param args Ei käytössä
     */
    public static void main(String[] args) {
        launch(args);
    }
}