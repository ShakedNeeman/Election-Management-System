package model;
import java.io.Serializable;

public class Soldier extends Citizen implements Serializable {
public boolean isWeapon;
	public Soldier(String name, String id, int yearOfBirth, int age, int idKalpi, boolean isolationStatus,
			boolean isProtected , boolean isWeapon) throws IdException, IdKalpiException {
		super(name, id, yearOfBirth, age, idKalpi, isolationStatus, isProtected);
		this.isWeapon  = isWeapon; 
	}
public boolean isWeapon () {
	return this.isWeapon;
	
}
public static String getType() {
	return "I.D.F";
}
public String GetType() {
	return getType();
}
@Override
public String toString() {
 		return "-" + "Name: " + getName() + " ID: " + getId() + " age: " + getAge()  +" Type Kalpi: Soldier" + " kalpi ID: "+ getIdKalpi()  + " Status weapon: "+ isWeapon;
}

}