package model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Scanner;

import controller.IController;

public class Election implements Model{

	private ElectionRotation rotation;
	public final static int REGULAR = 1;
	public final static int COVID = 2;
	public final static int IDF = 3;
	public final static int IDFCOVID = 4;
	private IController listener;
	
	public Election()
	{
		init();
	}
	/**
	 * simple init method so the object isn't empty
	 */
	@Override
	public void init() {
		// TODO Auto-generated method stub
		if(rotation == null) {//prevents reInitializing of the rotation object
			rotation = new ElectionRotation();
			Kalpi k1 = new Kalpi<IdfCovidSoldier>("Bhd 1", "I.D.F Covid ");
			Kalpi k2 = new Kalpi<Citizen>("Tel-Aviv", "Regular " );
			Kalpi k3 = new Kalpi<CovidCitizen>("Zefat", "Covid ");
			Kalpi k4 = new Kalpi<Soldier>("Tzrefin", "I.D.F " );
			rotation.addKalpi(k1);
			rotation.addKalpi(k2);
			rotation.addKalpi(k3);
			rotation.addKalpi(k4);
			Citizen c1,c2,c3,c4,c5;
			Party p1 = new Party("licod", "left");
			Party p2 = new Party("meretz", "left");
			Party p3 = new Party("lavan", "left");
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
			} catch (IdException | IdKalpiException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}	
		}
	}

	/**
	 * adds kalpi to the list of kalpies
	 * @param location - the location of the kalpies
	 * @param kindOfKalpi - the type of the kalpi
	 */
	@Override
	public void AddKalpi(String location,int kindOfKalpi) {
		// TODO Auto-generated method stub
		String r = "Regular";
		String co = "Covid";
		String ic = "I.D.F Covid";
		String i = "I.D.F";
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
			listener.onError("Wrong input!\nYou must choose 1-4! 2");
		}
		rotation.addKalpi(k);
		listener.onSuccess("The kalpi at location: " + location + "\nwas added successfully!");
	}

	/**
	 * adds a citizen to list of citizens
	 * @param name - citizen name
	 * @param idNumber - citizen id
	 * @param birthYear - citizens birth year
	 * @param age - citizens age
	 * @param kalpiID - the kalpies id
	 * @param isIsolated - is the citizen isolated
	 * @param isProtected - is the citizen protected
	 * @param isArmed - is the citizen carries a weapon
	 * @param sickDaysNumber - number of sick days the citizen was ill
	 */
	@Override
	public void AddCitizen(String name,String idNumber,int birthYear,int age,int kalpiID,boolean isIsolated,boolean isProtected,boolean isArmed,int sickDaysNumber) {
		// TODO Auto-generated method stub
		if(rotation.isCitizen(idNumber))
			listener.onError("duplicated id\na citizen with this id: " + idNumber + "\nis already registered, enter a different id");
		else {
			if(age < 18)
				listener.onError("underage citizens are not allowed");
			else {
				if(!rotation.searchKalpi(kalpiID))
					listener.onError("a kalpi with this id = " + kalpiID + "\nwas not found\nit doesn't exist");
				else
				{
					Citizen c;
					try {
						if (age <= 21)
						{
							if(isIsolated)
								c = new IdfCovidSoldier(name, idNumber, birthYear, age, kalpiID, isIsolated, isProtected, isArmed, sickDaysNumber);
							else
								c = new Soldier(name, idNumber, birthYear, age, kalpiID, isIsolated, isProtected, isArmed);
						}
						else if(isIsolated)
						{
							c = new CovidCitizen(name, idNumber, birthYear, age, kalpiID, isIsolated, isProtected, sickDaysNumber);
						}
						else 
						{
							c = new Citizen(name, idNumber, birthYear, age, kalpiID, isIsolated, isProtected);
						}
						if(rotation.isTypeOfKalpi(c))
						{
							rotation.addCitizen(c);
							listener.onSuccess("The citizen was added successfully!");
						}
						else
							listener.onError("The Kalpi dosn't match!\ntry again!");
					}catch(Exception e)
					{
						e.printStackTrace();
					}
				}
			}
		}
	}

	/**
	 * adds party to the list of parties
	 * @param partyName - the name of the party to name
	 * @param side - the political side the party is associates with
	 */
	@Override
	public void AddParty(String partyName,String side) {
		// TODO Auto-generated method stub
		if(rotation.searchParty(partyName))
			listener.onError("The party exists!");
		else
		{
			if(!(side.contains("right") || side.contains("center") || side.contains("left")))
			{
				listener.onError("select left\nright or center only");
			}
			else
			{
				Party party = new Party(partyName, side);
				rotation.addParty(party);
				listener.onSuccess("The party: " + partyName + "\nwas added successfully!");
			}
		}
	}

	/**
	 * adds party member
	 * @param kalpiID - the kalpi id
	 * @param citizenID - the citizen id
	 * @param partyName - the name of the party
	 */
	@Override
	public void AddPartyMember(int kalpiID,String citizenID,String partyName) {
		// TODO Auto-generated method stub
		if(!rotation.searchKalpi(kalpiID))
			listener.onError("The kalpi with the id: " + kalpiID + " doesn't exists");
		else
		{
			Kalpi kalpi = rotation.getKalpiById(kalpiID);
			Citizen citizen = kalpi.getCitizenById(citizenID);
			if(citizen == null)
				listener.onError("The citizen doesn't exists (id: " + citizenID);
			else
			{
				if(!rotation.searchParty(partyName))
					listener.onError("The party + " + partyName + " doesn't exists");
				else
					{
						rotation.addPartyMember(partyName, citizen);
						listener.onSuccess("citizen (id: " + citizenID + " )\nwas added to the party: " + partyName);
					}
			}
		}
	}

	/**
	 * returns/fetches the kalpies
	 */

	@Override
	public String[] getKalpies() {
		// TODO Auto-generated method stub
		ArrayList<Kalpi> kalpies = rotation.getKalpies();
		if(kalpies == null || kalpies.isEmpty())
			return null;
		String[] kalpiesArray = new String[kalpies.size()];
		for(int i = 0;i<kalpies.size();i++)
		{
			kalpiesArray[i] = kalpies.get(i).toString();
		}
		listener.onShowKalpies(kalpies);
		return kalpiesArray;
	}

	/**
	 * gets the citizens
	 */
	@Override
	public void getCitizens() {
		// TODO Auto-generated method stub
		ArrayList<Kalpi> kalpies = rotation.getKalpies();
		String[][] citizens = new String[kalpies.size()][];
		ArrayList<Citizen> v = new ArrayList<Citizen>();
		for(int i = 0;i<kalpies.size();i++)
		{
			Kalpi kalpie = kalpies.get(i);
			ArrayList<Citizen> arr = kalpie.getCitizens();
			v.addAll(arr);
			
			for(int j = 0;j<arr.size();j++)
			{
				Citizen c = arr.get(j);
				listener.onPrint(c.toString());
				//citizens[i][j] = c.toString();
			}
		}
		listener.onShowCitizens(v);
	}

	/**
	 * fetches all the parties list
	 */
	@Override
	public void getParties() {//shows all parties
		// TODO Auto-generated method stub
		if (rotation.getparties().isEmpty()) {
			listener.onPrint("no parties registered");
			
		}
		ArrayList<Party> parties = rotation.getparties();
		listener.onShowParties(parties);
		for (int i = 0;i<parties.size();i++) {
			listener.onPrint(parties.get(i).toString());
			
		}
		
	}

	/**
	 * sets the listener for this class
	 * @param listener - the listener to set
	 */
	public void SetListener(IController listener)
	{
		this.listener = listener;
	}

	/**
	 * Displays the elections results
	 */
	@Override
	public void ShowElections() {
		// TODO Auto-generated method stub
		ArrayList<Kalpi> kalpies = rotation.getKalpies();
		listener.onShowElecResults(kalpies);
		ArrayList<String> partyList=null;
		ArrayList<Integer> allTheResult = new ArrayList <Integer>();
		for(Kalpi kalpi : kalpies)
		{
			partyList = kalpi.getParties();
			ArrayList<Integer> voteList = kalpi.getNumOfVotes();
			listener.onPrint("The results of the vote at the kalpi:  " + kalpi.getId());
			for (int i = 0; i < partyList.size(); i++) {
		    	if(allTheResult.size() <= i) {
		    		allTheResult.add(0);}
		    	int updatedValue =  allTheResult.get(i) + voteList.get(i) ;
		    	allTheResult.set(i,updatedValue);
		    	listener.onPrint(partyList.get(i)  + " " + voteList.get(i));
		    }
		}
		String[] stringToPrint = {"Summary of voting results at all the kalpis are:  ",partyList.toString(),allTheResult.toString()};
		listener.onPrint(stringToPrint);
	}
	

	@Override
	public void ElectionProcess() {
		// TODO Auto-generated method stub
		getParties();
	}
	/**
	 * fetches the list of kalpies
	 */
	@Override
	public void getKalpiesNoDisplay()
	{
		listener.onKalpiesNoDisplay(rotation.getKalpies());
	}

	/**
	 * adds one vote for a chosen party
	 * @param partyName - the name of the party
	 * @param citizenIndex - the index of the citizen in the kalpi
	 * @param kalpiIndex - the index of the kalpi
	 */
	@Override
	public void AddVote(String partyName,int citizenIndex,int kalpiIndex) {
		// TODO Auto-generated method stub
		
		ArrayList<Kalpi> kalpies = rotation.getKalpies();
		Kalpi kalpi = kalpies.get(kalpiIndex);
		kalpi.addVotes(partyName, 1);
		listener.onPrint("you voted for: " + partyName);
	}

	
	/**
	 * Loads the last save file
	 */
	@Override
	public void LoadFile() {
		// TODO Auto-generated method stub
		try {
			rotation = loadFile();
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		} 
	}
	
	private ElectionRotation loadFile() throws FileNotFoundException, IOException, ClassNotFoundException{
		ObjectInputStream inFile = new ObjectInputStream(new FileInputStream("rotation.dat"));
			ElectionRotation rotation = (ElectionRotation)inFile.readObject();
			inFile.close();
			return rotation;
	}
	
	private void saveFile(ElectionRotation rotation) throws FileNotFoundException, IOException {
		ObjectOutputStream outFile = 
				new ObjectOutputStream(new FileOutputStream("rotation.dat"));
				outFile.writeObject(rotation);
				outFile.close();
	}

	/**
	 * saves the current data to a file
	 */
	@Override
	public void SaveFile() {
		// TODO Auto-generated method stub
		try {
			saveFile(rotation);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
