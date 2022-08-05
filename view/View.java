package view;

import java.util.ArrayList;

import model.Citizen;
import model.Kalpi;
import model.Party;

public interface View {

	void init1();
	void onPrint(String string);
	void onPrint(String...strings);
	
	/*******************************************/
	
	
	void onSuccessAdding(String addedStatus);
	void onFailedAdding(String failed);
	void onShowAllKalpies(ArrayList<Kalpi> kalpies);
	void onShowAllCitizens(ArrayList<Citizen> citizens);
	void onShowAllParties(ArrayList<Party> parties);
	void onShowElectionResult(ArrayList<Kalpi> kalpies);
	
	
	void onKalpiesNoDisplay(ArrayList<Kalpi> kalpies);
}
