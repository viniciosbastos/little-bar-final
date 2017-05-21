package littlebarfinal;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.net.URISyntaxException;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import littlebarfinal.Bar;
import littlebarfinal.Client;

/**
 *
 * @author Vinicios
 */
public class LittleBarFinal extends Application {

    /**
     *
     */
    public static Bar bar;
    
    @Override
    public void start(Stage primaryStage) throws URISyntaxException {
        Group root = new Group();

        Client cliente = new ClientBrendan(3, 3, "A");
        Client cliente2 = new ClientMay(3, 3, "B");
        Client cliente3 = new ClientWally(3, 3, "C");
        
//        Button btn = new Button();
//        btn.setText("Animar esse Priquito");
//        btn.setOnAction(e -> {
//            root.getChildren().add(cliente.imageView);
//            cliente.start();
//        });
//
//
        root.getChildren().add(cliente.imageView);
        root.getChildren().add(cliente2.imageView);
        root.getChildren().add(cliente3.imageView);

        Scene scene = new Scene(root, 300, 250);

        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
        cliente.start();
        cliente2.start();
        cliente3.start();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {        
        bar = new Bar(2);
        launch(args);
    }

}
