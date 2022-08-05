package view;

import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JOptionPane;

import controller.Controller;
import controller.IController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Citizen;
import model.Kalpi;
import model.Party;



public class GUI  implements View{
	
	private IController listener;
	private Scanner scan;
	Controller controller;
	public GUI()
	{
		scan = new Scanner(System.in);
	}
	
	public void init1()
	{
		
		boolean endMain = false;
		while (!endMain) 
		{ 
			Console();
		}
	}
	
	private void Console()
	{
	
		System.out.println("Do you want load file?(Yes or NO) " );
		if(scan.nextLine().toUpperCase().charAt(0) == 'Y' )
			listener.LoadFile();
		System.out.println("Please select an option:\r\n"
				+ "1 - Add kalpi \r\n"
				+ "2 - Add citizen \r\n"
				+ "3 - Add party \r\n"
				+ "4 - Add citizen for party \r\n"
				+ "5 - Show all the kalpi \r\n"
				+ "6 - Show all the citizen \r\n"
				+ "7 - Show all the parties \r\n"
				+ "8 – Election \r\n"
				+ "9 - Show the election results \r\n"
				+ "10 - Exit\r\n"
				+ "");
		int choise = scan.nextInt();
		scan.nextLine();
		switch (choise) {
		case 1:
			ShowKalpiInputText();
			break;
		case 2:
			ShowCitizenInputText();
			break;
		case 3:
			ShowPartyInputText();
			break;
		case 4: 
			ShowPartyMemberInputText();
			break;
		case 5:
			String[] allKalpies = listener.ShowAllKalpies();
			if(allKalpies!=null)
				for(int i = 0;i < allKalpies.length ; i++)
					System.out.println(allKalpies[i]);
			else
				System.out.println("No kalpies to show");
			break;
		case 6:
			listener.ShowAllCitizens();
			break;
		case 7:
			listener.ShowAllParties();
			break;
		case 8:
			ShowElectionsInputText();
			break;
		case 9:
			System.out.println("The results of the vote by kalpi:  " );
			listener.ShowElectionResult();
			break;
		case 10:
			listener.SaveFile();
			System.exit(0);
			break;
		default:
			break;
		}
	}
	
	public void ShowKalpiInputText()
	{
		
		System.out.println("Enter location:");
		String location = scan.nextLine();
		System.out.println("Which kind of Kalpi do you want to add(regular press 1 , covid press 2 , IDF press 3 , IDF covid press 4) : ");
		int kindOfKalpi = scan.nextInt();
		listener.AddKalpi(location,kindOfKalpi);
		scan.nextLine();
	}
	
	public void ShowCitizenInputText()
	{
		System.out.println("Enter name:");
		String name = scan.nextLine();
		System.out.println("Enter ID number:");
		String id = scan.next();
		System.out.println("Enter year of birth:");
		int yearOfBirth = scan.nextInt();
		System.out.println("Enter your age:");
		int age = scan.nextInt();
		System.out.println("Enter your id kalpi:");
		int idKalpi = scan.nextInt();
		System.out.println("Are you in isolation? ");
		boolean isolationStatus;
		char ch = scan.next().charAt(0);
		if (ch == 'y')
			isolationStatus = true;
		else
			isolationStatus = false;
		boolean isProtected = false;
		if (isolationStatus) {
			System.out.println("Are you protected?");
			ch = scan.next().charAt(0);
			if (ch == 'y')
				isProtected = true;
			else
				isProtected = false;
		}
		System.out.println("Do you have a weapon?");
		boolean isweapon;
		char cha = scan.next().charAt(0);
		if (cha == 'y')
			isweapon = true;
		else
			isweapon = false;
		System.out.println("How many days are you sick?");
		int numSick = scan.nextInt();
		listener.AddCitizen(name,id,yearOfBirth,age,idKalpi,isolationStatus,isProtected,isweapon,numSick);
		scan.nextLine();
	}
	
	public void ShowPartyInputText()
	{
		System.out.println("Enter the name of the pary:");
		String name = scan.nextLine();
		System.out.println("Select side(Left,Center,Right): ");
		String side = scan.nextLine();
		listener.AddParty(name,side.toLowerCase());
	}
	
	public void ShowPartyMemberInputText()
	{
		System.out.println("Enter ID kalpi");
		int idKalpi = scan.nextInt();
		scan.nextLine();
		System.out.println("Enter citizen ID:");
		String id = scan.nextLine();
		System.out.println("Enter name of the party");
		String partyName = scan.next();
		listener.AddPartyMember(idKalpi,id,partyName);
	}
	
	public void ShowElectionsInputText()
	{
		System.out.println("would you like to vote? Y/N");
		String vote = scan.nextLine();
		
		boolean b;
		if(vote.equals("y"))
			b = true;
		else
			b = false;
		
		if(b) {
		System.out.println("Which party do you want to vote to: ");
		listener.Elec();
		System.out.println("Enter the name of party: ");
		String partyName = scan.next();
		listener.AddVote(partyName,0,0);
		}
	}
	
	public void SetListener(IController listener)
	{
		this.listener = listener;
	}



	@Override
	public void onPrint(String string) {
		// TODO Auto-generated method stub
		System.out.println(string);
	}

	@Override
	public void onPrint(String... strings) {
		// TODO Auto-generated method stub
		for(int i = 0; i <strings.length; i++)
			System.out.println(strings[i]);
	}

	/************************************************************************************************************/
	
	
	

	
	
	


	@Override
	public void onSuccessAdding(String addedStatus) {
		// TODO Auto-generated method stub
		System.out.println(addedStatus);
	}

	@Override
	public void onFailedAdding(String failed) {
		// TODO Auto-generated method stub
		System.out.println(failed);
	}

	@Override
	public void onShowAllKalpies(ArrayList<Kalpi> kalpies) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onShowAllCitizens(ArrayList<Citizen> citizens) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onShowAllParties(ArrayList<Party> parties) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onShowElectionResult(ArrayList<Kalpi> kalpies) {
		// TODO Auto-generated method stub
		
	}

	/*

	@Override
	public void onPrepareVote(ArrayList<Kalpi> kalpies, String citizenName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onCitizenName(String name) {
		// TODO Auto-generated method stub
		
	}
*/
	@Override
	public void onKalpiesNoDisplay(ArrayList<Kalpi> kalpies) {
		// TODO Auto-generated method stub
		
	}

	

	
}
