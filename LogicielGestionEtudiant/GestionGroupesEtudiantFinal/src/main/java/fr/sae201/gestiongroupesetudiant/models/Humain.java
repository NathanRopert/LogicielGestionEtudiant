package fr.sae201.gestiongroupesetudiant.models;

public class Humain {
    private String nom, prenom;

    public Humain(String nom, String prenom) {
        this.nom = nom;
        this.prenom = prenom;
    }

    public String getNom() {
        return this.nom;
    }

    public String getPrenom() {
        return this.prenom;
    }
}
