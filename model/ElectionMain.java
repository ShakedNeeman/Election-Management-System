package model;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class ElectionMain{

	public final static int REGULAR = 1;
	public final static int COVID = 2;
	public final static int IDF = 3;
	public final static int IDFCOVID = 4;

	public static void main(String[] args) throws Exception {
		Scanner s = new Scanner(System.in);
		int choise;
		boolean exit = false;
		ElectionRotation rotation = new ElectionRotation();
		Initiate(rotation);
		Test(rotation);
		System.out.println("Do you want load file?(Yes or NO) " );
		if(s.next().toUpperCase().charAt(0) == 'Y' )
			rotation=loadFile();
		do { 
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
			choise = s.nextInt();

			switch (choise) {

			case 1:

				addKalpi(rotation);
				break;

			case 2:

				addCitizen(rotation);
				break;

			case 3:

				addParty(rotation);

				break;

			case 4:

				addPartyMember(rotation);

				break;

			case 5:
				showAllKalpi(rotation);

				break;

			case 6:
				showAllCitizen(rotation);

				break;

			case 7:
				showAllParties(rotation);

				break;

			case 8:
				Scanner e = new Scanner(System.in);
				election(e, rotation);

				break;

			case 9:

				showTheResult(rotation);

				break;

			case 10:
				saveFile(rotation);
				exit = true;

			}

		} while (!exit);
		System.out.println(" Good luck! ");
	}

	public static ElectionRotation loadFile() throws FileNotFoundException, IOException, ClassNotFoundException{
		ObjectInputStream inFile = new ObjectInputStream(new FileInputStream("rotation.dat"));
			ElectionRotation rotation = (ElectionRotation)inFile.readObject();
			inFile.close();
			return rotation;
	}

	public static void saveFile(ElectionRotation rotation) throws FileNotFoundException, IOException {
		ObjectOutputStream outFile = 
				new ObjectOutputStream(new FileOutputStream("rotation.dat"));
				outFile.writeObject(rotation);
				outFile.close();
	}

	private static void showTheResult(ElectionRotation rotation) {
		rotation.showTheResult();
	}
	public static void election(Scanner e, ElectionRotation rotation) {
		try {
			rotation.election(e);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}
	public static void showAllParties(ElectionRotation rotation) {
		if (rotation.getparties().isEmpty()) {
			System.out.println("No parties to show");
			return;
		}
		System.out.println("The parties are: ");
		for (Party p : rotation.getparties()) {
			System.out.println(p);
		}
		System.out.println("----------------------------------------------------------------------------------------");
	}
	static void showAllCitizen(ElectionRotation rotation) {
		System.out.println("The citizens are: ");
		for (Kalpi kalpi : rotation.getKalpies()) {
			ArrayList<Citizen> arr = kalpi.getCitizens();
			for (Citizen c : arr) {
				System.out.println(c);
			}
		}
		System.out.println("----------------------------------------------------------------------------------------");
	}
	public static void showAllKalpi(ElectionRotation rotation) {
		if (rotation.getKalpies().isEmpty()) {
			System.out.println("No kalpies to show");
			return;
		}
		System.out.println("The Kalpies are: ");
		for (Kalpi k : rotation.getKalpies()) {
			System.out.println(k);
		}
		System.out.println("----------------------------------------------------------------------------------------");
	}
	private static void addPartyMember(ElectionRotation rotation) {
		Scanner c = new Scanner(System.in);
		System.out.println("Enter ID kalpi");
		int idKalpi = c.nextInt();
		if (rotation.searchKalpi(idKalpi) == false) {
			System.out.println("The Kalpi is not exist!");
		} else {
			Kalpi kalpi = rotation.getKalpiById(idKalpi);
			System.out.println("Enter citizen ID:");
			String id = c.next();
			Citizen citizen = kalpi.getCitizenById(id);
			if (citizen == null) {
				System.out.println("The citizen is not exist! ");
			} else {
				System.out.println("Enter name of the party");
				String partyName = c.next();
				if (rotation.searchParty(partyName) == false) {
					System.out.println("The party is not exist! ");
				} else {
					rotation.addPartyMember(partyName, citizen);
					System.out.println(
							"----------------------------------------------------------------------------------------");
				}
			}
		}
	}

	public static void addParty(ElectionRotation rotation) {

		Scanner c = new Scanner(System.in);
		System.out.println("Enter the name of the pary:");
		String name = c.nextLine();
		if (rotation.searchParty(name)) {
			System.out.println("The party is exist! ");
		} else {
			System.out.println("Select side(Left,Center,Right): ");
			String side = c.nextLine();
			if (!((side.contains("Left")) || (side.contains("Center")) || (side.contains("Right")))) {
				System.out.println(
						"You must choose from the list(Left,Center,Right) and notice a capital letter at the beginning! ");
			} else {
				Party p = new Party(name, side);
				rotation.addParty(p);
				System.out.println("The party added successfully!");
				System.out.println(
						"----------------------------------------------------------------------------------------");
			}
		}
	}
	public static void addCitizen(ElectionRotation rotation) {
		Scanner a = new Scanner(System.in);
		// Get the name of the citizen:
		System.out.println("Enter name:");
		String name = a.nextLine();
		System.out.println("Enter ID number:");
		String id = a.next();
		if(rotation.isCitizen(id) == true) {
			System.out.println("The citizen is exist! ");
			return;
		}
		else 
			System.out.println("Enter year of birth:");
		int yearOfBirth = a.nextInt();
		System.out.println("Enter your age:");
		int age = a.nextInt();
		if (age < 18) {
			System.out.println("The citizen is under 18! ");
			System.out.println(
					"----------------------------------------------------------------------------------------");
			return;
		} else
			System.out.println("Enter your id kalpi:");
		int idKalpi = a.nextInt();
		if (rotation.searchKalpi(idKalpi) == false) {
			System.out.println("The Kalpi is not Exist");
			return;
		}else 
			System.out.println("Are you inisolation:");
		boolean isolationStatus;
		char ch = a.next().charAt(0);
		if (ch == 'y')
			isolationStatus = true;
		else
			isolationStatus = false;
		boolean isProtected = false;
		if (isolationStatus) {
			System.out.println("Are you protected?");
			ch = a.next().charAt(0);
			if (ch == 'y')
				isProtected = true;
			else
				isProtected = false;
		}

		Citizen c;
		try {
			if(age <= 21 )
			{
				System.out.println("Do you have a weapon?");
				boolean isweapon;
				char cha = a.next().charAt(0);
				if (cha == 'y')
					isweapon = true;
				else
					isweapon = false;
				if(isolationStatus)
				{
					System.out.println("How many days are you sick?");
					int numSick = a.nextInt();
					c = new IdfCovidSoldier(name, id, yearOfBirth, age, idKalpi, isolationStatus, isProtected, isweapon,numSick);	
				}
				else	c = new Soldier(name, id, yearOfBirth, age, idKalpi, isolationStatus, isProtected, isweapon);	
			}
			else if(isolationStatus)
			{
				System.out.println("How many days are you sick?");
				int numSick = a.nextInt();
				c = new CovidCitizen(name, id, yearOfBirth, age, idKalpi, isolationStatus, isProtected, numSick);
			}
			else c = new Citizen(name, id, yearOfBirth, age, idKalpi, isolationStatus, isProtected);
			if(rotation.isTypeOfKalpi(c)) {
			rotation.addCitizen(c);
			System.out.println("The citizen added successfully!");
			System.out.println(
					"----------------------------------------------------------------------------------------");
			} else System.out.println("The Kalpi dosn't match! try again! " );
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(
					"----------------------------------------------------------------------------------------");

		}
	}


	public static void addKalpi(ElectionRotation rotation) {
		String r = "Regular";
		String co = "Covid";
		String ic = "I.D.F Covid";
		String i = "I.D.F";
		Scanner c = new Scanner(System.in);
		System.out.println("Enter location:");
		String location = c.nextLine();
		System.out.println("Which kind of Kalpi do you want to add(regular press 1 , covid press 2 , IDF press 3 , IDF covid press 4) : ");
		int kindOfKalpi = c.nextInt();
		Kalpi k = null;
		switch (kindOfKalpi) {
		case REGULAR:
			k = new Kalpi<Citizen>(location, r);
			break;
		case COVID:
			k = new Kalpi<CovidCitizen>(location, co);
			break;
		case IDF:
			k = new Kalpi<Soldier>(location, i);
			break;
		case IDFCOVID:
			k = new Kalpi<IdfCovidSoldier>(location, ic);
			break;
		default:
			System.out.println("Wrong input!You must choose 1-4! 2");
			System.out.println(
					"----------------------------------------------------------------------------------------");
			return;
		}
		rotation.addKalpi(k);
		System.out.println("The kalpi added successfully!");
		System.out.println("----------------------------------------------------------------------------------------");
	}
	public static void Initiate(ElectionRotation rotation) throws Exception {
		Kalpi k1 = new Kalpi<IdfCovidSoldier>("Bhd 1", "I.D.F Covid ");
		Kalpi k2 = new Kalpi<Citizen>("Tel-Aviv", "Regular " );
		Kalpi k3 = new Kalpi<CovidCitizen>("Zefat", "Covid ");
		Kalpi k4 = new Kalpi<Soldier>("Tzrefin", "I.D.F " );
		rotation.addKalpi(k1);
		rotation.addKalpi(k2);
		rotation.addKalpi(k3);
		rotation.addKalpi(k4);
		Citizen c1,c2,c3,c4,c5;
		Party p1 = new Party("licod", "Left");
		Party p2 = new Party("meretz", "Left");
		Party p3 = new Party("lavan", "Left");
		rotation.addParty(p1);
		rotation.addParty(p2);
		rotation.addParty(p3);
		try {
			c1 = new IdfCovidSoldier("shaked", "123456789", 1997, 24, 0, true, true, true, 4);
			c2 = new Citizen("moshe", "223456789", 1997, 26, 1, false, false);
			c3 = new CovidCitizen("gal", "323456789", 1996, 25, 2, false, false, 6);
			c4 = new Citizen("idan", "423456789", 1997, 24, 1, false, false);
			c5 = new Citizen("gali", "523456789", 1997, 24, 1, false, false);
			rotation.addCitizen(c1);
			rotation.addCitizen(c2);
			rotation.addCitizen(c3);
			rotation.addCitizen(c4);
			rotation.addCitizen(c5);
			rotation.addPartyMember(p1, c3);
			rotation.addPartyMember(p2, c2);
		} catch (IdException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}	
	}
	public static void Test(ElectionRotation rotation) {
		System.out.println( rotation.getKalpiById(0) == rotation.getKalpiById(1));
		System.out.println( rotation.getKalpiById(0) == rotation.getKalpiById(0));
		System.out.println( rotation.getparties().get(0) == rotation.getparties().get(1));
		System.out.println( rotation.getparties().get(0) == rotation.getparties().get(0));
	}
}
