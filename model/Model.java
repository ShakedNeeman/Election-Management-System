package model;

import java.util.ArrayList;

public interface Model {

	void init();
	void AddKalpi(String location,int kind);
	void AddCitizen(String name,String idNumber,int birthYear,int age,int kalpiID,boolean isIsolated,boolean isProtected,boolean isArmed,int sickDaysNumber);
	void AddParty(String partyName,String side);
	void AddPartyMember(int kalpiID,String citizenID,String partyName);
	void AddVote(String partyName,int citizenIndex,int kalpiIndex);
	void ElectionProcess();
	String[] getKalpies();
	void getCitizens();
	void getParties();
	void ShowElections();
	void getKalpiesNoDisplay();
	void LoadFile();
	void SaveFile();
}
