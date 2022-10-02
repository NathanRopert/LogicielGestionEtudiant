package fr.sae201.gestiongroupesetudiant.models;

import fr.sae201.gestiongroupesetudiant.models.personnel.Personnel;

public class Secretaire extends Personnel {
    public Secretaire(int numHarpege, String nom, String prenom) {
        super(numHarpege, nom, prenom, "Secrétaire");
    }
}
