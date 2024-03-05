package Entites;

import java.util.Date;

public class Categorie {

    private int id;
    private String equipeGagnante; // Correction du nom de la variable
    private int montantCategorie;
    private Date dateCategorie;

    // Constructeur avec les quatre champs
    public Categorie(int id, String equipeGagnante, int montantCategorie, Date dateCategorie) {
        this.id = id;
        this.equipeGagnante = equipeGagnante;
        this.montantCategorie = montantCategorie;
        this.dateCategorie = dateCategorie;
    }

    // Constructeur avec trois champs (pour utilisation dans Service)
    public Categorie(String equipeGagnante, int montantCategorie, Date dateCategorie) {
        this.equipeGagnante = equipeGagnante;
        this.montantCategorie = montantCategorie;
        this.dateCategorie = dateCategorie;
    }

    public Categorie() {

    }


    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEquipeGagnante() {
        return equipeGagnante;
    }

    public void setEquipeGagnante(String equipeGagnante) {
        this.equipeGagnante = equipeGagnante;
    }

    public int getMontantCategorie() {
        return montantCategorie;
    }

    public void setMontantCategorie(int montantCategorie) {
        this.montantCategorie = montantCategorie;
    }

    public Date getDateCategorie() {
        return dateCategorie;
    }

    public void setDateCategorie(Date dateCategorie) {
        this.dateCategorie = dateCategorie;
    }

    @Override
    public String toString() {
        return equipeGagnante ;
    }
}
