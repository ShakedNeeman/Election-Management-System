package controller;

import java.util.ArrayList;

import model.Citizen;
import model.ElectionRotation;
import model.Kalpi;
import model.Party;

public interface IController {

	void AddKalpi(String location,int kind);
	void AddCitizen(String name,String idNumber,int birthYear,int age,int kalpiID,boolean isIsolated,boolean isProtected,boolean isArmed,int sickDaysNumber);
	void AddParty(String partyName,String side);
	void AddPartyMember(int kalpiID,String citizenID,String partyName);
	void AddVote(String partyName,int citizenIndex,int kalpiIndex);
	String[] ShowAllKalpies();
	void ShowAllCitizens();
	void ShowAllParties();
	void ShowElectionResult();
	void LoadFile();
	void SaveFile();
	void Elec();
	void Kalpies();
	void onError(String error);
	void onSuccess(String success);
	void onPrint(String...strings);
	void onPrint(String string);
	void onShowKalpies(ArrayList<Kalpi> kalpies);
	void onShowCitizens(ArrayList<Citizen> citizens);
	void onShowParties(ArrayList<Party> parties);
	void onShowElecResults(ArrayList<Kalpi> kalpies);

	void onKalpiesNoDisplay(ArrayList<Kalpi> kalpies);
}
