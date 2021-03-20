package sample;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AlertBox {

    public static void display(String resultat, String tilValuta){
        Label a = new Label(resultat+",- " + tilValuta);
        Label label = new Label("Resultatet blir: ");

        VBox design = new VBox();
        design.getChildren().addAll(label, a);
        design.setAlignment(Pos.CENTER);
        Stage window = new Stage();
        Scene scene1 = new Scene(design,300,200);

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Omregning");
        window.setScene(scene1);
        window.showAndWait();

    }
}
