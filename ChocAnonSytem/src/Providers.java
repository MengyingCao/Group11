import java.util.Random;
import java.util.Scanner;

public class Providers extends Person{
protected static Scanner input = null;
// Constructor
	public Providers(){
		super();
	}
public Providers(Person person) {
		// TODO Auto-generated constructor stub
		super(person);
	}
	// set the name of the provider
	public void SetName(){
		System.out.print("Provider Name: ");
		input = new Scanner(System.in);
		super.setname(input.nextLine());
		return;
	}
// I have this as a random ID generator
	/*
	 * I wanted to have a way to have an ID
	 */
	private int RandomID(){ // random ID for the provide
		Random SumNum = new Random();
		int RandoNumbo = SumNum.nextInt(999999999) + 100000000;
		return RandoNumbo;
	}
	public void SetIDnum(){
		super.setidnum(RandomID());
		return;
	}
// This sets the Member location; prompts the user for 
// details about the member's Address, city, State and zip
	public void SetLocation(){ 
		input = new Scanner(System.in);
		String TempAddress = null;
		String TempCity = null;
		String TempState = null;
		int TempZip = 0;
// inputs for the location
		System.out.print("Provider Address: ");
		TempAddress = input.nextLine();
		System.out.print("Provider City: ");
		TempCity = input.nextLine();
		System.out.print("Provider State(E.G. NY): ");
		TempState = input.nextLine();
		System.out.print("Provider Zip: ");
		TempZip = input.nextInt();
// saving in the base class
		super.setlocation(TempAddress, TempCity, TempState, TempZip); // This functions sets the location
		
		return;
	}
// displays the information about the provider
	public void DisplayInformation(){
		System.out.println();
		System.out.println("PROVIDER INFORMATION");
		System.out.println("Name: " + GetName());
		System.out.println("ID: " + GetID());
		System.out.println("Address:"); 
		System.out.println(GetAddress());
		System.out.println(GetCity() + ", " + GetState() + " " + GetZip());
	}
// RetrievePerson Checks the ID for matching members
	public boolean RetrievePerson(Integer IDEN_NUM){
		if(IDEN_NUM == IDGetter())
			return true;
		return false;
	}

	public void SetAddress(String address){
		super.setaddress(address);
		return;
	}
	public void SetCity(String city){
		super.setcity(city);
		return;
	}
	
	public void SetState(String state){
		super.setstate(state);
		return;
	}
	
	public void SetName(String name){
		super.setname(name);
		return;
	}
	
	public void SetID(Integer IDnumba){
		super.setidnum(IDnumba);
		return;
	}
	
	public void SetZip(Integer Zippos){
		super.setzip(Zippos);
		return;
	}
/*
 * Below are the getters of the function
 * (non-Javadoc)
 * @see Person#IDGetter()
 */
//-----------GETTERS-----------------------\\
	public Integer GetID(){
		return super.IDGetter();
	}
	public Integer GetZip(){
		return super.ZipGetter();
	}
	public String GetName(){
		return super.NameGetter();
	}
	public String GetCity(){
		return super.CityGetter();
	}
	public String GetAddress(){
		return super.AddressGetter();
	}
	public String GetState(){
		return super.StateGetter();
	}
}