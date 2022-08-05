package model;
import java.io.Serializable;

public class IdfCovidSoldier extends Soldier implements Serializable{
	public int numSick;
	public IdfCovidSoldier(String name, String id, int yearOfBirth, int age, int idKalpi, boolean isolationStatus,
			boolean isProtected, boolean isWeapon, int numSick) throws IdException, IdKalpiException {
		super(name, id, yearOfBirth, age, idKalpi, isolationStatus, isProtected, isWeapon);
		this.numSick = numSick;
	}
	public boolean isWeapon () {
		return this.isWeapon;
	}
	public int getNumSick() {
		return numSick;
	}
	public String GetType() {
		return getType();
	}
	public static String getType() {
		return "I.D.F Covid";
	}
	@Override
	public String toString() {
		return "-" + "Name: " + getName() + " ID: " + getId() + " age: " + getAge()  +" Type Kalpi: Covid Soldier" + " kalpi ID: "+ getIdKalpi() + " numSick: " + numSick + " Protected: "  + getIsProtected() + " Status weapon: " + isWeapon;
	}

}
