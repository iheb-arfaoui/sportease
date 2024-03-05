package Test;



import java.sql.SQLException;
import java.util.List;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{
    @Override
    public void start(Stage stage) throws Exception {

       FXMLLoader loader=new FXMLLoader(getClass().getResource("/AjouterUser.fxml"));
        //FXMLLoader loader=new FXMLLoader(getClass().getResource("/eventhome.fxml"));
       //FXMLLoader loader=new FXMLLoader(getClass().getResource("/AfficherEquipement.fxml"));
       // FXMLLoader loader=new FXMLLoader(getClass().getResource("/GetPassword.fxml"));
        //FXMLLoader loader=new FXMLLoader(getClass().getResource("/Admin.fxml"));
        // FXMLLoader loader=new FXMLLoader(getClass().getResource("/AjouterSpectateur.fxml"));
        //FXMLLoader loader=new FXMLLoader(getClass().getResource("/Ajouterjeu.fxml"));
        //FXMLLoader loader=new FXMLLoader(getClass().getResource("/AfficherJeu.fxml"));
        //FXMLLoader loader=new FXMLLoader(getClass().getResource("/AfficherEquipe.fxml"));
        Parent root=loader.load();
        Scene scene=new Scene(root);
        stage.setTitle("gerer user");
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
launch(args);

    }


}
