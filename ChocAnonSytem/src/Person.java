import java.util.*;
// this is the abstract base class
public abstract class Person {
private Integer IDnum; // ID for member or provider
private Integer Zip; // the zip for member or provider
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
	
	public void setcity(String city){
		City = city;
		return;
	}
	
	public void setstate(String state){
		State = state;
		return;
	}
	
	public void setzip(Integer zip){
		Zip = zip;
		return;
	}
	
	public void setaddress(String address){
		Address = address;
		return;
	}
	
	public void setidnum(int idnum){
		IDnum = idnum;
		return;
	}
	
	public void setlocation(String address, String city, String state, Integer zip){
		SetAddress(address);
		SetCity(city);
		SetState(state);
		SetZip(zip);
		return;
	}
	
/* Below are the getters of the Abstract Base Class(ABC)
 */
	public Integer IDGetter(){
		return IDnum;
	}
	
	public Integer ZipGetter(){
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
	public abstract void SetID(Integer IDNUMBA);
	public abstract void SetZip(Integer ziponous);
	public abstract void SetName(String ToName);
	public abstract void SetCity(String chittycity);
	public abstract void SetAddress(String AddAddress);
	public abstract void SetState(String states);
	
	public abstract Integer GetID();
	public abstract Integer GetZip();
	public abstract String GetName();
	public abstract String GetCity();
	public abstract String GetAddress();
	public abstract String GetState();
	
	public abstract void SetName();
	public abstract void SetIDnum();
	public abstract void SetLocation();
	public abstract void DisplayInformation();
	public abstract boolean RetrievePerson(Integer IDEN_NUM);
}