package fr.sae201.gestiongroupesetudiant.models.etudiant;

import fr.sae201.gestiongroupesetudiant.models.Humain;

public class Etudiant extends Humain {
    private int numEtu, age, tdGroupe;
    private String dateN, statut, promo, tpGroupe, urlPhoto, descAm;

    public Etudiant(int numEtu, String nom, String prenom, int age, String dateN, String statut, String promo, String tpGroupe, int tdGroupe, String urlPhoto, String descAm) {
        super(nom, prenom);

        this.numEtu = numEtu;
        this.age = age;
        this.dateN = dateN;
        this.statut = statut;
        this.promo = promo;
        this.tpGroupe = tpGroupe;
        this.tdGroupe = tdGroupe;
        this.urlPhoto = urlPhoto;
        this.descAm = descAm;
    }

    public int getNumEtu() {
        return numEtu;
    }

    public int getAge() {
        return age;
    }

    public int getTdGroupe() {
        return tdGroupe;
    }

    public String getDateN() {
        return dateN;
    }

    public String getStatut() {
        return statut;
    }

    public String getPromo() {
        return promo;
    }

    public String getTpGroupe() {
        return tpGroupe;
    }

    public String getUrlPhoto() {
        return urlPhoto;
    }

    public String getDescAm() {
        return descAm;
    }
}


