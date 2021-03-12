package gui;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.fxml.FXMLLoader;

/*
 * 
 * 1. To run through eclipse menu
 * please add vm arguments: --module-path ${project_classpath} --add-modules javafx.controls,javafx.fxml
 * 
 * 2. Run through maven
 * mvn java:run 
 * mvn exec:java
 * mvn jpro:run 
 * mvn jpro:stop
 */
public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			TabPane root = (TabPane) FXMLLoader.load(getClass().getResource("Prototype.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("Prototype.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setMaximized(true);
			primaryStage.setTitle("Prototype SalesManagementSystem");
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
	
