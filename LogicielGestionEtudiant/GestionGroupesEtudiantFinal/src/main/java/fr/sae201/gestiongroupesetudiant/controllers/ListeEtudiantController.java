package fr.sae201.gestiongroupesetudiant.controllers;

import fr.sae201.gestiongroupesetudiant.HelloApplication;
import fr.sae201.gestiongroupesetudiant.models.Enseignant;
import fr.sae201.gestiongroupesetudiant.models.Humain;
import fr.sae201.gestiongroupesetudiant.models.Secretaire;
import fr.sae201.gestiongroupesetudiant.models.etudiant.Etudiant;
import fr.sae201.gestiongroupesetudiant.models.etudiant.EtudiantDAO;
import fr.sae201.gestiongroupesetudiant.models.groupe.GroupeDAO;
import fr.sae201.gestiongroupesetudiant.utils.EtudiantTableRow;
import fr.sae201.gestiongroupesetudiant.utils.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ListeEtudiantController implements Initializable {
    private ObservableList<EtudiantTableRow> listeEtudiants = FXCollections.observableArrayList(); // Liste d'étudiants qui sera affichée

    private EtudiantDAO etudiantDAO;
    private GroupeDAO groupeDAO;

    private String statutChoisi = "Tous statuts",
                   promoChoisie = "Toutes promos",
                   tdChoisi = "Tous TD",
                   tpChoisi = "Tous TP";

    @FXML
    private TableView<EtudiantTableRow> etudiantsTable;
    @FXML
    private TableColumn<Etudiant, String> selectCol, nomCol, prenomCol, ageCol, dateNCol, statutCol, promoCol, tpCol, infosCol;
    @FXML
    private TableColumn<Etudiant, Integer> numeroCol, tdCol;

    @FXML
    private MenuButton statutMenu, promoMenu, tdMenu, tpMenu;

    @FXML
    private TextField rechercherTextField;

    @FXML
    private Button supprimerBtn, ajouterEtuBtn, rechercherBtn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (MesInformationsController.humainActuel instanceof Etudiant) {
            promoMenu.setVisible(false);
            statutMenu.setVisible(false);
            supprimerBtn.setVisible(false);
            ajouterEtuBtn.setVisible(false);
            numeroCol.setVisible(false);
            dateNCol.setVisible(false);
            statutCol.setVisible(false);
        } else {
            rechercherBtn.setVisible(true);
            rechercherTextField.setVisible(true);
        }

        if (MesInformationsController.humainActuel instanceof Secretaire) {
            ajouterEtuBtn.setVisible(true);
            supprimerBtn.setVisible(true);
        }

        // Configuration du tableau d'étudiants
        selectCol.setCellValueFactory(new PropertyValueFactory<Etudiant, String>("select"));
        numeroCol.setCellValueFactory(new PropertyValueFactory<Etudiant, Integer>("numEtu"));
        nomCol.setCellValueFactory(new PropertyValueFactory<Etudiant, String>("nom"));
        prenomCol.setCellValueFactory(new PropertyValueFactory<Etudiant, String>("prenom"));
        ageCol.setCellValueFactory(new PropertyValueFactory<Etudiant, String>("age"));
        dateNCol.setCellValueFactory(new PropertyValueFactory<Etudiant, String>("dateN"));
        statutCol.setCellValueFactory(new PropertyValueFactory<Etudiant, String>("statut"));
        promoCol.setCellValueFactory(new PropertyValueFactory<Etudiant, String>("promo"));
        tpCol.setCellValueFactory(new PropertyValueFactory<Etudiant, String>("tpGroupe"));
        tdCol.setCellValueFactory(new PropertyValueFactory<Etudiant, Integer>("tdGroupe"));
        infosCol.setCellValueFactory(new PropertyValueFactory<Etudiant, String>("button"));

        selectCol.prefWidthProperty().bind(etudiantsTable.widthProperty().multiply(0.04));
        selectCol.setResizable(false);
        infosCol.prefWidthProperty().bind(etudiantsTable.widthProperty().multiply(0.5));


        // Récupère tous les étudiants de la BDD
        etudiantDAO = new EtudiantDAO();
        etudiantDAO.chargerEtudiants();

        // Récupère tous les groupes de la BDD
        groupeDAO = new GroupeDAO();
        groupeDAO.chargerGroupes();

        rechargerDonneesOnClick();
        etudiantsTable.setItems(this.listeEtudiants);


        // Menu statuts
        statutMenu.getItems().addAll(
                new MenuItem("Tous statuts"),
                new MenuItem("RAS"),
                new MenuItem("À surveiller"),
                new MenuItem("Défaillant")
        );
        statutMenu.getItems().forEach(menuButton -> {
            menuButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    String selected = ((MenuItem) event.getSource()).getText();

                    statutMenu.setText(selected);
                    statutChoisi = selected;
                }
            });
        });

        // Menu promos
        promoMenu.getItems().addAll(
                new MenuItem("Toutes promos"),
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

        // Menu TD
        tdMenu.getItems().add(new MenuItem("Tous TD"));
        groupeDAO.getTdGroupes().forEach((td) -> {
            tdMenu.getItems().add(new MenuItem(td.getNom()));
        });
        tdMenu.getItems().forEach(menuButton -> {
            menuButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    String selected = ((MenuItem) event.getSource()).getText();

                    tdMenu.setText(selected);
                    tdChoisi = selected;
                }
            });
        });

        // Menu TP
        tpMenu.getItems().add(new MenuItem("Tous TP"));
        groupeDAO.getTpGroupes().forEach((tp) -> {
            tpMenu.getItems().add(new MenuItem(tp.getNom()));
        });
        tpMenu.getItems().forEach(menuButton -> {
            menuButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    String selected = ((MenuItem) event.getSource()).getText();

                    tpMenu.setText(selected);
                    tpChoisi = selected;
                }
            });
        });

    }

    @FXML
    void supprimerOnClick() {
        // Supprimer étudiants séléctionnés
        for (EtudiantTableRow etu : listeEtudiants) {
            if (etu.isSelected()) etudiantDAO.supprimerEtudiant(etu.getNumEtu());
        }
        listeEtudiants.removeIf((etu) -> etu.isSelected());
    }

    @FXML
    void filtrerOnClick() {
        // Filtrer les étudiants dans la liste
        if (statutChoisi != "Tous statuts") this.listeEtudiants.removeIf((etu) -> !etu.getStatut().equalsIgnoreCase(statutChoisi));
        if (promoChoisie != "Toutes promos") this.listeEtudiants.removeIf((etu) -> !etu.getPromo().equals(promoChoisie));
        if (tdChoisi != "Tous TD") this.listeEtudiants.removeIf((etu) -> etu.getTdGroupe() != Integer.parseInt(tdChoisi.substring(2)));
        if (tpChoisi != "Tous TP") this.listeEtudiants.removeIf((etu) -> !etu.getTpGroupe().equals(tpChoisi));
    }

    @FXML
    void rechargerDonneesOnClick() {
        // Recharger la vue
        listeEtudiants.clear();
        etudiantDAO.chargerEtudiants();

        statutMenu.setText("Tous statuts");
        promoMenu.setText("Toutes promos");
        tdMenu.setText("Tous TD");
        tpMenu.setText("Tous TP");

        if (MesInformationsController.humainActuel instanceof Etudiant) {
            etudiantDAO.getEtudiantsParPromo(((Etudiant)MesInformationsController.humainActuel).getPromo()).
            forEach((etudiant) -> {
                listeEtudiants.add(new EtudiantTableRow(etudiant));
            });
        } else {
            etudiantDAO.getEtudiants().forEach((etudiant) -> {
                listeEtudiants.add(new EtudiantTableRow(etudiant));
            });
        }
    }

    @FXML
    void ajouterEtuOnClick(ActionEvent e) throws IOException {
        // Ouverture page ajouter étudiant
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("AjoutEtudiantView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setResizable(false);
        stage.setTitle("Ajouter un etudiant");
        stage.setScene(scene);
        stage.show();

        ((AjoutEtudiantController) fxmlLoader.getController()).setController(this);
    }

    @FXML
    void listeEtuOnClick(ActionEvent e) {
        return;
    }

    @FXML
    void groupesOnClick(ActionEvent e) throws IOException {
        Utils.chargerGroupes(e);
    }

    @FXML
    void infosOnClick(ActionEvent e) throws IOException {
        Utils.chargerMesInformations(e);
    }

    @FXML
    void trombiOnClick(ActionEvent e) throws IOException {
        Utils.chargerTrombinoscope(e);
    }

    @FXML
    void disconnectOnClick(ActionEvent e) throws IOException {
        Utils.disconnect(e);
    }

    @FXML
    void rechercherOnClick(ActionEvent e) {
        // Recherche d'un/des étudiant et affichage
        listeEtudiants.clear();
        for (Etudiant etudiant : etudiantDAO.getEtudiantsParRecherche(rechercherTextField.getText())) {
            listeEtudiants.add(new EtudiantTableRow(etudiant));
        }
    }
}