package controllers;

import Service.ServiceR;
import Utils.DataSource;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import Entites.Categorie;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ListCategorieController implements Initializable {

    @FXML
    private TableColumn<Categorie, Date> dateCategorie_col;

    @FXML
    private TableColumn<Categorie, String> equipeGagnante_col;

    @FXML
    private TableView<Categorie> events_table;

    @FXML
    private TableColumn<?, ?> id_col;

    @FXML
    private TextField keywordTextFld;

    @FXML
    private TableColumn<Categorie, Integer> montantCategorie_col;


    @FXML
    private Button print_btn;

    @FXML
    private Button showEvents;

    @FXML
    private Button Add_btn;

    public ListCategorieController() throws SQLException {
    }



    Connection mc;
    PreparedStatement ste;

    private final ServiceR ser = new ServiceR();
    public ObservableList<Categorie> getEvents() throws SQLException {

        ObservableList<Categorie> categories_list = FXCollections.observableArrayList();
        mc = DataSource.getInstance().getCon();
        String sql = "SELECT * from categorie";
        try {
            ste = mc.prepareStatement(sql);
            ResultSet rs = ste.executeQuery();
            System.out.println(rs.toString());
            while (rs.next()) {
                categories_list.add(new Categorie(rs.getInt("id"),  rs.getString("equipeGagnante"),  rs.getInt("montantCategorie"), rs.getDate("dateCategorie")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories_list;
    }





    @FXML
    public void showEvents() throws SQLException {
        ObservableList<Categorie> listM = getEvents();
        id_col.setCellValueFactory(new PropertyValueFactory<>("id"));
        equipeGagnante_col.setCellValueFactory(new PropertyValueFactory<>("equipeGagnante")); // Vérifiez le nom de la propriété
        montantCategorie_col.setCellValueFactory(new PropertyValueFactory<>("montantCategorie"));
        dateCategorie_col.setCellValueFactory(new PropertyValueFactory<>("dateCategorie"));
        events_table.setItems(listM);
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            showEvents();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Définir le gestionnaire d'événements pour la table
        events_table.setOnMouseClicked(event -> {
            if (event.getClickCount() > 0) {
                Categorie selectedEvent = onEdit();
                setEventStorage(selectedEvent);
                getUpdateView(event);
            }
        });

        // Définir le gestionnaire d'événements pour le bouton "Add"
        Add_btn.setOnAction(event -> getAddView(event));
    }


    public Categorie onEdit() {
        // check the table's selected item and get selected item
        if (events_table.getSelectionModel().getSelectedItem() != null) {
            Categorie selectedEvent = (Categorie) events_table.getSelectionModel().getSelectedItem();

            return selectedEvent;

        }

        return new Categorie();
    }
    Categorie event = null;
    public void setEventStorage(Categorie e){
        event= e;

    }
    public Categorie getEventStorage(){
        return event;
    }




    @FXML
    private void getUpdateView(MouseEvent event) {
        try {

            System.out.println(event.getSource().getClass());
            //Parent parent_two = FXMLLoader.load(getClass().getResource("/tableView/UpdateEventView.fxml"));
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UpdateCategorie.fxml"));
            Parent root = loader.load();
            UpdateCategorieContoller controller = loader.getController();
            controller.setEvent(getEventStorage());
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    @FXML
    private void getAddView(ActionEvent event) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/ajouterCategorie.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }



    public void searchbynom(ActionEvent actionEvent) {
        String keyword = keywordTextFld.getText(); // Récupérer le texte de recherche
        ObservableList<Categorie> filteredlist = getFilteredCategorie(keyword);
        events_table.setItems(filteredlist); // Mettre à jour le contenu de la table avec la liste filtrée
    }

    private ObservableList<Categorie> getFilteredCategorie(String keyword) {
        ObservableList<Categorie> filteredList = FXCollections.observableArrayList();
        mc = DataSource.getInstance().getCon();
        String sql = "SELECT * FROM Categorie WHERE equipeGagnante LIKE ?";
        try {
            ste = mc.prepareStatement(sql);
            ste.setString(1, "%" + keyword.toLowerCase() + "%"); // Assurez-vous que la requête est insensible à la casse
            ResultSet rs = ste.executeQuery();
            while (rs.next()) {
                Categorie categorie = new Categorie(rs.getInt("id"), rs.getString("equipeGagnante"), rs.getInt("montantCategorie"), rs.getDate("dateCategorie"));
                filteredList.add(categorie);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return filteredList;
    }


    public void getpdf(ActionEvent actionEvent) {

        String filename = "categorie_list.pdf";

        try {
            PdfWriter writer = new PdfWriter(filename);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            document.add(new Paragraph("Liste des categories"));

            ObservableList<Categorie> allcategorie = events_table.getItems();

            // Créer un tableau avec 3 colonnes
            float[] columnWidths = {1, 2, 3,4,}; // largeur relative des colonnes
            Table table = new Table(columnWidths);
            table.addCell(new Cell().add(new Paragraph("ID")));
            table.addCell(new Cell().add(new Paragraph("equipeGagnante")));
            table.addCell(new Cell().add(new Paragraph("montantCategorie")));
            table.addCell(new Cell().add(new Paragraph("dateCategorie")));


            // Ajouter les données des équipements dans le tableau
            for (Categorie categorie : allcategorie) {
                table.addCell(new Cell().add(new Paragraph(String.valueOf(categorie.getId()))));
                table.addCell(new Cell().add(new Paragraph(categorie.getEquipeGagnante())));
                table.addCell(new Cell().add(new Paragraph(String.valueOf(categorie.getMontantCategorie()))));
                table.addCell(new Cell().add(new Paragraph(String.valueOf(categorie.getDateCategorie()))));

            }

            // Ajouter le tableau au document
            document.add(table);

            // Fermer le document
            document.close();

            System.out.println("PDF créé avec succès.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void pdf(MouseEvent mouseEvent) {
    }


    @FXML
    void retour(ActionEvent event) throws IOException{
        Parent view3= FXMLLoader.load(getClass().getResource("/Admin.fxml"));
        Scene scene3=new Scene(view3);
        Stage window =(Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene3);
        window.show();
    }

}

