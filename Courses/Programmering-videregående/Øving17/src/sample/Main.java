package sample;

import com.jfoenix.controls.JFXTextField;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sun.rmi.rmic.iiop.ValueType;

import java.util.ArrayList;


public class Main extends Application{

    Stage window;
    Scene scene;
    ListView<String> listView;
    ListView<String> listView2;
    Scene scene2;
    ArrayList<Valuta> arrayList = new ArrayList<Valuta>();


    @Override
    public void start(Stage primaryStage) throws Exception{
        window = primaryStage;
        window.setTitle("Valutaomregner");

        // Kopierer valuta fra oppgitt liste og legger til i ArrayList
        Valuta[] valutaliste = {
                new Valuta("Euro", 8.10, 1), new Valuta("US Dollar", 6.23, 1),
                new Valuta("Britiske pund", 12.27, 1), new Valuta("Svenske kroner", 88.96, 100),
                new Valuta("Danske kroner", 108.75, 100), new Valuta("Yen", 5.14, 100),
                new Valuta("Islandske kroner", 9.16, 100), new Valuta("Norske kroner", 100, 100)
        };

        for(int i=0; i<valutaliste.length; i++){
            arrayList.add(valutaliste[i]);
        }

        // Oppsett design SCENE1
        listView = new ListView<>();
        listView2 = new ListView<>();
        Label label = new Label("Fra valuta");
        Label label2 = new Label("Til valuta");
        Button button = new Button("Send");
        Button leggTil = new Button("Legg til flere valuta");

        VBox v = new VBox();
        VBox v2 = new VBox();
        HBox h = new HBox();
        h.setAlignment(Pos.CENTER);
        VBox container = new VBox();
        container.setAlignment(Pos.CENTER);

        JFXTextField txt = new JFXTextField();
        txt.setPromptText("Skriv inn beløp");
        txt.setPadding(new Insets(5,0,0,160));
        HBox menu = new HBox();
        menu.setAlignment(Pos.CENTER);
        v.getChildren().addAll(label, listView);
        v2.getChildren().addAll(label2,listView2);

        //Legger til alle valutaer i listviewsene
        for(int i=0; i<valutaliste.length; i++){
            listView.getItems().addAll(arrayList.get(i).getValutanavn());
            listView2.getItems().addAll(arrayList.get(i).getValutanavn());
        }

        //legger til alle panes til i en samlecontainer
        h.getChildren().addAll(v,v2);
        menu.getChildren().addAll(button, leggTil, txt);
        container.getChildren().addAll(h, menu);

        //Oppsett design SCENE2
        VBox container2 = new VBox();
        container2.setAlignment(Pos.CENTER);
        JFXTextField valutaNavn = new JFXTextField();
        valutaNavn.setPromptText("Valuta navn");
        valutaNavn.setMaxWidth(350);

        JFXTextField kurs = new JFXTextField();
        kurs.setPromptText("Kurs");
        kurs.setMaxWidth(350);
        JFXTextField enhet = new JFXTextField();
        enhet.setPromptText("Enhet");
        enhet.setMaxWidth(350);
        Button goBack = new Button("Go back");
        Button submit = new Button("Submit");
        HBox knapper = new HBox();
        knapper.setAlignment(Pos.CENTER);
        knapper.setPadding(new Insets(30,0,0,0));

        VBox textfields = new VBox();
        textfields.setAlignment(Pos.CENTER);
        textfields.getChildren().addAll(valutaNavn,kurs,enhet);
        knapper.getChildren().addAll(submit, goBack);

        container2.getChildren().addAll(textfields, knapper);

        submit.setOnAction(e ->{
            String v_navn = valutaNavn.getText();
            String kursInn = kurs.getText();
            double kursInnTall = Double.parseDouble(kursInn);
            String enhetInn = enhet.getText();
            int enhetInnTall = Integer.parseInt(enhetInn);



            arrayList.add(new Valuta(v_navn,kursInnTall,enhetInnTall));
            for(int i=0; i<arrayList.size(); i++){
                if(v_navn.equals(arrayList.get(i).getValutanavn())){
                    listView.getItems().add(arrayList.get(i).getValutanavn());
                    listView2.getItems().add(arrayList.get(i).getValutanavn());
                }
            }


            valutaNavn.clear();
            kurs.clear();
            enhet.clear();
            window.setScene(scene);
            window.show();

        });


        //Send knappen blir trykket
        button.setOnAction(e ->{
            String fra = listView.getSelectionModel().getSelectedItem().toString();
            String til = listView2.getSelectionModel().getSelectedItem().toString();

            System.out.println(fra.trim());
            String inntastetTekst = txt.getText();
            double inntastet = Double.parseDouble(inntastetTekst);


            double fraKurs=0;
            double tilKurs=0;

            int enhetFra =0;
            int enhetTil =0;

            for(int i=0; i<arrayList.size(); i++){
                if(fra.trim().equalsIgnoreCase(arrayList.get(i).getValutanavn().trim())){
                    fraKurs = arrayList.get(i).getKurs();
                    enhetFra = arrayList.get(i).getEnhet();
                }
                if(til.trim().equalsIgnoreCase(arrayList.get(i).getValutanavn().trim())){
                    tilKurs = arrayList.get(i).getKurs();
                    enhetTil = arrayList.get(i).getEnhet();
                }
            }


            double resultatTall;
            if(enhetFra == enhetTil){
              resultatTall =  fraKurs/tilKurs*inntastet;
            } else if(fra.equalsIgnoreCase("Norske kroner")){
              resultatTall =  inntastet/tilKurs/enhetTil;
            } else{
                resultatTall = inntastet*fraKurs/enhetFra/tilKurs*enhetTil;
            }


            String resultat2d = String.format("%.2f",resultatTall);


            AlertBox.display(resultat2d, til);

        });

        //Legger til ny valuta.
        leggTil.setOnAction(e ->{
            window.setScene(scene2);
            window.show();
        });

        goBack.setOnAction(e -> {
            window.setScene(scene);
            window.show();
        });

        //Hovedoppsett til å begynne med. (ROOT scene)
            scene2 = new Scene(container2, 800, 600);
            scene = new Scene(container, 800, 600);
            window.setScene(scene);
            window.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

}
