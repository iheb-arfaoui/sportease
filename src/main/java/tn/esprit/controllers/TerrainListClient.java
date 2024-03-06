package tn.esprit.controllers;

import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import tn.esprit.models.Terrain;
//import java.awt.Color;
//import java.awt.Desktop;
//import java.awt.Dimension;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
//import javax.swing.JFrame;
//import org.jfree.chart.ChartFactory;
//import org.jfree.chart.ChartPanel;
//import org.jfree.chart.JFreeChart;
//import org.jfree.chart.axis.NumberAxis;
//import org.jfree.chart.plot.CategoryPlot;
//import org.jfree.chart.plot.PlotOrientation;
//import org.jfree.chart.plot.XYPlot;
//import org.jfree.chart.renderer.category.CategoryItemRenderer;
//import org.jfree.chart.renderer.xy.XYBarRenderer;
//import org.jfree.data.category.DefaultCategoryDataset;
import javafx.geometry.Pos;
import tn.esprit.services.TerrainService;
import tn.esprit.assets.Constants;

public class TerrainListClient implements Initializable {

    private Label label;
    private ObservableList<Terrain> terrainList;
    private TerrainService terrainCRUD = new TerrainService();
    @FXML
    private GridPane gridAcademy;
    private int numColumns = 3;
    private List<Terrain> terrain_list_search;
    @FXML
    private TextField searchField;
    @FXML
    private ImageView btnClear;
    @FXML
    private Pagination academyPagination;
    // Define the number of academies to be displayed per page
    private final int ACADEMIES_PER_PAGE = 6;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        gridAcademy.setVgap(40);
        gridAcademy.setHgap(120);
        terrainList = FXCollections.observableArrayList(terrainCRUD.displayEntity());

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            terrain_list_search = terrainList.stream()
                    .filter(academy ->
                            academy.getName().toLowerCase().contains(newValue.toLowerCase()) ||
                                    academy.getSportType().toLowerCase().contains(newValue.toLowerCase()))
                    .collect(Collectors.toList());
            academyPagination.setPageCount((terrain_list_search.size() + ACADEMIES_PER_PAGE - 1) / ACADEMIES_PER_PAGE);
            academyPagination.setCurrentPageIndex(0);
            academyPagination.setPageFactory(this::createPage);
        });

        terrain_list_search = terrainList;
        academyPagination.setPageCount((terrain_list_search.size() + ACADEMIES_PER_PAGE - 1) / ACADEMIES_PER_PAGE);
        academyPagination.setPageFactory(this::createPage);
    }

    private Node createPage(int pageIndex) {
        GridPane pageGrid = new GridPane();
        pageGrid.setVgap(25);
        pageGrid.setHgap(200);
        int startIndex = pageIndex * ACADEMIES_PER_PAGE;
        int endIndex = Math.min(startIndex + ACADEMIES_PER_PAGE, terrain_list_search.size());
        int rowIndex = 0;
        int colIndex = 0;
        for (int i = startIndex; i < endIndex; i++) {
            Terrain terrain = terrain_list_search.get(i);
            ImageView imageView = new ImageView(new Image("file:"+Constants.DEST_PATH + terrain.getImageName()));
            imageView.setFitWidth(200);
            imageView.setFitHeight(200);
            Label nameLabel = new Label(terrain.getName() + " (" + terrain.getSportType() + ")");
            nameLabel.setAlignment(Pos.CENTER);
            nameLabel.setMaxWidth(Double.MAX_VALUE);
            GridPane.setHgrow(nameLabel, Priority.ALWAYS);
            GridPane.setVgrow(nameLabel, Priority.NEVER);

            // Add event listener to navigate to academy details
            imageView.setOnMouseClicked(event -> {
                try {
                    FXMLLoader loader = new FXMLLoader(URI.create(Constants.TerrainDetailsClient).toURL());
                    Parent root = loader.load();
                    TerrainDetailsClient controller = loader.getController();
                    controller.setTerrain(terrain);
                    Stage stage = (Stage) gridAcademy.getScene().getWindow();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            nameLabel.setOnMouseClicked(event -> {
                try {
                    FXMLLoader loader = new FXMLLoader(URI.create(Constants.TerrainDetailsClient).toURL());
                    Parent root = loader.load();
                    TerrainDetailsClient controller = loader.getController();
                    controller.setTerrain(terrain);
                    Stage stage = (Stage) gridAcademy.getScene().getWindow();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            pageGrid.add(imageView, colIndex, rowIndex);
            pageGrid.add(nameLabel, colIndex, rowIndex + 1);
            colIndex++;
            if (colIndex >= numColumns) {
                colIndex = 0;
                rowIndex += 2;
            }
        }
        return pageGrid;
    }



    @FXML
    private void handleClearButtonClick(MouseEvent event) throws IOException {
        searchField.clear();
        terrainList = FXCollections.observableArrayList(terrainCRUD.displayEntity());
        academyPagination.setPageCount((terrainList.size() + ACADEMIES_PER_PAGE - 1) / ACADEMIES_PER_PAGE);
        academyPagination.setCurrentPageIndex(0);
        academyPagination.setPageFactory(this::createPage);
    }


    @FXML
    private void Auth(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(URI.create(Constants.Auth).toURL());
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void handleResButtonClick(javafx.event.ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(URI.create(Constants.MyReservation).toURL());
            Parent terrainListParent = loader.load();
            Scene scene = new Scene(terrainListParent);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

}

