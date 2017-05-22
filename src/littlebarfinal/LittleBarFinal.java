package littlebarfinal;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author Vinicios
 */
public class LittleBarFinal extends Application {

    public static Bar bar;
    private Group root;
    private List<Client> clientes = new ArrayList();

    TextField nomeText;
    TextField tcText, tbText;

    @Override
    public void start(Stage primaryStage) throws URISyntaxException {
        // Cria a caixa de diálogo para informar a quantidade de cadeiras
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Inicialização do LitteBar");
        dialog.setHeaderText("Calma, estamos quase abrindo!");
        dialog.setContentText("Quantas cadeiras tem seu bar?");
        
        //"Segura" a execução do restante do método até que o OK seja pressionado
        Optional<String> result = dialog.showAndWait();
        int c = 0;
        if (result.isPresent()) {
            c = Integer.parseInt(result.get());
            bar.setCadeira(c);
        }
        root = new Group();
        String backName = "/images/backgrounds/back-" + c + ".png";
        ImageView background = new ImageView(new Image(getURI(backName)));
        
        root.getChildren().add(background);
        
        Scene scene = new Scene(root, 259, 250);

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
        secondaryStage.setTitle("Tabela de Clientes");
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
        btn.setText("Criar cliente");
        btn.prefWidthProperty().bind(vBox.widthProperty());
        btn.setOnAction(e -> {
            try {
                String nome = nomeText.getText();
                int tc = Integer.parseInt(tcText.getText());
                int tb = Integer.parseInt(tbText.getText());
                Client c = ClientMaker.createClient(tc, tb, nome);
                clientes.add(c);
                root.getChildren().add(c.label);
                parent.getChildren().add(c.getDetails());
                c.start();

                nomeText.clear();
                tcText.clear();
                tbText.clear();
                
                System.out.println("Cliente " + nome + " criado.");
            } catch (URISyntaxException ex) {
                Logger.getLogger(LittleBarFinal.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        vBox.getChildren().add(btn);
        return vBox;
    }

    private String getURI(String nome) throws URISyntaxException {
        return getClass().getResource(nome).toURI().toString();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        bar = new Bar();
        launch(args);
    }

}
