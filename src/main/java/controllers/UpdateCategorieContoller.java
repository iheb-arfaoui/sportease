package controllers;
//import Service.Service;
import Service.ServiceR;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import Entites.Categorie;
import java.time.LocalDate;
import java.sql.Date;
import java.sql.SQLException;


public class UpdateCategorieContoller {






        @FXML
        private Button Delete_button;

        @FXML
        private DatePicker txtdateCategorie;

        @FXML
        private TextField txtequipeGangnte;

        @FXML
        private TextField txtmonatantCategorie;

        @FXML
        private Button update_button;

        @FXML
       void deleteR(ActionEvent event) throws SQLException {

            ServiceR cntrl = new ServiceR() ;
            cntrl.delete(getid());
            ((Stage)txtequipeGangnte.getScene().getWindow()).close();

            }


            @FXML

            void updateR(ActionEvent event) throws SQLException {
                String equipeGagnante = txtequipeGangnte.getText();
                int montantCategorie = Integer.parseInt(txtmonatantCategorie.getText());
                LocalDate selectedDate = txtdateCategorie.getValue();

                // Vérification que la date est sélectionnée
                if (selectedDate != null) {
                    Date dateCategorie = Date.valueOf(selectedDate);

                    ServiceR ser = new ServiceR();
                    Categorie categorie = new Categorie(equipeGagnante, montantCategorie, dateCategorie);
                    ser.update(categorie, getid()); // Passer l'id en paramètre
                }
            }

    private int id;
    private int getid() {
            return id;
    }

    private void setid(int id) {
        this.id=id;


    }
    public void setEvent(Categorie e) {
        if (e != null && e.getDateCategorie() != null) {
            setid(e.getId());
            System.out.println(getid());
            txtequipeGangnte.setText(e.getEquipeGagnante());
            txtmonatantCategorie.setText(String.valueOf(e.getMontantCategorie()));
            // Conversion de java.sql.Date en LocalDate
            Date dateEvent = (Date) e.getDateCategorie();
            LocalDate parsedDate = dateEvent.toLocalDate();
            txtdateCategorie.setValue(parsedDate);
        } else {
            // Gérer le cas où l'objet Arbitre ou la date d'embauche est nulle
        }
    }}

