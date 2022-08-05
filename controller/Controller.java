package controller;

import view.View;

import java.util.ArrayList;

import model.Citizen;
import model.Election;
import model.ElectionRotation;
import model.Kalpi;
import model.Model;
import model.Party;

public class Controller implements IController{
	
	private Model model;
	private View view;
	
	public Controller()
	{
		
	}
	public Controller(Model model,View view)
	{
		this.model = model;
		this.view = view;
	}

	
	
	public void setModel(Model model) {
		this.model = model;
	}
	

	
	public void init()
	{
		view.init1();
	}
	
	
	
	@Override
	public void AddKalpi(String location,int kind)
	{
		model.AddKalpi(location,kind);
	}
	
	@Override
	public void AddCitizen(String name,String idNumber,int birthYear,int age,int kalpiID,boolean isIsolated,boolean isProtected,boolean isArmed,int sickDaysNumber)
	{
		model.AddCitizen(name,idNumber,birthYear,age,kalpiID,isIsolated,isProtected,isArmed,sickDaysNumber);
	}
	@Override
	public void AddParty(String partyName,String side)
	{
		model.AddParty(partyName,side);
	}
	@Override
	public void AddPartyMember(int kalpiID,String citizenID,String partyName)
	{
		model.AddPartyMember(kalpiID,citizenID,partyName);
	}
	
	@Override
	public void LoadFile()
	{
		model.LoadFile();
	}
	@Override
	public void SaveFile()
	{
		model.SaveFile();
	}
	@Override
	public void Elec() 
	{
		model.ElectionProcess();
	}
	//----------------------------------------------//

	
	
	
	public void ElectionPhase(String electPhase)
	{
		
	}
	
	@Override
	public void onError(String error) {
		// TODO Auto-generated method stub
		view.onFailedAdding(error);
	}
	@Override
	public void onSuccess(String success) {
		// TODO Auto-generated method stub
		view.onSuccessAdding(success);
	}
	
	@Override
	public String[] ShowAllKalpies() {
		// TODO Auto-generated method stub
		return model.getKalpies();
		
	}
	@Override
	public void ShowAllCitizens() {
		// TODO Auto-generated method stub
		 model.getCitizens();
	}
	@Override
	public void ShowAllParties() {
		// TODO Auto-generated method stub
		model.getParties();
	}
	@Override
	public void ShowElectionResult() {
		// TODO Auto-generated method stub
		model.ShowElections();
	}
	@Override
	public void onPrint(String string) {
		// TODO Auto-generated method stub
		view.onPrint(string);
	}
	@Override
	public void onPrint(String... strings) {
		// TODO Auto-generated method stub
		view.onPrint(strings);
	}
	@Override
	public void AddVote(String partyName,int citizenIndex,int kalpiIndex) {
		// TODO Auto-generated method stub
		model.AddVote(partyName,citizenIndex,kalpiIndex);
	}
	@Override
	public void onShowKalpies(ArrayList<Kalpi> kalpies) {
		// TODO Auto-generated method stub
		view.onShowAllKalpies(kalpies);
	}
	@Override
	public void onShowCitizens(ArrayList<Citizen> citizens) {
		// TODO Auto-generated method stub
		view.onShowAllCitizens(citizens);
	}
	@Override
	public void onShowParties(ArrayList<Party> parties) {
		// TODO Auto-generated method stub
		view.onShowAllParties(parties);
	}
	@Override
	public void onShowElecResults(ArrayList<Kalpi> kalpies) {
		// TODO Auto-generated method stub
		view.onShowElectionResult(kalpies);
	}
	
	@Override
	public void onKalpiesNoDisplay(ArrayList<Kalpi> kalpies) {
		// TODO Auto-generated method stub
		view.onKalpiesNoDisplay(kalpies);
	}
	@Override
	public void Kalpies() {
		// TODO Auto-generated method stub
		model.getKalpiesNoDisplay();
	}
	
	
}
