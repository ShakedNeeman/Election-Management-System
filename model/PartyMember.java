package model;
import java.io.Serializable;

public class PartyMember extends Citizen implements Serializable{
	
	private String partyName;

	public PartyMember(String name, int age, String id, int yearOfBirth, boolean isolationStatus, int idKalpi,
			boolean isProtected , String partyName) throws IdException , IdKalpiException {
		super( name,  id,  yearOfBirth,  age,  idKalpi, isolationStatus, isProtected);
		this.partyName = partyName;
	}

	public String getPartyName() {
		return partyName;
	}
	@Override
	public boolean equals(Object obj) {
		if(getId() == ((PartyMember)obj).getId())
		return true;
		return false;
	}
	
}
