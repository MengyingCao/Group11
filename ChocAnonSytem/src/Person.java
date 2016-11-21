import java.util.*;
// this is the abstract base class
public abstract class Person {
private int IDnum; // ID for member or provider
private int Zip; // the zip for member or provider
private String name; // name of the member or provider
private String Address; 
private String City;
private String State;
protected static Scanner input = null;
// constructor
	public Person(){
		IDnum = 0;
		Zip = 0;
		name = null;
		Address = null;
		City = null;
		State = null;
	}
// clone constructor
	public Person(Person ToCopy){
		IDnum = ToCopy.IDnum;
		Zip = ToCopy.Zip;
		name = ToCopy.name;
		Address = ToCopy.Address;
		City = ToCopy.City;
		State = ToCopy.State;
	}
// sets the name
	public void setname(String Name){
		name = Name;
		return;
	}
	
	public void setlocation(String address, String city, String state, int zip){
		Address = address;
		City = city;
		State = state;
		Zip = zip;
		return;
	}
	
	public void setidnum(int idnum){
		IDnum = idnum;
		return;
	}
/* Below are the getters of the Abstract Base Class(ABC)
 */
	public int IDGetter(){
		return IDnum;
	}
	
	public int ZipGetter(){
		return Zip;
	}
	
	public String NameGetter(){
		return name;
	}
	
	public String AddressGetter(){
		return Address;
	}
	
	public String CityGetter(){
		return City;
	}
	
	public String StateGetter(){
		return State;
	}
/* Below are the abstract functions, the derived
 * classes can call upon to retrieve information
 * by these functions
 */
	public abstract void SetName();
	public abstract void SetIDnum();
	public abstract void SetLocation();
	public abstract void DisplayInformation();
	public abstract boolean RetrievePerson(int IDEN_NUM);
}