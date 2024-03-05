package Entites;

import java.util.Date;

public class Evenement {

    private int id;
    private String nomEvent;
    private Date dateEvent;
    private int duree;
    private String lieu;
    private Categorie categorie ;
    private String QrCode ;
    public Evenement(int id, String nomEvent, String lieu, Date dateEvent, int duree , String QrCode) {
        this.id = id;
        this.nomEvent = nomEvent;
        this.lieu = lieu;
        this.dateEvent = dateEvent;
        this.duree = duree;
        this.categorie = categorie ;
        this.QrCode =QrCode ;
    }

    public Evenement(String nomEvent, String lieu, Date dateEvent, int duree, Categorie categorie,String QrCode) {
        this.nomEvent = nomEvent;
        this.lieu = lieu;
        this.dateEvent = dateEvent;
        this.duree = duree;
        this.categorie = categorie ;
        this.QrCode =QrCode ;
    }

    public String getQrCode() {
        return QrCode;
    }

    public void setQrCode(String qrCode) {
        QrCode = qrCode;
    }

    public Evenement() {
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public String getNomEvent() {
        return nomEvent;
    }

    public int getId() {
        return id;
    }

    public Date getDateEvent() {
        return dateEvent;
    }

    public int getDuree() {
        return duree;
    }

    public String getLieu() {
        return lieu;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNomEvent(String nomEvent) {
        this.nomEvent = nomEvent;
    }

    public void setDateEvent(Date dateEvent) {
        this.dateEvent = dateEvent;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    @Override
    public String toString() {
        return "Evenement{" +
                "id=" + id +
                ", nomEvent='" + nomEvent + '\'' +
                ", dateEvent=" + dateEvent +
                ", duree=" + duree +
                ", lieu='" + lieu + '\'' +
                ", categorie=" + categorie +
                '}';
    }
}
