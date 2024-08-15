package editor;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainEditor extends Application{
	@Override
	public void start(Stage meineStage) throws Exception {
		//Instanz von FXMLLoader erzeugen
		FXMLLoader meinLoader = new FXMLLoader(getClass().getResource("editor.fxml"));
		//Datei laden
		Parent root = meinLoader.load();
		//Controller beschaffen
		FXMLControllerEditor meinController = meinLoader.getController();
		//die Buehne übergeben
		meinController.setMeineStage(meineStage);
		//Szene erzeugen
		//an den Konstruktor oberste Knoten und Groesse uebergeben
		Scene meineScene = new Scene(root,400,400);
		//den Titel ueber stage setzen
		meineStage.setTitle("JavaFX Editor");
		//die Szene setzen
		meineStage.setScene(meineScene);
		//im Vollbild darstellen
		meineStage.setMaximized(true);
		//und anzeigen
		meineStage.show();	
	}

	public static void main(String[] args) {
		// TODO Automatisch generierter Methodenstub
		launch(args);
	}
}
