package fr.sae201.gestiongroupesetudiant.controllers;

import fr.sae201.gestiongroupesetudiant.models.etudiant.Etudiant;
import fr.sae201.gestiongroupesetudiant.models.etudiant.EtudiantDAO;
import fr.sae201.gestiongroupesetudiant.models.groupe.GroupeDAO;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ResourceBundle;

public class ModifierEtudiantController implements Initializable {
    public static Etudiant etudiantAModifier;
    private GroupeDAO groupeDAO;
    private String promoChoisie, tpChoisi;

    private TrombinoscopeController trombinoscopeController;

    @FXML
    private Label titreLabel;

    @FXML
    private TextField statutTextField, nomTextField, numTextField, prenomTextField, nomPhotoTextField, motDePasseTextField;
    @FXML
    private TextArea descAmTextField;

    @FXML
    private MenuButton promoMenu, groupeTpMenu;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        titreLabel.setText("Modifier l'étudiant - " + etudiantAModifier.getNom() + " " + etudiantAModifier.getPrenom());

        // Chargement des données de base
        numTextField.setText(String.valueOf(etudiantAModifier.getNumEtu()));
        nomTextField.setText(etudiantAModifier.getNom());
        prenomTextField.setText(etudiantAModifier.getPrenom());
        statutTextField.setText(etudiantAModifier.getStatut());
        nomPhotoTextField.setText(etudiantAModifier.getUrlPhoto());
        promoMenu.setText(etudiantAModifier.getPromo());
        descAmTextField.setText(etudiantAModifier.getDescAm());
        groupeTpMenu.setText(etudiantAModifier.getTpGroupe());

        groupeDAO = new GroupeDAO();
        groupeDAO.chargerGroupes();

        this.promoChoisie = "BUT1";
        this.tpChoisi = groupeDAO.getTpGroupes().get(0).getNom();

        // Menu promos
        promoMenu.getItems().addAll(
                new MenuItem("BUT1"),
                new MenuItem("BUT2"),
                new MenuItem("BUT3"),
                new MenuItem("DUT")
        );
        promoMenu.getItems().forEach(menuButton -> {
            menuButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    String selected = ((MenuItem) event.getSource()).getText();

                    promoMenu.setText(selected);
                    promoChoisie = selected;
                }
            });
        });

        // Menu TP
        groupeDAO.getTpGroupes().forEach((tp) -> {
            groupeTpMenu.getItems().add(new MenuItem(tp.getNom()));
        });
        groupeTpMenu.getItems().forEach(menuButton -> {
            menuButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    String selected = ((MenuItem) event.getSource()).getText();

                    groupeTpMenu.setText(selected);
                    tpChoisi = selected;
                }
            });
        });
    }

    @FXML
    void modifierEtudiantOnClick(ActionEvent event) {
        // Modification de l'étudiant
        EtudiantDAO etudiantDAO = new EtudiantDAO();
        etudiantDAO.updateEtudiant(
                etudiantAModifier.getNumEtu(),
                nomTextField.getText(),
                prenomTextField.getText(),
                statutTextField.getText(),
                descAmTextField.getText(),
                nomPhotoTextField.getText(),
                promoChoisie,
                groupeDAO.getId(tpChoisi),
                motDePasseTextField.getText());

        ((Node) event.getSource()).getScene().getWindow().hide();

        if (trombinoscopeController != null)
            trombinoscopeController.rechargerDonneesOnClick();
    }

    public void setTrombiController(TrombinoscopeController trombinoscopeController) {
        this.trombinoscopeController = trombinoscopeController;
    }

    /*@FXML
    void choisirImageOnClick(ActionEvent e) throws IOException {
        String lienFichier = "";

        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(stage);

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPG File", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG File", "*.png"));

        if(selectedFile != null){
            lienFichier = String.valueOf(new File(selectedFile.getAbsolutePath()));
            nomFichier = selectedFile.getName();
            System.out.println(nomFichier);
        }
        else return;

        Path copied = Paths.get("src/main/resources/fr/sae201/gestiongroupesetudiant/images/" + nomFichier);
        File original = new File(lienFichier);
        Path originalPath = original.toPath();
        Files.copy(originalPath, copied, StandardCopyOption.COPY_ATTRIBUTES);
    }*/
}

