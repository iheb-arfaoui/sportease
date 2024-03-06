package tn.esprit.controllers;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import tn.esprit.models.Terrain;
//import java.awt.Color;
import java.awt.*;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.swing.JFrame;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import javafx.geometry.Pos;
import tn.esprit.services.TerrainService;
import tn.esprit.assets.Constants;

/**
 *
 * @author ramib
 */
public class TerrainListController implements Initializable {

    private Label label;
    @FXML
    private TableView<Terrain> tvTerrain;
    private ObservableList<Terrain> terrainList;
    @FXML
    private TableColumn<Terrain, Integer> idColumn;
    @FXML
    private TableColumn<Terrain, String> nameColumn;
    @FXML
    private TableColumn<Terrain, Integer> capColumn;
    @FXML
    private TableColumn<Terrain, String> sportColumn;
    @FXML
    private TableColumn<Terrain, Integer> rentColumn;
    @FXML
    private TableColumn<Terrain, Boolean> disponibilityColumn;
    @FXML
    private TableColumn<Terrain, String> addressColumn;
    @FXML
    private TableColumn<Terrain, String> imgColumn;
    @FXML
    private TableColumn<Terrain, LocalDateTime> updatedAtColumn;
    @FXML
    private TextField searchField;
    @FXML
    private ImageView imgSearch;
    @FXML
    private ImageView btnClear;
    @FXML
    private Button btnSearch;
    @FXML
    private Button btnPDF;
    private List<Terrain> terrain_list_search;

    private TerrainService terrainCRUD = new TerrainService();
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnChart;
    @FXML
    private Button btnCoach;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        capColumn.setCellValueFactory(new PropertyValueFactory<>("capacity"));
        sportColumn.setCellValueFactory(new PropertyValueFactory<>("sportType"));
        rentColumn.setCellValueFactory(new PropertyValueFactory<>("rentPrice"));
        disponibilityColumn.setCellValueFactory(new PropertyValueFactory<>("disponibility"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        updatedAtColumn.setCellValueFactory(new PropertyValueFactory<>("updatedAt"));
        imgColumn.setCellValueFactory(new PropertyValueFactory<>("ImageName"));
        imgColumn.setCellFactory(column -> new TableCell<Terrain, String>() {
            private final ImageView imageView = new ImageView();

            @Override
            protected void updateItem(String imageName, boolean empty) {
                super.updateItem(imageName, empty);

                if (empty || imageName == null) {
                    imageView.setImage(null);
                    setGraphic(null);
                } else {
                    // Load the image from the file system or a URL
                    Image image = new Image("file:"+Constants.DEST_PATH + imageName);

                    // Set the image and adjust its size
                    imageView.setImage(image);
                    imageView.setFitWidth(100);
                    imageView.setFitHeight(100);
                    setGraphic(imageView);
                    setAlignment(Pos.CENTER);
                }
            }
        });

        terrainList = FXCollections.observableArrayList(terrainCRUD.displayEntity());
        tvTerrain.setItems(terrainList);

        // Set the selection mode to MULTIPLE
        tvTerrain.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        // Add listener for the selection of an Terrain in the table
        tvTerrain.setOnMousePressed(event -> {
            if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                Terrain selectedTerrain = tvTerrain.getSelectionModel().getSelectedItem();
                if (selectedTerrain != null) {
                    try {
                        // Load the TerrainDetails FXML file
//                        String filePath = "file:/C:/Users/Rami/Desktop/Java%20Aziz/Final/Sportease/target/classes/tn/esprit/TerrainDetails.fxml";
//                        filePath = filePath.replace(" ", "%20");
                        FXMLLoader loader = new FXMLLoader(URI.create(Constants.TerrainDetails).toURL());


                        Parent root = loader.load();

                        // Get a reference to the TerrainDetails controller
                        TerrainDetailsController controller = loader.getController();

                        // Call a method on the controller to set the Terrain to display its details
                        controller.setTerrain(selectedTerrain);

                        // Get a reference to the current stage
                        Stage stage = (Stage) tvTerrain.getScene().getWindow();

                        // Create a new stage to display the TerrainDetails scene
                        Scene scene = new Scene(root);
                        stage.setScene(scene);
                        stage.show();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            terrain_list_search = terrainList.stream()
                    .filter(terrain -> String.valueOf(terrain.getName()).toLowerCase().contains(newValue.toLowerCase())
                            || String.valueOf(terrain.getSportType()).toLowerCase().contains(newValue.toLowerCase())
                    )
                    .collect(Collectors.toList());
            tvTerrain.setItems(FXCollections.observableArrayList(terrain_list_search));
        });
    }

    @FXML
    private void handleSearchButtonClick(ActionEvent event) {
        String searchText = searchField.getText();
        ObservableList<Terrain> filteredList = FXCollections.observableArrayList();
        for (Terrain terrain : terrainList) {
            if (terrain.getName().toLowerCase().contains(searchText.toLowerCase())) {
                filteredList.add(terrain);
            }
        }
        tvTerrain.setItems(filteredList);
    }

    @FXML
    private void handleAddButtonClick(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(URI.create(Constants.TerrainAdd).toURL());
            Parent terrainListParent = loader.load();
            Scene scene = new Scene(terrainListParent);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void handleClearButtonClick(MouseEvent event) throws IOException {
        String searchText = "";
        ObservableList<Terrain> filteredList = FXCollections.observableArrayList();
        for (Terrain terrain : terrainList) {
            if (terrain.getName().toLowerCase().contains(searchText.toLowerCase())) {
                filteredList.add(terrain);
            }
        }
        tvTerrain.setItems(filteredList);
        searchField.clear();
    }

    @FXML
    private void handlePDFButtonClick(ActionEvent event) {
        try {
            byte[] pdfBytes = generatePDF();
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save PDF File");
            fileChooser.setInitialFileName("terrainList.pdf");
            File file = fileChooser.showSaveDialog(btnPDF.getScene().getWindow());
            if (file != null) {
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(pdfBytes);
                fos.close();
                // open the PDF file
                if (Desktop.isDesktopSupported()) {
                    Desktop.getDesktop().open(file);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    private byte[] generatePDF() throws Exception {
        Document document = new Document();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, baos);
        document.open();

        // Define font styles
        Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.BLACK);

        Paragraph title = new Paragraph("Terrain List");
        title.setFont(FontFactory.getFont(FontFactory.TIMES_BOLD, 24, BaseColor.RED));
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(20);
        document.add(title);
        // Add table headers
        PdfPTable table = new PdfPTable(8);
        table.addCell(new Phrase("ID", headerFont));
        table.addCell(new Phrase("Name", headerFont));
        table.addCell(new Phrase("Capacity", headerFont));
        table.addCell(new Phrase("Category", headerFont));
        table.addCell(new Phrase("Rent Price", headerFont));
        table.addCell(new Phrase("Disponibility", headerFont));
        table.addCell(new Phrase("Address", headerFont));
        table.addCell(new Phrase("Creation Date", headerFont));

        // Define font styles for table data
        Font dataFont = FontFactory.getFont(FontFactory.HELVETICA, 10, BaseColor.BLACK);

        // Add table data
        for (Terrain terrain : terrainList) {
            table.addCell(new Phrase(String.valueOf(terrain.getId()), dataFont));
            table.addCell(new Phrase(terrain.getName(), dataFont));
            table.addCell(new Phrase(String.valueOf(terrain.getCapacity()), dataFont));
            table.addCell(new Phrase(terrain.getSportType(), dataFont));
            table.addCell(new Phrase(String.valueOf(terrain.getRentPrice()), dataFont));
            table.addCell(new Phrase(String.valueOf(terrain.isDisponibility()), dataFont));
            table.addCell(new Phrase(terrain.getAddress(), dataFont));
            table.addCell(new Phrase(String.valueOf(terrain.getUpdatedAt()), dataFont));
        }

        // Add style to table
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setSpacingAfter(10f);

        document.add(table);
        document.close();

        return baos.toByteArray();
    }

    public void generateCategoryChart(List<Terrain> terrainList) {
        if (terrainList == null || terrainList.isEmpty()) {
            throw new IllegalArgumentException("terrain list cannot be null or empty");
        }
        // Create a map to store the count of academies in each category
        Map<String, Integer> categoryCount = new HashMap<>();
        for (Terrain terrain : terrainList) {
            String category = terrain.getSportType();
            categoryCount.put(category, categoryCount.getOrDefault(category, 0) + 1);
        }
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (String category : categoryCount.keySet()) {
            dataset.addValue(categoryCount.get(category), "Terrains", category);
        }
        JFreeChart chart = ChartFactory.createBarChart(
                "Number of Terrains by Category",
                "Category",
                "Number of Terrains",
                dataset,
                PlotOrientation.VERTICAL,
                false,
                true,
                false);
        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        CategoryItemRenderer renderer = plot.getRenderer();
        renderer.setSeriesPaint(0, Color.RED);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(1156, 800));
        JFrame frame = new JFrame("Category Chart");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(chartPanel);
        frame.pack();
        frame.setVisible(true);
    }


    @FXML
    private void handleChartButtonClick(ActionEvent event) {
        generateCategoryChart(terrainList);
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

}
