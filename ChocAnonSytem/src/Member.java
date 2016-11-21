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
		int TempZip = 0;
		
		System.out.print("Member Address: ");
		TempAddress = input.nextLine();
		System.out.print("Member City: ");
		TempCity = input.nextLine();
		System.out.print("Member State(E.G. NY): ");
		TempState = input.nextLine();
		System.out.print("Member Zip: ");
		TempZip = input.nextInt();
		
		super.setlocation(TempAddress, TempCity, TempState, TempZip); // This functions sets the location
		
		return;
	}
	
	public void DisplayInformation(){
		System.out.println();
		System.out.println("MEMBER INFORMATION");
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
	
/* Used just for testing purposes	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("HELLO");
		Person Aperson;
		Aperson = new Member();
		Aperson.SetName();
		Aperson.SetIDnum();
		Aperson.SetLocation();
		
		Person AnotherPerson;
		AnotherPerson = new Providers();
		AnotherPerson.SetName();
		AnotherPerson.SetIDnum();
		AnotherPerson.SetLocation();
		
		Aperson.DisplayInformation();
		AnotherPerson.DisplayInformation();
		
		Aperson = null;
		AnotherPerson = null;
		return;
	}
*/
}
