package editor;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

public class FXMLControllerEditor {
	//das Textfeld
		@FXML private TextArea editor;
		//die Buehne
		private Stage meineStage;
		
		//Methode zum Anzeigen des Oeffnendialog
		@FXML protected void ladenKlick(ActionEvent event) {
			//eine neue Instanz der Klasse FileChooser erzeugen
			FileChooser oeffnenDialog = new FileChooser();
			//den Titel fuer den Dialog setzen
			oeffnenDialog.setTitle("Datei oeffnen");
			oeffnenDialog.setInitialDirectory(new File(System.getProperty("user.home")));
			//Filter setzen
			oeffnenDialog.getExtensionFilters().add(new ExtensionFilter("Textdateien","*.txt"));
			//den Oeffnendialog anzeigen und das Ergebnis beschaffen
			File datei = oeffnenDialog.showOpenDialog(meineStage);
			//wurde eine Datei ausgewaehlt
			if (datei != null)
				//Inhalt ueber eine eigene Methode lesen
				editor.setText(datenLesen(datei));
		}

		//Methode zum Laden
		//enthaehlt die Datei, die gelesen werden soll und liefert eine Zeichenkette mit dem Inhalt zurueck
		private String datenLesen(File dateiLesen) {
			//gelesen wird als int
			int zeichen = 0;

			StringBuilder text = new StringBuilder();
			//eine Instanz der Klasse FileReader mit der Datei erzeugen
			try (FileReader tempDatei = new FileReader(dateiLesen)){
				//solange das Ende der Datei nicht erreicht ist, werden die Zeichen einzeln gelesen und
				//an einem StringBuilder angehaengt
				zeichen = tempDatei.read();
				while (zeichen != -1) {
					//zeichen wird in den Typ char umgewandelt
					text.append((char)zeichen);
					zeichen = tempDatei.read();
				}
			}
			catch (IOException e) {
				//bei Problemen einen Dialog erzeugen
				Alert meinDialog = new Alert(AlertType.INFORMATION,"Beim Laden ist ein Problem aufgetreten");
				meinDialog.setHeaderText("Achtung");
				meinDialog.showAndWait();
			}
			return (text.toString());
		}
		
		//Methode zum Anzeigen des Speicherndialogs
		@FXML protected void speichernKlick(ActionEvent event) {
			//eine neue Instanz der Klasse FileChooser erzeugen
			FileChooser speichernDialog = new FileChooser();
			//den Titel für den Dialog setzen
			speichernDialog.setTitle("Datei speichern");
			
			//Ordner setzen
			speichernDialog.setInitialDirectory(new File(System.getProperty("user.home")));
			
			//Filter setzen
			speichernDialog.getExtensionFilters().add(new ExtensionFilter("Textdateien", "*.txt"));
			//Speicherndialog anzeigen und Ergebnis beschaffen
			File datei = speichernDialog.showSaveDialog(meineStage);
			//wurde eine Datei ausgewaehlt
			if (datei != null)
				//Inhalt ueber eine eigene Methode speichern
				datenSchreiben(datei);
		}

		//Methode zum Schreiben
		//erhaelt die Datei, die geschrieben werden soll
		private void datenSchreiben(File dateiSchreiben) {
			//eine Instanz der Klasse FileWriter mit der Datei erzeugen
			try (FileWriter tempDatei = new FileWriter(dateiSchreiben)){
				//der Inhalt des Textfeldes wird direkt in die Datei geschrieben
				tempDatei.write(editor.getText());
			}
			catch (IOException e) {
				//bei Problemen einen Dialog erzeugen
				Alert meinDialog = new Alert(AlertType.INFORMATION, "Beim Schreiben ist ein Problem aufgetreten");
				meinDialog.setHeaderText("Bitte beachten");
				meinDialog.showAndWait();
			}
		}

		//Methode zur Erstellung eines neuen Dokuments
		@FXML protected void neuKlick(ActionEvent event) {
			//Abfrage zur Erstellung eines neuen Dokuments
			Alert meinDialog = new Alert(AlertType.CONFIRMATION,"Die Daten loeschen und ein neues Dokment erstellen?",ButtonType.YES,ButtonType.NO);
			meinDialog.setTitle("Neues Dokument erstellen");
			meinDialog.setHeaderText("bitte bestaetigen");
			meinDialog.showAndWait();
			if (meinDialog.getResult()==ButtonType.YES) {
				//Abfrage ob Datei vorher gespeichert werden soll
				Alert vorherSpeichern = new Alert(Alert.AlertType.CONFIRMATION,"Wollen Sie die Daten speichern?",ButtonType.YES,ButtonType.NO);
				vorherSpeichern.setTitle("Frage");
				vorherSpeichern.setHeaderText("bitte auswaehlen");
				vorherSpeichern.showAndWait();
				if (vorherSpeichern.getResult() == ButtonType.YES) {
					speichernKlick(event);
					editor.clear();
				}
				else
					editor.clear();
			}		
		}

		//Methode zum Beenden des Programms
		@FXML protected void beendenKlick(ActionEvent event) {
			Alert beendenDialog = new Alert(AlertType.CONFIRMATION,"Wollen Sie das Programm schliessen?",ButtonType.YES,ButtonType.NO);
			beendenDialog.setTitle("Programm schliessen");
			beendenDialog.setHeaderText("bitte bestaetigen");
			beendenDialog.showAndWait();
			if (beendenDialog.getResult() == ButtonType.YES) {
				//Abfrage Datei vor dem Schliessen speichern
				Alert vorherSpeichern = new Alert(Alert.AlertType.CONFIRMATION,"Wollen Sie die Daten speichern?",ButtonType.YES,ButtonType.NO);
				vorherSpeichern.setTitle("Frage");
				vorherSpeichern.setHeaderText("bitte auswaehlen");
				vorherSpeichern.showAndWait();
				if (vorherSpeichern.getResult() == ButtonType.YES) {
					speichernKlick(event);
					Platform.exit();
				}
				else
					Platform.exit();
			}
		}
		
		public void setMeineStage(Stage meineStage) {
			this.meineStage = meineStage;
		}
		//ActionEvent fuer Ausschneiden
		@FXML protected void cut(ActionEvent event) {
			editor.cut();
		}
		//ActionEvent fuer Kopieren
		@FXML protected void copy(ActionEvent event) {
			editor.copy();
		}
		//ActionEvent fuer Einfuegen
		@FXML protected void paste(ActionEvent event) {
			editor.paste();
		}
}
