package fr.sae201.gestiongroupesetudiant.models;

import fr.sae201.gestiongroupesetudiant.models.personnel.Personnel;

public class Enseignant extends Personnel {
    public Enseignant(int numHarpege, String nom, String prenom) {
        super(numHarpege, nom, prenom, "Enseignant");
    }
}
