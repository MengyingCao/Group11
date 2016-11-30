
import java.util.Map;
import java.util.Stack;
import java.util.TreeMap;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class MemberDB {
	
	MemberDB()
	{
		this.memberMap = new TreeMap<Integer,Member>();
	}
	private Map<Integer, Member> memberMap;
	
	public Boolean printMap()
	{
		if(this.memberMap != null)
		{
			for(java.util.Map.Entry<Integer, Member> entry : memberMap.entrySet())
				System.out.println(entry.getValue().toString());
			//System.out.println(memberMap);
			return true;
		}
		return false;
	}

	@SuppressWarnings("unused")
	private Stack<Member> stackMembers()
	{
		Stack<Member> myStack = new Stack<Member>(); 
		for(Map.Entry<Integer, Member> entry : memberMap.entrySet())
		  		myStack.push(entry.getValue());
		
		System.out.println(myStack);
		return myStack;
	}
	
	public Boolean ReadFromFile(String filenamePath) throws FileNotFoundException
	{
		try {
			File file = new File(filenamePath);
			Scanner in = new Scanner(file);
			in.useDelimiter(";");
			while(in.hasNext())
			{
				String name = in.next();
				String ID = in.next();
				String address = in.next();
				String city = in.next();
				String state = in.next();
				String zip = in.next();
				Boolean suspended = in.nextBoolean();
				
				Member tempMember = new Member();
				tempMember.SetName(name);
				Integer Integerid = new Integer(ID);
				tempMember.SetID(Integerid);
				tempMember.SetAddress(address);
				tempMember.SetCity(city);
				tempMember.SetState(state);
				Integer Integerzip = new Integer(zip);
				tempMember.SetZip(Integerzip);
				tempMember.SetSuspendedStatus(suspended);
				addMember(tempMember);
			}
			in.close();
		} catch (Exception e) {
			return false;
		}

		return true;
	}
	public Boolean writeToFile(String filenamePath) throws IOException
	{
		PrintWriter writer = null;
		
		try {
			writer = new PrintWriter(filenamePath);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		}
		
		for(java.util.Map.Entry<Integer, Member> entry : memberMap.entrySet())
		{
			writer.write(entry.getValue().toString());
//			Essentialy,
//			writer.write
//			(
//			entry.getValue().getMemberName() + ";" +
//			entry.getValue().getMemberNumber() + ";" +
//			entry.getValue().getMemberAddress() + ";" +
//			entry.getValue().getMemberCity() + ";" + 
//			entry.getValue().getMemberState() + ";" +
//			entry.getValue().getMemberZip() + ";" + 
//			"\n"
//			);
		}

		writer.close();
		return true;
	}
	public Boolean addMember(Member toAdd)
	{
		if(toAdd != null)
		{
			this.memberMap.put(toAdd.GetID(), toAdd);
			return true;
		}
		return false;
	}
	public Boolean removeMember(Member toRem)
	{
		if(toRem != null)
		{
			Integer temp = toRem.GetID();
			this.memberMap.remove(temp);
			return true;
		}
		return false;
	}
	public Boolean updateMember(Member toUpdate, Member newMember)
	{
		if(this.memberMap.replace(toUpdate.GetID(), toUpdate, newMember))
			return true;
		else
			return false;
	}
	public Member retrieveMember(int memberNumber)
	{
		return this.memberMap.get(memberNumber);
	}
	
	public Boolean isValid(Integer memberNumber)
	{
		if(this.memberMap.containsKey(memberNumber))
			return true;
		else
			return false;
	}
	
	public Boolean isSuspended(Integer memberNumber)
	{
		if (this.memberMap.containsKey(memberNumber))
			return this.memberMap.get(memberNumber).GetSuspendedStatus();
		else
			return false;
	}
	
	private String convertMemberToStringThruMember(Member m) throws NullPointerException
	{
		String temp = "";
		try {
			 temp += "Name: " + m.GetName() + "\nID: " + m.GetID() + "\nAddress: " + m.GetAddress() + 
					 "\nCity: " + m.GetCity() + "\nState: " + m.GetState()  + "\nZip: " + m.GetZip() +
					 "\nSuspeneded: " + m.GetSuspendedStatus();
		} catch (Exception e) {
			System.out.println("Empty object in convertMemberToString");
		}

		return temp;
	}
	
	private String convertMemberToStringThruID(Integer idNumber) throws NullPointerException
	{
		String temp = "";
		Member tempMember = memberMap.get(idNumber);
		try {
			temp += "Name: " + tempMember.GetName() + "\nID: " + tempMember.GetID() + "\nAddress: " + tempMember.GetAddress() + 
					 "\nCity: " + tempMember.GetCity() + "\nState: " + tempMember.GetState()  + "\nZip: " + tempMember.GetZip() +
					 "\nSuspended: " + tempMember.GetSuspendedStatus();
		} catch (Exception e) {
			System.out.println("Empty object in convertMemberToString");
		}
		return temp;
	}
	

	public static void main(String[] args) throws IOException {
		MemberDB myDB = new MemberDB();
		try{
			myDB.ReadFromFile("members.txt");
		} catch(IOException e){
			e.printStackTrace();
		}
		//myDB.printMap();
		Member test = new Member("name", new Integer(01234567), "123 fake", "city", "state", new Integer(33333),true);
		
		/*
		System.out.println(test);
		String temp = myDB.convertMemberToStringThruMember(test);
		System.out.println("temp member has info: " + "\n" + temp);
		myDB.printMap();
		String temp2 = myDB.convertMemberToStringThruID(new Integer(01234567));
		System.out.println("temp2 member has info: " + "\n" + temp2);
		*/
		
		System.out.println("OG map: ");
		myDB.printMap();
		myDB.addMember(test);
		System.out.println("after adding test member to map: ");
		myDB.printMap();
		
//		System.out.println("after removing test member from map: ");
//		myDB.removeMember(test);
//		myDB.printMap();
		myDB.writeToFile("members.txt");
		//myDB.addMember(test);
		//System.out.println("after adding name: ");
		//myDB.printMap();
		//myDB.writeToFile("members.txt");
		//System.out.print(temp);
		// myDB.convertMemberToString(test);
//		try {
//			myDB.writeToFile("members.txt");
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		Member retr = new Member();
//		retr = myDB.retrieveMember(999999999);
//		System.out.println("\n\n" + retr.toString());
		//for(Map.Entry<Integer, Member> entry : myDB.memberMap.entrySet())
		     //System.out.println("Key: " + entry.getKey() + "\nValue: " + entry.getValue().toString());
		//System.out.println(Arrays.toString(myDB.stackMembers().toArray()));
		
		// validation testing
		System.out.println("Member 888888888's suspension status:" + myDB.isSuspended(888888888));
		System.out.println("Member 999999999's suspension status:" + myDB.isSuspended(999999999));
	}

}
