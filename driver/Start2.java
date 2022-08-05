package driver;

import controller.Controller;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.Election;
import model.Model;
import view.GUI;
import view.GUI2;
import view.View;

public class Start2 extends Application{

	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		//lets user decide for what ui to use - console/graphical
		BorderPane border = new BorderPane();
		HBox hbox = new HBox();
		hbox.setSpacing(10);
		hbox.setPadding(new Insets(15,12,15,12));
		hbox.setStyle("-fx-background-color: #336699;");
		Button consoleBtn = new Button("Console User Interface");
		consoleBtn.setPrefSize(200, 20);
		consoleBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override 
			public void handle(MouseEvent event) {
				//starts console
				primaryStage.close();
				Model model = new Election();
				View view = new GUI();
				Election election = (Election)model;
				Controller controller = new Controller(model,view);
				election.SetListener(controller);
				GUI gui = (GUI)view;
				gui.SetListener(controller);
				controller.init();
				
				
			 }
			 
		});
		Button GUIBtn = new Button("Graphical User Interface");
	    GUIBtn.setPrefSize(200, 20);
	    GUIBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override 
			public void handle(MouseEvent event) {
				//starts gui
				primaryStage.setScene(null);
				View view = new GUI2(primaryStage);
				Model model = new Election();
				GUI2 gui2 = (GUI2)view;
				Election election = (Election)model;
				Controller controller = new Controller(model, view);
				gui2.setListener(controller);
				election.SetListener(controller);
			 }
			 
		});
		
	    hbox.getChildren().addAll(consoleBtn, GUIBtn);
	    border.setTop(hbox);
	    Scene scene = new Scene(border);
	    primaryStage.setScene(scene);
	    primaryStage.setTitle("UI Picker");
	    primaryStage.setResizable(false);
	    primaryStage.show();
	}

	
}
