package fr.sae201.gestiongroupesetudiant.controllers;

import fr.sae201.gestiongroupesetudiant.HelloApplication;
import fr.sae201.gestiongroupesetudiant.models.etudiant.Etudiant;
import fr.sae201.gestiongroupesetudiant.models.etudiant.EtudiantDAO;
import fr.sae201.gestiongroupesetudiant.models.groupe.GroupeDAO;
import fr.sae201.gestiongroupesetudiant.models.Secretaire;
import fr.sae201.gestiongroupesetudiant.models.groupe.GroupeType;
import fr.sae201.gestiongroupesetudiant.utils.Utils;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GroupesController implements Initializable {
    EtudiantDAO etudiantDAO;

    private int numEtuChoisi;

    @FXML
    public ScrollPane scrollPane;

    @FXML
    public Button modifierBtn, ajouterGroupeTdBtn, ajouterGroupeTpBtn;

    @FXML
    public Label erreurLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        etudiantDAO = new EtudiantDAO();
        etudiantDAO.chargerEtudiants();

        if (MesInformationsController.humainActuel instanceof Secretaire) {
            modifierBtn.setVisible(true);
            ajouterGroupeTdBtn.setVisible(true);
            ajouterGroupeTpBtn.setVisible(true);
        }

        rechargerVue();
    }

    public void rechargerVue() {
        // Rechargement de la vue
        GroupeDAO groupeDAO = new GroupeDAO();
        groupeDAO.chargerGroupes();

        VBox list = new VBox();

        groupeDAO.getTdGroupes().forEach((tdGroupe) -> {
            VBox td = new VBox();
            Label tdLabel = new Label(tdGroupe.getNom());
            tdLabel.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
            td.getChildren().add(tdLabel);

            HBox listTp = new HBox();
            groupeDAO.getTpParTd(tdGroupe.getNom().substring(2)).forEach((tpGroupe) -> {
                VBox group = new VBox();
                ListView etudiants = new ListView();

                etudiants.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
                    @Override
                    public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                        modifierBtn.setDisable(false);
                        numEtuChoisi = Integer.parseInt(etudiants.getSelectionModel().getSelectedItem().toString().split(" - ")[1]);
                    }
                });

                etudiantDAO.getEtudiantsParGroupe(tpGroupe.getNom()).forEach((etu) -> {
                    etudiants.getItems().add(etu.getNom() + " " + etu.getPrenom() + " - " + etu.getNumEtu());
                });
                etudiants.setPrefWidth(320);
                etudiants.setMaxHeight(25 * etudiants.getItems().size());

                // Bouton ajouter étudiant à un groupe
                Button ajouterEtudiantsBtn = new Button("Ajouter des étudiants");
                ajouterEtudiantsBtn.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        try {
                            chargerFenetreAjouterEtudiant(tpGroupe.getNom());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });

                // Bouton modifier groupe
                Button modifierGroupeBtn = new Button("Modifier le groupe");
                modifierGroupeBtn.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        try {
                            chargerFenetreModifierEtudiant(tpGroupe.getNom());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });

                // Bouton supprimer groupe
                Button supprimerGroupeBtn = new Button("Supprimer le groupe");
                supprimerGroupeBtn.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        try {
                            if (etudiantDAO.getEtudiantsParGroupe(tpGroupe.getNom()).size() == 0) {
                                chargerFenetreConfirmer(tpGroupe.getNom());
                            } else {
                                erreurLabel.setVisible(true);
                            }
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });

                // Ajout des boutons dans la vue
                group.getChildren().addAll(
                        new Label(tpGroupe.getNom()),
                        etudiants);
                if (MesInformationsController.humainActuel instanceof Secretaire) {
                    group.getChildren().addAll(ajouterEtudiantsBtn, modifierGroupeBtn, supprimerGroupeBtn);
                }
                group.setSpacing(10);
                group.setPadding(new Insets(5));

                listTp.getChildren().add(group);
            });
            td.getChildren().add(listTp);
            list.getChildren().add(td);
        });

        list.setSpacing(10);
        list.setPadding(new Insets(10));

        scrollPane.setContent(list);
    }

    @FXML
    void modifierOnClick(ActionEvent e) throws IOException {
        // Ouverture de la page de modification d'étudiant
        ModifierEtudiantController.etudiantAModifier = etudiantDAO.getEtudiant(numEtuChoisi);
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("ModifierEtudiantView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle(ModifierEtudiantController.etudiantAModifier.getNom() + " " + ModifierEtudiantController.etudiantAModifier.getPrenom());
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void groupesOnClick(ActionEvent event) {
        return;
    }

    @FXML
    void infosOnClick(ActionEvent event) throws IOException {
        Utils.chargerMesInformations(event);
    }

    @FXML
    void listeEtuOnClick(ActionEvent event) throws IOException {
        Utils.chargerListeEtudiants(event);
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
    void ajouterGroupeTdOnClick(ActionEvent e) throws IOException {
        // Ouverture page ajout TD
        AjoutGroupeController.groupeTypeACreer = GroupeType.TD;
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("AjoutGroupeView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("Créer un nouveau groupe TD");
        stage.setScene(scene);
        stage.show();

        ((AjoutGroupeController) fxmlLoader.getController()).setController(this);
    }

    @FXML
    void ajouterGroupeTpOnClick(ActionEvent e) throws IOException {
        // Ouverture page ajout TP
        AjoutGroupeController.groupeTypeACreer = GroupeType.TP;
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("AjoutGroupeView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setResizable(false);
        stage.setTitle("Créer un nouveau groupe TP");
        stage.setScene(scene);
        stage.show();

        ((AjoutGroupeController) fxmlLoader.getController()).setController(this);
    }

    private void chargerFenetreConfirmer(String nomGroupe) throws IOException {
        // Ouverture page de confirmation lors de la suppression d'un groupe
        ConfirmerSuppressionController.groupeASupprimer = nomGroupe;
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("ConfirmerSuppressionView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setResizable(false);
        stage.setTitle("Supprimer le groupe");
        stage.setScene(scene);
        stage.show();

        ((ConfirmerSuppressionController) fxmlLoader.getController()).setController(this);
    }

    private void chargerFenetreAjouterEtudiant(String nomGroupe) throws IOException {
        // Ouverture page ajout étudiant
        AjoutEtudiantDansGroupeController.groupeAAjouter = nomGroupe;
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("AjoutEtudiantDansGroupeView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setResizable(false);
        stage.setTitle("Créer un nouveau groupe TD");
        stage.setScene(scene);
        stage.show();

        ((AjoutEtudiantDansGroupeController) fxmlLoader.getController()).setController(this);
    }

    private void chargerFenetreModifierEtudiant(String nomGroupe) throws IOException {
        // Ouverture page modifier étudiant
        ModifierGroupeController.groupeAModifier = nomGroupe;
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("ModifierGroupeView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setResizable(false);
        stage.setTitle("Modifier le groupe");
        stage.setScene(scene);
        stage.show();

        ((ModifierGroupeController) fxmlLoader.getController()).setController(this);
    }
}
