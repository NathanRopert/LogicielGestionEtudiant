package fr.sae201.gestiongroupesetudiant.controllers;

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
import javafx.util.StringConverter;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class AjoutEtudiantController implements Initializable {
    private GroupeDAO groupeDAO;
    private String promoChoisie, tpChoisi;
    private ListeEtudiantController listeEtudiantController;
    private String nomFichier = "";

    @FXML
    private DatePicker dateNTextField;

    @FXML
    private TextField statutTextField, nomTextField, numTextField, prenomTextField, mdpTextField, nomPhotoTextField;

    @FXML
    private TextArea descAmTextField;

    @FXML
    private MenuButton promoMenu, groupeMenu;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
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
            groupeMenu.getItems().add(new MenuItem(tp.getNom()));
        });
        groupeMenu.getItems().forEach(menuButton -> {
            menuButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    String selected = ((MenuItem) event.getSource()).getText();

                    groupeMenu.setText(selected);
                    tpChoisi = selected;
                }
            });
        });

        // Converter pour la date
        StringConverter<LocalDate> converter = new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter =
                    DateTimeFormatter.ofPattern("dd-MM-yyyy");

            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }
            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        };
        dateNTextField.setConverter(converter);
        dateNTextField.setPromptText("dd-MM-yyyy");
    }

    @FXML
    void creerEtudiantOnClick(ActionEvent e) {
        // Ajout d'un nouvel étudiant dans la BDD
        EtudiantDAO etudiantDAO = new EtudiantDAO();
        etudiantDAO.insertEtudiant(
                Integer.parseInt(numTextField.getText()),
                nomTextField.getText(),
                prenomTextField.getText(),
                dateNTextField.getValue(),
                statutTextField.getText(),
                descAmTextField.getText(),
                nomPhotoTextField.getText(),
                promoChoisie,
                groupeDAO.getId(tpChoisi),
                mdpTextField.getText());

        // Fermeture de la fenêtre
        ((Node) e.getSource()).getScene().getWindow().hide();

        listeEtudiantController.rechargerDonneesOnClick();

        System.out.println("Étudiant ajouté avec succès !");
    }

    public void setController(ListeEtudiantController listeEtudiantController) {
        this.listeEtudiantController = listeEtudiantController;
    }

    /*@FXML
    void choisirImageOnClick(ActionEvent e) throws IOException {
        Stage stage = new Stage();

        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(stage);
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPG File", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG File", "*.png")
        );

        nomFichier = selectedFile.getName();
        Path copied = Paths.get("src/main/resources/fr/sae201/gestionsgroupesetudiant/images/" + nomFichier);
        File original = new File(String.valueOf(new File(selectedFile.getAbsolutePath())));
        Files.copy(original.toPath(), copied, StandardCopyOption.COPY_ATTRIBUTES);

        System.out.println(selectedFile.getName());
    }*/
}
