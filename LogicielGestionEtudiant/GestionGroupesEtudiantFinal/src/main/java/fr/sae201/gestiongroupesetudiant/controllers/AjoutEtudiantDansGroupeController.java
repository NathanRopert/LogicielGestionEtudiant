package fr.sae201.gestiongroupesetudiant.controllers;

import fr.sae201.gestiongroupesetudiant.models.etudiant.Etudiant;
import fr.sae201.gestiongroupesetudiant.models.etudiant.EtudiantDAO;
import fr.sae201.gestiongroupesetudiant.utils.EtudiantTableRow;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class AjoutEtudiantDansGroupeController implements Initializable {
    private GroupesController groupesController;
    private ObservableList<EtudiantTableRow> listeEtudiants = FXCollections.observableArrayList();
    private EtudiantDAO etudiantDAO;
    public static String groupeAAjouter;

    @FXML
    public TableView etudiantsTable;

    @FXML
    public TableColumn<Etudiant, String> selectCol, nomCol, prenomCol, tdCol, tpCol;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Configuration du tableau
        selectCol.setCellValueFactory(new PropertyValueFactory<Etudiant, String>("select"));
        nomCol.setCellValueFactory(new PropertyValueFactory<Etudiant, String>("nom"));
        prenomCol.setCellValueFactory(new PropertyValueFactory<Etudiant, String>("prenom"));
        tdCol.setCellValueFactory(new PropertyValueFactory<Etudiant, String>("tdGroupe"));
        tpCol.setCellValueFactory(new PropertyValueFactory<Etudiant, String>("tpGroupe"));

        selectCol.prefWidthProperty().bind(etudiantsTable.widthProperty().multiply(0.04));
        selectCol.setResizable(false);

        // Récupère tous les étudiants de la BDD
        etudiantDAO = new EtudiantDAO();
        etudiantDAO.chargerEtudiants();

        // Pour chaque étudiant, on l'ajoute
        for (Etudiant etudiant : etudiantDAO.getEtudiants()) {
            EtudiantTableRow etudiantTableRow = new EtudiantTableRow(etudiant);
            etudiantTableRow.createCheckBox();
            listeEtudiants.add(etudiantTableRow);
        }

        etudiantsTable.setItems(listeEtudiants);
    }

    @FXML
    void ajouterEtudiantsOnClick(ActionEvent e) {
        // Ajout des étudiants séléctionnés dans le groupe
        listeEtudiants.removeIf((etu) -> !etu.isSelected());
        etudiantDAO.ajouterEtudiantsDansGroupe(listeEtudiants, groupeAAjouter);

        ((Node) e.getSource()).getScene().getWindow().hide();
        groupesController.rechargerVue();
    }

    public void setController(GroupesController groupesController) {
        this.groupesController = groupesController;
    }
}
