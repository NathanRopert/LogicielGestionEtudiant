package fr.sae201.gestiongroupesetudiant.utils;

import fr.sae201.gestiongroupesetudiant.HelloApplication;
import fr.sae201.gestiongroupesetudiant.controllers.ListeEtudiantController;
import fr.sae201.gestiongroupesetudiant.controllers.MesInformationsController;
import fr.sae201.gestiongroupesetudiant.controllers.ModifierEtudiantController;
import fr.sae201.gestiongroupesetudiant.models.etudiant.Etudiant;
import fr.sae201.gestiongroupesetudiant.models.Secretaire;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;

import java.io.IOException;

public class EtudiantTableRow {
    private boolean isShortRow = false;
    private CheckBox select;
    private Etudiant etudiant;
    private Button button;

    public EtudiantTableRow(Etudiant etudiant) {
        this.etudiant = etudiant;

        if (isShortRow) this.select = new CheckBox();
        if (MesInformationsController.humainActuel instanceof Secretaire) {
            this.select = new CheckBox();

            this.button = new Button("Modifier");
            this.button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    try {
                        ouvrirModifierEtudiantFenetre();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        }
    }

    public void createCheckBox() {
        this.select = new CheckBox();
    }

    public CheckBox getSelect() {
        return this.select;
    }
    public boolean isSelected() { return this.select.isSelected(); }

    public int getNumEtu() { return etudiant.getNumEtu(); }
    public String getNom() {
        return etudiant.getNom();
    }
    public String getPrenom() {
        return etudiant.getPrenom();
    }
    public int getAge() { return etudiant.getAge(); }
    public String getDateN() { return etudiant.getDateN(); }
    public String getStatut() { return etudiant.getStatut(); }
    public String getPromo() { return etudiant.getPromo(); }
    public String getTpGroupe() { return etudiant.getTpGroupe(); }
    public int getTdGroupe() { return etudiant.getTdGroupe(); }

    public Button getButton() {
        return this.button;
    }

    private void ouvrirModifierEtudiantFenetre() throws IOException {
        ModifierEtudiantController.etudiantAModifier = this.etudiant;
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("ModifierEtudiantView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle(this.getNom() + " " + this.getPrenom());
        stage.setScene(scene);
        stage.show();
    }
}
