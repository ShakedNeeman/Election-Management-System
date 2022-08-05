package view;

import java.util.ArrayList;
import java.util.Date;
import controller.IController;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.Citizen;
import model.Kalpi;
import model.Party;


public class GUI2 implements View{

	private IController listener;
	private BorderPane border;
	private int kalpiIndex = 0;
	private int citizenIndex = 0;
	private ArrayList<Kalpi> kalpies;
	
	public GUI2(Stage stage)
	{
		border = new BorderPane();
		addHbox(border);
		Scene scene = new Scene(border);
        stage.setScene(scene);
        stage.setTitle("Kalpi Manager");
        stage.show();
        EventHandler<WindowEvent> windowEvent = new EventHandler<WindowEvent>() {
			
			@Override
			public void handle(WindowEvent event) {
				// TODO Auto-generated method stub
				listener.SaveFile();//saves the file on X button press
			}
		};
        stage.addEventHandler(WindowEvent.WINDOW_CLOSE_REQUEST,windowEvent );
        ShowFileDialog();
	}
	
	/**
	 * Shows the load file window when the GUI2 starts
	 */
	private void ShowFileDialog()
	{
		Stage fileStage = new Stage();
        fileStage.setTitle("Load file");
        BorderPane fileBorder = new BorderPane();
        Scene fileScene = new Scene(fileBorder);
        HBox hbox = new HBox();
    	hbox.setSpacing(10);
		hbox.setPadding(new Insets(15,12,15,12));
		hbox.setStyle("-fx-background-color: #336699;");
		Button yesBtn = new Button("Load");
		yesBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override 
			public void handle(MouseEvent event) {
				listener.LoadFile();
				fileStage.close();
			 }
			 
		});
		Button noBtn = new Button("Don't load");
		noBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override 
			public void handle(MouseEvent event) {
				fileStage.close();
			 }
			 
		});
		Label title = new Label("Load saved file?");
		hbox.getChildren().addAll(yesBtn,noBtn);
		VBox vbox = new VBox();
		vbox.getChildren().addAll(title,hbox);
		fileBorder.setTop(vbox);
		fileStage.setScene(fileScene);
		fileStage.show();
		fileStage.setResizable(false);
		fileBorder.setPrefSize(250, 40);
	}
	
	/**
	 * Creates the horizontal buttons at the top of the window
	 * @param border - the pane that is used to allocate the window objects
	 */
	private void addHbox(BorderPane border)
	{
		HBox hbox = new HBox();
		hbox.setSpacing(10);
		hbox.setPadding(new Insets(15,12,15,12));
		hbox.setStyle("-fx-background-color: #336699;");
		Button addBtn = new Button("Add");
		addBtn.setPrefSize(100, 20);
		addBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override 
			public void handle(MouseEvent event) {
				border.setLeft(null);
				border.setCenter(null);
				border.setRight(null);
				LoadAddVbox(border);
			 }
			 
		});
		Button showBtn = new Button("Show");
	    showBtn.setPrefSize(100, 20);
	    showBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override 
			public void handle(MouseEvent event) {
				border.setLeft(null);
				border.setCenter(null);
				border.setRight(null);
				LoadShowVbox(border);
			 }
			 
		});
	    Button voteBtn = new Button("Vote");
	    voteBtn.setPrefSize(100, 20);
	    voteBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override 
			public void handle(MouseEvent event) {
				border.setLeft(null);
				border.setCenter(null);
				border.setRight(null);
				LoadVoteVbox(border);
			 }
			 
		});
	    
	    hbox.getChildren().addAll(voteBtn, addBtn, showBtn);
	    border.setTop(hbox);
	    border.setPrefSize(1200, 500);
	    //addStackPane(hbox);
	}
	/**
	 * Shows the vertical options of the vote sub-menu
	 * @param border - the pane that is used to allocate the window objects
	 */
	private void LoadVoteVbox(BorderPane border)
	{
		VBox vbox = new VBox();
		vbox.setPadding(new Insets(10));
		vbox.setSpacing(8);
		vbox.setStyle("-fx-background-color: CORNFLOWERBLUE;");
		Text title = new Text("Vote for a party of your choosing");
		title.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		vbox.getChildren().add(title);
		citizenIndex = 0;
		kalpiIndex = 0;
		Label chooseParty = new Label("Pick the party you would like to vote to ");
		vbox.getChildren().add(chooseParty);
		Button vote = new Button("Vote");
		vbox.getChildren().add(vote);
		vote.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override 
			public void handle(MouseEvent event) {
				border.setRight(null);
				TableView<Party> tableView = (TableView) border.getCenter();
				ObservableList<Party>selectedItem =  tableView.getSelectionModel().getSelectedItems();
				if(selectedItem.size()>1)
				{
					DisplayMessage("you can vote for single party only", false);
				}
				else if(selectedItem.size() == 0)
				{
					DisplayMessage("select the party\nyou want to vote for\nand then press the\nvote button", false);
				}else
					{
						border.setRight(null);
						listener.AddVote(selectedItem.get(0).getName(),citizenIndex,kalpiIndex);
						IterateVoting();
						UpdateVotingText();
					}
			 }
		});
		Button skipBtn = new Button("Skip");
		Tooltip toolTip = new Tooltip("i don't want to vote");
		skipBtn.setTooltip(toolTip);
		skipBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override 
			public void handle(MouseEvent event) {
				border.setRight(null);
				//shows next citizen for vote
				IterateVoting();
				UpdateVotingText();
			}
		});
		vbox.getChildren().add(skipBtn);
		border.setLeft(vbox);
		listener.Elec();//gets all the parties names
		listener.Kalpies();
	}
	/**
	 * Iterates over all eligible voters
	 */
	private void IterateVoting()
	{
		citizenIndex++;
		Kalpi kalp = kalpies.get(kalpiIndex);
		ArrayList<Citizen>citizens = kalp.getCitizens();
		if(citizenIndex >= citizens.size())
		{
			citizenIndex = 0;
			kalpiIndex++;
			if(kalpiIndex >= kalpies.size())
			{
				citizenIndex = 0;
				kalpiIndex = 0;
			}
		}	
	}
	
	/**
	 * Updates the text of the vertical voting sub-menu
	 */
	private void UpdateVotingText()
	{
		ArrayList<Citizen>c_arr = kalpies.get(kalpiIndex).getCitizens();
		String name;
		if(c_arr.size() > 0)
		{
			name = c_arr.get(citizenIndex).getName();
			VBox vbox = (VBox)border.getLeft();
			ObservableList<Node>list = vbox.getChildren();
			for(Node item : list)
			{
				if(item instanceof Label)
				{
					Label text = (Label)item;
					text.setText(name + " is voting now.\nPick the party you would like to vote to ");
					break;
				}
			}
		}
		else 
			{
				IterateVoting();
				UpdateVotingText();
			}
	}
	
	/**
	 * Shows the showing options sub-menu
	 * @param border - the pane that is used to allocate the window objects
	 */
	public void LoadShowVbox(BorderPane border)
	{
		VBox vbox = new VBox();
		vbox.setPadding(new Insets(10));
		vbox.setSpacing(8);
		vbox.setStyle("-fx-background-color: CORNFLOWERBLUE;");
		Text title = new Text("Show Options");
		title.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		vbox.getChildren().add(title);
		Button showKalpiesBtn = new Button("Show all kalpies");
		Button showCitizensBtn = new Button("Show all citizens");
		Button showPartiesBtn = new Button("Show all parties");
		Button showElectionResultBtn = new Button("Show election Result");
		
		showKalpiesBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override 
			public void handle(MouseEvent event) {
				border.setRight(null);
				VBox vbox = (VBox) border.getLeft();
				if(vbox.getChildren().get(vbox.getChildren().size()-1) instanceof Label)
					vbox.getChildren().remove(vbox.getChildren().size()-1);
				listener.ShowAllKalpies();
			 }
			 
		});
		
		showCitizensBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override 
			public void handle(MouseEvent event) {
				border.setRight(null);
				VBox vbox = (VBox) border.getLeft();
				if(vbox.getChildren().get(vbox.getChildren().size()-1) instanceof Label)
					vbox.getChildren().remove(vbox.getChildren().size()-1);
				listener.ShowAllCitizens();
			 }
			 
		});
		
		showPartiesBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override 
			public void handle(MouseEvent event) {
				border.setRight(null);
				VBox vbox = (VBox) border.getLeft();
				if(vbox.getChildren().get(vbox.getChildren().size()-1) instanceof Label)
					vbox.getChildren().remove(vbox.getChildren().size()-1);
				listener.ShowAllParties();
			 }
			 
		});
		
		showElectionResultBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override 
			public void handle(MouseEvent event) {
				border.setRight(null);
				listener.ShowElectionResult();
			 }
			 
		});
		
		 Button btns[] = new Button[] {
			    	showKalpiesBtn,
			    	showCitizensBtn,
			    	showPartiesBtn,
			    	showElectionResultBtn
			    };
			  
			    for (int i=0; i<btns.length; i++) {
			    	btns[i].setPrefSize(150, 20);
			        VBox.setMargin(btns[i], new Insets(0, 0, 0, 8));
			        vbox.getChildren().add(btns[i]);
			    }

	    border.setLeft(vbox);
	}
	/**
	 * Shows the add options of the Add sub-menu
	 * @param border - the pane that is used to allocate the window objects
	 */
	public void LoadAddVbox(BorderPane border) {
	    VBox vbox = new VBox();
	    vbox.setPadding(new Insets(10));
	    vbox.setSpacing(8);
	    vbox.setStyle("-fx-background-color: CORNFLOWERBLUE;");
	    Text title = new Text("Add Options");
	    title.setFont(Font.font("Arial", FontWeight.BOLD, 14));
	    vbox.getChildren().add(title);
	    Button newKalpiBtn = new Button("Add new kalpi");
	    Button newCitizenBtn = new Button("Add new citizen");
	    Button newParty = new Button("Add new party");
	    Button newPartyCitizen = new Button("Add citizen for a party");
	    
	    newKalpiBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override 
			public void handle(MouseEvent event) {
				border.setRight(null);
				LoadKalpiForm(border);
			 }
			 
		});
	    
	    newCitizenBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override 
			public void handle(MouseEvent event) {
				border.setRight(null);
				LoadCitizenForm(border);
			 }
			 
		});
	    
	    newParty.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override 
			public void handle(MouseEvent event) {
				border.setRight(null);
				LoadPartyForm(border);
			 }
			 
		});
	    
	    newPartyCitizen.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override 
			public void handle(MouseEvent event) {
				border.setRight(null);
				LoadPartyCitizenForm(border);
			 }
			 
		});
	    Button btns[] = new Button[] {
	    	newKalpiBtn,
	    	newCitizenBtn,
	    	newParty,
	    	newPartyCitizen
	    };
	  
	    for (int i=0; i<btns.length; i++) {
	    	btns[i].setPrefSize(150, 20);
	        VBox.setMargin(btns[i], new Insets(0, 0, 0, 8));
	        vbox.getChildren().add(btns[i]);
	    }

	    border.setLeft(vbox);
	}
	
	/**
	 * Loads the Kalpi form
	 * @param border - the pane that is used to allocate the window objects
	 */
	private void LoadKalpiForm(BorderPane border)
	{
		//location and kind
		Text title = new Text("Add New Kalpi");
		title.setFont(new Font(20));
		Text location = new Text("Location");
		TextField locationField = new TextField();
		Text kind = new Text("Kind");
		ChoiceBox<String> kindChoice = new ChoiceBox<String>();
		kindChoice.getItems().addAll("regular","covid","IDF","IDF covid");
		kindChoice.setValue("regular");
		Button saveBtn = new Button("Save");
		saveBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override 
			public void handle(MouseEvent event) {
				int choice = 1;
				String kindValue = kindChoice.getValue();
				switch (kindValue) {
				case "regular":
					choice = 1;
					break;
				case "covid":
					choice = 2;
					break;
				case "IDF":
					choice = 3;
					break;
				case "IDF covid": 
					choice = 4;
					break;
				default:
					break;
				}
				border.setRight(null);
				listener.AddKalpi(locationField.getText(), choice);
				locationField.setText("");
			 }
			 
		});
		BorderPane innerBorder = new BorderPane();
		innerBorder.setTop(title);
		innerBorder.setPadding(new Insets(30,10,10,25));
		GridPane grid = new GridPane();
		grid.setMinSize(100, 60);
		grid.setPadding(new Insets(10,10,10,10));
		grid.setVgap(5);
		grid.setHgap(5);
		grid.add(location, 0, 1);
		grid.add(locationField, 1, 1);
		grid.add(kind, 0, 2);
		grid.add(kindChoice, 1, 2);
		innerBorder.setCenter(grid);
		innerBorder.setBottom(saveBtn);
		border.setCenter(innerBorder);
	}
	
	/**
	 * Loads the citizen form
	 * @param border - the pane that is used to allocate the window objects
	 */
	private void LoadCitizenForm(BorderPane border)
	{
		Text title = new Text("Add New Citizen");
		title.setFont(new Font(20));
		BorderPane innerPane = new BorderPane();
		innerPane.setTop(title);
		innerPane.setPadding(new Insets(30,10,10,25));
		
		Text name = new Text("Name: ");
		TextField nameField = new TextField();
		Text id = new Text("ID: ");
		TextField idField = new TextField();
		Text birthYear = new Text("Birth year: ");
		TextField birthYearField = new TextField();
		Text age = new Text("Age: ");
		TextField ageField = new TextField();
		Text kalpiId = new Text("Kalpi id: ");
		TextField kalpiIDField = new TextField();
		ToggleGroup isolationGroup = new ToggleGroup();
		RadioButton yes = new RadioButton("Yes");
		yes.setToggleGroup(isolationGroup);
		RadioButton no = new RadioButton("No");
		no.setToggleGroup(isolationGroup);
		ToggleGroup protectedGroup = new ToggleGroup();
		RadioButton yProtected = new RadioButton("Yes");
		yProtected.setToggleGroup(protectedGroup);
		RadioButton nProtected = new RadioButton("No");
		nProtected.setToggleGroup(protectedGroup);
		RadioButton yArmed = new RadioButton("Yes");
		RadioButton nArmed = new RadioButton("No");
		ToggleGroup armedGroup = new ToggleGroup();
		yArmed.setToggleGroup(armedGroup);
		nArmed.setToggleGroup(armedGroup);
		Text isolationLabel = new Text("are you isolated? ");
		Text protectedLabel = new Text("are you protected? ");
		Text armedLabel = new Text("are you armed? ");
		Text sickDaysLabel = new Text("How long have you been sick?(in days)");
		TextField sickDaysField = new TextField();
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(10,10,10,10));
		grid.setVgap(5);
		grid.setHgap(5);
		grid.addRow(0, name,nameField);
		grid.addRow(1, id,idField);
		grid.addRow(2, birthYear,birthYearField);
		grid.addRow(3, age,ageField);
		grid.addRow(4, kalpiId,kalpiIDField);
		grid.addRow(5, isolationLabel,yes,no);
		grid.addRow(6, protectedLabel,yProtected,nProtected);
		grid.addRow(7, armedLabel,yArmed,nArmed);
		grid.addRow(8, sickDaysLabel,sickDaysField);
		
		Button saveBtn = new Button("Save");
		saveBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override 
			public void handle(MouseEvent event) {
				String regexNum = "[0-9]*";
				String name = nameField.getText();
				String id = idField.getText();
				String birthYear = birthYearField.getText();
				String age = ageField.getText();
				String kalpiID = kalpiIDField.getText();
				boolean isolated = yes.isSelected();
				boolean isDis = false;
				if(!yProtected.isDisabled())
					isDis = yProtected.isSelected();
				boolean armed = yArmed.isSelected();
				String sickDays = sickDaysField.getText();
				if(birthYear.matches(regexNum) && age.matches(regexNum) && kalpiID.matches(regexNum) && sickDays.matches(regexNum))
				{
					border.setRight(null);
					listener.AddCitizen(name, id, Integer.parseInt(birthYear), Integer.parseInt(age), Integer.parseInt(kalpiID), isolated, isDis, armed, Integer.parseInt(sickDays));
				}
				else
				{
					DisplayMessage("fields birth year, \nage, \nkalpi id, \nsickdays \nmust be a numeric value only",false);
				}
			 }
			 
		});
		
		innerPane.setCenter(grid);
		innerPane.setBottom(saveBtn);
		border.setCenter(innerPane);
		
		yes.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override 
			public void handle(MouseEvent event) {
				if(yes.isSelected())
					{
						yProtected.setDisable(false);
						nProtected.setDisable(false);
					}
				else
					{
						yProtected.setDisable(true);
						nProtected.setDisable(true);
					}
			 }
		});
		no.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				if(no.isSelected())
				{
					yProtected.setDisable(true);
					nProtected.setDisable(true);
				}
				else
				{
					yProtected.setDisable(false);
					nProtected.setDisable(false);
				}
			}
		});
		
	}
	
	/**
	 * Loads the party form
	 * @param border - the pane that is used to allocate the window objects
	 */
	private void LoadPartyForm(BorderPane border)
	{
		Text title = new Text("Add party");
		title.setFont(new Font(20));
		Text partyName = new Text("Party Name: ");
		TextField partyNameField = new TextField();
		Text partySide = new Text("Party side");
		ChoiceBox<String> side = new ChoiceBox<String>();
		side.getItems().addAll("Left","Center","Right");
		side.setValue("Center");
		BorderPane innerBorder = new BorderPane();
		innerBorder.setTop(title);
		innerBorder.setPadding(new Insets(30,10,10,25));
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(10,10,10,10));
		grid.addRow(0, partyName,partyNameField);
		grid.addRow(1, partySide,side);
		grid.setVgap(5);
		grid.setHgap(5);
		Button saveBtn = new Button("Save");
		saveBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override 
			public void handle(MouseEvent event) {
				String partyName = partyNameField.getText();
				String sideS = side.getValue();
				border.setRight(null);
				listener.AddParty(partyName, sideS);
			 }
		});
		innerBorder.setCenter(grid);
		innerBorder.setBottom(saveBtn);
		border.setCenter(innerBorder);
	}
	
	/**
	 * Loads the party citizen form
	 * @param border - the pane that is used to allocate the window objects
	 */
	private void LoadPartyCitizenForm(BorderPane border)
	{
		Text title = new Text("Add citizen for party");
		title.setFont(new Font(20));
		Text kalpiId = new Text("Kalpi id: ");
		TextField kalpiIdField = new TextField();
		Text citizenID = new Text("Citizen ID: ");
		TextField citizenIdField = new TextField();
		Text partyName = new Text("Party Name: ");
		TextField partyNameField = new TextField();
		BorderPane innerBorder = new BorderPane();
		innerBorder.setTop(title);
		innerBorder.setPadding(new Insets(30,10,10,25));
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(10,10,10,10));
		grid.addRow(0, kalpiId,kalpiIdField);
		grid.addRow(1, citizenID,citizenIdField);
		grid.addRow(2, partyName,partyNameField);
		grid.setVgap(5);
		grid.setHgap(5);
		Button saveBtn = new Button("Save");
		saveBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override 
			public void handle(MouseEvent event) {
				String kalpiID = kalpiIdField.getText();
				String citizenID = citizenIdField.getText();
				String partyName = partyNameField.getText();
				border.setRight(null);
				if(kalpiID.matches("[0-9]*"))
					listener.AddPartyMember(Integer.parseInt(kalpiID), citizenID, partyName);
				else
					DisplayMessage("kalpi id must\nbe as numerical value",false);
			 }
		});
		innerBorder.setTop(title);
		innerBorder.setCenter(grid);
		innerBorder.setBottom(saveBtn);
		border.setCenter(innerBorder);
	}
	
	

	@Override
	public void init1() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPrint(String string) {
		// TODO Auto-generated method stub
		
		
	}

	@Override
	public void onPrint(String... strings) {
		// TODO Auto-generated method stub
		//System.out.println(strings);
	}
	
	public void setListener(IController listener)
	{
		this.listener = listener;
	}


	@Override
	public void onSuccessAdding(String addedStatus) {
		// TODO Auto-generated method stub
		DisplayMessage(addedStatus,true);/*
		Text successesText = new Text(addedStatus);
		border.setRight(successesText);*/
	}

	@Override
	public void onFailedAdding(String failed) {
		// TODO Auto-generated method stub
		DisplayMessage(failed,false);
		/*Text failedText = new Text(failed);
		border.setRight(failedText);*/
	}
	
	/**
	 * Displays the feedback message on the right side of the window
	 * @param message - the message to display
	 * @param success - determine background colour - red for false and green for true
	 */
	private void DisplayMessage(String message,boolean success)
	{
		Label label = new Label(message);
		label.setWrapText(true);
		Font font = new Font(30);
		label.setFont(font);
		StackPane layout = new StackPane();
		if(success)
			layout.setStyle("-fx-background-color: PALEGREEN; -fx-padding: 10;");//greenish background color to indicate success
		else
			layout.setStyle("-fx-background-color: mistyrose; -fx-padding: 10;");//reddish background color to indicate error
	    layout.getChildren().setAll(label);
		border.setRight(layout);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void onShowAllKalpies(ArrayList<Kalpi> kalpies) {
		// TODO Auto-generated method stub
		TableView tableView = new TableView();
		tableView.setPlaceholder(new Label("No rows to display"));
		TableColumn<Kalpi,Integer>col0 = new TableColumn<>("ID");
		col0.setCellValueFactory(new PropertyValueFactory<>("id"));
		TableColumn<Kalpi, String>col1 = new TableColumn<>("Location");
		col1.setCellValueFactory(new PropertyValueFactory<>("location"));
		TableColumn<Kalpi, String>col2 = new TableColumn<>("Type");
		col2.setCellValueFactory(new PropertyValueFactory<>("type"));
		TableColumn<Kalpi, String>col3 = new TableColumn<>("% Voters");
		col3.setCellValueFactory(new PropertyValueFactory<>("percentageOfVoters"));
		TableColumn<Kalpi, String>col4 = new TableColumn<>("registered parties");
		col4.setCellValueFactory(new PropertyValueFactory<>("parties"));
		TableColumn<Kalpi, String>col5 = new TableColumn<>("Number of votes");
		col5.setCellValueFactory(new PropertyValueFactory<>("numOfVotes"));
		TableColumn<Kalpi, String>col6 = new TableColumn<>("Registered voters");
		col6.setCellValueFactory(new PropertyValueFactory<>("voterRegister"));
		tableView.getColumns().add(col0);
		tableView.getColumns().add(col1);
		tableView.getColumns().add(col2);
		tableView.getColumns().add(col3);
		tableView.getColumns().add(col4);
		tableView.getColumns().add(col5);
		tableView.getColumns().add(col6);
		for(int i = 0;i<kalpies.size();i++) {
			Kalpi kalp = kalpies.get(i);
			tableView.getItems().add(kalp);
		}
		border.setCenter(tableView);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void onShowAllCitizens(ArrayList<Citizen> citizens) {
		// TODO Auto-generated method stub
		TableView tableView = new TableView();
		tableView.setPlaceholder(new Label("No rows to display"));
		TableColumn<Citizen, String>col0 = new TableColumn<>("ID");
		col0.setCellValueFactory(new PropertyValueFactory<>("id"));
		TableColumn<Citizen, String>col1 = new TableColumn<>("Name");
		col1.setCellValueFactory(new PropertyValueFactory<>("name"));
		TableColumn<Citizen, Integer>col2 = new TableColumn<>("BirthYear");
		col2.setCellValueFactory(new PropertyValueFactory<>("yearOfBirth"));
		TableColumn<Citizen, Integer>col3 = new TableColumn<>("Age");
		col3.setCellValueFactory(new PropertyValueFactory<>("age"));
		TableColumn<Citizen, Integer>col4 = new TableColumn<>("KalpiID");
		col4.setCellValueFactory(new PropertyValueFactory<>("idKalpi"));
		TableColumn<Citizen, Boolean>col5 = new TableColumn<>("Isolated");
		col5.setCellValueFactory(new PropertyValueFactory<>("isolationStatus"));
		TableColumn<Citizen, Boolean>col6 = new TableColumn<>("Protected");
		col6.setCellValueFactory(new PropertyValueFactory<>("isProtected"));
		TableColumn<Kalpi, String>col7 = new TableColumn<>("Sick Days");
		col7.setCellValueFactory(new PropertyValueFactory<>("numSick"));
		TableColumn<Kalpi, String>col8 = new TableColumn<>("Type");
		col8.setCellValueFactory(new PropertyValueFactory<>("type"));
		TableColumn<Kalpi, String>col9 = new TableColumn<>("Armed");
		col9.setCellValueFactory(new PropertyValueFactory<>("isWeapon"));
		tableView.getColumns().add(col0);
		tableView.getColumns().add(col1);
		tableView.getColumns().add(col2);
		tableView.getColumns().add(col3);
		tableView.getColumns().add(col4);
		tableView.getColumns().add(col5);
		tableView.getColumns().add(col6);
		tableView.getColumns().add(col7);
		tableView.getColumns().add(col8);
		tableView.getColumns().add(col9);
		for(int i = 0;i<citizens.size();i++) {
			Citizen citizen = citizens.get(i);
			tableView.getItems().add(citizen);
		}
		border.setCenter(tableView);
	}

	@Override
	public void onShowAllParties(ArrayList<Party> parties) {
		// TODO Auto-generated method stub
		TableView tableView = new TableView();
		tableView.setPlaceholder(new Label("No rows to display"));
		TableColumn<Citizen, String>col0 = new TableColumn<>("Name");
		col0.setCellValueFactory(new PropertyValueFactory<>("name"));
		TableColumn<Citizen, Date>col1 = new TableColumn<>("Date");
		col1.setCellValueFactory(new PropertyValueFactory<>("date"));
		TableColumn<Citizen, Date>col2 = new TableColumn<>("Candidates List");
		col2.setCellValueFactory(new PropertyValueFactory<>("candidatesList"));
		TableColumn<Citizen, String>col3 = new TableColumn<>("Side");
		col3.setCellValueFactory(new PropertyValueFactory<>("sidetype"));
		tableView.getColumns().add(col0);
		tableView.getColumns().add(col1);
		tableView.getColumns().add(col2);
		tableView.getColumns().add(col3);
		for(int i = 0;i<parties.size();i++) {
			Party party = parties.get(i);
			tableView.getItems().add(party);
		}
		border.setCenter(tableView);
		tableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
	}

	@Override
	public void onShowElectionResult(ArrayList<Kalpi> kalpies) {
		// TODO Auto-generated method stub
		TableView tableView = new TableView<>();
		tableView.setPlaceholder(new Label("No rows to display"));
		TableColumn<TableHelper,Integer>col0 = new TableColumn<TableHelper, Integer>("KalpiID");
		TableColumn<String,String>col1 = new TableColumn<String, String>("Parties Info");
		TableColumn<TableHelper,String>col2 = new TableColumn<TableHelper, String>("Party Name");
		TableColumn<TableHelper,Integer>col3 = new TableColumn<TableHelper, Integer>("# votes");
		col0.setCellValueFactory(new PropertyValueFactory<>("kalpiID"));
		col2.setCellValueFactory(new PropertyValueFactory<>("partyName"));
		col3.setCellValueFactory(new PropertyValueFactory<>("partyVotes"));
		//col1.getColumns().addAll(col2,col3);
		tableView.getColumns().add(col0);
		tableView.getColumns().add(col2);
		tableView.getColumns().add(col3);
		
		ArrayList<String> partyList=null;
		ArrayList<Integer> allTheResult = new ArrayList <Integer>();
		int j = -1;
		for(Kalpi kalpi : kalpies)
		{
			partyList = kalpi.getParties();
			ArrayList<Integer> voteList = kalpi.getNumOfVotes();
			for (int i = 0; i < partyList.size(); i++) {
		    	if(allTheResult.size() <= i) {
		    		allTheResult.add(0);}
		    	int updatedValue =  allTheResult.get(i) + voteList.get(i) ;
		    	allTheResult.set(i,updatedValue);
		    	TableHelper helper = new TableHelper();
		    	helper.setKalpiID(kalpi.getId());
		    	helper.setPartyName(partyList.get(i));
		    	helper.setPartyVotes(voteList.get(i));
		    	tableView.getItems().add(helper);
		    }
		}
		
		border.setCenter(tableView);
		VBox vbox = (VBox) border.getLeft();
		StringBuilder builder = new StringBuilder();
		builder.append("Election summery:\n");
		for(int i = 0;i<partyList.size();i++)
		{
			builder.append(partyList.get(i) +": " + allTheResult.get(i) + " votes\n");	
		}
		Label summeryLabel = new Label(builder.toString());
		vbox.getChildren().add(summeryLabel);
	}


	@Override
	public void onKalpiesNoDisplay(ArrayList<Kalpi> kalpies) {
		// TODO Auto-generated method stub
		this.kalpies = kalpies;
	
		UpdateVotingText();
	}
}
