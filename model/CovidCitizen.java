package model;
import java.io.Serializable;

public class CovidCitizen extends Citizen implements Serializable{
public int numSick;
	public CovidCitizen(String name, String id, int yearOfBirth, int age, int idKalpi, boolean isolationStatus,
			boolean isProtected , int numSick) throws IdException, IdKalpiException {
		super(name, id, yearOfBirth, age, idKalpi, isolationStatus, isProtected);
		this.numSick = numSick;
	}
	public static String getType() {
		return "Covid";
	}
	public String GetType() {
		return getType();
	}
	@Override
	public String toString() {
		return "-" + "Name: " + getName() + " ID: " + getId() + " age: " + getAge()  +" Type Kalpi: Covid Citizen" + " kalpi ID: "+ getIdKalpi() + " numSick: " + numSick + "Protected: "  + getIsProtected();
	}
	
	
}
