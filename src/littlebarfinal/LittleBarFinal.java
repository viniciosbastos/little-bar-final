package littlebarfinal;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author Vinicios
 */
public class LittleBarFinal extends Application {

    public static Bar2 bar;
    private Group root;
    private List<Client> clientes = new ArrayList();
    
    TextField nomeText;
    TextField tcText, tbText;
    
    @Override
    public void start(Stage primaryStage) throws URISyntaxException {
        root = new Group();

        Scene scene = new Scene(root, 300, 250);

        primaryStage.setTitle("Little Bar");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        createSecondaryStage();
    }
    
    private void createSecondaryStage() {
        VBox vBox = new VBox();
        vBox.setSpacing(2);
        
        VBox control = createClientForm(vBox);

        vBox.getChildren().add(control);

        Scene scene = new Scene(vBox, 200, 600);
        Stage secondaryStage = new Stage();
        secondaryStage.setTitle("Hello World!");
        secondaryStage.setScene(scene);
        secondaryStage.show();
        
    }
    
    private VBox createClientForm(VBox parent) {
        VBox vBox = new VBox();
        vBox.setPrefWidth(parent.getPrefWidth());
        
        GridPane grid = new GridPane();
        grid.setPrefWidth(parent.getPrefWidth());
        ColumnConstraints cons1 = new ColumnConstraints(50, 50, 50);
        ColumnConstraints cons2 = new ColumnConstraints(150, 150, 150);
        grid.getColumnConstraints().addAll(cons1, cons2);
        
        Label nomeLabel = new Label("Nome");
        nomeText = new TextField();
                
        Label tcLabel = new Label("Casa");
        tcText = new TextField();
                
        Label tbLabel = new Label("Bar");
        tbText = new TextField();
                
        grid.add(nomeLabel, 0, 0);
        grid.add(nomeText, 1, 0);
        grid.add(tcLabel, 0, 1);
        grid.add(tcText, 1, 1);
        grid.add(tbLabel, 0, 2);
        grid.add(tbText, 1, 2);
        vBox.getChildren().add(grid);
        
        Button btn = new Button();
        btn.setText("Tabela de Clientes");
        btn.prefWidthProperty().bind(vBox.widthProperty());
        btn.setOnAction(e -> {
            try {
                String nome = nomeText.getText();
                int tc = Integer.parseInt(tcText.getText());
                int tb = Integer.parseInt(tbText.getText());
                Client c = new ClientBrendan(tc, tb, nome);
                clientes.add(c);
                root.getChildren().add(c.label);
                parent.getChildren().add(c.getDetails());
                c.start();
                
                nomeText.clear();
                tcText.clear();
                tbText.clear();
            } catch (URISyntaxException ex) {
                Logger.getLogger(LittleBarFinal.class.getName()).log(Level.SEVERE, null, ex);
            }
        });        
        vBox.getChildren().add(btn);
        return vBox;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {        
        bar = new Bar2(2);
        launch(args);
    }

}
