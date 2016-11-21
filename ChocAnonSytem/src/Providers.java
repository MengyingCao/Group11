import java.util.Random;
import java.util.Scanner;

public class Providers extends Person{
protected static Scanner input = null;
// Constructor
	public Providers(){
		super();
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
		System.out.println("Name: " + NameGetter());
		System.out.println("ID: " + IDGetter());
		System.out.println("Address: " + AddressGetter());
		System.out.println("City: " + CityGetter());
		System.out.println("State: " + StateGetter());
		return;
	}
// RetrievePerson Checks the ID for matching members
	public boolean RetrievePerson(int IDEN_NUM){
		if(IDEN_NUM == IDGetter())
			return true;
		return false;
	}
/*
 * Below are the getters of the function
 * (non-Javadoc)
 * @see Person#IDGetter()
 */
	public int IDGetter(){
		return super.IDGetter();
	}
	
	public int ZipGetter(){
		return super.ZipGetter();
	}
	
	public String NameGetter(){
		return super.NameGetter();
	}
	
	public String AddressGetter(){
		return super.AddressGetter();
	}
	
	public String CityGetter(){
		return super.CityGetter();
	}
	
	public String StateGetter(){
		return super.StateGetter();
	}
//-----------END OF GETTERS----------------\\

}
