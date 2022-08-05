package model;
import java.io.Serializable;

public class Citizen implements Serializable {

	private String name;
	private String id;
	private int yearOfBirth;
	private int age;
	private int idKalpi;
	private boolean isolationStatus; 
	private boolean isProtected;

	ElectionRotation rotation = new ElectionRotation();

	public Citizen(String name, String id, int yearOfBirth, int age, int idKalpi, boolean isolationStatus,
			boolean isProtected) throws IdException  {

		this.name = name;
		this.id = id;
		if(id.length()!= 9 ) 
			throw new IdException("Id is not 9 digits" );
		this.yearOfBirth = yearOfBirth;
		this.age = age;
		this.idKalpi = idKalpi;
		this.isolationStatus = isolationStatus;
		this.isProtected = isProtected;
	}

	public boolean isProtected() {
		return isProtected;
	}

	public void setProtected(boolean isProtected) {
		this.isProtected = isProtected;
	}

	public int getIdKalpi() {
		return idKalpi;
	}
	public static String getType() {
		return "Regular";
	}

	public String getName() {
		return name;
	}
	public int getAge() {
		return age;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean getIsProtected() {
		return isProtected;
	}
	public boolean getIsolationStatus() {
		return isolationStatus;
	}

	public void setIsolationStatus(boolean isolationStatus) {
		this.isolationStatus = isolationStatus;
	}

	public String getId() {
		return id;
	}

	public int getYearOfBrith() {
		return yearOfBirth;
	}

	@Override
	public String toString() {
		return "-" + "Name: " + getName() + " ID: " + getId() + " age: " + getAge()  +" Type Kalpi: Citizen" + " kalpi ID:  "+ getIdKalpi();
	}

	public boolean equals(String id) {
		return this.id.equals(id);
	}

	public String GetType() {
		return getType();
	}
@Override
public boolean equals(Object obj) {
	if(obj == null)return false;
	try {
	    if(getId()== ((Citizen)obj).getId())return true;
	    }catch(Exception e){}
return false;
} 
}
