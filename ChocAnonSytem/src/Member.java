import java.util.Scanner;
import java.util.Random;
public class Member extends Person{
protected static Scanner input = null;

	public Member(){
		super();
	}
	
	public void SetName(){
		System.out.print("Member Name: ");
		input = new Scanner(System.in);
		super.setname(input.nextLine());
		return;
	}
	private int RandomID(){
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
		Integer TempZip = 0;
		
		System.out.print("Member Address: ");
		TempAddress = input.nextLine();
		SetAddress(TempAddress);
		System.out.print("Member City: ");
		TempCity = input.nextLine();
		SetCity(TempCity);
		System.out.print("Member State(E.G. NY): ");
		TempState = input.nextLine();
		SetState(TempState);
		System.out.print("Member Zip: ");
		TempZip = input.nextInt();
		SetZip(TempZip);
		
		return;
	}
	
	public void DisplayInformation(){
		System.out.println();
		System.out.println("MEMBER INFORMATION");
		System.out.println("Name: " + GetName());
		System.out.println("ID: " + GetID());
		System.out.println("Address:"); 
		System.out.println(GetAddress());
		System.out.println(GetCity() + ", " + GetState() + " " + GetZip());
		return;
	}
// RetrievePerson Checks the ID for matching members
	public boolean RetrievePerson(Integer IDEN_NUM){
		if(IDEN_NUM == IDGetter())
			return true;
		return false;
	}
/*
 * Below are the getters of the function
 * (non-Javadoc)
 * @see Person#IDGetter()
 */
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
//-----------END OF GETTERS----------------\\
	
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
	
// Used just for testing purposes	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("HELLO");
		Person Aperson;
		Aperson = new Member();
		Aperson.SetName();
		Aperson.SetIDnum();
		Aperson.SetLocation();
/*		
		Person AnotherPerson;
		AnotherPerson = new Providers();
		AnotherPerson.SetName();
		AnotherPerson.SetIDnum();
		AnotherPerson.SetLocation();
*/		
		Aperson.DisplayInformation();
//		AnotherPerson.DisplayInformation();
		Person justTesting;
		Aperson = null;
		//AnotherPerson = null;
		return;
	}

}
