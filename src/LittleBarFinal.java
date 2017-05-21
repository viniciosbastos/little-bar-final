/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import littlebarfinal.Client;

/**
 *
 * @author Vinicios
 */
public class LittleBarFinal extends Application {

    @Override
    public void start(Stage primaryStage) throws URISyntaxException {
        Client cliente = new Client();
        Button btn = new Button();
        btn.setText("Animar esse Priquito");
        btn.setOnAction(e -> cliente.start());

        ImageView imageView = cliente.imageView;

        Group root = new Group();
        root.getChildren().add(btn);
        root.getChildren().add(imageView);

        Scene scene = new Scene(root, 300, 250);

        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private String getURI(String nome) {
        String uri = null;
        try {
            uri = getClass().getResource(nome).toURI().toString();
        } catch (URISyntaxException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        return uri;
        }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
